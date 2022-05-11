package ro.unibuc.hello.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Currency;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;
import ro.unibuc.hello.service.ApplicationService;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HelloWorldController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private MeterRegistry metricsRegistry;
//    @GetMapping("/hello-world")
//    @ResponseBody
//    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Roxana") String name) {
//
//        return applicationService.sayHello(name);
//    }
    public final AtomicLong counter = new AtomicLong();
    public final AtomicLong counter_weekly = new AtomicLong();
    public final AtomicLong counter_daily = new AtomicLong();
    public final AtomicLong counter_monthly = new AtomicLong();

    @GetMapping("/statistic")
    @ResponseBody
    @Timed(value = "hello.statistic.time", description = "Time taken to return statistic")
    @Counted(value = "hello.statistic.count", description = "Times statistic was returned")
    public Statistic showStatistic(@RequestParam(name="title", required=false, defaultValue="Today currency conversion") String title) {
        metricsRegistry.counter("my_non_aop_metric", "endpoint", "hello").increment(counter.incrementAndGet());
        return applicationService.showStatistic(title);
    }

//    @GetMapping("/test-api")
//    @ResponseBody
//    public Greeting testApi() {
//        String url = "http://localhost:8080/hello-world?name=Roki";
//        RestTemplate restTemplate = new RestTemplate();
//        Greeting result = restTemplate.getForObject(url, Greeting.class);
//
//        return result;
//    }
//
//    @GetMapping("/test-currency-api")
//    @ResponseBody
//    public Object testCurrencyApi() {
//        String url = "https://open.er-api.com/v6/latest/USD";
//        RestTemplate restTemplate = new RestTemplate();
//        Object result = restTemplate.getForObject(url, Object.class);
//
//        return result;
//    }

    @GetMapping("/get-data")
    @ResponseBody
    public Currency updateApiData() {
        return applicationService.updateApiData();
    }

    @GetMapping("/update")
    @ResponseBody
    public String update() {
        return applicationService.update();
    }

    @GetMapping("/weekly-update")
    @ResponseBody
    @Timed(value = "hello.weekly.update.time", description = "Time taken to return weekly update")
    @Counted(value = "hello.weekly.update.count", description = "Times weekly update was returned")
    public Statistic getWeeklyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        metricsRegistry.counter("my_weekly_update_total_requests_metric", "endpoint", "hello").increment(counter_weekly.incrementAndGet());
        return applicationService.getWeeklyUpdate(currency);
    }

    @GetMapping("/monthly-update")
    @ResponseBody
    @Timed(value = "hello.monthly.update.time", description = "Time taken to return monthly update")
    @Counted(value = "hello.monthly.update.count", description = "Times monthly update was returned")
    public Statistic getMonthlyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        metricsRegistry.counter("my_monthly_update_total_requests_metric", "endpoint", "hello").increment(counter_monthly.incrementAndGet());
        return applicationService.getMontlyUpdate(currency);
    }

    @GetMapping("/daily-update")
    @ResponseBody
    @Timed(value = "hello.daily.update.time", description = "Time taken to return daily update")
    @Counted(value = "hello.daily.update.count", description = "Times daily update was returned")
    public Statistic getDailyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        metricsRegistry.counter("my_daily_update_total_requests_metric", "endpoint", "hello").increment(counter_daily.incrementAndGet());
        return applicationService.getDailyUpdate(currency);
    }
}
