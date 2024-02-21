package otpemailsms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otpemailsms.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByMobile(String mobile);
}
