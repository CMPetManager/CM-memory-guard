package com.catchthemoment.service;

import com.catchthemoment.dto.ConfirmationEmailDTO;
import com.catchthemoment.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserConfirmMailService implements UserService {
    private final ServiceMail serviceMail;
    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO) {

        ConfirmationEmailDTO confirmationEmailDTO = new ConfirmationEmailDTO(userDTO.name());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userDTO.email());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationEmailDTO.confirmationToken());
        serviceMail.sendMail(mailMessage);

        return ResponseEntity.ok("Verify email by the link sent on your email address");

    }

    @Override
    public ResponseEntity<?> confirmEmail(ConfirmationEmailDTO emailDTO) {
        return null;
    }
}
