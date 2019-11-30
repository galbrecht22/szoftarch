package prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        for(Order order: orders) {
            float minDistance = Float.MAX_VALUE;
            final Coordinate from = order.getFrom();
            final Coordinate to = order.getTo();
            for(VehiclePark vp: vehicleParks) {
                final Coordinate loc = vp.getLocation();
                final float forth = DistanceCalculator.distFrom(loc, from);
                final float transport = DistanceCalculator.distFrom(from, to);
                final float back = DistanceCalculator.distFrom(to, loc);
                final float distance = forth + transport + back;

                if(!map.containsKey(order)){
                    minDistance = distance;
                    map.put(order, vp);
                }
                else if(distance < minDistance){
                    minDistance = distance;
                    map.put(order, vp);
                }
            }
        }
        return map;
    }
}
