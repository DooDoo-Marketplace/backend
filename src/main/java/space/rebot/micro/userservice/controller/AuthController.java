package space.rebot.micro.userservice.controller;

import space.rebot.micro.userservice.dto.auth.AuthRequestDto;
import space.rebot.micro.userservice.dto.auth.AuthResponseDto;
import space.rebot.micro.userservice.dto.auth.CodeRequestDto;
import space.rebot.micro.common.dto.MessageDto;
import space.rebot.micro.userservice.exception.*;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.repository.UsersRepository;
import space.rebot.micro.userservice.service.AuthorizationService;
import space.rebot.micro.userservice.validator.PhoneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private PhoneValidator phoneValidator;

    @PostMapping(value="login", produces="application/json;charset=UTF-8")
    private ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto){
        Map<Object, Object> model = new HashMap<>();
        if (!phoneValidator.validate(authRequestDto.getPhone(), true)) {
            model.put("message", "INVALID_PHONE");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        try{
            authorizationService.generateAuthRequest(authRequestDto.getPhone());
        }catch (TooFastRequestsException ex) {
            model.put("message", ex.getMessage());
            return new ResponseEntity<>(model, HttpStatus.FORBIDDEN);
        }
        model.put("message", "REQUEST_CREATED");
        return new ResponseEntity<>(model, HttpStatus.OK);




    }
    @PostMapping(value = "code", produces = "application/json;charset=UTF-8")
    private ResponseEntity<?> code (@RequestBody CodeRequestDto codeRequestDto){
        Map<Object, Object> model = new HashMap<>();
        try {
            AuthResponseDto responseDto = authorizationService.authorizeByCode(codeRequestDto.getPhone(), codeRequestDto.getCode());
            model.put("response", responseDto);
        } catch (AuthException e) {
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, e.getStatus());
        } catch (InvalidPhoneException e){
            model.put("message", e.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model,HttpStatus.OK);

    }

    @PostMapping(value = "logout", produces = "application/json;charset=UTF-8")
    private ResponseEntity<?> logout (@RequestParam(name = "all") Boolean closeAll){
       Session session = (Session)(this.context.getAttribute(Session.SESSION));
        if (closeAll){
            this.authorizationService.closeAllSessions(session);
        }
        else{
            this.authorizationService.closeSession(session);
        }
        return new ResponseEntity<>(new MessageDto("LOGOUT"), HttpStatus.OK);
    }
}
