package com.example.ondosocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OndoSocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(OndoSocialApplication.class, args);
    }
}
