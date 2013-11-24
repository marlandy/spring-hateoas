package com.autentia.tutorial.springhateoas.soccer.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class StadiumShortInfo {

    private final String name;

    public StadiumShortInfo(String name) {
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
        return new EqualsBuilder().append(this.name, other.getName()).isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.name).hashCode();
    }

    @Override
    public String toString() {
        return "StadiumShortInfo{" +
                "name='" + name + '\'' +
                '}';
    }
}
