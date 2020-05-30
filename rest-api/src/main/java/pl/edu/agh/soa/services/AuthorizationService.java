package pl.edu.agh.soa.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.authorization.KeyGenerator;
import pl.edu.agh.soa.authorization.PasswordUtils;
import pl.edu.agh.soa.dao.UserRepository;
import pl.edu.agh.soa.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Key;
import java.util.Date;

@Slf4j
@Stateless
public class AuthorizationService {

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    UserRepository userRepository;

    public String token(String login, String issuer) {
        log.info("Starting token generation");
        Key key = keyGenerator.generateKey();

        return Jwts.builder()
                .setSubject(login)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 15 * 60 * 60L))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public void authenticate(String login, String password) {
        var user = userRepository.getUser(login);
        var hashedPassword = PasswordUtils.hash(password);
        if ( user == null || !user.getHashedPassword()
                .equals(hashedPassword) ) {
            throw new SecurityException("Invalid login/password");
        }
    }

    public void createUser(String login, String password) {
        userRepository.addUser(User.builder()
                .login(login)
                .hashedPassword(PasswordUtils.hash(password))
                .build());
    }

}
