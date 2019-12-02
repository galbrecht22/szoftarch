package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VehiclePark {
	private ArrayList<Vehicle> vehicles;
	private Coordinate location;
	private List<Order> orders;
	private int id;

	public VehiclePark() {
		vehicles = new ArrayList<Vehicle>();
		orders = new ArrayList<Order>();
	}

	public int getID() {
		return id;
	}

	public void setID(final int id) {
		this.id = id;
	}

	public void addOrderToQueue(Order o) {
		orders.add(o);
	}

	public void addOrders(final Collection<Order> orders) {
		this.orders.addAll(orders);
	}

	public List<Order> getOrders() {
		return orders;
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

	private void addVehicles(final Collection<Vehicle> col) {
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

	public int getUnusedVehicles() {
		int unused = 0;
		for(Vehicle v : vehicles) {
			if(v.getMax_mass() == v.getRemainingMass()) {
				unused++;
			}
		}
		return unused;
	}

	public VehiclePark copy() {
		final VehiclePark vp = new VehiclePark();
		vp.setID(this.getID());
		vp.setLocation(this.getLocation());
		vp.addVehicles(this.getVehicles());
		vp.addOrders(this.getOrders());
		return vp;
	}

	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(getID()).append(getLocation());
		for(Order o : orders) {
			builder.append(o.getID());
		}
		return builder.toString();
	}
}