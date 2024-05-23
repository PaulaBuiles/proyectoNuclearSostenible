package co.edu.cue.proyectoNuclearSostenible.service.imp;
import co.edu.cue.proyectoNuclearSostenible.infraestructure.dao.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import co.edu.cue.proyectoNuclearSostenible.domain.entities.User;
import co.edu.cue.proyectoNuclearSostenible.config.Constants;


@Service
public class JwtService {

    @Autowired
    private TokenRepository tokenRepository;

    private final String LLAVE_SECRETA =
            "6baa694f8a35f98c3c39bf7eaab58b7167b0db4f1e570da0cb6cfd76d684edc7";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        Boolean isValidToken = tokenRepository.findByToken(token).map(t ->

                !t.getIsLogOut()).orElse(false);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) &&

                isValidToken;
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts

                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    public String generateToken(User user) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +

                        Constants.DAY_MILIS))

                .signWith(getSigninKey())
                .compact();

        return token;
    }
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(LLAVE_SECRETA);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
