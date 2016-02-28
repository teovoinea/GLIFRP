import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> "Hello World");
        
        get("/test", (req, res) -> 
        	"<img src='/img/2.PNG'/>"
        );

        post("/search", (request, response) ->{
        	try{
	        	String latitude = request.attribute("lat");
	        	String longitude = request.attribute("lon");
	        	String zip_code = request.attribute("zip");
        		response.type("application/javascript");
        		response.status(200);
        		String r = String.format("{
        						lat:%s,
        						lon:%s,
        						zip:%s
        					}", latitude, longitude, zip_code);
        		return r;
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