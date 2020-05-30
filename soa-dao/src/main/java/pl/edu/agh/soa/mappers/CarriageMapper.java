package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.RailwayCarriage;

public class CarriageMapper {

    public static RailwayCarriage map(pl.edu.agh.soa.entity.RailwayCarriage entity) {
        return RailwayCarriage.builder()
                .registrationNumber(entity.getRegistrationNumber())
                .available(entity.isAvailable())
                .id(entity.getId())
                .build();
    }

    public static pl.edu.agh.soa.entity.RailwayCarriage map(RailwayCarriage model) {
        return pl.edu.agh.soa.entity.RailwayCarriage.builder()
                .registrationNumber(model.getRegistrationNumber())
                .available(model.isAvailable())
                .build();
    }
}
