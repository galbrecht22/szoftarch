package bme.szoftarch.spring.model;

import java.time.LocalDate;
import java.util.Comparator;

public class Order {
    private int id;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    private double volume;
    private double mass;
    private float[] from;
    private float[] to;
    private LocalDate when;

    public Order(double volume, double mass, float[] from, float[] to, LocalDate when) {
        this.volume = volume;
        this.mass = mass;
        this.from = from;
        this.to = to;
        this.when = when;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public float[] getFrom() {
        return from;
    }

    public void setFrom(float[] from) {
        this.from = from;
    }

    public float[] getTo() {
        return to;
    }

    public void setTo(float[] to) {
        this.to = to;
    }

    public LocalDate getWhen() {
        return when;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }
}
