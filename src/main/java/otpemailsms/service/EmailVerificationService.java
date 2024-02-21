package otpemailsms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otpemailsms.entity.User;

import java.util.HashMap;
import java.util.Map;
@Service
public class EmailVerificationService {
    @Autowired
    private UserService userService;
    static final Map<String, String> emailOtpMapping = new HashMap<>();

    public Map<String, String> verifyOtp(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);
        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                userService.verifyEmail(user);
                emailOtpMapping.remove(email);
                response.put("status", "success");
                response.put("message", "Email verified successfully");
            }else{
                response.put("status", "error");
                response.put("message", "User not found");
            }
        }else{
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;
    }
}
