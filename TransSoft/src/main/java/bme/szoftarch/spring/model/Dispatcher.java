package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContextListener;

public class Dispatcher implements ServletContextListener {
	
    private static Dispatcher Dispatcher;

	private Dispatcher(){}

    public static Dispatcher getInstance(){
        if(Dispatcher == null){
            Dispatcher = new Dispatcher();
        }
        return Dispatcher;
    }
    
    private DBService dbService = new DBService();
    //private OrderFactory orderFactory = new OrderFactory();
    private VehicleRegistry vehicleRegistry = new VehicleRegistry();
    
    public void loadModel() {
    	ArrayList<VehiclePark> vehicleParks = dbService.loadVehicleParks();
    	ArrayList<Vehicle> vehicles = dbService.loadVehicles();
    	
    	for(VehiclePark vp : vehicleParks) {
        	vehicleRegistry.addVehiclePark(vp);
    	}
    	
    	for(Vehicle v : vehicles) {
    		VehiclePark vp = vehicleRegistry.getVehicleParkById(v.getVehiclePark_ID());
     	    vp.addVehicle(v);
    	}
    	
    	for(VehiclePark vp : vehicleRegistry.getVehicleParks()) {
     	   System.out.println("VehiclePark " + vp.getID());
     	   for(Vehicle v : vp.getVehicles()) {
         	   System.out.println("Vehicle " + v.getID());
     	   }
        }
    }
        
    public Map<String, String> onOrderReceived(Map<String, String> orderParams) {
    	String resultID = dbService.insertOrder(orderParams);
    	orderParams.put("id", resultID);
    	return orderParams;
    }
    
    public void onComputeRequestReceived(Map<String, String> datemap) {
    	ArrayList<Order> orders = dbService.getOrders(datemap.get("date"));
    	ArrayList<VehiclePark> vehicleParks = vehicleRegistry.getVehicleParks();
    	
        Map<Order, VehiclePark> map = Allocator.compute(vehicleParks, orders);
        
        map.forEach((key, value) -> System.out.println(key.getID() + " " + value.getID()));
    }
}
