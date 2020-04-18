package pl.edu.agh.soa.soap.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Train {

    @XmlElement(name = "id")
    private long id;

    @XmlElementWrapper(name = "carriages")
    @XmlElement(name = "carriage")
    private List<RailwayCarriage> carriages;

    @XmlElement(name = "logoPath")
    private String logoPath;
}
