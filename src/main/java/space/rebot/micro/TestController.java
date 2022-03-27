package space.rebot.micro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.rebot.micro.common.dto.MessageDto;

@RestController
@RequestMapping("api/v1/")
public class TestController {

    @PostMapping(value = "hello", produces = "application/json")
    private ResponseEntity<?> login(@RequestParam String text) {

        return ResponseEntity.ok(new MessageDto(text));


    }
}