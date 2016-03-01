import static spark.Spark.*;
import com.google.gson.*;

public class Main {
	private static int currentId = 0;
    private static Graph graph; 
    private Gson gson = new Gson();
    public static void main(String[] args) {
        graph = new Graph();
        staticFileLocation("/public");
        get("/", (req, res) -> response.redirect("/public/index.html"));
        
        get("/test", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        post("/search", (request, response) ->{
        	try{
	        	Gson receive_object = new GsonBuilder().create();
	        	System.out.println(request.body());
	        	JsonObject jobject = new JsonParser().parse(request.body()).getAsJsonObject();
	        	City rec = null;
	        	if (jobject.has("zip")){
	        		rec = new City(currentId, jobject.get("zip").getAsString());
	        	}
	        	if (jobject.has("lat") && jobject.has("lon")){
	        		rec = new City(currentId,jobject.get("lat").getAsString(), jobject.get("lon").getAsString());
	        	}
                graph.addNode(rec);
                currentId++;

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
