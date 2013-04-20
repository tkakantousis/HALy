package com.haly.brain;

public enum BrainStatus
{
    OK("OK"),
    ERROR("ERROR");

    private String descr;
    
    private BrainStatus(String descr) {
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
