package ro.unibuc.hello.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.StatisticEntity;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Currency;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;

@Controller
public class HelloWorldController {

    List<String> importantCurrencies = List.of("EUR", "USD", "CHF");

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Roxana") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(helloTemplate, name));
    }

    @GetMapping("/info")
    @ResponseBody
    public Greeting listAll(@RequestParam(name="title", required=false, defaultValue="Overview") String title) {
        InformationEntity entity = informationRepository.findByTitle(title);
        return new Greeting(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description));
    }

    @GetMapping("/statistic")
    @ResponseBody
    public Statistic showStatistic(@RequestParam(name="title", required=false, defaultValue="Today currency conversion") String title) {
        StatisticEntity entity = statisticRepository.findByTitle(title);
        return new Statistic(String.format("Statistic type: %s", title), String.format("Statistic description: %s", entity.description), entity.statistic);
    }

    @GetMapping("/test-api")
    @ResponseBody
    public Greeting testApi() {
        String url = "http://localhost:8080/hello-world?name=Roki";
        RestTemplate restTemplate = new RestTemplate();
        Greeting result = restTemplate.getForObject(url, Greeting.class);

        return result;
    }

    @GetMapping("/test-currency-api")
    @ResponseBody
    public Object testCurrencyApi() {
        String url = "https://open.er-api.com/v6/latest/USD";
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.getForObject(url, Object.class);

        return result;
    }

    @GetMapping("/get-data")
    @ResponseBody
    public Currency updateApiData() {
        String url = "https://open.er-api.com/v6/latest/RON";
        RestTemplate restTemplate = new RestTemplate();
        Currency result = restTemplate.getForObject(url, Currency.class);

        return result;
    }

    @GetMapping("/update")
    @ResponseBody
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

    @GetMapping("/weekly-update")
    @ResponseBody
    public String getWeeklyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
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
            return "Please select a currency for the weekly report.";
        }

        weeklyAvg.updateAndGet(v -> v / noOfDays.get());

        return String.format("%.4f", weeklyAvg.get().floatValue());
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
