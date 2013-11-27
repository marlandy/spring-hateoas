package com.autentia.tutorial.springhateoas.soccer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Players {

    private List<Player> players;

    private Players() {
        this.players = null;
    }

    public Players(List<Player> players) {
        this.players = players;
    }

    @XmlElement(name = "player")
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
