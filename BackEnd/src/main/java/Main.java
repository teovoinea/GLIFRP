import static spark.Spark.*;
import com.google.gson.*;

public class Main {
	private Gson gson = new Gson();
    public static void main(String[] args) {
        get("/", (req, res) -> "Hello World");
        
        get("/test", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        post("/search", (request, response) ->{
        	try{
	        	Gson receive_object = new GsonBuilder().create();
	        	System.out.println(request.body());
	        	City rec = receive_object.fromJson(request.body(), City.class);
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