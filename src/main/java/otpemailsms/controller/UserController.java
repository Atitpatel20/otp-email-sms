package otpemailsms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import otpemailsms.entity.User;
import otpemailsms.service.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private TwilioSmsVerificationService twilioSmsVerificationService;

    @PostMapping("/register/email")
    public Map<String, String> registerUser(@RequestBody User user) {
        // Register the user without email verification
        User registeredUser = userService.registerUser(user);
        return emailService.sendOtpEmail(user.getEmail());
    }

    @PostMapping("/register/sms")
    public Map<String,String> registerUserBySms(@RequestBody User user) {
        userService.registerUser(user);
        return twilioService.sendOtpSms(user.getMobile());
    }
    @PostMapping("/verify-email-otp")
    public Map<String, String> verifyEmailOtp(@RequestParam String email, @RequestParam String otp) {
        return emailVerificationService.verifyOtp(email, otp);
    }
    @PostMapping("/verify-sms-otp")
    public Map<String,String> verifySmsOtp(@RequestParam String mobile, @RequestParam String otp) {
        return twilioSmsVerificationService.verifyOtp(mobile, otp);
    }
}

