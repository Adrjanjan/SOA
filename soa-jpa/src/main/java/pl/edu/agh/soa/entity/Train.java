package pl.edu.agh.soa.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue
    private String id;

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
    private Set<RailwayCarriage> carriages;

    @Column(name = "logo_path")
    private String logoPath;

    @OneToMany(mappedBy = "train")
    private Set<Drive> drive;
}

























