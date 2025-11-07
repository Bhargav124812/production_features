package LearningSpring.Security;

import LearningSpring.Security.entity.SessionLimit;
import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.repo.Session_LimitRepo;
import LearningSpring.Security.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class LearningSpringApplicationTests {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private SessionLimit sessionLimit;

    @Autowired
    private Session_LimitRepo sessionLimitRepo;

	@Test
	void contextLoads() {

	}

}
