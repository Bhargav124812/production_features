package LearningSpring.Security.appConfig;

import LearningSpring.Security.entity.Enum.Permissions.*;
import LearningSpring.Security.filters.JwtSecurityFilter;
import LearningSpring.Security.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import static LearningSpring.Security.entity.Enum.Permissions.*;
import static LearningSpring.Security.entity.Enum.Roles.ADMIN;
import static LearningSpring.Security.entity.Enum.Roles.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final JwtSecurityFilter jwtSecurityFilter;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    @Bean
    SecurityFilterChain customSecurityChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/post/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                        .requestMatchers(HttpMethod.POST,"/post/**")
                        .hasAnyAuthority(POST_CREATE.name())
                        .requestMatchers(HttpMethod.PUT,"/post/**")
                        .hasAnyAuthority(POST_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/post/**")
                        .hasAnyAuthority(POST_DELETE.name())
                        //.requestMatchers("/post/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );

        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myCustomUsers(){
//        UserDetails normaluser= User
//                .withUsername("bhargav")
//                .password(passwordEncoder().encode("bhargav@123"))
//                .roles("USER")
//                .build();
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(normaluser,adminUser);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
