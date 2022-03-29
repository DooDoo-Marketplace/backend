package space.rebot.micro.userservice.dto.user;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.wildfly.common.annotation.NotNull;

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
