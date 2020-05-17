package pl.edu.agh.soa;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class TrainApp extends Application {

    public TrainApp() {
        var config = new BeanConfig();

        config.setScan(true);
        config.setBasePath("/rest-api/api");
        config.setResourcePackage("pl.edu.agh.soa");
    }
}
