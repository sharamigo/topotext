package lb.edu.aub.cmps.maps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import lb.edu.aub.cmps.DealWithUncertainty;
import lb.edu.aub.cmps.GeoLocation;

public class LeafletMap implements GenerateMap{

	public void generateMap(String path, List<GeoLocation> locs, String title, String[] nearPlaces) 
			throws FileNotFoundException {
		PrintStream out = new PrintStream(new File(path));
		DealWithUncertainty dwu = new DealWithUncertainty();
		
		
		//head
		//head
				String head = "<!DOCTYPE html>\n"+
		"<html>\n"+
		"<head>\n"+
			
			"<title>Your map</title>\n"+

			"<meta charset=\"utf-8\" />\n"+
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"+
			
			"<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"docs/images/favicon.ico\" />"+

			"<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.0.3/dist/leaflet.css\" />\n"+
			"<script src=\"https://unpkg.com/leaflet@1.0.3/dist/leaflet.js\"></script>\n"+
			
			"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
			"<script src=\"" + path + "tabbedContent.js\"></script>\n" +
			"<link rel=\"stylesheet\" href=\"" + path + "tabs.css\" />\n" +

			
		"</head>\n"+
		"<body>\n"+



"<div id=\"mapid\" style=\"width: 100%; height:100%; position: fixed;top: 0;left: 0; height: 100%; z-index:1;\"></div>"+
		"<script>\n"+

			"var map = L.map('mapid').setView([51.505, -0.09], 2);\n"+

			"L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {\n"+
				"maxZoom: 18,\n"+
				"attribution: 'Map data &copy; <a href=\"http://openstreetmap.org\">OpenStreetMap</a> contributors, ' +\n"+
					"'<a href=\"http://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, ' +\n"+
					"'Imagery � <a href=\"http://mapbox.com\">Mapbox</a>',\n"+
				"id: 'mapbox.streets'\n"+
			"}).addTo(map);\n";
		out.print(head);
		for(GeoLocation loc: locs){
						
			NumberFormat formatter = new DecimalFormat("#0.00000");
			
			//String x = formatter.format(loc.getX());
			//String y = formatter.format(loc.getY());
			double x = loc.getX();
			double y = loc.getY();
			String anno = ": " +loc.getAnnotation();
			String formatedCoordinates = dwu.DecToGrad(loc.getX(), loc.getY());			
			
			
			
			//showing a labeling for a second point on map-marker if location-name is null
			if (loc.getAnnotation() == null) { 
				if (loc.getLocation_name() == null) {
					anno = "New point";
				} else {
					anno = loc.getLocation_name() + "";
				}
				
			} else {
				anno = loc.getLocation_name() +anno;
			}
			//if(anno != null && anno.length()!=0)
			//added x and y-coordinates in output-marker on map
				out.print("\nL.marker(["+x+", "+y+"]).addTo(map).bindPopup(\"<dl><dt><strong>" +anno+ "</strong></dt><dd>  Lat: "+ formatter.format(x)+ " Lon: " +formatter.format(y)+ "</dd><dt>Formatted output: </dt><dd>" + formatedCoordinates +  "<dd>\").openPopup();");
			
		}
		
		//getJSTabbedContent(path);
		out.print("\nfunction openCity(evt, cityName) {\r\n" + 
				"    var i, tabcontent, tablinks;\r\n" + 
				"    tabcontent = document.getElementsByClassName(\"tabcontent\");\r\n" + 
				"    for (i = 0; i < tabcontent.length; i++) {\r\n" + 
				"        tabcontent[i].style.display = \"none\";\r\n" + 
				"    }\r\n" + 
				"    tablinks = document.getElementsByClassName(\"tablinks\");\r\n" + 
				"    for (i = 0; i < tablinks.length; i++) {\r\n" + 
				"        tablinks[i].className = tablinks[i].className.replace(\" active\", \"\");\r\n" + 
				"    }\r\n" + 
				"    document.getElementById(cityName).style.display = \"block\";\r\n" + 
				"    evt.currentTarget.className += \" active\";\r\n" + 
				"}");
		
		String tail = "\n</script>";
		String tail2 = "\n</body>\n</html>";
		out.print(tail);
		
		
		
		
		//display all near places of first point in a box
		out.print("\n<div id=\"nearby-box\" style=\"z-index:1000; background-color: white; opacity: 0.7; width: 200px; position: relative; top: 50px;\">\n");
		
		//showing tabs
		out.print("\n<div class=\"tab\">");
		for (int k=0; k < locs.size(); k++) {
			String placename = (locs.get(k).getLocation_name() != null) ? locs.get(k).getLocation_name() : "Point" + k;
			out.print("\n<button class=\"tablinks\" onclick=\"openCity(event, '"+placename+"')\">"+ placename +"</button>\n");
		}
		out.print("</div>\n");
		
		//default showing near places of first point
		out.print("<div id=\""+ locs.get(0).getLocation_name()+ "\" class=\"tabcontent\">\n");
		out.print("<strong>Nearby places towards " + locs.get(0).getLocation_name() + "</strong>\n");
		out.print("<ul>\n");
		for (int i=0; i < nearPlaces.length; i++) {
			out.print("<li>" + nearPlaces[i] + "</li>\n");
			
		}
		out.print("</ul>\n");
		out.print("</div>\n");
		
		
		//showing nearby places of the other points displayed on the map...
		for (int counter=1; counter < locs.size(); counter++) {
			String[] placesNearby = dwu.getPlacenamesNearby(locs.get(counter).getX(), locs.get(counter).getY(), 50);
			String placename = (locs.get(counter).getLocation_name() != null) ? locs.get(counter).getLocation_name() : "Point" + counter;
		    
			out.print("<div id=\""+ placename + "\" class=\"tabcontent\">\n");
		    out.print("<strong>Nearby places towards " + placename + "</strong>\n");
		    out.print("<ul>\n");
		    for (int i=0; i < placesNearby.length; i++) {
		    	out.print("<li>" + placesNearby[i] + "</li>\n");
			
		    }
		    out.print("</ul>\n");
		    out.print("</div>\n");
		}
		
		
		
		out.print("</div>\n");
		out.print("<style>\r\n" + 
				"body {font-family: Arial;}\r\n" + 
				"\r\n" + 
				"/* Style the tab */\r\n" + 
				".tab {\r\n" + 
				"    overflow: hidden;\r\n" + 
				"    border: 1px solid #ccc;\r\n" + 
				"    background-color: #f1f1f1;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"/* Style the buttons inside the tab */\r\n" + 
				".tab button {\r\n" + 
				"    background-color: inherit;\r\n" + 
				"    float: left;\r\n" + 
				"    border: none;\r\n" + 
				"    outline: none;\r\n" + 
				"    cursor: pointer;\r\n" + 
				"    padding: 14px 16px;\r\n" + 
				"    transition: 0.3s;\r\n" + 
				"    font-size: 17px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"/* Change background color of buttons on hover */\r\n" + 
				".tab button:hover {\r\n" + 
				"    background-color: #ddd;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"/* Create an active/current tablink class */\r\n" + 
				".tab button.active {\r\n" + 
				"    background-color: #ccc;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"/* Style the tab content */\r\n" + 
				".tabcontent {\r\n" + 
				"    display: none;\r\n" + 
				"    padding: 6px 12px;\r\n" + 
				"    border: 1px solid #ccc;\r\n" + 
				"    border-top: none;\r\n" + 
				"}\r\n" + 
				"</style>");
					
		
		out.print(tail2);
		out.flush();
		out.close();
		
	}

	
	public void generateMapWithWeights(String path, List<GeoLocation> locs, String title) throws FileNotFoundException {
		PrintStream out = new PrintStream(new File(path));
		//head
		String head = "<!DOCTYPE html>\n"+
				"<html>\n"+
				"<head>\n"+
					
					"<title>Your map</title>\n"+

					"<meta charset=\"utf-8\" />\n"+
					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"+
					
					"<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"docs/images/favicon.ico\" />"+

					"<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.0.3/dist/leaflet.css\" />\n"+
					"<script src=\"https://unpkg.com/leaflet@1.0.3/dist/leaflet.js\"></script>\n"+


					
				"</head>\n"+
				"<body>\n"+



		"<div id=\"mapid\" style=\"width: 100%; height:100%; position: fixed;top: 0;left: 0; height: 100%;\"></div>"+
				"<script>\n"+

					"var map = L.map('mapid').setView([51.505, -0.09], 2);\n"+

					"L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {\n"+
						"maxZoom: 18,\n"+
						"attribution: 'Map data &copy; <a href=\"http://openstreetmap.org\">OpenStreetMap</a> contributors, ' +\n"+
							"'<a href=\"http://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, ' +\n"+
							"'Imagery � <a href=\"http://mapbox.com\">Mapbox</a>',\n"+
						"id: 'mapbox.streets'\n"+
					"}).addTo(map);\n";
		out.print(head);
		//int max = getMaxWeight(locs);
		for(GeoLocation loc: locs){
			double x = loc.getX();
			double y = loc.getY();
			double w = loc.getWeight();
			/*
			//FIXME here
			double ratio = Math.sqrt(w) *1.0/ Math.sqrt(max);
			System.out.println(ratio+"----------");
			double radius = (ratio * ratio)* 1500000;*/
			double radius = (w *4000 > 700000)? 700000: w*4000;
			
			System.out.println(radius);
			String s = (w ==1)? "": "s";
			
			String anno = ": " +loc.getAnnotation();
			if (loc.getAnnotation() == null) anno = "";
			anno = loc.getLocation_name() +anno;
			
			anno = anno+" ("+(int)w+" time"+s+").";
			out.print("\nL.circle(["+x+", "+y+"], " +(radius)+", { color: \'blue\', fillColor : \'#30f\', fillOpacity: 0.3}).addTo(map).bindPopup(\""+anno+"\").openPopup();");
		}
		String tail = "\n</script>\n</body>\n</html>";
		out.print(tail);
		out.flush();
		out.close();
		
	}

	
	public int getMaxWeight(List<GeoLocation> locs){
		if(locs.size() == 0) return 1;
		int max = locs.get(0).getWeight();
		for(int i = 1; i < locs.size(); i++){
			if(locs.get(i).getWeight() > max) max= locs.get(i).getWeight();
		}
		return max;
	}		
	
}
