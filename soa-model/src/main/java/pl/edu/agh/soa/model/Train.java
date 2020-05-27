package pl.edu.agh.soa.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@ToString
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Train {

    @XmlElement(name = "id")
    private long id;

    @XmlElementWrapper(name = "carriages")
    @XmlElement(name = "carriages")
    private List<RailwayCarriage> carriages;

    @XmlElement(name = "logoPath")
    private String logoPath;

    @XmlElementWrapper(name = "drives")
    @XmlElement(name = "drives")
    private Set<Drive> drive;
}
