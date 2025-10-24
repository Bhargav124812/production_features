package Spring.Testing.Service;


import Spring.Testing.Repository.TestContainerConfig;
import Spring.Testing.dto.EmployeeDto;
import Spring.Testing.entities.Employee;
import Spring.Testing.repositories.EmployeeRepository;
import Spring.Testing.services.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Import(TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(MockitoExtension.class)
public class TestEmployeeServiceImple {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockemployee;

    private EmployeeDto mockEmployeeDto;

    @BeforeEach
    public void mockEmployee(){
         mockemployee=Employee.builder()
                .id(1L)
                .name("Bhargav")
                .email("bhargav@gmail.com")
                .salary(103893L)
                .build();
        mockEmployeeDto = modelMapper.map(mockemployee, EmployeeDto.class);
    }

    @Test
    public void test_GetEmployeeWithIdAndReturnTheEmployee(){

        Long id=mockemployee.getId();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(mockemployee));

        EmployeeDto employeeDto=employeeService.getEmployeeById(id);

        assertThat(employeeDto.getId()).isEqualTo(id);
        assertThat(employeeDto.getEmail()).isEqualTo(mockemployee.getEmail());
    }

    @Test
    public void test_creationOfTheEmployee(){
         when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
         when(employeeRepository.save(any(Employee.class))).thenReturn(mockemployee);

         EmployeeDto employeeDto =employeeService.createNewEmployee(mockEmployeeDto);

         assertThat(employeeDto).isNotNull();
         assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
         ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
         verify(employeeRepository).save(captor.capture());


         Employee capturedEmployee=captor.getValue();
         assertThat(capturedEmployee.getEmail()).isEqualTo(mockemployee.getEmail());


    }
}
