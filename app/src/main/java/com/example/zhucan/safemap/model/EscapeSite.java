package com.example.zhucan.safemap.model;

/**
 * Created by zhucan on 2017/4/27.
 */

public class EscapeSite {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getCoord() {
        return coord;
    }

    public void setCoord(double[] coord) {
        this.coord = coord;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    private String name;
    private double[] coord;
    private int peopleCount;

    public EscapeSite(String name, double[] coord, int peopleCount) {
        this.name = name;
        this.coord = coord;
        this.peopleCount = peopleCount;
    }
}
