package pl.edu.agh.soa.mappers;

import pl.edu.agh.soa.model.Passenger;

public class PassengerMapper {

    public static Passenger map (pl.edu.agh.soa.entity.Passenger entity){
        return Passenger.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .ticketNumber(entity.getTicketNumber())
                .build();
    }

    public static pl.edu.agh.soa.entity.Passenger map (Passenger model){
        return pl.edu.agh.soa.entity.Passenger.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .ticketNumber(model.getTicketNumber())
                .build();
    }
}
