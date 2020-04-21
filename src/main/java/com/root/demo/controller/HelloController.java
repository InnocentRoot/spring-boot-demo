package com.root.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    String sayHello() {

        Logger l = LoggerFactory.getLogger(HelloController.class);
        l.info("HelloController called");

        return "hello there";
    }
}
