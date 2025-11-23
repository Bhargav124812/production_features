package Spring.Testing.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckContoller {

    @GetMapping("/")
    public ResponseEntity<String> healthCheckController(){

        return ResponseEntity.ok("It is Good");
    }
}
