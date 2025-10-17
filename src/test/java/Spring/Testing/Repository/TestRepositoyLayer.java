package Spring.Testing.Repository;


import Spring.Testing.entities.Employee;
import Spring.Testing.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestContainerConfig.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestRepositoyLayer {
    @Autowired
    EmployeeRepository employeeRepository;


    private Employee employee;

    @BeforeEach
    void setup(){
        employee=Employee.builder()
                .name("Bhargav")
                .email("bhargav@gmail.com")
                .salary(103893L)
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {
//        Arrange, Given
        employeeRepository.save(employee);

//        Act, When
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

//        Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {
//        Given
        String email = "notPresent.123@gmail.com";
//        When
        List<Employee> employeeList = employeeRepository.findByEmail(email);
//        Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();
    }
}
