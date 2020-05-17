package pl.edu.agh.soa.model;

import java.util.List;

import javax.xml.bind.annotation.*;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    private long id;

    private List<RailwayCarriage> carriages;

    private String logoPath;
}
