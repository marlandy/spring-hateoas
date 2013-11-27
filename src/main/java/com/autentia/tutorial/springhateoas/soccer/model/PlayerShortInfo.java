package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerShortInfo extends ResourceSupport {

    @XmlTransient
    @JsonIgnore
    private int idPlayer;

    private String name;

    public PlayerShortInfo() {
        this.name = null;
    }

    public PlayerShortInfo(int idPlayer, String name) {
        this.idPlayer = idPlayer;
        this.name = name;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public PlayerShortInfo(String name) {
        this.name = name;
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
        if (!(o instanceof PlayerShortInfo)) {
            return false;
        }

        final PlayerShortInfo other = (PlayerShortInfo) o;
        return new EqualsBuilder().append(this.idPlayer, other.getIdPlayer()).
                append(this.name, other.getName()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.idPlayer).
                append(this.name).hashCode();
    }

    @Override
    public String toString() {
        return "PlayerShortInfo{" +
                "idPlayer=" + idPlayer +
                ", name='" + name + '\'' +
                '}';
    }
}
