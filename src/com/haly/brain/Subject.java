package com.haly.brain;

public enum Subject
{
    NONE("None"),
    WINDOW("Window"),
    DOOR("Door"),
    LIGHTS("Lights"),
    POLICE("Police"),
    DEVICE("Device"),
    USER("User"),
    SYSTEM("System");

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
