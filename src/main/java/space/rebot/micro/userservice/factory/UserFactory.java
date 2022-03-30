package space.rebot.micro.userservice.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import space.rebot.micro.common.factory.AbstractFactory;
import space.rebot.micro.common.mapper.AbstractMapper;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.exception.InvalidPhoneException;
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.validator.PhoneValidator;

import java.util.Date;


public class UserFactory extends AbstractFactory<User> implements AbstractMapper<User, UserDto> {

    public UserFactory(){
        super();
        this.entity = new User();
    }
    public void setPhone(String phone){
        setIfNotNull(entity::setPhone, phone);
    }
    public void setEmail(String email){
        setIfNotNull(entity::setEmail, email);
    }
    public void setLastName(String lastName){
        setIfNotNull(entity::setLastName, lastName);
    }
    public void setFirstName(String firstName){
        setIfNotNull(entity::setFirstName, firstName);
    }
    public void addRole(Role role) {
        entity.addRole(role);
    }
    public void setRegisteredAt(Date registeredAt){
        entity.setRegisteredAt(registeredAt);
    }
    @Override
    public User fromDto(UserDto dto) {
        setEmail(dto.getEmail());
        setPhone(dto.getPhone());
        setFirstName(dto.getFirstName());
        setLastName(dto.getLastName());
        return entity;
    }

    @Override
    public UserDto fromEntity(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        return dto;
    }
}
