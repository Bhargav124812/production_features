package Spring.Testing.Contollers;


import Spring.Testing.entities.Employee;
import Spring.Testing.repositories.EmployeeRepository;
import Spring.Testing.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class TestingContollerWithWebClients extends AbstractWebClient{

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    public void test_GetEmployeeWithId(){
        Employee savedEmployee=employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId().intValue())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
    }

    @Test
    public void test_NotFound(){
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_throwExceptionforInvalidUser(){
        Employee savedEmployee=employeeRepository.save(testEmployee);

        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployee)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void test_CReateThyeEmployee(){
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());
    }

    @Test
    public void test_UpdateTheEmployeeWithEmail(){
        Employee savedEmployee=employeeRepository.save(testEmployee);
         testEmployeeDto.setName("Bhargav");
         testEmployeeDto.setEmail("Bhargav@gmail.com");

        webTestClient.put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    public void test_UpdateTheEmployeewithValidDetails(){
        Employee savedEmployee=employeeRepository.save(testEmployee);

        testEmployeeDto.setName("Bhargav");
        testEmployeeDto.setSalary(10020L);

        webTestClient.put()
                .uri("/employees/{id}",savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName())
                .jsonPath("$.salary").isEqualTo(testEmployeeDto.getSalary());
    }

    @Test
    public void test_UpdateWithInvalidUSerID(){
        webTestClient.put()
                .uri("/employees/9999")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_DeleteWhenEmployeeDoesNotExists(){
        webTestClient.delete()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_DeleteForTheValidUSer(){
        Employee savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("/employees/{id}",savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);

        webTestClient.delete()
                .uri("/employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }
}
