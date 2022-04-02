package space.rebot.micro.userservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.exception.InvalidPhoneException;
import space.rebot.micro.userservice.exception.InvalidUserDtoException;
import space.rebot.micro.userservice.factory.UserFactory;
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.RolesRepository;
import space.rebot.micro.userservice.repository.UsersRepository;
import space.rebot.micro.userservice.validator.PhoneValidator;
import space.rebot.micro.userservice.validator.UserDtoValidator;


@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final DateService dateService;

    public User generateDefault(String phone) throws InvalidPhoneException {
        UserFactory factory = new UserFactory();
        PhoneValidator validator = new PhoneValidator();
        if (!validator.validate(phone, false)) throw new InvalidPhoneException();
        Role role = rolesRepository.getRoleByName(RoleConfig.ROLE_USER.toString());
        factory.setPhone(phone);
        factory.addRole(role);
        factory.setRegisteredAt(dateService.utcNow());
        User user = factory.get();
        usersRepository.save(user);
        return user;

    }

    public void editFromDto(User user, UserDto userDto) throws InvalidUserDtoException {
        UserFactory factory = new UserFactory();
        UserDtoValidator validator = new UserDtoValidator();
        if (!validator.validate(userDto, true)) throw new InvalidUserDtoException();
        factory.load(user);
        User updated = factory.fromDto(userDto);
        usersRepository.save(updated);
    }

    public UserDto getUserDto(User user) {
        UserFactory factory = new UserFactory();
        return factory.fromEntity(user);
    }
}
