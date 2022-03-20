package space.rebot.micro.userservice.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.wildfly.common.annotation.NotNull;

@JsonView
public class CodeRequestDto {
    @JsonView
    @NotNull
    private String phone;

    @JsonView
    @NotNull
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
