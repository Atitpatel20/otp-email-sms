package otpemailsms.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otpemailsms.entity.User;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class TwilioSmsVerificationService {
    @Autowired
    private UserService userService;


    static final Map<String, String> smsOtpMapping = new HashMap<>();

    public Map<String, String> verifyOtp(String mobile, String otp) {

        String storedOtp = smsOtpMapping.get(mobile);
        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {
            User user = userService.getUserByMobile(mobile);
            if (user != null) {
                smsOtpMapping.remove(mobile);
                userService.verifyMobile(user);
                response.put("status", "success");
                response.put("message", "MobileNumber verified successfully");
            } else {
                response.put("status", "error");
                response.put("message", "User not found");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;
    }

}