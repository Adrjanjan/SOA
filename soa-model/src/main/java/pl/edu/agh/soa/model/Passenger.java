package pl.edu.agh.soa.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    private int id;

    private String firstName;

    private String lastName;

    private String ticketNumber;

    private List<Drive> drives;
}
