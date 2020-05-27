package pl.edu.agh.soa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@Entity
public class Drive {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String startCity;

    @Column
    private String endCity;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name="drive_to_passengers",
            joinColumns = @JoinColumn(name = "drive_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    )
    private List<Passenger> passengers;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

}
