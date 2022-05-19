package space.rebot.micro.userservice.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRequestDto {
    @NonNull
    private String phone;

    private int code;
}
