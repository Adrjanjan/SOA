package pl.edu.agh.soa.soap.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RailwayCarriage {

    @XmlElement(name = "id")
    private long id;

    @XmlElement(name = "registrationNumber")
    private String registrationNumber;

    @XmlElement(name = "isAvailable")
    private boolean isAvailable;
}
