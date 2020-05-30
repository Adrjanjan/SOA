package pl.edu.agh.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "railway_carriage")
public class RailwayCarriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column
    private boolean available;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name= "train_id", nullable = false, referencedColumnName = "id")
    private Train train;



}
