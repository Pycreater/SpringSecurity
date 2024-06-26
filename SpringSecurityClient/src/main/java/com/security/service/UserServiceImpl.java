package com.security.service;

import com.security.entity.PasswordResetToken;
import com.security.entity.User;
import com.security.entity.VerificationToken;
import com.security.model.UserModel;
import com.security.repository.PasswordResetTokenRepository;
import com.security.repository.UserRepository;
import com.security.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid Token";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime() -
                calendar.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "Expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "Valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {

        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public void createPasswordResetTokenForUser(User user, String token) {
//        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
//        passwordResetTokenRepository.save(passwordResetToken);
//    }
//
//    @Override
//    public String validatePasswordResetToken(String token) {
//        PasswordResetToken passwordResetToken =
//                passwordResetTokenRepository.findByToken(token);
//        if (passwordResetToken == null) {
//            return "Invalid Token";
//        }
//        User user = passwordResetToken.getUser();
//        Calendar calendar = Calendar.getInstance();
//
//        if ((passwordResetToken.getExpirationTime().getTime() -
//                calendar.getTime().getTime()) <= 0) {
//            passwordResetTokenRepository.delete(passwordResetToken);
//            return "Expired";
//        }
//        return "Valid";
//    }
//
//    @Override
//    public Optional<User> getUserByPasswordResetToken(String token) {
//        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
//    }


    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        passwordResetTokenRepository.save(passwordResetToken);
        log.info("Password reset token created for user {}: {}", user.getEmail(), token);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            log.info("Password reset token not found: {}", token);
            return "Invalid Token";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            log.info("Password reset token expired: {}", token);
            return "Expired";
        }
        return "Valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            log.info("Password reset token not found when retrieving user: {}", token);
            return Optional.empty();
        }
        log.info("Password reset token valid for user: {}", passwordResetToken.getUser().getEmail());
        return Optional.ofNullable(passwordResetToken.getUser());
    }


    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
