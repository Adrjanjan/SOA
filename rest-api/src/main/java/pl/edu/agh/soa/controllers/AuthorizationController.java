package pl.edu.agh.soa.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.services.AuthorizationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Slf4j
@Api("Authentication")
@Path("")
public class AuthorizationController {

    @Context
    private UriInfo uriInfo;

    @Inject
    AuthorizationService authorizationService;

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
            authorizationService.authenticate(login, password);
            final var issuer = uriInfo.getAbsolutePath()
                    .toString();
            return Response.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationService.token(login, issuer))
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
        authorizationService.createUser(login, password);
        return Response.status(Response.Status.CREATED)
                .build();
    }

}
