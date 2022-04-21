package space.rebot.micro.userservice.dto.auth;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.NonNull;

@JsonView
public class CodeRequestDto {
    @JsonView
    @NonNull
    private String phone;

    @JsonView
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
