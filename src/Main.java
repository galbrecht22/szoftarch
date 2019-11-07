import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        Controller controller = new Controller();
        VehiclePark vehiclePark_1 = new VehiclePark();
        VehiclePark vehiclePark_2 = new VehiclePark();
        VehiclePark vehiclePark_3 = new VehiclePark();

        vehiclePark_1.setId(1);
        vehiclePark_2.setId(2);
        vehiclePark_3.setId(3);

        vehiclePark_1.setLocation(new float[]{47.474980f, 19.049293f});
        vehiclePark_2.setLocation(new float[]{47.537709f, 21.651271f});
        vehiclePark_3.setLocation(new float[]{46.446274f, 16.994947f});

        Vehicle vehicle_1 = new Vehicle(100.0, 1000.0, 1, 120);
        Vehicle vehicle_2 = new Vehicle(200.0, 2000.0, 2, 100);
        Vehicle vehicle_3 = new Vehicle(300.0, 3000.0, 3, 80);

        vehiclePark_1.addVehicle(vehicle_1);
        vehiclePark_2.addVehicle(vehicle_2);
        vehiclePark_3.addVehicle(vehicle_3);

        Pack pack_1 = new Pack(1.0, 1.0,
                new float[]{47.473980f, 19.049393f}, new float[]{47.475980f, 19.049193f},
                LocalDate.of(2019, 11, 10));
        Pack pack_2 = new Pack(2.0, 2.0,
                new float[]{47.538709f, 21.652271f}, new float[]{47.537609f, 21.650271f},
                LocalDate.of(2019, 11, 11));

        Pack pack_3 = new Pack(3.0, 3.0,
                new float[]{46.447274f, 16.995947f}, new float[]{46.445274f, 16.993947f},
                LocalDate.of(2019, 11, 12));

        pack_1.setId(1);
        pack_2.setId(2);
        pack_3.setId(3);

        controller.addVehiclePark(vehiclePark_1);
        controller.addVehiclePark(vehiclePark_2);
        controller.addVehiclePark(vehiclePark_3);

        controller.onPackArrived(pack_1);
        controller.onPackArrived(pack_2);
        controller.onPackArrived(pack_3);

        Map<Pack, VehiclePark> map = controller.compute();

        map.forEach((key, value) -> System.out.println(key.getId() + " " + value.getId()));

    }
}
