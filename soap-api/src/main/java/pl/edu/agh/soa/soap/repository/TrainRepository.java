package pl.edu.agh.soa.soap.repository;

import pl.edu.agh.soa.soap.model.Train;

import java.util.List;

public interface TrainRepository {

    List<Train> allTrains();
    Train getTrainById(long id);
    void addTrain(long id, Train train);
    List<Train> getTrainsByNumberOfCarriages(int numOfCarriages);
    Train editTrain(long id, Train newTrain);
    void mockData();
}
