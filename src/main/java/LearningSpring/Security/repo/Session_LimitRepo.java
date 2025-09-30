package LearningSpring.Security.repo;

import LearningSpring.Security.entity.SessionLimit;
import LearningSpring.Security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Session_LimitRepo extends JpaRepository<SessionLimit,Long> {
    SessionLimit findByUser(UserEntity user);
}
