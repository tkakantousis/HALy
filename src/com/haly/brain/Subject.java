package com.haly.brain;

public enum Subject
{
    NONE("None"),
    WINDOW("Window"),
    DOOR("Door"),
    LIGHTS("Lights"),
    OVEN("Oven"),
    BOILER("Boiler"),
    TENT("Tent"),
    MUSIC("Music"),
    FRIDGE("Fridge"),
    VENTILATOR("Ventilator"),
    USER("User");

    private String descr;
    
    private Subject(String descr) {
        this.descr = descr;
    }

    public String getDescr() {
        return this.descr;
    }

    public void setDescr(String s) {
        this.descr = s;
    }

    @Override
    public String toString() {
        return descr;
    }
}
