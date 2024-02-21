package otpemailsms.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static otpemailsms.service.TwilioSmsVerificationService.smsOtpMapping;


@Service
public class TwilioService {
    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(100000)).trim();
    }

    public Map<String, String> sendOtpSms(String mobile) {
        String otp = generateOtp();
        smsOtpMapping.put(mobile,otp);
        sendSms(mobile, "otp for mobile verification:" + otp);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "check your sms for verification");
        return response;
    }

    public void sendSms(String to, String messageBody) {
        // Ensure that 'to' includes the country code (e.g., +91 for India)
        if (!to.startsWith("+")) {
            to = "+91" + to;  // Assuming the country code for India is +91
        }

        Twilio.init(twilioAccountSid, twilioAuthToken);

        Message message = Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(twilioPhoneNumber),
                        messageBody)
                .create();
    }
}