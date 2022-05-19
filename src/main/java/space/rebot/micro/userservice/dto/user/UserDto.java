package space.rebot.micro.userservice.dto.user;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@JsonView
@Data
public class UserDto {

    @JsonView
    private String firstName;

    @JsonView
    private String lastName;

    @JsonView
    private String phone;

    @JsonView
    private String email;
}
