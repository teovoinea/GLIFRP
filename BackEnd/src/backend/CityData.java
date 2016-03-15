package backend;

public class CityData 
{
	private int zip;
	private double lat, lng;
	private String state, city;
	
	public int getZip() {
		return zip;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public CityData(String zip, String state, String city, String lat, String lng)
	{
		this.zip = Integer.parseInt(zip);
		this.state = state;
		this.city = city;
		this.lat = Double.parseDouble(lat);
		this.lng = Double.parseDouble(lng);
	}
}
