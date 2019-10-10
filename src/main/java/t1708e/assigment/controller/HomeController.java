package t1708e.assigment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }
}
