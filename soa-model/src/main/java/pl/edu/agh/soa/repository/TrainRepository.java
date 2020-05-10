package pl.edu.agh.soa.repository;

import pl.edu.agh.soa.model.Train;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TrainRepository {

    List<Train> allTrains();
    Train getTrainById(long id);
    void addTrain(long id, Train train);
    List<Train> getTrainsByNumberOfCarriages(int numOfCarriages);
    Train editTrain(long id, Train newTrain);
    void mockData();
}
