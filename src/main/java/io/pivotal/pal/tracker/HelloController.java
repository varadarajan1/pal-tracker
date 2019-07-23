package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private String message;

    public HelloController(@Value("${welcome.message}")String message){
            this.message = message;
    }

    @RequestMapping("/")
    public String sayHello(){
        return message;
    }
}
