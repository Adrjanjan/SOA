package pl.edu.agh.soa.authorization;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.model.User;
import pl.edu.agh.soa.dao.UserDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.util.Date;

@Slf4j
@Api("Authentication")
@Path("")
public class AuthorizationService {

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    UserDao userDao;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiResponses({
            @ApiResponse(code = 200, message = "Authorized", responseHeaders = {
                    @ResponseHeader(name = "Authentication")
            }),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public Response authenticateUser(@FormParam("login") String login, @FormParam("password") String password) {
        log.info("User {} logging in", login);

        try {
            authenticate(login, password);
            return Response.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token(login))
                    .build();
        } catch (SecurityException e) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("login") String login, @FormParam("password") String password) {
        userDao.addUser(User.builder()
                .login(login)
                .hashedPassword(PasswordUtils.hash(password))
                .build());
        return Response.status(Response.Status.CREATED).build();
    }

    private String token(String login) {
        Key key = keyGenerator.generateKey();
        return Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath()
                        .toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 15 * 60 * 60L))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private void authenticate(String login, String password) {
        var user = userDao.getUser(login);
        var hashedPassword = PasswordUtils.hash(password);
        if ( user == null || !user.getHashedPassword()
                .equals(hashedPassword) ) {
            throw new SecurityException("Invalid login/password");
        }
    }

}
