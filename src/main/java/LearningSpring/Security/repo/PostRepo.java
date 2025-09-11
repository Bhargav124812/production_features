package LearningSpring.Security.repo;

import LearningSpring.Security.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepo extends JpaRepository<PostEntity,Long> {

}
