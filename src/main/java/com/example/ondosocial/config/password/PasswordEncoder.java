package com.example.ondosocial.config.password;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.ondosocial.config.error.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z0-9$@$!%*#?&]{8,}$";

        Pattern pattern = Pattern.compile(passwordPattern);

        if (!pattern.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException(ErrorCode.INVALID_PASSWORD_FORMAT.getMessage());
        }

        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}

