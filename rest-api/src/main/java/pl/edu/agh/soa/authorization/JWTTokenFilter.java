package pl.edu.agh.soa.authorization;

import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@JWTAuthorization
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenFilter implements ContainerRequestFilter {

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        var authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if ( authHeader == null || !authHeader.startsWith("Bearer ") ) {
            throw new NotAuthorizedException("No authorization header");
        }

        var token = authHeader.substring("Bearer ".length())
                .trim();

        try {
            var key = keyGenerator.generateKey();
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .build());
        }

    }
}
