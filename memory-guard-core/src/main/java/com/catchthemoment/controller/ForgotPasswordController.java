package com.catchthemoment.controller;

import com.catchthemoment.exception.ApplicationErrorEnum;
import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ForgotPassword;
import com.catchthemoment.model.UpdatePasswordModel;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.model.ForgotPassword;

import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.util.SiteUrlUtil;
import com.catchthemoment.validation.LoginSuccess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author shele
 * @version 1.0.0
 * This controller commits for updating user's password via sending email to user
 * for changing email .
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController implements ForgotPasswordControllerApiDelegate {

    private final UserResetPasswordService resetPasswordService;
    private final String siteUrl = "/users/reset_password?token=";


    @LoginSuccess
    @Override
    public ResponseEntity<Void> changePassword(UpdatePasswordModel updatePasswordModel) throws Exception {
        resetPasswordService.changeUserPasswords(updatePasswordModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> forgotPassword(ForgotPassword forgotPassword) throws ServiceProcessingException {
        resetPasswordService.forgotPassword(forgotPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * When user fill incoming input form , sending email starts to come up .
     * This method  charges of  sending email to user which changes email
     *
     * @param forgotPassword (optional)
     */
    @Override
    @LoginSuccess
    public ResponseEntity<Void> resetPassword(ForgotPassword forgotPassword) throws Exception {
        String email = forgotPassword.getEmail();
        String token = RandomString.make(20);
        try {
            resetPasswordService.updateResetPasswordToken(email, token);

            String resetUrl = "/users/reset_password?token=";
            String resetPasswordLink = SiteUrlUtil.getSiteURL(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()) + resetUrl + token;
            resetPasswordService.sendResetPasswordEmail(email, resetPasswordLink);
            log.debug("*** email was sent to user: {} ***", resetPasswordLink);
        } catch (ServiceProcessingException e) {
            throw new ServiceProcessingException(ApplicationErrorEnum.INCORRECT_INPUT);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
