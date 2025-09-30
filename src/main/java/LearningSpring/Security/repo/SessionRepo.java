package LearningSpring.Security.repo;

import LearningSpring.Security.entity.SessionEntity;
import LearningSpring.Security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<SessionEntity,Long> {

    List<SessionEntity> findByUser(UserEntity user);

    Optional<SessionEntity> findByrefreshtoken(String RefreshToken);

    void deleteByrefreshtoken(String refreshToken);
}
