package co.edu.cue.proyectoNuclearSostenible.config;

import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    private User user;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((request) -> {

                    request.requestMatchers("/api/**").permitAll(); //Aca debemos poner todas las rutas o metodos que vamos a permitir para todos
                    request.requestMatchers("/admin/**").hasRole("ADMIN");
                    request.requestMatchers("/user/**").hasRole("USER");
                    request.anyRequest().authenticated();
                }).userDetailsService(userService)
                .exceptionHandling(e -> e
                        .accessDeniedHandler((request, response, accessDeniedException) -> response.setStatus(403))
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(l -> l
                        .logoutUrl("/logout").addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler((request, response, accessDeniedException) -> SecurityContextHolder.clearContext()));
        return http.build();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
