package pl.edu.agh.soa.repository;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.model.RailwayCarriage;
import pl.edu.agh.soa.model.Train;

import javax.ejb.Stateful;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Stateful
@Slf4j
public class InMemoryTrainRepositoryImpl implements TrainRepository {

    private Map<Long, Train> trains = new HashMap<>();

    @Override
    public List<Train> allTrains() {
        log.info("Getting all trains");
        return new ArrayList<>(trains.values());
    }

    @Override
    public Train getTrainById(long id) {
        log.info("Getting train by id: " + id);
        return trains.get(id);
    }

    @Override
    public void addTrain(long id, Train train) {
        log.info("Adding train with id: " + id);
        trains.put(id, train);
    }

    @Override
    public List<Train> getTrainsByNumberOfCarriages(int numOfCarriages) {
        log.info("Getting trains with number of carriages: " + numOfCarriages);
        return trains.values()
                .stream()
                .filter(train -> train.getCarriages()
                        .size() == numOfCarriages)
                .collect(toList());
    }

    @Override
    public Train editTrain(long id, Train newTrain) {
        trains.put(id, newTrain);
        return newTrain;
    }

    public void mockData() {
        RailwayCarriage c1 = RailwayCarriage.builder()
                .id(1L)
                .isAvailable(false)
                .registrationNumber("RC1")
                .build();
        RailwayCarriage c2 = RailwayCarriage.builder()
                .id(2L)
                .isAvailable(false)
                .registrationNumber("RC2")
                .build();
        RailwayCarriage c3 = RailwayCarriage.builder()
                .id(3L)
                .isAvailable(false)
                .registrationNumber("RC3")
                .build();
        Train t1 = Train.builder()
                .id(1L)
                .logoPath("logo.png")
                .carriages(Arrays.asList(c1, c2, c3))
                .build();
        trains.put(1L, t1);
    }
}
