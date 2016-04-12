package edu.ap.jaxrs;

import java.io.*;
import javax.ws.rs.*;
import javax.json.*;

@Path("/quotes")
public class ProductResource {
	
	static final String FILE = "/Users/Achraf/Desktop/Quotes.json";
	
	@GET
	@Produces({"text/html"})
	public String getQuotesHTML() {
		String htmlString = "<html><body>";
		try {
			JsonReader reader = Json.createReader(new StringReader(getQuotesJSON()));
			JsonObject rootObj = reader.readObject();
			JsonArray array = rootObj.getJsonArray("quotes");
			
			
			for(int i = 0 ; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);
				htmlString += "<b>Author : " + obj.getString("author") + "</b><br>";
				htmlString += "Quote : " + obj.getString("quote") + "<br>";
				htmlString += "<br><br>";
			}
		}
		catch(Exception ex) {
			htmlString = "<html><body>" + ex.getMessage();
		}
		
		return htmlString + "</body></html>";
	}
	
	@GET
	@Produces({"application/json"})
	public String getQuotesJSON() {
		String jsonString = "";
		try {
			InputStream fis = new FileInputStream(FILE);
	        JsonReader reader = Json.createReader(fis);
	        JsonObject obj = reader.readObject();
	        reader.close();
	        fis.close();
	        
	        jsonString = obj.toString();
		} 
		catch (Exception ex) {
			jsonString = ex.getMessage();
		}
		
		return jsonString;
	}
	
	@GET
	@Path("{author}")
	@Produces({"application/json"})
	public String getQuoteJSON(@PathParam("author") String id) {
		String jsonString = "";
		try {
			InputStream fis = new FileInputStream(FILE);
	        JsonReader reader = Json.createReader(fis);
	        JsonObject jsonObject = reader.readObject();
	        reader.close();
	        fis.close();
	        
	        JsonArray array = jsonObject.getJsonArray("quotes");
	        for(int i = 0; i < array.size(); i++) {
	        	JsonObject obj = array.getJsonObject(i);
	        	if(obj.getString("author").equalsIgnoreCase(id)) {
	            	jsonString = obj.toString();
	            	break;
	            }
	        }
		} 
		catch (Exception ex) {
			jsonString = ex.getMessage();
		}
		
		return jsonString;
	}
	
	@POST
	@Path("{author}")
	@Produces({"text/html"})
	public String postQuotesHTML(@PathParam("author") String id) {
		String htmlString = "<html><body>";
		try {
			JsonReader reader = Json.createReader(new StringReader(getQuoteJSON(id)));
			JsonObject rootObj = reader.readObject();
			JsonArray array = rootObj.getJsonArray("quotes");
			
			
			for(int i = 0 ; i < array.size(); i++) {
				JsonObject obj = array.getJsonObject(i);
				htmlString += "<b>Author : " + obj.getString("author") + "</b><br>";
				htmlString += "Quote : " + obj.getString("quote") + "<br>";
				htmlString += "<br><br>";
			}
		}
		catch(Exception ex) {
			htmlString = "<html><body>" + ex.getMessage();
		}
		
		return htmlString + "</body></html>";
	}
	
}