package space.rebot.micro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.orderservice.dto.GroupResponseDTO;
import space.rebot.micro.orderservice.service.GroupService;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.exception.InvalidUserDtoException;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UsersController {

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private UsersService usersService;

    @Autowired
    private GroupService groupService;

    @GetMapping(value="", produces="application/json;charset=UTF-8")
    private ResponseEntity<?> getUser(){
        Session session = (Session) context.getAttribute(Session.SESSION);
        User user = session.getUser();
        return new ResponseEntity<>(usersService.getUserDto(user), HttpStatus.OK);

    }
    @PutMapping(value="", produces="application/json;charset=UTF-8")
    private ResponseEntity<?> updateUser(@NonNull @RequestBody UserDto userDto){
        Map<Object, Object> model = new HashMap<>();
        Session session = (Session) context.getAttribute(Session.SESSION);
        User user = session.getUser();
        try {
            usersService.editFromDto(user, userDto);
            model.put("user", usersService.getUserDto(user));
        } catch (InvalidUserDtoException ex){
            model.put("message", ex.getMessage());
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value="groups", produces="application/json;charset=UTF-8")
    public ResponseEntity<?> getUserGroups() {
        Map<Object, Object> model = new HashMap<>();
        Session session = (Session) context.getAttribute(Session.SESSION);
        User user = session.getUser();
        List<GroupResponseDTO> groups = groupService.getUserGroups(user);
        model.put("groups", groups);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

}
