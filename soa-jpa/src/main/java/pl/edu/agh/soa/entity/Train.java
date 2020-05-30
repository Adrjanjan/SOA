package pl.edu.agh.soa.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
    private Set<RailwayCarriage> carriages;

    @Column(name = "logo_path")
    private String logoPath;

    @OneToMany(mappedBy = "train")
    private Set<Drive> drive;
}

























