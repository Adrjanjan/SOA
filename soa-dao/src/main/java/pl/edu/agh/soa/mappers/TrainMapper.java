package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.Train;

import java.util.stream.Collectors;

public class TrainMapper {

    public static Train map(pl.edu.agh.soa.entity.Train entity) {
        var carriages = entity.getCarriages()
                .stream()
                .map(CarriageMapper::map)
                .collect(Collectors.toList());
        return Train.builder()
                .carriages(carriages)
                .logoPath(entity.getLogoPath())
                .id(entity.getId())
                .build();
    }

    public static pl.edu.agh.soa.entity.Train map(Train model) {
        var carriages = model.getCarriages()
                .stream()
                .map(CarriageMapper::map)
                .collect(Collectors.toSet());
        return pl.edu.agh.soa.entity.Train.builder()
                .carriages(carriages)
                .logoPath(model.getLogoPath())
                .id(model.getId())
                .build();
    }

}
