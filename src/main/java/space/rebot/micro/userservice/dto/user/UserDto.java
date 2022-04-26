package space.rebot.micro.userservice.dto.user;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@JsonView
@Data
public class UserDto {

    @JsonView
    String firstName;

    @JsonView
    String lastName;

    @JsonView
    String phone;

    @JsonView
    String email;





}
