package pl.edu.agh.soa.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@Builder
@ToString
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Train {

    @XmlElement(name = "id")
    private Long id;

    @XmlElementWrapper(name = "carriages")
    @XmlElement(name = "carriages")
    private List<RailwayCarriage> carriages;

    @XmlElement(name = "logoPath")
    private String logoPath;

}
