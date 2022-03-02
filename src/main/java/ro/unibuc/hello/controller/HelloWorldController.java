package ro.unibuc.hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.StatisticEntity;
import ro.unibuc.hello.data.StatisticRepository;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;

@Controller
public class HelloWorldController {

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
}
