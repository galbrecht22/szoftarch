package prod;

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

}
