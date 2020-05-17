package pl.edu.agh.soa.services;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.authorization.JWTAuthorization;
import pl.edu.agh.soa.model.Train;
import pl.edu.agh.soa.repository.TrainRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Slf4j
@Path("/trains")
@Api("Trains API")
public class TrainServices {

    @Inject
    TrainRepository trainRepository;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Return train by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Train found"),
            @ApiResponse(code = 404, message = "Train not found")
    })
    public Response getTrain(@PathParam("id") long id) {
        var train = trainRepository.getTrainById(id);
        var status = train == null ? Response.Status.NOT_FOUND : Response.Status.OK;
        return Response.status(status)
                .entity(train)
                .build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Return all trains")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trains found")
    })
    public Response getAllTrains() {
        return Response.status(Response.Status.OK)
                .entity(trainRepository.allTrains())
                .build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTAuthorization
    @ApiOperation("Add train")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Train created"),
            @ApiResponse(code = 409, message = "Train with this id already exists")
    })
    @ApiImplicitParam(name="Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class)
    public Response addTrain(Train train) {
        if ( trainRepository.getTrainById(train.getId()) == null ) {
            trainRepository.addTrain(train.getId(), train);
            return Response.status(Response.Status.CREATED)
                    .build();
        }
        return Response.status(Response.Status.CONFLICT)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/number-of-carriages/{numOfCarriages}")
    @ApiOperation("Return all trains with matching number of carriages")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trains found")
    })
    public Response getTrainsByNumberOfCarriages(@PathParam("numOfCarriages") int numOfCarriages) {
        return Response.status(Response.Status.OK)
                .entity(trainRepository.getTrainsByNumberOfCarriages(numOfCarriages))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTAuthorization
    @ApiOperation("Edit train by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Train updated"),
            @ApiResponse(code = 404, message = "Train not found")
    })
    @ApiImplicitParam(name="Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class)
    public Response editTrain(@PathParam("id") long id, Train newTrain) {
        if(trainRepository.editTrain(id, newTrain) == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        return Response.status(Response.Status.OK)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @JWTAuthorization
    @ApiOperation("Delete train by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Train updated"),
            @ApiResponse(code = 404, message = "Train not found")
    })
    @ApiImplicitParam(name="Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class)
    public Response deleteTrain(@PathParam("id") long id) {
        trainRepository.deleteTrain(id);
        return Response.status(Response.Status.OK)
                .build();
    }

    @POST
    @Path("/mock")
    public void mockData() {
        trainRepository.mockData();
    }

    @GET
    @Path("/{id}/logo")
    @Produces("image/png")
    @ApiOperation("Return train logo by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logo found"),
            @ApiResponse(code = 404, message = "Student with this id is not found or logo is not found"),
            @ApiResponse(code = 500, message = "Server error, try later")
    })
    public Response getTrainIcon(@PathParam("id") long id) {
        final var train = trainRepository.getTrainById(id);
        if ( train == null ) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
        try {
            final var stream = getClass().getClassLoader()
                    .getResourceAsStream(train.getLogoPath());
            if ( stream == null ) {
                return Response.status(Response.Status.NOT_FOUND)
                        .build();
            }
            byte[] icon = stream.readAllBytes();
            return Response.status(Response.Status.OK)
                    .entity(icon)
                    .build();
        } catch (IOException e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
