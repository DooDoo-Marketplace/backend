package com.rebot.micro.userservice.utils;

import com.rebot.micro.userservice.model.User;
import org.postgresql.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Instant;
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
            return Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);

        } catch (NoSuchAlgorithmException ex) {
            throw new Error("Пизда рулю. Алгоритма нет");
        }
    }


}
