public class Vehicle {
    private double max_volume;
    private double max_mass;
    private long vehicle_id;
    private int max_speed;

    public Vehicle(double max_volume, double max_mass, long vehicle_id, int max_speed) {
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

    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }

}
