package LearningSpring.Security.dto;

import LearningSpring.Security.entity.Enum.Roles;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
    private Set<Roles> roles;
}
