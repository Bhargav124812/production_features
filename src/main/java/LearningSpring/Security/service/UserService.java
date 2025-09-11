package LearningSpring.Security.service;

import LearningSpring.Security.appConfig.WebSecurityConfig;
import LearningSpring.Security.dto.SignUpDTO;
import LearningSpring.Security.dto.UserDTO;
import LearningSpring.Security.entity.UserEntity;
import LearningSpring.Security.exceptions.ResourceNotFoundException;
import LearningSpring.Security.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() ->new ResourceNotFoundException("There Is No user With username"));
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> userEntity=userRepo.findByEmail(signUpDTO.getEmail());
        if(userEntity.isPresent()){
            throw  new BadCredentialsException("User With Email "+signUpDTO.getEmail()+"Already Exists");
        }
        UserEntity user=modelMapper.map(signUpDTO,UserEntity.class);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        UserEntity savedUser=userRepo.save(user);
        return modelMapper.map(savedUser,UserDTO.class);
    }

}
