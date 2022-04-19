package space.rebot.micro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.userservice.dto.user.UserDto;
import space.rebot.micro.userservice.exception.AuthRequestNotFoundException;
import space.rebot.micro.userservice.exception.InvalidPhoneException;
import space.rebot.micro.userservice.exception.TooFastRequestsException;
import space.rebot.micro.userservice.factory.UserFactory;
import space.rebot.micro.userservice.model.AuthRequest;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.AuthRequestsRepository;
import space.rebot.micro.userservice.repository.RolesRepository;
import space.rebot.micro.userservice.repository.SessionsRepository;
import space.rebot.micro.userservice.repository.UsersRepository;
import space.rebot.micro.userservice.service.AuthorizationService;
import space.rebot.micro.userservice.service.DateService;
import space.rebot.micro.userservice.service.SmsService;
import space.rebot.micro.userservice.service.UsersService;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest{
    UsersService usersService;

    AuthorizationService authorizationService;
    @Mock
    UsersRepository usersRepository;

    @Mock
    SessionsRepository sessionsRepository;

    @Mock
    AuthRequestsRepository authRequestsRepository;

    @Mock
    RolesRepository rolesRepository;

    @BeforeEach
    void init(){
        usersService = new UsersService(usersRepository, rolesRepository, new DateService());
        authorizationService = new AuthorizationService(
                sessionsRepository,
                authRequestsRepository,
                new DateService(),
                usersService,
                usersRepository,
                new SmsService()
        );

    }
    @Test
    void testAuthCycle() throws Exception{
        String goodPhone = "+79123435665";
        String badPhone = "123123";
        String nonAuthRequestPhone = "+79123435111";
        Mockito.when(authRequestsRepository.getAuthRequestByPhone(goodPhone)).thenReturn(null);
        Mockito.when(authRequestsRepository.getAuthRequestByPhone(nonAuthRequestPhone)).thenReturn(null);

        try {
            authorizationService.generateAuthRequest(badPhone);
        }
        catch (Exception ex){
            Assertions.assertEquals(ex.getClass(), InvalidPhoneException.class);
        }


        try {
            authorizationService.authorizeByCode(badPhone, 1234);
        }
        catch (Exception ex){
            Assertions.assertEquals(ex.getClass(), InvalidPhoneException.class);
        }
        try {
            authorizationService.authorizeByCode(nonAuthRequestPhone, 1234);
        }
        catch (Exception ex){
            Assertions.assertEquals(ex.getClass(), AuthRequestNotFoundException.class);
        }

        authorizationService.generateAuthRequest(goodPhone);
        AuthRequest authRequest = new AuthRequest();
        authRequest.setCode(1234);
        authRequest.setPhone(goodPhone);
        authRequest.setAttempts(3);
        Mockito.when(authRequestsRepository.getAuthRequestByPhone(goodPhone)).thenReturn(authRequest);
        authorizationService.authorizeByCode(goodPhone, 1234);



    }

    @Test
    void testUsersParsing(){
        String phone = "+79234654556";
        String firstName = "Domenic";
        String lastName = "Toretto";
        String email = "unittest@spring.space";

        UserFactory factory = new UserFactory();
        factory.setPhone(phone);
        factory.setFirstName(firstName);
        factory.setLastName(lastName);
        factory.setEmail(email);
        User user = factory.get();

        UserDto excepted = new UserDto(firstName, lastName, phone, email);
        UserDto real = usersService.getUserDto(user);

        Assertions.assertEquals(real.getFirstName(), excepted.getFirstName());
        Assertions.assertEquals(real.getLastName(), excepted.getLastName());
        Assertions.assertEquals(real.getEmail(), excepted.getEmail());
        Assertions.assertEquals(real.getPhone(), excepted.getPhone());

    }
}
