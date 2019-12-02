package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextListener;

import org.springframework.util.MultiValueMap;

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
    private VehicleRegistry vehicleRegistry = new VehicleRegistry();
    private DeliveryController deliveryController = new DeliveryController();
    
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
    
    public List<VehiclePark> onComputeRequestReceived(Map<String, String> datemap) {
    	ArrayList<Order> orders = dbService.getOrders(datemap.get("date"));
    	ArrayList<VehiclePark> vehicleParks = vehicleRegistry.getVehicleParks();
    	
        List<VehiclePark> vPs = Allocator.compute(vehicleParks, orders);
		vPs.forEach((vP) -> System.out.println(vP.toString()));
		deliveryController.calculate(vPs);
		
//		System.out.println("\nRESULTS:\n");
//		for (VehiclePark vp : vPs) {
//			System.out.println("VEHICLEPARK ID:");
//			System.out.println(vp.getID());
//			System.out.println("VEHICLEPARK orders:");
//			System.out.println(vp.getOrders());
//			for (Vehicle v : vp.getVehicles()) {
//				System.out.println("VEHICLE ID:");
//				System.out.println(v.getID());
//				System.out.println("VEHICLE orders:");
//				System.out.println(v.getOrders());
//				System.out.println("VEHICLEPARK path:");
//				System.out.println(v.getPath());
//				System.out.println("VEHICLEPARK pathlength:");
//				System.out.println(v.getPathLength());
//				}
//		}
		System.out.println("deliveryController ended.");
		return vehicleParks;
    }
    
    public String onOrderDeleteRequest(int id) {
    	return dbService.deleteOrder(id);
    }
}