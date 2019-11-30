package prod;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

public class VehiclePark {
	private ArrayList<Vehicle> vehicles;
	private Coordinate location;
	private Queue<Order> queue = new ArrayDeque<>();
	private int id;

	public VehiclePark() {
		vehicles = new ArrayList<>();
	}

	public int getID() {
		return id;
	}

	public void setID(final int id) {
		this.id = id;
	}

	public void addPackToQueue(Order o) {
		queue.add(o);
	}

	public Coordinate getLocation() {
		return location;
	}

	public void setLocation(final Coordinate location) {
		this.location = location;
	}

	public void addVehicle(Vehicle v) {
		vehicles.add(v);
	}

	public void addVehicles(final Collection<Vehicle> col) {
		vehicles.addAll(col);
	}

	public void removeAllVehicles() {
		vehicles.removeAll(vehicles);
	}

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(ArrayList<Vehicle> vehicles) {
		removeAllVehicles();
		this.vehicles.addAll(vehicles);
	}

	public Vehicle getVehicleById(int id) {
		for (Vehicle v : vehicles) {
			if (v.getID() == id) {
				return v;
			}
		}
		return null;
	}
}
