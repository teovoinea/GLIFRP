package backend;
import java.io.InputStream;
import java.net.URL.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import spark.utils.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class OpenStreetMapWrapper {
	private static final String reverse_url =  "http://nominatim.openstreetmap.org/reverse"; //for reverse searching with lat + lon
	private static final String search_url  =  "http://nominatim.openstreetmap.org/search";  //for searching when you have a name/zip code/state
	private static final String charset = "UTF-8"; 											 //charset for the request

	//attributes that can be stored
	private static String zip = "";
	private static String lat = "";
	private static String lon = "";
	private static String name = "";
	private static String state = "";
	
	public OpenStreetMapWrapper(){
		
	}
	
	/**
	 * Build City Information by Zip Code
	 * @param zip_code Zip Code of location
	 */
	public void buildByZip(String zip_code){
		try{
			//query attributes for openstreetmap
			//postalcode 		  -> zipcode to search for
			//countrycodes = us   -> only look in the US
			//limit = 1 		  -> only want the most relevant location
			//addressdetails = 1  -> request detailed information
			//format = json 	  -> response should be in json
			//polygon_geojson = 1 -> for extensibility if we want to make polygons from json
			//country = us 		  -> only look in the US
			String query = String.format("postalcode=%s&countrycodes=us&limit=1&addressdetails=1&format=json&polygon_geojson=1&country=us",URLEncoder.encode(zip_code, charset));
			URLConnection connection = new URL(search_url + "?" + query).openConnection(); //create request
			connection.setRequestProperty("Accepted-Charset", charset); //make sure we received the correct charset
			InputStream response = connection.getInputStream(); //receive input as input stream
			String in = IOUtils.toString(response); //turn response to string
			JsonArray jarray = (JsonArray) new JsonParser().parse(in); //use gson to parse string response
			JsonObject jobject = jarray.get(0).getAsJsonObject(); //get the first element (most relevant & should be only element because limit=1)
			String[] res = {jobject.get("lat").getAsString(), jobject.get("lon").getAsString()}; //parse latitude and longitude into a string array
			//System.out.println("Found lat: " + res[0] + " and lon: " + res[1] + " from zip code: " + zip_code);
			lat = res[0]; //store lat
			lon = res[1]; //store lon
			JsonObject address = jobject.getAsJsonObject("address");
			name = address.get("city").getAsString(); //store city name
			state = address.get("state").getAsString(); //store state name
		}
		catch (Exception e){ //catch errors
			e.printStackTrace();
		}
	}
	
	/**
	 * Build City information by latitude and longitude
	 * @param latlong [latitude,longitude]
	 */
	public void buildByLatLong(String[] latlong){
		try{
			//query attributes for openstreetmap
			//format = json 	 -> response should be in json
			//lat 				 -> set latitude
			//lon 				 -> set longitude
			//limit = 1 		 -> only want the most relevant location
			//addressdetails = 1 -> request detailed information
			//country = us 		 -> only look in the US
			String query = String.format("format=json&lat=%s&lon=%s&limit=1&addressdetails=1&country=us", URLEncoder.encode(latlong[0], charset), URLEncoder.encode(latlong[1], charset));
			URLConnection connection = new URL(reverse_url + "?" + query).openConnection(); //create request
			connection.setRequestProperty("Accepted-Charset", charset); //make sure we received the correct charset
			InputStream response = connection.getInputStream(); //receive input as input stream
			String in = IOUtils.toString(response); //turn response to string
			JsonObject jobject = new JsonParser().parse(in).getAsJsonObject(); //use gson to parse string response
			JsonObject address = jobject.getAsJsonObject("address");
			zip = address.get("postcode").getAsString(); //store zip code
			name = address.get("city").getAsString();    //store city name
			state = address.get("state").getAsString();  //store state name
		}
		catch (Exception e){ //catch errors
			e.printStackTrace();
		}
	}

	/**
	 * Build City information by city name and city state
	 * @param city_name Name of city
	 * @param city_state Name of state
	 */
	public void buildByCityState(String city_name, String city_state){
		try{
			//query attributes for openstreetmap
			//format = json 	 -> response should be in json
			//city 				 -> name of city
			//state 			 -> name of state
			//limit = 1 		 -> only want the most relevant location
			//addressdetails = 1 -> request detailed information
			//country = us 		 -> only look in the US
			String query = String.format("city=%s&state=%s&limit=1&addressdetails=1&format=json&country=us", URLEncoder.encode(city_name, charset), URLEncoder.encode(city_state, charset));
			URLConnection connection = new URL(search_url + "?" + query).openConnection(); //create request
			connection.setRequestProperty("Accepted-Charset", charset); //make sure we received the correct charset
			InputStream response = connection.getInputStream(); //receive input as input stream
			String in  = IOUtils.toString(response); //turn response to string
			JsonArray jarray = (JsonArray) new JsonParser().parse(in); //use gson to parse string response
			JsonObject jobject = jarray.get(0).getAsJsonObject();
			lat = jobject.get("lat").getAsString(); //store lat
			lon = jobject.get("lon").getAsString(); //store lon
			JsonObject address = jobject.getAsJsonObject("address");
			name = address.get("city").getAsString();//store city
			state = address.get("state").getAsString();//store state
		}
		catch (Exception e){ //catch errors
			e.printStackTrace();
		}
	}

	//------------------------GETTERS-------------------------------
	/**
	 * Returns zip code
	 * @return zip code as String
	 */
	public String getZip(){
		return zip;
	}

	/**
	 * Returns latitude
	 * @return latitude as String
	 */
	public String getLat(){
		return lat;
	}

	/**
	 * Returns longitude
	 * @return longitude as String
	 */
	public String getLon(){
		return lon;
	}

	/**
	 * Returns city name
	 * @return city name as String
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns state name
	 * @return state name as String
	 */
	public String getState(){
		return state;
	}
}
