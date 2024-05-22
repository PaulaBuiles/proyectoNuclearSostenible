package co.edu.cue.proyectoNuclearSostenible.utilities;

import co.edu.cue.proyectoNuclearSostenible.service.imp.JwtService;
import co.edu.cue.proyectoNuclearSostenible.service.imp.UserServiceImp;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImp userService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(Constants.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(Constants.BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(Constants.SUB_STRING_HEADER_HTTP);
        String username = jwtService.extractUsername(token);
        if (username != null &&

                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtService.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =

                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,

                                userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        filterChain.doFilter(request, response);
    }
}
