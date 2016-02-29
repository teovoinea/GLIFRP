import com.google.gson.annotations.SerializedName;


public class City implements Comparable<City>{
	@SerializedName("lat")
	private String lat;
	@SerializedName("lon")
	private String lon;
	@SerializedName("zip")
	private String zip;
	
	public City(String lati, String longi){
		lat = lati;
		lon = longi;
	}
	
	public City(String zip_code){
		zip = zip_code;
	}
	
	@Override
	public int compareTo(City o){
		return  0;
	}
	
	public String getLat(){
		return lat;
	}
	
	public String getLong(){
		return lon;
	}
	
	public String getZip(){
		return zip;
	}
}
