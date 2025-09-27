package LearningSpring.Security.service;

import LearningSpring.Security.dto.LogInDTO;
import LearningSpring.Security.dto.LoginResponseDTO;
import LearningSpring.Security.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    public LoginResponseDTO logIn(LogInDTO logInDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword())
        );

        UserEntity user=(UserEntity) authentication.getPrincipal();
        String Accesstoken=jwtService.generateJwtToken(user);
        String refreshtoken=jwtService.generateRefreshToken(user);
        return new LoginResponseDTO(user.getId(), Accesstoken,refreshtoken);
    }

    public LoginResponseDTO refershToken(String refreshToken) {
        Long id=jwtService.getIdFromTheToken(refreshToken);
        UserEntity user =userService.getUserById(id);
        String accesstoken=jwtService.generateJwtToken(user);

        return new LoginResponseDTO(user.getId(), accesstoken,refreshToken);

    }
}
