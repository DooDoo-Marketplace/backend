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

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    private HttpServletRequest context;


    @PostMapping(value="login", produces="application/json")
    private ResponseEntity<?> login(@NonNull @RequestBody AuthRequestDto authRequestDto){

        try{
            authorizationService.generateAuthRequest(authRequestDto.getPhone());
        }catch (TooFastRequestsException ex) {
            MessageDto error = new MessageDto("TOO_FAST_RESPONSES");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        catch (InvalidPhoneException ex){
            MessageDto error = new MessageDto("INVALID_PHONE");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageDto("REQUEST_CREATED"));




    }
    @PostMapping(value = "code", produces = "application/json")
    private ResponseEntity<?> code (@NonNull @RequestBody CodeRequestDto codeRequestDto){
        try {
            AuthResponseDto responseDto = authorizationService.authorizeByCode(codeRequestDto.getPhone(), codeRequestDto.getCode());
            return ResponseEntity.ok(responseDto);
        }

        catch (AuthRequestNotFoundException ex){
            MessageDto error = new MessageDto("AUTH_REQUEST_NOT_FOUND");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        catch (InvalidCodeException ex){
            MessageDto error = new MessageDto("INVALID_CODE");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
        catch (AttemptsLimitException ex){
            MessageDto error = new MessageDto("ATTEMPTS_LIMIT_REACHED");
            return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);
        }
        catch (InvalidPhoneException ex){
            MessageDto error = new MessageDto("INVALID_PHONE");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "logout", produces = "application/json")
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
