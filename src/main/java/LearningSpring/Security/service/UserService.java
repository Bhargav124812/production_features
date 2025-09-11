package LearningSpring.Security.service;

import LearningSpring.Security.exceptions.ResourceNotFoundException;
import LearningSpring.Security.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() ->new ResourceNotFoundException("There Is No user With username"));
    }
}
