package com.haly.eyes;

public enum EyeState
{
    RECOGNIZING("Recognizing Face"),
    RECORDING("Recording Face");

    private String descr;
    
    private EyeState(String descr) {
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
