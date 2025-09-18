package LearningSpring.Security.contoller;

import LearningSpring.Security.dto.LogInDTO;
import LearningSpring.Security.dto.SignUpDTO;
import LearningSpring.Security.dto.UserDTO;
import LearningSpring.Security.service.AuthService;
import LearningSpring.Security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

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
    public ResponseEntity<String> login(@RequestBody LogInDTO logInDTO, HttpServletRequest request, HttpServletResponse response){
        String token= authService.logIn(logInDTO);

        Cookie cookie =new Cookie("token",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }

}
