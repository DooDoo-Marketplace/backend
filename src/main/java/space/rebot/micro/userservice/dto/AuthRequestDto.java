package space.rebot.micro.userservice.dto;

import org.wildfly.common.annotation.NotNull;

public class AuthRequestDto {
    @NotNull
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
