package com.rebot.micro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class TestController {

    @PostMapping(value = "hello", produces = "application/json")
    private ResponseEntity<?> login(@RequestParam(name = "text") String text) {

        return ResponseEntity.ok(
                "{" +
                        "text: " + text +
                        "}");


    }
}