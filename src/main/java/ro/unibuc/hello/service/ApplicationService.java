package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.StatisticEntity;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Currency;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ApplicationService {
    List<String> importantCurrencies = List.of("EUR", "USD", "CHF");

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";
    private final AtomicLong counter = new AtomicLong();

    public Greeting sayHello(String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    public Statistic showStatistic(String title) {
        StatisticEntity entity = statisticRepository.findByTitle(title);
        return new Statistic(String.format("Statistic type: %s", title), String.format("Statistic description: %s", entity.description), entity.statistic);
    }


    public Currency updateApiData() {
        String url = "https://open.er-api.com/v6/latest/RON";
        RestTemplate restTemplate = new RestTemplate();
        Currency result = restTemplate.getForObject(url, Currency.class);

        return result;
    }

    public String update() {
        String url = "http://localhost:8080/get-data";
        RestTemplate restTemplate = new RestTemplate();
        Currency apiResult = restTemplate.getForObject(url, Currency.class);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Double> rates = objectMapper.convertValue(apiResult.getRates(), Map.class);

        importantCurrencies.forEach((currency) -> {
            StatisticEntity statistic = new StatisticEntity("Conversion_" + dateFormat.format(date) + "_" + currency, "RON to " + currency, Float.parseFloat(String.format("%.2f", rates.get(currency).floatValue())));
            statisticRepository.insert(statistic);
        });

        return "";
    }


    public Statistic getWeeklyUpdate(String currency) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if (statisticRepository.findByTitle("Conversion_" + dateFormat.format(date) + "_EUR") == null) {
            String url = "http://localhost:8080/update";
            RestTemplate restTemplate = new RestTemplate();
            String updated = restTemplate.getForObject(url, String.class);
        }

        AtomicReference<Double> weeklyAvg = new AtomicReference<>(0.0);
        AtomicReference<Integer> noOfDays = new AtomicReference<>(0);

        if (importantCurrencies.contains(currency)) {

            List<String> outputInterval = getTimeInterval(7);

            outputInterval.forEach((day) -> {
                StatisticEntity statisticEntity = statisticRepository.findByTitle("Conversion_" + day + "_" + currency);

                if (statisticEntity == null) {
//
                } else {
                    weeklyAvg.updateAndGet(v -> v + statisticEntity.statistic);
                    noOfDays.updateAndGet(v -> v + 1);
                }
            });
        } else {
            return new Statistic("RON to " + currency, "Conversion not available", 0);
        }

        weeklyAvg.updateAndGet(v -> v / noOfDays.get());

        return new Statistic("RON to " + currency, "Weekly statistic", weeklyAvg.get().floatValue());
    }


    public Statistic getMontlyUpdate(String currency) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if (statisticRepository.findByTitle("Conversion_" + dateFormat.format(date) + "_EUR") == null) {
            String url = "http://localhost:8080/update";
            RestTemplate restTemplate = new RestTemplate();
            String updated = restTemplate.getForObject(url, String.class);
        }

        AtomicReference<Double> montlyAvg = new AtomicReference<>(0.0);
        AtomicReference<Integer> noOfDays = new AtomicReference<>(0);

        if (importantCurrencies.contains(currency)) {

            List<String> outputInterval = getTimeInterval(30);

            outputInterval.forEach((day) -> {
                StatisticEntity statisticEntity = statisticRepository.findByTitle("Conversion_" + day + "_" + currency);

                if (statisticEntity == null) {
//
                } else {
                    montlyAvg.updateAndGet(v -> v + statisticEntity.statistic);
                    noOfDays.updateAndGet(v -> v + 1);
                }
            });
        } else {
            return new Statistic("RON to " + currency, "Conversion not available", 0);
        }

        montlyAvg.updateAndGet(v -> v / noOfDays.get());

        return new Statistic("RON to " + currency, "Montly statistic", montlyAvg.get().floatValue());
    }


    public Statistic getDailyUpdate(String currency) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if (statisticRepository.findByTitle("Conversion_" + dateFormat.format(date) + "_EUR") == null) {
            String url = "http://localhost:8080/update";
            RestTemplate restTemplate = new RestTemplate();
            String updated = restTemplate.getForObject(url, String.class);
        }

        AtomicReference<Double> dailyAvg = new AtomicReference<>(0.0);
        AtomicReference<Integer> noOfDays = new AtomicReference<>(0);

        if (importantCurrencies.contains(currency)) {

            List<String> outputInterval = getTimeInterval(2);

            outputInterval.forEach((day) -> {
                StatisticEntity statisticEntity = statisticRepository.findByTitle("Conversion_" + day + "_" + currency);

                if (statisticEntity == null) {
//
                } else {
                    dailyAvg.updateAndGet(v -> v + statisticEntity.statistic);
                    noOfDays.updateAndGet(v -> v + 1);
                }
            });
        } else {
            return new Statistic("RON to " + currency, "Conversion not available", 0);
        }

        dailyAvg.updateAndGet(v -> v / noOfDays.get());

        return new Statistic("RON to " + currency, "Daily statistic", dailyAvg.get().floatValue());
    }

    private static List<String> getTimeInterval(Integer numberOfDays) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        List<String> outputInterval = new ArrayList<>();

        for (int aux = 0; aux <= numberOfDays; aux++) {
            calendar.add(Calendar.DATE, -aux);
            Date auxDate = calendar.getTime();

            outputInterval.add(dateFormat.format(auxDate));

            calendar.setTime(date);
        }

        System.out.println(outputInterval);
        return outputInterval;
    }
}
