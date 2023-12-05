package slowxmas.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slowxmas.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoId(Long id);
}
