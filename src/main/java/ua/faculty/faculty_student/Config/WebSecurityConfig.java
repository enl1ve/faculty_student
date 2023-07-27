package ua.faculty.faculty_student.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.faculty.faculty_student.Service.UsersDetailsService;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig{
    private final UsersDetailsService usersService;

    @Autowired
    public WebSecurityConfig(UsersDetailsService usersService) {
        this.usersService = usersService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain spiFilterChain(HttpSecurity http) throws Exception{

        http.authenticationManager(
                http.getSharedObject(AuthenticationManagerBuilder.class)
                        .userDetailsService(usersService)
                        .passwordEncoder(passwordEncoder())
                        .and()
                        .build());

        http
                .csrf()
                .disable()
                .authorizeRequests()
                .requestMatchers("/", "/register", "/login").permitAll()
                .requestMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin((form) -> form
                        .permitAll()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                )
                .logout((logout)->logout
                        .permitAll()
                        .logoutSuccessUrl("/"));

        return http.build();
    }
}
