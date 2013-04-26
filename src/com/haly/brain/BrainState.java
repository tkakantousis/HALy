package com.haly.brain;

public enum BrainState
{
    IDLE("Idle");

    private String descr;
    
    private BrainState(String descr) {
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
