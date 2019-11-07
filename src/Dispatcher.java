import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Dispatcher {
    private static Dispatcher dispatcher;
    private ArrayList<Pack> packs = new ArrayList<>();
    private static OrderFactory orderFactory = OrderFactory.getInstance();
    private ActivationQueue activationQueue = new ActivationQueue();

    public static VehicleRegistry getVehicleRegistry() {
        return vehicleRegistry;
    }

    private static VehicleRegistry vehicleRegistry = new VehicleRegistry();
    private static Allocator allocator = Allocator.getInstance();
    private static DeliveryController deliveryController;

    private Dispatcher(){}

    public static Dispatcher getInstance(){
        if(dispatcher == null){
            dispatcher = new Dispatcher();
        }
        return dispatcher;
    }

    public void onPackArrived(Pack p){
        packs.add(p);
        packs.sort(new DateComparator());
    }

    public void onPackReceived(Map<String, String> map){
        Pack p = orderFactory.createPack(map);
        activationQueue.addPack(p);
    }

    public void onComputeRequestReceived(){
        Queue<Pack> packs = activationQueue.getPacks();
        ArrayList<VehiclePark> vehicleParks = vehicleRegistry.getVehicleParks();
        Map<Pack, VehiclePark> map = Allocator.compute(vehicleParks, packs);

        map.forEach((key, value) -> System.out.println(key.getID() + " " + value.getID()));

        //deliveryController.calculate(map);
    }
}
