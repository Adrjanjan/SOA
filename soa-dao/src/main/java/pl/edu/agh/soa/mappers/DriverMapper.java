package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.Drive;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DriverMapper {

    public static Drive map(pl.edu.agh.soa.entity.Drive entity) {
        if(entity == null) {
            return null;
        }
        var passengers = Optional.ofNullable(entity.getPassengers())
                .orElse(List.of())
                .stream()
                .map(PassengerMapper::map)
                .collect(Collectors.toList());

        return Drive.builder()
                .id(entity.getId())
                .startCity(entity.getStartCity())
                .endCity(entity.getEndCity())
                .passengers(passengers)
                .train(TrainMapper.map(entity.getTrain()))
                .build();
    }

    public static pl.edu.agh.soa.entity.Drive map(Drive model) {
        if(model == null) {
            return null;
        }
        var passengers = Optional.ofNullable(model.getPassengers())
                .orElse(List.of())
                .stream()
                .map(PassengerMapper::map)
                .collect(Collectors.toList());

        return pl.edu.agh.soa.entity.Drive.builder()
                .startCity(model.getStartCity())
                .endCity(model.getEndCity())
                .passengers(passengers)
                .train(TrainMapper.map(model.getTrain()))
                .build();
    }

}
