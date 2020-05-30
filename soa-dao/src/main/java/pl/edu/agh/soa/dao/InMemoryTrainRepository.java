package pl.edu.agh.soa.dao;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.model.RailwayCarriage;
import pl.edu.agh.soa.model.Train;

import javax.ejb.Stateful;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Stateful
public class InMemoryTrainRepository {

    private static final Map<Long, Train> trains = new HashMap<>();

    public List<Train> allTrains() {
        log.info("Getting all trains");
        return new ArrayList<>(trains.values());
    }

    public Train getTrainById(long id) {
        log.info("Getting train by id: " + id);
        return trains.get(id);
    }

    public void addTrain(long id, Train train) {
        log.info("Adding train with id: " + id);
        trains.put(id, train);
    }

    public List<Train> getTrainsByNumberOfCarriages(int numOfCarriages) {
        log.info("Getting trains with number of carriages: " + numOfCarriages);
        return trains.values()
                .stream()
                .filter(train -> train.getCarriages()
                        .size() == numOfCarriages)
                .collect(toList());
    }

    public Train editTrain(long id, Train newTrain) {
        log.info("Edit train with id: " + id);
        if(trains.get(id) == null){
            return null;
        }
        trains.put(id, newTrain);
        return newTrain;
    }

    public void mockData() {
        RailwayCarriage c1 = RailwayCarriage.builder()
                .id(1L)
                .available(false)
                .registrationNumber("RC1")
                .build();
        RailwayCarriage c2 = RailwayCarriage.builder()
                .id(2L)
                .available(false)
                .registrationNumber("RC2")
                .build();
        RailwayCarriage c3 = RailwayCarriage.builder()
                .id(3L)
                .available(false)
                .registrationNumber("RC3")
                .build();
        Train t1 = Train.builder()
                .id(1L)
                .logoPath("logo.png")
                .carriages(Arrays.asList(c1, c2, c3))
                .build();
        trains.put(1L, t1);
    }

    public void deleteTrain(long id) {
        trains.remove(id);
    }
}
