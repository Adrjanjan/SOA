package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.Drive;

import java.util.stream.Collectors;

public class DriverMapper {

    public static Drive map(pl.edu.agh.soa.entity.Drive entity){
        var passengers = entity.getPassengers().stream().map(PassengerMapper::map).collect(Collectors.toList());
        return Drive.builder()
                .id(entity.getId())
                .startCity(entity.getStartCity())
                .endCity(entity.getEndCity())
                .passengers(passengers)
                .train(TrainMapper.map(entity.getTrain()))
                .build();
    }

    public static pl.edu.agh.soa.entity.Drive map(Drive entity){
        var passengers = entity.getPassengers().stream().map(PassengerMapper::map).collect(Collectors.toList());
        return pl.edu.agh.soa.entity.Drive.builder()
                .id(entity.getId())
                .startCity(entity.getStartCity())
                .endCity(entity.getEndCity())
                .passengers(passengers)
                .train(TrainMapper.map(entity.getTrain()))
                .build();
    }


}
