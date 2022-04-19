package space.rebot.micro.userservice.dto.user;
import com.fasterxml.jackson.annotation.JsonView;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.wildfly.common.annotation.NotNull;

@JsonView
@Data
public class UserDto {

    public UserDto(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
    public UserDto(){}

    @JsonView
    String firstName;

    @JsonView
    String lastName;

    @JsonView
    String phone;

    @JsonView
    String email;





}
