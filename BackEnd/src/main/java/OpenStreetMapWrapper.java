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
	private static final String reverse_url =  "http://nominatim.openstreetmap.org/reverse";
	private static final String search_url  =  "http://nominatim.openstreetmap.org/search";
	private static final String charset = "UTF-8";

	private static String zip = "";
	private static String lat = "";
	private static String lon = "";
	private static String name = "";
	private static String state = "";
	
	public OpenStreetMapWrapper(){
		
	}
	
	public void buildByZip(String zip_code){
		try{
			String query = String.format("postalcode=%s&countrycodes=us&limit=1&addressdetails=1&format=json&polygon_geojson=1&country=us",URLEncoder.encode(zip_code, charset));
			URLConnection connection = new URL(search_url + "?" + query).openConnection();
			connection.setRequestProperty("Accepted-Charset", charset);
			InputStream response = connection.getInputStream();
			String in = IOUtils.toString(response);
			JsonArray jarray = (JsonArray) new JsonParser().parse(in);
			JsonObject jobject = jarray.get(0).getAsJsonObject();
			String[] res = {jobject.get("lat").getAsString(), jobject.get("lon").getAsString()};
			System.out.println("Found lat: " + res[0] + " and lon: " + res[1] + " from zip code: " + zip_code);
			lat = res[0];
			lon = res[1];
			JsonObject address = jobject.getAsJsonObject("address");
			name = address.get("city").getAsString();
			state = address.get("state").getAsString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void buildByLatLong(String[] latlong){
		try{
			String query = String.format("format=json&lat=%s&lon=%s&limit=1&addressdetails=1&country=us", URLEncoder.encode(latlong[0], charset), URLEncoder.encode(latlong[1], charset));
			URLConnection connection = new URL(reverse_url + "?" + query).openConnection();
			connection.setRequestProperty("Accepted-Charset", charset);
			InputStream response = connection.getInputStream();
			String in = IOUtils.toString(response);
			JsonObject jobject = new JsonParser().parse(in).getAsJsonObject();
			JsonObject address = jobject.getAsJsonObject("address");
			zip = address.get("postcode").getAsString();
			name = address.get("city").getAsString();
			state = address.get("state").getAsString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void buildByCityState(String city_name, String city_state){
		try{
			String query = String.format("city=%s&state=%s&limit=1&addressdetails=1&format=json&country=us", URLEncoder.encode(city_name, charset), URLEncoder.encode(city_state, charset));
			URLConnection connection = new URL(search_url + "?" + query).openConnection();
			connection.setRequestProperty("Accepted-Charset", charset);
			InputStream response = connection.getInputStream();
			String in  = IOUtils.toString(response);
			JsonArray jarray = (JsonArray) new JsonParser().parse(in);
			JsonObject jobject = jarray.get(0).getAsJsonObject();
			lat = jobject.get("lat").getAsString();
			lon = jobject.get("lon").getAsString();
			JsonObject address = jobject.getAsJsonObject("address");
			name = address.get("city").getAsString();
			state = address.get("state").getAsString();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	//------------------------GETTERS-------------------------------
	public String getZip(){
		return zip;
	}

	public String getLat(){
		return lat;
	}

	public String getLon(){
		return lon;
	}

	public String getName(){
		return name;
	}

	public String getState(){
		return state;
	}
}
