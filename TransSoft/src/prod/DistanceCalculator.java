package prod;

public interface DistanceCalculator {

	static float distFrom(final float lat1, final float lng1, final float lat2, final float lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (float) (earthRadius * c);
	}

	static float distFrom(final Coordinate from, final Coordinate to) {
		return distFrom(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
	}
}
