package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ForgotPassword;
import com.catchthemoment.model.UpdatePassword;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.util.SiteUrlUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class ForgotPasswordController implements ForgotPasswordControllerApiDelegate {

    private final UserResetPasswordService resetPasswordService;

    @GetMapping("/forgot-password")
    public ResponseEntity<Object> forgotPasswordForm(Model model,@RequestParam String emailParam) {
        model.addAttribute("Return form for reset password",emailParam);
        return new ResponseEntity<>(model,HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody @Validated ForgotPassword forgotPassword,
                                                 @Parameter(hidden = true) HttpServletRequest servletRequest, Model model)
            throws MessagingException {
        String email = forgotPassword.getEmail();
        String token = RandomString.make(20);

        try {
            resetPasswordService.updateResetPasswordToken(email, token);
            var resetPasswordLink = SiteUrlUtil.getSiteURL(servletRequest) + "/reset_password?token=" + token;
            resetPasswordService.sendResetPasswordEmail(email, resetPasswordLink);
            model.addAttribute("message",
                    "We have sent a reset password link to your email. Please check.");
            log.debug("*** email was sent to user: {} ***",resetPasswordLink);
        } catch (ServiceProcessingException e) {
            model.addAttribute("error", e.getMessage());
            throw new RuntimeException(e);
        } catch (MessagingException ex) {
            model.addAttribute("error", "Error appeared while sending email");

        }
        return new ResponseEntity<>(model, HttpStatus.CREATED);

    }

    @GetMapping(value = "/reset", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Object> resetPasswordForm(@RequestParam String token, Model model) {
        var user = resetPasswordService.getUserFromResetToken(token);
        model.addAttribute("token",token);
        if (user == null) {
            model.addAttribute("message", "Invalid incoming token");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(model, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Object> resetPassword(@RequestBody @Validated UpdatePassword updatePasswordModel, Model model) {
        String token = updatePasswordModel.getToken();
        String password = updatePasswordModel.getPassword();
        var userFromResetToken = resetPasswordService.getUserFromResetToken(token);
        model.addAttribute("title", "Reset your password");
        if (userFromResetToken == null) {
            model.addAttribute("message", "Something went wrong..");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        } else {
            log.info("*** user changed password {} ***", userFromResetToken);
            resetPasswordService.updatePassword(userFromResetToken, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }


}
