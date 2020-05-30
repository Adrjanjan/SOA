package pl.edu.agh.soa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Passenger {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String ticketNumber;

    @ManyToMany(mappedBy = "passengers", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Drive> drives;
}
