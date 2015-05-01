package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/")
@Produces("text/html")
public class PageController {

	@POST
	@Path("/goTopage")
	public Response BackToHome(@FormParam("email") String Email){
		Map<String, String> map = new HashMap<String, String>();
        map.put("email", Email);
		return Response.ok(new Viewable("/jsp/CreatePage",map)).build();
	}
	
	
	
	@POST
	@Path("/CreatePage")
	public String CreatPage(@FormParam("PageName") String PageName,
			@FormParam("PageType") String PageType
			,@FormParam("pageCategory") String pageCategory,
			@FormParam("email") String PageOwner) {


		String serviceUrl = "http://localhost:8888/rest/CreatPageService";
		String urlParameters = "PageName=" + PageName+ "&PageType="+PageType+
			"&pageCategory="+pageCategory+ "&PageOwner="+PageOwner;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
				try {
					obj = parser.parse(retJson);
					
					JSONObject object = (JSONObject) obj;
					Map<String, String> map = new HashMap<String, String>();
					if (object.get("Status").equals("OK"))
					{
					//map.put("addcheck", "Page Created Successfly");

					return "Page Created Successfly";
					}

				///	map.put("addcheck", "fail to create page");

						return "fail to create page";
			
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		return null;
	}
	
	@POST
	@Path("/PagePost")
	public Response User_Post(@FormParam("PageName") String PageName,
			@FormParam("email") String email,@FormParam("PostContent") String PostContent ,
			@FormParam("privcy") String privcy) {
		PostContent+=" ";
		System.out.println(privcy);
		String serviceUrl = "http://localhost:8888/rest/CreatPagePostService";
		String urlParameters = "PostContent=" + PostContent 
				+ "&email=" + email + "&PageName=" + PageName+ "&privcy=" + privcy;
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		 Map<String, String> map = new HashMap<String, String>();		

		try {
				obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{	
			map.put("Check","Posted succsesfully");
			map.put("email",email);

			return Response.ok(new Viewable("/jsp/home",map)).build(); 
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		map.put("Check","Faild To Post");
		map.put("email",email);

		 return Response.ok(new Viewable("/jsp/home",map)).build(); 
	}
	
	
}
