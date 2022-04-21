package space.rebot.micro.userservice.dto.auth;

import lombok.NonNull;

public class AuthRequestDto {
    @NonNull
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
