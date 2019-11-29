package prod;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Allocator implements DistanceCalculator{

    private static Allocator allocator;
    private Allocator(){}

    public static Allocator getInstance(){
        if(allocator == null){
            allocator = new Allocator();
        }
        return allocator;
    }

    public static Map<Order, VehiclePark> compute(ArrayList<VehiclePark> vehicleParks, ArrayList<Order> orders){
        Map<Order, VehiclePark> map = new HashMap<>();
        for(Order o: orders) {
            float minDistance = Float.MAX_VALUE;
            float[] from = o.getFrom();
            float[] to = o.getTo();
            for(VehiclePark v: vehicleParks) {
                float[] location = v.getLocation();
                float forth = DistanceCalculator.distFrom(location[0], location[1], from[0], from[1]);
                float transport = DistanceCalculator.distFrom(from[0], from[1], to[0], to[1]);
                float back = DistanceCalculator.distFrom(to[0], to[1], location[0], location[1]);
                float distance = forth + transport + back;

                if(!map.containsKey(o)){
                    minDistance = distance;
                    map.put(o, v);
                }
                else if(distance < minDistance){
                    minDistance = distance;
                    map.put(o, v);
                }
            }
        }
        return map;
    }
}
