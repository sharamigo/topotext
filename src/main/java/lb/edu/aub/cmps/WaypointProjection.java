package lb.edu.aub.cmps;



public class WaypointProjection  {

	
	private double lon;
	private double lat;
	
	private WaypointProjection(double lon, double lat) { 
		this.lon = lon; 
		this.lat = lat; 
	}
	
	public WaypointProjection() {
		// TODO Auto-generated constructor stub
	}

		
	
	/**
	 * calculate-Function
	 * calculates the new coordinates of a given point and a distance to the new unknown 
	 * point.
	 * @param double  degrees  direction in degrees (e.g. 90 degrees means east)
	 * @param double  distance the distance from point A to point B (in kilometers)
	 * @return WaypointProjection-Object with new coordinates 
	 */
//	public WaypointProjection calculate(double lat, double lon, double degrees, double distance) {
//		
//		double d = Math.toRadians(distance / 111.2);
//		double lat1 = Math.toRadians(lat);
//		double lon1 = Math.toRadians(lon);
//		double rad = Math.toRadians(degrees);
//		
//		double lat2 = Math.asin(Math.sin(lat1)*Math.cos(d) + 
//				Math.cos(lat1)*Math.sin(d)*Math.cos(rad));
//		double lon2 = lon1 + Math.asin(Math.sin(d) / Math.cos(lat2) * Math.sin(rad));
//		
//		double latitudePoint2 = Math.toDegrees(lat2);
//		double longitudePoint2 = Math.toDegrees(lon2);
//		
//		WaypointProjection point2 = new WaypointProjection(latitudePoint2,longitudePoint2);
//		return point2;
//	}
	
	
	/**
	 * calculate-Function
	 * calculates the new coordinates of a given point and a distance to the new unknown 
	 * point.
	 * @param double  degrees  direction in degrees (e.g. 90 degrees means east)
	 * @param double  distance the distance from point A to point B (in kilometers)
	 * @return WaypointProjection-Object with new coordinates 
	 * 	
	 */
	public GeoLocation calculateNewPoint(double lat, double lon, double degrees, double distance) {
		
		double d = Math.toRadians(distance / 111.2);
		double lat1 = Math.toRadians(lat);
		double lon1 = Math.toRadians(lon);
		double rad = Math.toRadians(degrees);
		
		double lat2 = Math.asin(Math.sin(lat1)*Math.cos(d) + 
				Math.cos(lat1)*Math.sin(d)*Math.cos(rad));
		double lon2 = lon1 + Math.asin(Math.sin(d) / Math.cos(lat2) * Math.sin(rad));
		
		double latitudePoint2 = Math.toDegrees(lat2);
		double longitudePoint2 = Math.toDegrees(lon2);
		
		GeoLocation point2 = new GeoLocation(latitudePoint2,longitudePoint2);
		return point2;
	}
	
	
//	public WaypointProjection translate(double lat, double lon, double degrees, double distance) {
//        double rad = Math.toRadians(degrees);
//        double dist = Math.toRadians(distance / 111.2);
//        double newLat = Math.asin(Math.sin(lat)*Math.cos(dist) +
//                Math.cos(lat)*Math.sin(dist)*Math.cos(rad));
//        double newLon = lon + Math.asin(Math.sin(dist) /
//                Math.cos(newLat) * Math.sin(rad));
//        return new WaypointProjection(newLon, newLat);
//    }
	
	
	

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaypointProjection [lon=");
		builder.append(lon);
		builder.append(", lat=");
		builder.append(lat);
		builder.append("]");
		return builder.toString();
	}
	
	
}
