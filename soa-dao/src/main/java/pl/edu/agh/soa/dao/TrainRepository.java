package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.entity.Train;
import pl.edu.agh.soa.entity.Train_;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class TrainRepository extends AbstractRepository<Train> {

    @Override
    Class<Train> getType() {
        return Train.class;
    }

    public List<Train> getByNumberOfCarriages(int numOfCarriages) {
        var trainQuery = cb.createQuery(getType());
        var trainSelect = trainQuery.from(getType());
        // subquery
        var carriagesCountSubquery = trainQuery.subquery(Long.class);
        // subquery from
        var carriages = carriagesCountSubquery.correlate(trainSelect).join(Train_.carriages);
        
        carriagesCountSubquery.select(cb.count(carriages));
        
        
        trainQuery.select(trainSelect).where(cb.equal(carriagesCountSubquery, numOfCarriages));

        return em.createQuery(trainQuery).getResultList();
    }
}
