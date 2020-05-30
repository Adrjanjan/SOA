package pl.edu.agh.soa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String startCity;

    @Column
    private String endCity;


    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name="drive_to_passengers",
            joinColumns = @JoinColumn(name = "drive_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    )
    private List<Passenger> passengers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false, referencedColumnName = "id")
    private Train train;

}
