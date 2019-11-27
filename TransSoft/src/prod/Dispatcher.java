package prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class Dispatcher {
    private static Dispatcher dispatcher;
    private ArrayList<Order> orders = new ArrayList<>();
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

    public void onPackArrived(Order o){
    	orders.add(o);
    	orders.sort(new DateComparator());
    }

    public void onPackReceived(Map<String, String> map){
        Order o = orderFactory.createOrder(map);
        activationQueue.addOrder(o);
    }

    public void onComputeRequestReceived(){
        Queue<Order> orders = activationQueue.getOrders();
        ArrayList<VehiclePark> vehicleParks = vehicleRegistry.getVehicleParks();
        Map<Order, VehiclePark> map = Allocator.compute(vehicleParks, orders);

        map.forEach((key, value) -> System.out.println(key.getID() + " " + value.getID()));

        //deliveryController.calculate(map);
    }
}
