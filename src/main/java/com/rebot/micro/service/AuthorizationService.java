package com.rebot.micro.service;

import com.rebot.micro.dto.AuthResponseDto;
import com.rebot.micro.exception.AttemptsLimitException;
import com.rebot.micro.exception.AuthRequestNotFoundException;
import com.rebot.micro.exception.InvalidCodeException;
import com.rebot.micro.exception.TooFastRequestsException;
import com.rebot.micro.model.AuthRequest;
import com.rebot.micro.model.Session;
import com.rebot.micro.model.User;
import com.rebot.micro.repository.AuthRequestRepository;
import com.rebot.micro.repository.SessionRepository;
import com.rebot.micro.repository.UserRepository;
import com.rebot.micro.utils.DateUtils;
import com.rebot.micro.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorizationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    AuthRequestRepository authRequestRepository;

    @Autowired
    DateService dateService;

    @Resource(name = "smsService")
    SmsService smsService;


    public void generateAuthRequest(String phone) throws TooFastRequestsException{
        AuthRequest authRequest = authRequestRepository.getAuthRequestByPhone(phone);
        Date now = dateService.utcNow();
        if (authRequest != null){
            if (DateUtils.between(now, authRequest.getCreatedAt()) < 3 * 60) {
                throw new TooFastRequestsException();
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

    public AuthResponseDto authorizeByCode(String phone, int code) throws AuthRequestNotFoundException, AttemptsLimitException, InvalidCodeException {
        AuthRequest authRequest = authRequestRepository.getAuthRequestByPhone(phone);
        Date now = dateService.utcNow();
        if (authRequest == null){
            throw new AuthRequestNotFoundException();
        }
        if (code != authRequest.getCode()) {
            authRequest.setAttempts(authRequest.getAttempts() - 1);
            authRequestRepository.setAttemptsById(authRequest.getId(), authRequest.getAttempts());

            if (authRequest.getAttempts() <= 0) {
                authRequestRepository.delete(authRequest);
                throw new AttemptsLimitException();
            } else throw new InvalidCodeException();

        }
        authRequestRepository.delete(authRequest);
        User user = userRepository.getUserByPhone(phone);
        boolean registered = false;
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setRegisteredAt(now);
            userRepository.save(user);
            registered = true;
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
}