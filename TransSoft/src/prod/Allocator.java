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
            for(VehiclePark v: vehicleParks) {
                final Coordinate loc = v.getLocation();
                float forth = DistanceCalculator.distFrom(loc.getLatitude(), loc.getLongitude(), from.getLatitude(), from.getLongitude());
                float transport = DistanceCalculator.distFrom(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
                float back = DistanceCalculator.distFrom(to.getLatitude(), to.getLongitude(), loc.getLatitude(), loc.getLongitude());
                float distance = forth + transport + back;

                if(!map.containsKey(order)){
                    minDistance = distance;
                    map.put(order, v);
                }
                else if(distance < minDistance){
                    minDistance = distance;
                    map.put(order, v);
                }
            }
        }
        return map;
    }
}
