package pl.edu.agh.soa.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RailwayCarriage {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "registrationNumber")
    private String registrationNumber;

    @XmlElement(name = "available")
    private boolean available;
}
