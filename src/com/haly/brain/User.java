package com.haly.brain;

import Luxand.FSDK.FSDK_FaceTemplate;
import Luxand.FSDK.FSDK_FaceTemplate.ByReference;
import java.util.List;


public class User implements Comparable
{
    private int ID;
    private String name;
    private List<FSDK_FaceTemplate.ByReference> biometrics;

    public User(int ID, String name, List<FSDK_FaceTemplate.ByReference> biometrics) {
        this.ID = ID;
        this.name = name;
        this.biometrics = biometrics;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ByReference> getBiometrics() {
        return biometrics;
    }

    public void setBiometrics(List<ByReference> biometrics) {
        this.biometrics = biometrics;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", name=" + name + ", biometrics=" + biometrics.size() + '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }

        User u = (User) o;
        return this.getID() - u.getID();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.ID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.ID != other.ID) {
            return false;
        }
        return true;
    }
    
    
}
