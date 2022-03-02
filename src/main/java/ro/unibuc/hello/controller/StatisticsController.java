package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.StatisticEntity;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;

import java.util.concurrent.atomic.AtomicLong;

public class StatisticsController {

    @Autowired
    private StatisticRepository statisticRepository;

//    private static final String helloTemplate = "Hello, %s!";
//    private static final String informationTemplate = "%s : %s!";
//    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/statistic")
    @ResponseBody
    public Statistic showStatistic(@RequestParam(name="title", required=false, defaultValue="Today currency conversion") String title) {
        StatisticEntity entity = statisticRepository.findByTitle(title);
        return new Statistic(String.format("Statistic type: %s", title), String.format("Statistic description: %s", entity.description), entity.statistic);
    }
}
