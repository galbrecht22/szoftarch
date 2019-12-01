package bme.szoftarch.spring.model;

public class Coordinate {
	
	private final float lat;
	private final float lon;
	
	public Coordinate(final float latitude, final float longitude) {
		lat = latitude;
		lon = longitude;
	}

	public float getLongitude() {
		return lon;
	}

	public float getLatitude() {
		return lat;
	}

	public String toString() {
		return "Coordinate, latitude: " + lat  + ", longitude: " + lon + " ";  
	}

	public boolean equals(OrderCoordinate oc) {
		return (getLatitude() == oc.getLatitude() && getLongitude() == oc.getLongitude());
	}
}