package space.rebot.micro.userservice.dto.user;

import com.fasterxml.jackson.annotation.JsonView;
import org.wildfly.common.annotation.NotNull;

@JsonView
public class UserDto {

    @JsonView
    Long id;

    @JsonView
    String firstName;

    @JsonView
    String lastName;

    @JsonView
    @NotNull
    String phone;




}
