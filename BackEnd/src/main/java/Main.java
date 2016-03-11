package main.java;

import static spark.Spark.*;
import com.google.gson.*;

public class Main {
    private static Usa usa; 
    private Gson gson = new Gson();
    public static void main(String[] args) {
        usa = new Usa();
        staticFileLocation("/public");
        get("/", (req, res) -> {
        	res.redirect("/index.html");
        	//dunno why index.html has GoWrench page title
        	//*ahem* Roberto *ahem*
        	//but it routes properly
        	return "";
        });
        
        get("/dankmeme", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        post("/search", (request, response) ->{
        	try{
	        	Gson receive_object = new GsonBuilder().create();
	        	System.out.println(request.body());
	        	JsonObject jobject = new JsonParser().parse(request.body()).getAsJsonObject();
	        	City rec = null;
	        	if (jobject.has("zip")){
	        		rec = new City(jobject.get("zip").getAsString());
	        	}
	        	if (jobject.has("lat") && jobject.has("lon")){
	        		String[] ll = {jobject.get("lat").getAsString(), jobject.get("lon").getAsString()};
	        		rec = new City(ll);
	        	}
	        	if (jobject.has("city") && jobject.has("state")){
	        		rec = new City(jobject.get("city").getAsString(), jobject.get("state").getAsString());
	        	}
                usa.addCity(rec);

	        	Gson return_object= new GsonBuilder().create();
        		response.type("application/javascript");
        		response.status(200);
        		return return_object.toJson(rec);
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
