package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamShortInfo extends ResourceSupport {

    @XmlTransient
    @JsonIgnore
    private int idTeam;

    private String name;

    public TeamShortInfo() {
    }

    public TeamShortInfo(String name) {
        this.name = name;
    }

    public TeamShortInfo(int idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamShortInfo)) {
            return false;
        }

        final TeamShortInfo other = (TeamShortInfo) o;
        return new EqualsBuilder().append(this.idTeam, other.getIdTeam()).
                append(this.name, other.getName()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.idTeam).append(this.name).hashCode();
    }

    @Override
    public String toString() {
        return "TeamShortInfo{" +
                "idTeam=" + idTeam +
                ", name='" + name + '\'' +
                '}';
    }
}
