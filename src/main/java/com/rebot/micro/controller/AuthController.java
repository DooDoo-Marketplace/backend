package com.rebot.micro.controller;

import com.rebot.micro.dto.AuthRequestDto;
import com.rebot.micro.dto.AuthResponseDto;
import com.rebot.micro.dto.CodeRequestDto;
import com.rebot.micro.dto.ErrorDto;
import com.rebot.micro.exception.AttemptsLimitException;
import com.rebot.micro.exception.AuthRequestNotFoundException;
import com.rebot.micro.exception.InvalidCodeException;
import com.rebot.micro.exception.TooFastRequestsException;
import com.rebot.micro.service.AuthorizationService;
import com.rebot.micro.service.DateService;
import com.rebot.micro.service.SmsService;
import com.rebot.micro.validator.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    PhoneValidator phoneValidator;
    public AuthController(){
        this.phoneValidator = new PhoneValidator();
    }
    @PostMapping(value="login", produces="application/json")
    private ResponseEntity<?> login(@NonNull @RequestBody AuthRequestDto authRequestDto){
        if (!phoneValidator.validate(authRequestDto.getPhone())) {
            ErrorDto error = new ErrorDto("INVALID_PHONE");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try{
            authorizationService.generateAuthRequest(authRequestDto.getPhone());
        }catch (TooFastRequestsException ex) {
            ErrorDto error = new ErrorDto("TOO_FAST_RESPONSES");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(new ErrorDto("REQUEST_CREATED"));




    }
    @PostMapping(value = "code", produces = "application/json")
    private ResponseEntity<?> code (@NonNull @RequestBody CodeRequestDto codeRequestDto){
        try {
            AuthResponseDto responseDto = authorizationService.authorizeByCode(codeRequestDto.getPhone(), codeRequestDto.getCode());
            return ResponseEntity.ok(responseDto);
        }
        catch (AuthRequestNotFoundException ex){
            ErrorDto error = new ErrorDto("AUTH_REQUEST_NOT_FOUND");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        catch (InvalidCodeException ex){
            ErrorDto error = new ErrorDto("INVALID_CODE");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        catch (AttemptsLimitException ex){
            ErrorDto error = new ErrorDto("ATTEMPTS_LIMIT_REACHED");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


    }
}
