package prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static List<VehiclePark> compute(ArrayList<VehiclePark> vehicleParks, ArrayList<Order> orders){
        for(Order order: orders) {
            float minDistance = Float.MAX_VALUE;
            final Coordinate from = order.getFrom();
            final Coordinate to = order.getTo();
            VehiclePark vp = null;
            for(VehiclePark park: vehicleParks) {
                final Coordinate loc = park.getLocation();
                final float forth = DistanceCalculator.distFrom(loc, from);
                final float transport = DistanceCalculator.distFrom(from, to);
                final float back = DistanceCalculator.distFrom(to, loc);
                final float distance = forth + transport + back;

                if(distance < minDistance){
                    minDistance = distance;
                    vp = park;
                }
            }
            vp.addOrderToQueue(order);
        }
        return vehicleParks;
    }
}
