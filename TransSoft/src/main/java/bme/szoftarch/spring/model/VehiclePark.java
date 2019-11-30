package bme.szoftarch.spring.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class VehiclePark {
    private ArrayList<Vehicle> vehicles;
    private float[] location;
    private Queue<Order> queue = new ArrayDeque<>();

    private int id;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public VehiclePark(){
        vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle v){
        vehicles.add(v);
    }

    public float[] getLocation() {
        return location;
    }

    public void setLocation(float[] location) {
        this.location = location;
    }

    public void addPackToQueue(Order o){
        queue.add(o);
    }

    public Vehicle getVehicleById(int id){
        for(Vehicle v: vehicles){
            if(v.getID() == id){
                return v;
            }
        }
        return null;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
