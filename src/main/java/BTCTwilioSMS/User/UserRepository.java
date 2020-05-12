package BTCTwilioSMS.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUpperBoundLessThan(Double upperBound);
    List<User> findByLowerBoundGreaterThan(Double upperBound);
}
