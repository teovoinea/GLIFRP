package backend;

import static spark.Spark.*;

import java.util.ArrayList;

import com.google.gson.*;

public class Main {
    private static Usa usa; 
    private Gson gson = new Gson();
    
    public static void main(String[] args) {
        usa = new Usa();
        staticFileLocation("/public");
        init();
        get("/", (request, res) -> {
        	System.out.println(request.requestMethod() + " request on " + request.url() + " from ip " + request.ip() + " with user agent " + request.userAgent() + "\nRequest Body:\n" + request.body());
        	res.redirect("/index.html");
        	return "";
        });
        
        get("/dankmeme", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        /* 
         * This function will take a post request with any of the following attributes
         * latitude + longitude OR
         * zip_code OR
         * city name + state name
         * and will return the top 10 cities with the LOWEST crime rate in that state*/
        post("/search", (request, response) ->{
        	System.out.println(request.requestMethod() + " request on " + request.url() + " from ip " + request.ip() + " with user agent " + request.userAgent() + "\nRequest Body:\n" + request.body());
        	try{
	        	Gson receive_object = new GsonBuilder().create();
	        	JsonObject jobject = new JsonParser().parse(request.body()).getAsJsonObject();
	        	City rec = null;
	        	int distance = 0;
	        	// if (jobject.has("zip")){
	        	// 	rec = new City(jobject.get("zip").getAsString());
	        	// }
	        	// if (jobject.has("lat") && jobject.has("lon")){
	        	// 	String[] ll = {jobject.get("lat").getAsString(), jobject.get("lon").getAsString()};
	        	// 	rec = new City(ll);
	        	// }
	        	if (jobject.has("city") && jobject.has("state")){
	        		rec = new City(jobject.get("city").getAsString(), jobject.get("state").getAsString());
	        		distance = jobject.get("dist").getAsInteger();
	        	}
	        	ArrayList<State> states = usa.getNeighbouringStates(usa.findStateByCity(rec), distance);
	        	City min = new City("");
	        	for(int i = 0; i < states.size(); i++){
	        		if (min.compareTo(3, states.get(i).findLowestCrimeRate())){
	        			min = states.get(i);
	        		}
	        	}
	        	usa.printUSA();
	        	
	        	
	        	Gson return_object= new GsonBuilder().create();
        		response.type("application/javascript");
        		response.status(200);
        		return return_object.toJson(min);
        	}
        	catch (Exception e){
        		response.type("text/plain");
        		response.status(500);
        		e.printStackTrace();
        		return e.toString();
        	}
        });
    }
    
    private static void init(){
    	/* 
    	 * Connect to database
    	 * SELECT * FROM TABLE
    	 * for i in row:
    	 * 	City t = new City()
    	 * 	t.addAttribute(row info)
    	 * 	usa.addCity(t)*/
    }
}
