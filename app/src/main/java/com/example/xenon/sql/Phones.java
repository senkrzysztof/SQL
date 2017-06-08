package com.example.xenon.sql;

/**
 * Created by xenon on 08.06.17.
 */

public class Phones {
    private long id;
    private String description;
    private double number;

    public Phones(long id, String description, double number) {
        this.id = id;
        this.description = description;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
