package Spring.Testing.Service;


import Spring.Testing.TestContainerConfig;
import Spring.Testing.dto.EmployeeDto;
import Spring.Testing.entities.Employee;
import Spring.Testing.exceptions.ResourceNotFoundException;
import Spring.Testing.repositories.EmployeeRepository;
import Spring.Testing.services.impl.EmployeeServiceImpl;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    @Test
    public void testCreateNEwEmployee_whenEmployeeAlreadyExists(){
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockemployee));

        assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: "+mockemployee.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testGetEmployeeById_WhenNotPresent(){
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository).findById(1L);
    }

    @Test
    public void test_updateTheEmployee(){
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockemployee));
        mockEmployeeDto.setName("Hari");
        mockEmployeeDto.setSalary(23438L);

        Employee testEmployee= modelMapper.map(mockEmployeeDto,Employee.class);

        when(employeeRepository.save(any(Employee.class))).thenReturn(testEmployee);

        EmployeeDto updatedDto=employeeService.updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto);

        assertThat(updatedDto).isEqualTo(mockEmployeeDto);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any());

    }

    @Test
    public void test_updateTheEmployee_With_The_Email(){
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockemployee));
        mockEmployeeDto.setName("Bhargav");
        mockEmployeeDto.setEmail("sai@gmail.com");

        assertThatThrownBy(() ->employeeService.updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");
    }

    @Test
    public void test_updateTheEmployee_WhenEmployeeWithIdNotFound(){
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDto.getId(),mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: "+mockEmployeeDto.getId());
    }

    @Test
    public void test_DeleteEmployeeWithIdNotFound(){
        when(employeeRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Employee not found with id: 1");

        verify(employeeRepository,never()).deleteById(1L);
    }

    @Test
    public void test_DeleteEmployeeWithIdFound(){
        when(employeeRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();

        verify(employeeRepository).deleteById(1L);

    }
}
