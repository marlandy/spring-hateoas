package com.autentia.tutorial.springhateoas.soccer.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PlayerShortInfo {

    private final String name;

    public PlayerShortInfo(String name) {
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
        if (!(o instanceof PlayerShortInfo)) {
            return false;
        }

        final PlayerShortInfo other = (PlayerShortInfo) o;
        return new EqualsBuilder().append(this.name, other.getName()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.name).hashCode();
    }

    @Override
    public String toString() {
        return "PlayerShortInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
