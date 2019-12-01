package bme.szoftarch.spring.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
	private double max_volume;
	private double max_mass;
	private int vehicle_id;
	private int max_speed;
	private int vehiclePark_id;
	private List<Order> orders;
	private List<OrderCoordinate> path;

	public Vehicle(final double max_volume, final double max_mass, final int vehicle_id, final int max_speed) {
		this.max_volume = max_volume;
		this.max_mass = max_mass;
		this.vehicle_id = vehicle_id;
		this.max_speed = max_speed;
		this.orders = new ArrayList<Order>();
		this.path = new ArrayList<OrderCoordinate>();
	}

	public void setVehiclePark_ID(final int vp_id) {
		vehiclePark_id = vp_id;
	}

	public int getVehiclePark_ID() {
		return vehiclePark_id;
	}

	public double getMax_volume() {
		return max_volume;
	}

	public void setMax_volume(final double max_volume) {
		this.max_volume = max_volume;
	}

	public double getMax_mass() {
		return max_mass;
	}

	public void setMax_mass(final double max_mass) {
		this.max_mass = max_mass;
	}

	public int getID() {
		return vehicle_id;
	}

	public void setID(final int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public int getMax_speed() {
		return max_speed;
	}

	public void setMax_speed(final int max_speed) {
		this.max_speed = max_speed;
	}

	public void addOrder(final Order order) {
		this.orders.add(order);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setPath(final List<OrderCoordinate> path) {
		this.path = path;
	}

	public List<OrderCoordinate> getPath() {
		return path;
	}

	public float getPathLength() {
		float distance = 0;
		for(int i = 0; i < path.size() - 1; i++) {
			distance += DistanceCalculator.distFrom(path.get(i), path.get(i + 1));
		}
		return distance;
	}

	public double getRemainingMass() {
		double currentMass = 0;
		for(Order o : orders) {
			currentMass += o.getMass();
		}
		return max_mass - currentMass;
	}

	public double getRemainingVolume() {
		double currentVolume = 0;
		for(Order o : orders) {
			currentVolume += o.getVolume();
		}
		return max_volume - currentVolume;
	}
}