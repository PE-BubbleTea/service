package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.dto.Currency;
import ro.unibuc.hello.dto.Greeting;
import ro.unibuc.hello.dto.Statistic;
import ro.unibuc.hello.service.ApplicationService;

@Controller
public class HelloWorldController {

    @Autowired
    private ApplicationService applicationService;

//    @GetMapping("/hello-world")
//    @ResponseBody
//    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Roxana") String name) {
//
//        return applicationService.sayHello(name);
//    }

    @GetMapping("/statistic")
    @ResponseBody
    public Statistic showStatistic(@RequestParam(name="title", required=false, defaultValue="Today currency conversion") String title) {
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
    public Statistic getWeeklyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        return applicationService.getWeeklyUpdate(currency);
    }

    @GetMapping("/montly-update")
    @ResponseBody
    public Statistic getMontlyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        return applicationService.getMontlyUpdate(currency);
    }


    @GetMapping("/daily-update")
    @ResponseBody
    public Statistic getDailyUpdate(@RequestParam(name="currency", required=false, defaultValue="EUR") String currency) {
        return applicationService.getDailyUpdate(currency);
    }
}
