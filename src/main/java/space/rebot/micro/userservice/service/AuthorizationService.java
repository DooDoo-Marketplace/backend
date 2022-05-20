package space.rebot.micro.userservice.service;

import org.springframework.http.HttpStatus;
import space.rebot.micro.config.RoleConfig;
import space.rebot.micro.userservice.dto.auth.AuthResponseDto;
import space.rebot.micro.userservice.exception.*;
import space.rebot.micro.userservice.model.AuthRequest;
import space.rebot.micro.userservice.model.Role;
import space.rebot.micro.userservice.model.Session;
import space.rebot.micro.userservice.model.User;
import space.rebot.micro.userservice.repository.AuthRequestsRepository;
import space.rebot.micro.userservice.repository.RolesRepository;
import space.rebot.micro.userservice.repository.SessionsRepository;
import space.rebot.micro.userservice.repository.UsersRepository;
import space.rebot.micro.userservice.utils.DateUtils;
import space.rebot.micro.userservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthorizationService {

    @Autowired
    private SessionsRepository sessionRepository;

    @Autowired
    private AuthRequestsRepository authRequestRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersRepository usersRepository;

    @Resource(name = "smsService")
    private SmsService smsService;


    public void generateAuthRequest(String phone) throws TooFastRequestsException {
        AuthRequest authRequest = authRequestRepository.getAuthRequestByPhone(phone);
        Date now = dateService.utcNow();
        if (authRequest != null) {
            if (DateUtils.between(now, authRequest.getCreatedAt()) < 3 * 60) {
                throw new TooFastRequestsException("TOO_FAST_RESPONSES");
            }
            authRequestRepository.delete(authRequest);
        }
        int code = 1234;
        smsService.sendCode(code);
        AuthRequest request = new AuthRequest();
        request.setCode(code);
        request.setCreatedAt(now);
        request.setPhone(phone);
        request.setAttempts(3);
        authRequestRepository.save(request);
    }

    public AuthResponseDto authorizeByCode(String phone, int code)
            throws InvalidPhoneException, AuthException {
        AuthRequest authRequest = authRequestRepository.getAuthRequestByPhone(phone);
        if (authRequest == null) {
            throw new AuthException("AUTH_REQUEST_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }
        Date now = dateService.utcNow();
        if (code != authRequest.getCode()) {
            authRequest.setAttempts(authRequest.getAttempts() - 1);
            authRequestRepository.setAttemptsById(authRequest.getId(), authRequest.getAttempts());

            if (authRequest.getAttempts() <= 0) {
                authRequestRepository.delete(authRequest);
                throw new AuthException("ATTEMPTS_LIMIT_REACHED", HttpStatus.TOO_MANY_REQUESTS);
            } else throw new AuthException("INVALID_CODE", HttpStatus.FORBIDDEN);

        }
        authRequestRepository.delete(authRequest);
        User user = usersRepository.getUserByPhone(phone);
        boolean registered = false;
        if (user == null) {
            registered = true;
            user = usersService.generateDefault(phone);
        }
        Session session = new Session();
        session.setExpired(false);
        session.setCreatedAt(now);
        String token = TokenUtils.generateToken(user);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);
        return new AuthResponseDto(token, registered);

    }

    public void closeSession(Session session) {
        this.sessionRepository.setExpirationById(session.getId());
    }

    public void closeAllSessions(Session session) {
        this.sessionRepository.setExpirationByUser(session.getUser().getId());
    }

}