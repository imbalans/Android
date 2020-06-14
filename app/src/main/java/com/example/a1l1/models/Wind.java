package com.example.a1l1.models;

public class Wind {
    private double speed;
    private int deg;

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getSpeed() {
        return (int) speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
