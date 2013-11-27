package com.autentia.tutorial.springhateoas.soccer.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "stadium")
@XmlAccessorType(XmlAccessType.FIELD)
public class StadiumShortInfo extends ResourceSupport{

    @XmlTransient
    @JsonIgnore
    private int idStadium;

    private String name;

    private StadiumShortInfo() {
        this.name = "";
    }

    public StadiumShortInfo(int idStadium, String name) {
        this.idStadium = idStadium;
        this.name = name;
    }

    public StadiumShortInfo(String name) {
        this.name = name;
    }

    public int getIdStadium() {
        return idStadium;
    }

    public void setIdStadium(int idStadium) {
        this.idStadium = idStadium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StadiumShortInfo)) {
            return false;
        }

        final StadiumShortInfo other = (StadiumShortInfo) o;
        return new EqualsBuilder().append(this.idStadium, other.getIdStadium()).
                append(this.name, other.getName()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.idStadium).
                append(this.name).hashCode();
    }

    @Override
    public String toString() {
        return "StadiumShortInfo{" +
                "idStadium=" + idStadium +
                ", name='" + name + '\'' +
                '}';
    }
}
