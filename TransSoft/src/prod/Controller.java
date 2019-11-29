package prod;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletContextListener;

public class Controller implements ServletContextListener {
	
    private static Controller Controller;

	private Controller(){}

    public static Controller getInstance(){
        if(Controller == null){
            Controller = new Controller();
        }
        return Controller;
    }
    
    private DBService dbService = new DBService();
    private OrderFactory orderFactory = new OrderFactory();
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
        
    public void onOrderReceived(Map<String, String> orderParams) {
    	dbService.insertOrder(orderParams);
    }
    
    public void onComputeRequestReceived() {
    	ArrayList<Order> orders = dbService.getOrders();
    	ArrayList<VehiclePark> vehicleParks = vehicleRegistry.getVehicleParks();
    	
        Map<Order, VehiclePark> map = Allocator.compute(vehicleParks, orders);
        
        map.forEach((key, value) -> System.out.println(key.getID() + " " + value.getID()));

    }
}
