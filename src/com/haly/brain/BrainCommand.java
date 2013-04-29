package com.haly.brain;

public enum BrainCommand
{
    NOP("NOP"),
    OPEN("Open"),
    CLOSE("Close"),
    SEE("See"),
    NOT_SEE("Don't See"),
    HELP("Help"),
    CALL("Call"),
    FIND("Find"),
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
