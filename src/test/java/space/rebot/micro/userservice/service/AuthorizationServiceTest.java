package space.rebot.micro.userservice.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import space.rebot.micro.userservice.dto.auth.AuthResponseDto;
import space.rebot.micro.userservice.exception.*;
import space.rebot.micro.userservice.model.AuthRequest;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.AuthRequestsRepository;
import space.rebot.micro.userservice.repository.SessionsRepository;
import space.rebot.micro.userservice.repository.UsersRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest {


    @Mock
    private DateService dateService;

    @Mock
    private AuthRequestsRepository authRequestRepository;

    @Mock
    private SessionsRepository sessionRepository;

    @Mock
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private AuthorizationService authorizationService;


    @Test
    @DisplayName("Authorization by code should throw AuthRequestNotFoundException" +
            " when auth request doesn't exist")
    public void authorizeByCode_shouldThrowException_whenAuthRequestIsNull() {
        String phone = "+79215641751";
        int code = 1234;
        Mockito.when(authRequestRepository.getAuthRequestByPhone(phone))
                .thenReturn(null);
        assertThrows(AuthException.class, () ->
                authorizationService.authorizeByCode(phone, code));

    }

    @Test
    @DisplayName("Authorization by code should throw AuthRequestNotFoundException" +
            " when phone number is invalid")
    public void authorizeByCode_shouldThrowException_whenPhoneInvalid() {
        String phone = "+792156";
        int code = 1234;
        Mockito.when(authRequestRepository.getAuthRequestByPhone(phone))
                .thenReturn(null);
        assertThrows(AuthException.class, () ->
                authorizationService.authorizeByCode(phone, code));

    }

    @Test
    @DisplayName("Authorization by code should return token and registered = false")
    public void authorizeByCode_shouldReturnRegisteredFalse() throws AuthException, InvalidPhoneException {
        String phone = "+79215641751";
        int code = 1234;
        User user = Mockito.mock(User.class);
        AuthRequest authRequest = Mockito.mock(AuthRequest.class);

        Mockito.when(authRequest.getCode()).thenReturn(code);
        Mockito.when(authRequestRepository.getAuthRequestByPhone(phone)).thenReturn(authRequest);
        Mockito.when(usersRepository.getUserByPhone(phone)).thenReturn(user);

        AuthResponseDto authResponseDto = authorizationService.authorizeByCode(phone, code);

        assertFalse(authResponseDto.isRegistered());
    }

    @Test
    @DisplayName("Authorization by code should throw InvalidCodeException " +
            "when code is invalid")
    public void authorizeByCode_shouldThrowException_whenInvalidCode () {
        String phone = "+79215641751";
        int code = 1234;
        int invalidCode = 1233;
        int attempts = 3;
        AuthRequest authRequest = Mockito.mock(AuthRequest.class);

        Mockito.when(authRequestRepository.getAuthRequestByPhone(phone)).thenReturn(authRequest);
        Mockito.when(authRequest.getCode()).thenReturn(invalidCode);
        Mockito.when(authRequest.getAttempts()).thenReturn(attempts);


        assertThrows(AuthException.class, () ->
                authorizationService.authorizeByCode(phone, code));
    }

    @Test
    @DisplayName("Authorization by code should throw AttemptsLimitException " +
            "when attempts = 0")
    public void authorizeByCode_shouldThrowException_whenAttemptsIsOver () {
        String phone = "+79215641751";
        int code = 1234;
        int invalidCode = 1233;
        int attempts = 0;
        AuthRequest authRequest = Mockito.mock(AuthRequest.class);

        Mockito.when(authRequestRepository.getAuthRequestByPhone(phone)).thenReturn(authRequest);
        Mockito.when(authRequest.getCode()).thenReturn(invalidCode);
        Mockito.when(authRequest.getAttempts()).thenReturn(attempts);


        assertThrows(AuthException.class, () ->
                authorizationService.authorizeByCode(phone, code));
    }


}
