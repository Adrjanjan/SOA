package pl.edu.agh.soa.soap.services;

import lombok.extern.slf4j.Slf4j;
import org.jboss.annotation.security.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;
import pl.edu.agh.soa.model.Train;
import pl.edu.agh.soa.dao.TrainDao;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static javax.jws.soap.SOAPBinding.*;

@Slf4j
@Stateless
@WebService
@DeclareRoles("TrainsManager")
@SecurityDomain("TrainsManagementDomain")
@WebContext(authMethod = "BASIC")
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class TrainService {

    @Inject
    TrainDao trainDao;

    @WebMethod
    @RolesAllowed("TrainsManager")
    public void addTrain(@WebParam(name = "id") long id, @WebParam(name = "train") Train train) {
        if ( trainDao.getTrainById(id) != null ) {
            trainDao.addTrain(id, train);
        }
    }

    @WebMethod
    @PermitAll
    public List<Train> getTrainsByNumberOfCarriages(@WebParam(name = "numOfCarriages") int numOfCarriages) {
        return trainDao.getTrainsByNumberOfCarriages(numOfCarriages);
    }

    @WebMethod
    @PermitAll
    public Train getTrain(@WebParam(name = "id") long id) {
        return trainDao.getTrainById(id);
    }

    @WebMethod
    @RolesAllowed("TrainsManager")
    public Train editTrain(@WebParam(name = "id") long id, @WebParam(name = "train") Train newTrain) {
        return trainDao.editTrain(id, newTrain);
    }

    @WebMethod
    @PermitAll
    public List<Train> getAllTrains() {
        return trainDao.allTrains();
    }

    @WebMethod
    @PermitAll
    public void mockData() {
        trainDao.mockData();
    }

    @WebMethod
    @RolesAllowed("TrainsManager")
    public String getTrainIconBase64(@WebParam(name = "id") long id) {
        try {
            final var stream = getClass().getClassLoader()
                    .getResourceAsStream(trainDao.getTrainById(id)
                            .getLogoPath());
            if ( stream == null ) {
                return null;
            }
            byte[] icon = stream.readAllBytes();
            return Base64.getEncoder()
                    .encodeToString(icon);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
