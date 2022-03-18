package com.rebot.micro;

import com.rebot.micro.userservice.dto.AuthRequestDto;
import com.rebot.micro.userservice.dto.ErrorDto;
import com.rebot.micro.userservice.exception.TooFastRequestsException;
import com.rebot.micro.userservice.service.AuthorizationService;
import com.rebot.micro.userservice.validator.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
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