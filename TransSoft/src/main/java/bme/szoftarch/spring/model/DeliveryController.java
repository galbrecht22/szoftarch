package bme.szoftarch.spring.model;

import java.util.HashMap;
import java.util.Map;

public class DeliveryController implements DistanceCalculator{
    public void calculate(Map<Order, VehiclePark> map){}

    private void scheduleOrders(HashMap<Order, VehiclePark> map){
        for(Map.Entry<Order, VehiclePark> entry : map.entrySet()){

        }
    }
}

