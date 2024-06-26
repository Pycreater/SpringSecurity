package com.security.event.listener;

import com.security.entity.User;
import com.security.event.RegistrationCompleteEvent;
import com.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create the verification token for the user with link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVerificationForUser(token, user);
        //send Mail to user.

        String url =
                event
                        .getApplicationUrl()
                        + "/verifyRegistration?token="
                        + token;

        //send verification email method here
        log.info("Click the link to verify your account: {}", url);
    }
}
