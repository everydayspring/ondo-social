package com.example.ondosocial.config.password;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.example.ondosocial.config.error.ErrorCode;
import com.example.ondosocial.config.validate.Preconditions;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        String passwordPattern =
                "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";

        Pattern pattern = Pattern.compile(passwordPattern);

        Preconditions.validate(
                pattern.matcher(rawPassword).matches(), ErrorCode.INVALID_PASSWORD_FORMAT);

        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
