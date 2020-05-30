package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.Train;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TrainMapper {

    public static Train map(pl.edu.agh.soa.entity.Train entity) {
        if(entity == null) {
            return null;
        }
        var carriages = Optional.ofNullable(entity.getCarriages())
                .orElse(Set.of())
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
        if(model == null) {
            return null;
        }

        final var train = pl.edu.agh.soa.entity.Train.builder()
                .logoPath(model.getLogoPath())
                .id(model.getId())
                .build();

        Optional.ofNullable(model.getCarriages())
                .orElse(List.of())
                .stream()
                .map(CarriageMapper::map)
                .forEach(train::addCarriage);

        return train;
    }

}
