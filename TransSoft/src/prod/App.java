package prod;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class App {

    private static Dispatcher dispatcher;
    public App() {
    	System.out.println("Constructor of App");
    	init();
    }
    private static void init(){
        dispatcher = Dispatcher.getInstance();
        VehicleRegistry vehicleRegistry = Dispatcher.getVehicleRegistry();
        VehiclePark vehiclePark_1 = new VehiclePark();
        VehiclePark vehiclePark_2 = new VehiclePark();
        VehiclePark vehiclePark_3 = new VehiclePark();

        vehiclePark_1.setID(1);
        vehiclePark_2.setID(2);
        vehiclePark_3.setID(3);

        vehiclePark_1.setLocation(new float[]{47.474980f, 19.049293f});
        vehiclePark_2.setLocation(new float[]{47.537709f, 21.651271f});
        vehiclePark_3.setLocation(new float[]{46.446274f, 16.994947f});

        Vehicle vehicle_1 = new Vehicle(100.0, 1000.0, 1, 120);
        Vehicle vehicle_2 = new Vehicle(200.0, 2000.0, 2, 100);
        Vehicle vehicle_3 = new Vehicle(300.0, 3000.0, 3, 80);

        vehiclePark_1.addVehicle(vehicle_1);
        vehiclePark_2.addVehicle(vehicle_2);
        vehiclePark_3.addVehicle(vehicle_3);

        vehicleRegistry.addVehiclePark(vehiclePark_1);
        vehicleRegistry.addVehiclePark(vehiclePark_2);
        vehicleRegistry.addVehiclePark(vehiclePark_3);

        //Pack pack_1 = new Pack(1.0, 1.0,
        //        new float[]{47.473980f, 19.049393f}, new float[]{47.475980f, 19.049193f},
        //        LocalDate.of(2019, 11, 10));
        //Pack pack_2 = new Pack(2.0, 2.0,
        //        new float[]{47.538709f, 21.652271f}, new float[]{47.537609f, 21.650271f},
        //        LocalDate.of(2019, 11, 11));
        //Pack pack_3 = new Pack(3.0, 3.0,
        //        new float[]{46.447274f, 16.995947f}, new float[]{46.445274f, 16.993947f},
        //        LocalDate.of(2019, 11, 12));

        
        // pack_1.setID(1);
        // pack_2.setID(2);
        // pack_3.setID(3);
        //
        // dispatcher.onPackArrived(pack_1);
        // dispatcher.onPackArrived(pack_2);
        // dispatcher.onPackArrived(pack_3);

    }

    private static void run(){

        Map<String, String> order_1 = new HashMap<>();
        order_1.put("ID", "1");
        order_1.put("volume", "1.0");
        order_1.put("mass", "1.0");
        order_1.put("from_lat", "47.473980");
        order_1.put("from_lon", "19.049393");
        order_1.put("to_lat", "47.475980");
        order_1.put("to_lon", "19.049193");
        order_1.put("year", "2019");
        order_1.put("month", "11");
        order_1.put("day", "10");

        Map<String, String> order_2 = new HashMap<>();
        order_2.put("ID", "2");
        order_2.put("volume", "2.0");
        order_2.put("mass", "2.0");
        order_2.put("from_lat", "47.538709");
        order_2.put("from_lon", "21.652271");
        order_2.put("to_lat", "47.537609");
        order_2.put("to_lon", "21.650271");
        order_2.put("year", "2019");
        order_2.put("month", "11");
        order_2.put("day", "11");

        Map<String, String> order_3 = new HashMap<>();
        order_3.put("ID", "3");
        order_3.put("volume", "3.0");
        order_3.put("mass", "3.0");
        order_3.put("from_lat", "46.447274");
        order_3.put("from_lon", "21.652271");
        order_3.put("to_lat", "46.445274");
        order_3.put("to_lon", "16.993947");
        order_3.put("year", "2019");
        order_3.put("month", "11");
        order_3.put("day", "12");

        OrderEvent oe_1 = new OrderEvent();
        oe_1.setEvent(order_1);
        oe_1.setOrderEventEnum(OrderEventEnum.NEW_ORDER);

        OrderEvent oe_2 = new OrderEvent();
        oe_2.setEvent(order_2);
        oe_2.setOrderEventEnum(OrderEventEnum.NEW_ORDER);

        OrderEvent oe_3 = new OrderEvent();
        oe_3.setEvent(order_3);
        oe_3.setOrderEventEnum(OrderEventEnum.NEW_ORDER);

        OrderEventHandler oeh = new OrderEventHandler();

        oeh.handle(oe_1);
        oeh.handle(oe_2);
        oeh.handle(oe_3);

        OrderEvent oe_compute = new OrderEvent();
        oe_compute.setOrderEventEnum(OrderEventEnum.COMPUTE_REQUEST);
        oeh.handle(oe_compute);
    }

    //public static void main(String[] args){
    //    init();
    //    run();

        //Map<Pack, VehiclePark> map = dispatcher.compute();

        //map.forEach((key, value) -> System.out.println(key.getID() + " " + value.getID()));
    //}
    public void onReq(String ID, String from_lat, String from_lon, String to_lat, String to_lon, String mass, String volume, String date) {
    	// run();
    	Map<String, String> order_1 = new HashMap<>();
        order_1.put("ID", ID);
        order_1.put("volume", volume);
        order_1.put("mass", mass);
        order_1.put("from_lat", from_lat);
        order_1.put("from_lon", from_lon);
        order_1.put("to_lat", to_lat);
        order_1.put("to_lon", to_lon);
        
        order_1.put("year", "2019");
        order_1.put("month", "11");
        order_1.put("day", "10");
        
        OrderEvent oe_1 = new OrderEvent();
        oe_1.setEvent(order_1);
        oe_1.setOrderEventEnum(OrderEventEnum.NEW_ORDER);
        
        OrderEventHandler oeh = new OrderEventHandler();
        oeh.handle(oe_1);
    }
    
    public void onComputeReq() {
    	OrderEvent oe_compute = new OrderEvent();
    	oe_compute.setOrderEventEnum(OrderEventEnum.COMPUTE_REQUEST);
    	
    	OrderEventHandler oeh = new OrderEventHandler();
    	oeh.handle(oe_compute);
    }
}
