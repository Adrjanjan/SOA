package pl.edu.agh.soa.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Drive {

    private int id;

    private String startCity;

    private String endCity;

    private List<Passenger> passengers;

    private Train train;
}
