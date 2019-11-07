import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private ArrayList<VehiclePark> vehicleParks = new ArrayList<>();
    private ArrayList<Pack> packs = new ArrayList<>();

    public void onPackArrived(Pack p){
        packs.add(p);
        packs.sort(new DateComparator());
    }

    public void addVehiclePark(VehiclePark p){
        vehicleParks.add(p);
    }

    public Map<Pack, VehiclePark> compute(){
        Map<Pack, VehiclePark> map = new HashMap<>();
        for(Pack p: packs) {
            float minDistance = Float.MAX_VALUE;
            float[] from = p.getFrom();
            float[] to = p.getTo();
            for(VehiclePark v: vehicleParks) {
                float[] location = v.getLocation();
                float forth = distFrom(location[0], location[1], from[0], from[1]);
                float transport = distFrom(from[0], from[1], to[0], to[1]);
                float back = distFrom(to[0], to[1], location[0], location[1]);
                float distance = forth + transport + back;

                if(!map.containsKey(p)){
                    minDistance = distance;
                    map.put(p, v);
                }
                else if(distance < minDistance){
                    minDistance = distance;
                    map.put(p, v);
                }
            }
        }
        return map;
    }

    private static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (float) (earthRadius * c);
    }
}
