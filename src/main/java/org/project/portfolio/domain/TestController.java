package org.project.portfolio.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String test() {
        System.out.println("Hello World");
        return "Hello World";
    }
}
