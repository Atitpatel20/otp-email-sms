package otpemailsms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otpemailsms.entity.User;
import otpemailsms.repository.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    public User getUserByMobile(String mobile) {
        User user = userRepository.findByMobile(mobile);
        return user;
    }

    public void verifyMobile(User user) {
        user.setMobileVerified(true);
        userRepository.save(user);
    }

    public boolean isEmailVerified(String email) {
        User user = userRepository.findByEmail(email);
        return user!=null && user.isEmailVerified();
    }
}
