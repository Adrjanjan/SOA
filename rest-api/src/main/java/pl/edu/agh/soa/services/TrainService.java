package pl.edu.agh.soa.services;

import lombok.extern.slf4j.Slf4j;
import pl.edu.agh.soa.dao.TrainRepository;
import pl.edu.agh.soa.mappers.TrainMapper;
import pl.edu.agh.soa.model.RailwayCarriage;
import pl.edu.agh.soa.model.Train;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Stateless
public class TrainService {

    @EJB
    TrainRepository trainRepository;

    public Train getTrain(long id) {
        log.info("Getting train by id: " + id);
        return TrainMapper.map(trainRepository.find(id));
    }

    public List<Train> allTrains() {
        log.info("Getting all trains");
        return trainRepository.findAll()
                .stream()
                .map(TrainMapper::map)
                .collect(Collectors.toList());
    }

    public void addTrain(Train train) {
        log.info("Adding train with id: " + train.getId());
        trainRepository.create(TrainMapper.map(train));
    }

    public List<Train> getTrainsByNumberOfCarriages(int numOfCarriages) {
        log.info("Getting trains with number of carriages: " + numOfCarriages);
        return trainRepository.getByNumberOfCarriages(numOfCarriages)
                .stream()
                .map(TrainMapper::map)
                .collect(Collectors.toList());
    }

    public Train editTrain(Train newTrain) {
        log.info("Edit train with id: " + newTrain.getId());
        return TrainMapper.map(trainRepository.edit(TrainMapper.map(newTrain)));
    }

    public void deleteTrain(long id) {
        log.info("Delete train with id: " + id);
        trainRepository.delete(id);
    }

    public void mockData() {
        log.info("Mock data");

        var c1 = RailwayCarriage.builder()
                .available(false)
                .registrationNumber("RC1")
                .build();
        var c2 = RailwayCarriage.builder()
                .available(false)
                .registrationNumber("RC2")
                .build();
        var c3 = RailwayCarriage.builder()
                .available(false)
                .registrationNumber("RC3")
                .build();
        var t1 = Train.builder()
                .logoPath("logo.png")
                .carriages(Arrays.asList(c1, c2, c3))
                .build();

        trainRepository.create(TrainMapper.map(t1));
    }
}
