package Spring.Testing.Contollers;


import Spring.Testing.NoSecurityConfig;
import Spring.Testing.TestContainerConfig;
import Spring.Testing.dto.EmployeeDto;
import Spring.Testing.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout= "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class, NoSecurityConfig.class})
public class AbstractWebClient {

    @Autowired
    WebTestClient webTestClient;

    Employee testEmployee = Employee.builder()
            .email("Sai@gmail.com")
            .name("Sai")
            .salary(200L)
            .build();
    EmployeeDto testEmployeeDto = EmployeeDto.builder()
            .email("Sai@gmail.com")
            .name("Sai")
            .salary(200L)
            .build();
}
