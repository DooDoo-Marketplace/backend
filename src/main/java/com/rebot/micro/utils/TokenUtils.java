package com.rebot.micro.utils;

import com.rebot.micro.model.User;
import com.rebot.micro.service.DateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TokenUtils {

    public static String generateToken(User user) {
        String in = String.valueOf(Date.from(Instant.now())) +
                Math.random() +
                user.getPhone() +
                user.getId() +
                user;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(in.getBytes(StandardCharsets.UTF_8));
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                try {
                    s.append(Character.toChars(aByte));
                }
                catch (IllegalArgumentException ex){
                    s.append(ThreadLocalRandom.current().nextInt(1,9));
                }
            }
            return s.toString();

        } catch (NoSuchAlgorithmException ex) {
            throw new Error("Пизда рулю. Алгоритма нет");
        }
    }


}
