package pl.edu.agh.soa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<RailwayCarriage> carriages = new HashSet<>();

    @Column(name = "logo_path")
    private String logoPath;

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Drive> drives = new HashSet<>();

    public void addCarriage(RailwayCarriage railwayCarriage) {
        if ( carriages == null ) {
            carriages = new HashSet<>();
        }
        carriages.add(railwayCarriage);
        railwayCarriage.setTrain(this);
    }

    public void addDrive(Drive drive) {
        if ( drives == null ) {
            drives = new HashSet<>();
        }
        drives.add(drive);
        drive.setTrain(this);
    }
}
