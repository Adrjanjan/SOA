package pl.edu.agh.soa.dao;

import pl.edu.agh.soa.entity.RailwayCarriage;

import javax.ejb.Stateless;

@Stateless
public class RailwayCarriageRepository extends AbstractRepository<RailwayCarriage> {
    @Override
    Class<RailwayCarriage> getType() {
        return RailwayCarriage.class;
    }
}
