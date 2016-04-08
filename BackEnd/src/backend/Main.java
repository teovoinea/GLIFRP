package backend;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.*;

public class Main {
    private static Usa usa; 
    private Gson gson = new Gson();
    private static OpenStreetMapWrapper mapwrap = new OpenStreetMapWrapper();
    public static Query q = new Query();
    
    /**
     * Main entrance to the program, starts the server
     * @param args arguments when you run the program
     */
    public static void main(String[] args) {
        usa = new Usa();
        staticFileLocation("/public");
        /**
         * Loads the index.html
         */
        get("/", (request, res) -> {
        	System.out.println(request.requestMethod() + " request on " + request.url() + " from ip " + request.ip() + " with user agent " + request.userAgent() + "\nRequest Body:\n" + request.body());
        	res.redirect("/index.html");
        	return "";
        });
    
        get("/dankmeme", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        /**
         * Searches for city based on hops 
         */
        post("/search", (request, response) ->{
        	System.out.println(request.requestMethod() + " request on " + request.url() + " from ip " + request.ip() + " with user agent " + request.userAgent() + "\nRequest Body:\n" + request.body());
        	try{
	        	Gson receive_object = new GsonBuilder().create();
	        	JsonObject jobject = new JsonParser().parse(request.body()).getAsJsonObject();
	        	City rec = null;
	        	int count = 0;
	        	if (jobject.has("count")){
	        		count = jobject.get("count").getAsInt();
	        	}else{
	        		Gson return_object= new GsonBuilder().create();
	        		response.type("application/javascript");
	        		response.status(200);
	        		return return_object.toJson(null);
	        	}
	        	String city;
	        	String state = null;
	        	double crime;
	        	double price;
	        	int distance = 0;
	        	if (jobject.has("city")){
	        		city = jobject.get("city").getAsString();
	        	}
	        	if (jobject.has("state")){
	        		state = jobject.get("state").getAsString();	        		
	        	}
	        	if (jobject.has("crime")){
	        		crime = jobject.get("crime").getAsDouble();	        		
	        	}
	        	if (jobject.has("price")){
	        		price = jobject.get("price").getAsDouble();	        		
	        	}
	        	if (jobject.has("distance")){
	        		distance = jobject.get("distance").getAsInt();	        		
	        	}
         
                ArrayList<City> hopCities = usa.findLowestCrimeRate(usa.getNeighbouringStates(usa.findStateByStateName(state), distance),count);
                
                ArrayList<City> final_out = new ArrayList<City>();
                for (City c : hopCities){
                	mapwrap.buildByCityState(c.getName(), c.getUState());
                    c.setLat(mapwrap.getLat());
                    c.setLong(mapwrap.getLon());
                    c.setScore();
                    final_out.add(q.getCityByCrime(c));
                }
	        	Gson return_object= new GsonBuilder().create();
        		response.type("application/javascript");
        		response.status(200);
        		return return_object.toJson(final_out);
        	}
        	catch (Exception e){
        		response.type("text/plain");
        		response.status(500);
        		e.printStackTrace();
        		return e.toString();
        	}
        });
    
    /**
     * Searches for city based on lat/long
     */
    post("/searchLCMByState", (request, response) ->{
    	System.out.println(request.requestMethod() + " request on " + request.url() + " from ip " + request.ip() + " with user agent " + request.userAgent() + "\nRequest Body:\n" + request.body());
    	try{
        	Gson receive_object = new GsonBuilder().create();
        	JsonObject jobject = new JsonParser().parse(request.body()).getAsJsonObject();
        	City rec = null;
        	int count = 0;
        	String state = null;
        	if (jobject.has("count") && jobject.has("state")){
        		count = jobject.get("count").getAsInt();
        		state = jobject.get("state").getAsString();
        	}else{
        		Gson return_object= new GsonBuilder().create();
        		response.type("application/javascript");
        		response.status(200);
        		return return_object.toJson(null);
        	}
        	
        	String city;
	       	double crime;
	       	double price;
	       	double distance;
	       	if (jobject.has("city")){
	       		city = jobject.get("city").getAsString();
	       	}
	       	if (jobject.has("state")){
	       		state = jobject.get("state").getAsString();	        		
	       	}
	       	if (jobject.has("crime")){
	       		crime = jobject.get("crime").getAsDouble();	        		
	       	}
	       	if (jobject.has("price")){
	       		price = jobject.get("price").getAsDouble();	        		
	       	}
	       	if (jobject.has("distance")){
	        	distance = jobject.get("distance").getAsDouble();	        		
	        }	

        	ArrayList<City> c= usa.findStateByStateName(state).findLowestCrimeRate(count);
        	for (int i = 0; i < c.size(); i++){
        		//System.out.println(c.get(i).getCity());
                mapwrap.buildByCityState(c.get(i).getPlace_name(), c.get(i).getState());
                c.get(i).setLat(mapwrap.getLat());
                c.get(i).setLong(mapwrap.getLon());
            }
        	City[] cities2 = c.toArray(new City[c.size()]);
    		Sorting.SortByType(3, cities2);
    		
    		c =new ArrayList<City>(Arrays.asList(cities2));	
        	
        	Gson return_object= new GsonBuilder().create();
    		response.type("application/javascript");
    		response.status(200);
    		return return_object.toJson(c);
    	}
    	catch (Exception e){
    		response.type("text/plain");
    		response.status(500);
    		e.printStackTrace();
    		return e.toString();
    	}
    });
   }
}
