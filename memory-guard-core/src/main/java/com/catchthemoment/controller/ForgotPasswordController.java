package com.catchthemoment.controller;

import com.catchthemoment.exception.ServiceProcessingException;
import com.catchthemoment.service.UserResetPasswordService;
import com.catchthemoment.util.SiteUrlUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ForgotPasswordController {

    private final UserResetPasswordService resetPasswordService;

    @GetMapping("/forgot_password")
    public String forgotPasswordForm() {
        return "Should be reset password html form";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(HttpServletRequest servletRequest, Model model) throws MessagingException {
        String email = servletRequest.getParameter("email");
        String token = RandomString.make(20);

        try {
            resetPasswordService.updateResetPasswordToken(email, token);
            var resetPasswordLink = SiteUrlUtil.getSiteURL(servletRequest) + "/reset_password?token=" + token;
            resetPasswordService.sendResetPasswordEmail(email, resetPasswordLink);
            model.addAttribute("message",
                    "We have sent a reset password link to your email. Please check.");
        } catch (ServiceProcessingException e) {
            model.addAttribute("error", e.getMessage());
            throw new RuntimeException(e);
        } catch (MessagingException ex) {
            model.addAttribute("error", "Error appeared while sending email");

        }
        return "Forgot_Password_Form";

    }

    @GetMapping("/reset_password_form")
    public String resetPasswordForm(@Param("token") String resetToken, Model model) {
        var user = resetPasswordService.getUserFromResetToken(resetToken);
        model.addAttribute("token", resetToken);
        if (user == null) {
            model.addAttribute("message", "Invalid incoming token");
            return "message";
        }
        return "shold_be_reset_password_form";

    }

    @PostMapping("/reset_password_form")
    public String postResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        var userFromResetToken = resetPasswordService.getUserFromResetToken(token);
        model.addAttribute("title", "Reset your password");

        if (userFromResetToken == null) {
            model.addAttribute("message", "Something went wrong..");
            return "message";
        } else {
            resetPasswordService.updatePassword(userFromResetToken, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }
        return "message";
    }


}
