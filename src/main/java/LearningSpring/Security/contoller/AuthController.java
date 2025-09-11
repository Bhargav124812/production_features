package LearningSpring.Security.contoller;

import LearningSpring.Security.dto.LogInDTO;
import LearningSpring.Security.dto.SignUpDTO;
import LearningSpring.Security.dto.UserDTO;
import LearningSpring.Security.service.AuthService;
import LearningSpring.Security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO= userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public String login(@RequestBody LogInDTO logInDTO){
        return authService.logIn(logInDTO);
    }

}
