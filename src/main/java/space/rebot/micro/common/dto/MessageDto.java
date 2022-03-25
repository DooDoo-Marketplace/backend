package space.rebot.micro.common.dto;

import com.fasterxml.jackson.annotation.JsonView;

@JsonView
public class MessageDto {

    @JsonView
    String message;
    public MessageDto(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
