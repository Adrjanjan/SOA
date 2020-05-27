package pl.edu.agh.soa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "railway_carriage")
public class RailwayCarriage {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column
    private boolean available;

    @ManyToOne
    @JoinColumn(name= "train_id")
    private Train train;

}
