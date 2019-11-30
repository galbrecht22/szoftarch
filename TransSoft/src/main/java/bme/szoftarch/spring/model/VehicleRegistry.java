package bme.szoftarch.spring.model;

import java.util.ArrayList;

public class VehicleRegistry {
    private ArrayList<VehiclePark> vehicleParks;

    public VehicleRegistry(){
        vehicleParks = new ArrayList<>();
    }

    public void addVehiclePark(VehiclePark vp){
        vehicleParks.add(vp);
    }

    public VehiclePark getVehiclePark(int index){
        return vehicleParks.get(index);
    }

    public VehiclePark getVehicleParkById(int id){
        for(VehiclePark vp: vehicleParks){
            if(vp.getID() == id){
                return vp;
            }
        }
        return null;
    }

    public ArrayList<VehiclePark> getVehicleParks(){
        return vehicleParks;
    }
}
