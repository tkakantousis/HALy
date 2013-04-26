package com.haly.brain;

public enum BrainCommand
{
    NOP("NOP"),
    OPEN("Open"),
    CLOSE("Close"),
    ON("On"),
    OFF("Off"),
    SEE("See"),
    NOT_SEE("Don't See"),
    HELP("Help"),
    CALL("Call"),
    LOCATE("Locate"),
    REGISTER("Register"),
    SHUTDOWN("Shutdown");

    private String descr;
    
    private BrainCommand(String descr) {
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
