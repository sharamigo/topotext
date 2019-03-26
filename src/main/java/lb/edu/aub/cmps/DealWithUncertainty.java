package lb.edu.aub.cmps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.geonames.Toponym;
import org.geonames.WebService;

public class DealWithUncertainty {
	
	public int directionLocationCounter = 0;

	/**
	 * 
	 * @param  List<String> textAsList  text from the TextField
	 * @return double distance          distance from the given text    
	 */
	public List<Double> getDistanceFromText(List<String> textAsList) {
		
		
		List<Double> distance = new ArrayList<Double>();
		double tmp_distance;
		String entfernung;
		
		for (int i=0; i < textAsList.size(); i++) {
			String currentItem = textAsList.get(i);
			switch(currentItem) {
				case "kilometers":
				case "Kilometers":
				case "KIOLMETERS":
					entfernung = textAsList.get(i-1);
					distance.add(Double.parseDouble(entfernung));
					break;
				case "miles":
				case "Miles":
				case "MILES":
					entfernung = textAsList.get(i-1);
					tmp_distance = Double.parseDouble(entfernung);
					tmp_distance = tmp_distance * 1.60934;
					distance.add(tmp_distance);
					break;
			}
		}
		
		return distance;
	}

	/**
	 * This method deals with the given text from an array-list and translates
	 * it into a direction in grad (degrees) as on a compass.
	 * I.e: 0 degrees means north, 90 degrees means east, 180 means south, 270 means west
	 * As we have north-west or some other classifications like that, we can split
	 * it up to 45-degrees classifications or 22.5 degrees if we granulate finer (NNW or SSE)
	 * 
	 * 								(0°)
	 *                               N 
	 * 						 NNW		   NNE
	 * 					 NW						NE
	 *
	 * 					W (270°)				  E (90°)
	 * 					
	 * 					SW						SE
	 * 						SSW			  SSE
	 * 								S 
	 * 							  (180°)					
	 * 
	 * 
	 * @param  List<String> textAsList   text from the TextField
	 * @return double  directionGrad     the translated direction in grad (°)
	 */
	public List<Double> getDirectionFromText(List<String> textAsList) {
		
		
		List<Double> directionGrad = new ArrayList<Double>();
		//run textAsList in a loop
		for (int i=0; i < textAsList.size(); i++) {
			String currentItem = textAsList.get(i);
			switch(currentItem) {
			case "north":
			case "northerly":
				directionGrad.add(0.0);				
				break;
			case "north-north-east":
				directionGrad.add(22.5);				
				break;
			case "north-east":
				directionGrad.add(45.0);				
				break;
			case "east-north-east":
				directionGrad.add(67.5);				
				break;
			case "east":
			case "easterly":
				directionGrad.add(90.0);				
				break;
			case "east-south-east":
				directionGrad.add(112.5);				
				break;
			case "south-east":
				directionGrad.add(135.0);				
				break;
			case "south-south-east":
				directionGrad.add(157.5);				
				break;
			case "south":
			case "southerly":
				directionGrad.add(180.0);				
				break;
			case "south-south-west":
				directionGrad.add(202.5);				
				break;
			case "south-west":
				directionGrad.add(225.0);				
				break;
			case "west-south-west":
				directionGrad.add(247.5);				
				break;
			case "west":
			case "westerly":
				directionGrad.add(270.0);				
				break;
			case "west-north-west":
				directionGrad.add(292.5);				
				break;
			case "north-west":
				directionGrad.add(315.0);				
				break;
			case "north-north-west":
				directionGrad.add(337.5);				
				break;
			}
		}
		System.out.println(this.directionLocationCounter);
		setDirectionLocationCounter(this.directionLocationCounter);
		return directionGrad;
	}
	
	
	/**
	 * showing up nearest places from a given point using a webservice (geonames.org)
	 * 
	 * @param latitude    x-coordinate (latitude) of the given point
	 * @param longitude   y-coordinate (longitude) of the given point
	 * @param radius 
	 * @see   https://www.geonames.org/source-code/javadoc/src-html/org/geonames/WebService.html#line.718    
	 * @return String[]  array with 20 (maxRows) results from webservice findNearbyPlaceName
	 */
	public String[] getPlacenamesNearby(double latitude, double longitude, double radius) {
		
		int maxRows = 20;
		WebService.setUserName("julia94"); // add your username here
		
		List<String> placesNearby = new ArrayList<String>();
		
		try {
			List<Toponym> places = WebService.findNearbyPlaceName(latitude, longitude, radius, maxRows);
			List<String> names = new ArrayList<String>();
			
			for (Toponym placenames : places) {
				//System.out.println(placenames);
				names.add(placenames.getName());
				
			}
			placesNearby.addAll(names);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			placesNearby.add(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] locationsnames_array = new String[placesNearby.size()];
		int i = 0;
		for(String location: placesNearby){
			locationsnames_array[i] = location; 
			i++;
		}
		return locationsnames_array;
		
		
	}
	
	
	/**
	 * showing up nearest places from a given point using a webservice (geonames.org)
	 * 
	 * @param latitude    x-coordinate (latitude) of the given point
	 * @param longitude   y-coordinate (longitude) of the given point
	 * @return String[]   array with results from webservice findNearbyPlaceName
	 * @see   https://www.geonames.org/source-code/javadoc/org/geonames/WebService.html#findNearbyPlaceName-double-double-double-int-
	 * 
	 */
    public String[] getPlacenamesNearby(double latitude, double longitude) {
		
		WebService.setUserName("julia94"); // add your username here
		
		List<String> placesNearby = new ArrayList<String>();
		
		try {
			List<Toponym> places = WebService.findNearbyPlaceName(latitude, longitude);
			List<String> names = new ArrayList<String>();
			
			for (Toponym placenames : places) {
				//System.out.println(placenames);
				names.add(placenames.getName());
				
			}
			placesNearby.addAll(names);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			placesNearby.add(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] locationsnames_array = new String[placesNearby.size()];
		int i = 0;
		for(String location: placesNearby){
			locationsnames_array[i] = location; 
			i++;
		}
		return locationsnames_array;
		
		
	}
    
    
    public void countNumberOfPlaces(List<String> textAsList) {
    	
    	//ArrayList<String> directionalWords = new ArrayList<String>();
    	List<String> directionalWords = Arrays.asList("north", "northerly", "north-north-east",
    			"north-east", "east-north-east", "east", "easterly", "east-south-east","south-east", "south-south-east",
    			"south", "southerly", "south-south-west", "south-west", "west-south-west", "west", "westerly",
    			"west-north-west", "north-west", "north-north-west"
    			);
    	//run textAsList in a loop
    	for (int i=0; i < textAsList.size(); i++) {
    		String currentItem = textAsList.get(i);
    		if (directionalWords.contains(currentItem)) {
    			this.directionLocationCounter++;
    		}
    	}
    }
		
	/**
	 * this methods converts geo-coordinates from decimal into grad-output
	 * how this should work, see here: 
	 * http://www.mwegner.de/geo/geo-koordinaten/umrechnung-grad-minute-sekunde-dezimalgrad.html
	 * 
	 * @param   x		latitude of a given point (in decimal format)
	 * @param   y		longitude of a given point (in decimal format)
	 * @return	String	formated output of geo-coordinates in Grad° Min' Sek.XXX''
	 * @see		http://www.mwegner.de/geo/geo-koordinaten/umrechnung-grad-minute-sekunde-dezimalgrad.html
	 */
    public String DecToGrad(double x, double y) {
		String formatedOutput;
		String directionalSignX, directionalSignY;
		//first get the values before the decimal point
		int gradLatitude = (int) x;
		int gradLongitude = (int) y;
		//then calculate the minutes-value of each coordinate		
		double valueMinutesLat = (x - gradLatitude) * 60;
		double valueMinutesLon = (y - gradLongitude) * 60;
		
		//get the values before the decimal point of the minute-value
		int minuteLatitude = (int) valueMinutesLat;
		int minuteLongitude = (int) valueMinutesLon;
		//finally,calculate the seconds-value of each coordinate
		double valueSecondsLat = (valueMinutesLat - minuteLatitude) * 60;
		double valueSecondsLon = (valueMinutesLon - minuteLongitude) * 60;
		String formatedSecondsLat = String.format("%.4f", (double)valueSecondsLat);
		String formatedSecondsLon = String.format("%.4f", (double)valueSecondsLon);
		
		if (x < 0) {
			directionalSignX = "S";
		} else {
			directionalSignX = "N";
		}
		if (y < 0) {
			directionalSignY = "W";
		} else {
			directionalSignY = "E";
		}
		formatedOutput = gradLatitude + "&#186; " + minuteLatitude + "\' " + formatedSecondsLat + "\'\'"  + directionalSignX + 
				    " " + gradLongitude + "&#186; " + minuteLongitude + "\' " + formatedSecondsLon + "\'\'" + directionalSignY;
		
		return formatedOutput;
	}
		

	public int getDirectionLocationCounter() {
		return this.directionLocationCounter;
	}

	public void setDirectionLocationCounter(int directionLocationCounter) {
		this.directionLocationCounter = directionLocationCounter;
	}

	
    
    

}
