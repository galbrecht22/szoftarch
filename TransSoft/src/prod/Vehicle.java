package prod;

public class Vehicle {
	private double max_volume;
	private double max_mass;
	private int vehicle_id;
	private int max_speed;
	private int vehiclePark_id;

	public void setVehiclePark_ID(int vp_id) {
		vehiclePark_id = vp_id;
	}

	public int getVehiclePark_ID() {
		return vehiclePark_id;
	}

	public Vehicle(double max_volume, double max_mass, int vehicle_id, int max_speed) {
		this.max_volume = max_volume;
		this.max_mass = max_mass;
		this.vehicle_id = vehicle_id;
		this.max_speed = max_speed;
	}

	public double getMax_volume() {
		return max_volume;
	}

	public void setMax_volume(double max_volume) {
		this.max_volume = max_volume;
	}

	public double getMax_mass() {
		return max_mass;
	}

	public void setMax_mass(double max_mass) {
		this.max_mass = max_mass;
	}

	public int getID() {
		return vehicle_id;
	}

	public void setID(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public int getMax_speed() {
		return max_speed;
	}

	public void setMax_speed(int max_speed) {
		this.max_speed = max_speed;
	}

}
