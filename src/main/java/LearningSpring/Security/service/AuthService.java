package LearningSpring.Security.service;

import LearningSpring.Security.dto.LogInDTO;
import LearningSpring.Security.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public String logIn(LogInDTO logInDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword())
        );

        UserEntity user=(UserEntity) authentication.getPrincipal();
        return jwtService.generateJwtToken(user);
    }
}
