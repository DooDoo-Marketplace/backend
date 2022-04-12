package space.rebot.micro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.userservice.repository.AuthRequestsRepository;
import space.rebot.micro.userservice.repository.RolesRepository;
import space.rebot.micro.userservice.repository.SessionsRepository;
import space.rebot.micro.userservice.repository.UsersRepository;
import space.rebot.micro.userservice.service.AuthorizationService;
import space.rebot.micro.userservice.service.DateService;
import space.rebot.micro.userservice.service.SmsService;
import space.rebot.micro.userservice.service.UsersService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
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
    void testAuthCycle(){


    }
}
