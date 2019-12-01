package bme.szoftarch.spring.model;

public class OrderCoordinate extends Coordinate {

	private int id;

	public OrderCoordinate(float latitude, float longitude, int id) {
		super(latitude, longitude);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(OrderCoordinate oc) {
		if (getLatitude() == oc.getLatitude() && getLongitude() == oc.getLongitude()) {
			return true;
		} else {
			return false;
		}
	}
}
