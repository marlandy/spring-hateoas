package com.autentia.tutorial.springhateoas.soccer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Stadiums {

    private List<Stadium> stadiums;

    private Stadiums() {
        this.stadiums = null;
    }

    public Stadiums(List<Stadium> stadiums) {
        this.stadiums = stadiums;
    }

    @XmlElement(name = "stadium")
    public List<Stadium> getStadiums() {
        return stadiums;
    }

    public void setStadiums(List<Stadium> stadiums) {
        this.stadiums = stadiums;
    }
}
