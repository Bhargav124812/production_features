package LearningSpring.Security;

import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.service.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningSpringApplicationTests {

    @Autowired
    private JWTService jwtService;

	@Test
	void contextLoads() {
        UserEntity user=new UserEntity(4L,"bhargav@gmail.com","bhargav@123");
        String token=jwtService.generateJwtToken(user);
        System.out.println(token);
        System.out.println(jwtService.getIdFromTheToken(token));
	}

}
