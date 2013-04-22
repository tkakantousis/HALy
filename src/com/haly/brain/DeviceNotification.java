package com.haly.brain;

public enum DeviceNotification
{
    HELP("HELP");

    private String descr;
    
    private DeviceNotification(String descr) {
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
