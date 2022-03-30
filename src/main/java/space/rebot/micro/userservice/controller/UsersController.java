package space.rebot.micro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.common.dto.MessageDto;
import space.rebot.micro.userservice.dto.auth.AuthRequestDto;
import space.rebot.micro.userservice.dto.auth.AuthResponseDto;
import space.rebot.micro.userservice.dto.auth.CodeRequestDto;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.exception.*;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.AuthorizationService;
import space.rebot.micro.userservice.service.UsersService;
import space.rebot.micro.userservice.validator.PhoneValidator;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/")
public class UsersController {

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private UsersService usersService;

    @GetMapping(value="user", produces="application/json")
    private ResponseEntity<?> getUser(){
        Session session = (Session) context.getAttribute(Session.SESSION);
        User user = session.getUser();
        return new ResponseEntity<>(usersService.getUserDto(user), HttpStatus.OK);

    }
    @PutMapping(value="user", produces="application/json")
    private ResponseEntity<?> updateUser(@NonNull @RequestBody UserDto userDto){
        Session session = (Session) context.getAttribute(Session.SESSION);
        User user = session.getUser();
        try {
            usersService.editFromDto(user, userDto);
        }
        catch (InvalidUserDtoException ex){
            MessageDto error = new MessageDto("INVALID_FIELDS");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(usersService.getUserDto(user), HttpStatus.OK);
    }

}