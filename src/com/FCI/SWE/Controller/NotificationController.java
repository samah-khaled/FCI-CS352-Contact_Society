package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.Notification;
@Path("/")
@Produces("text/html")

public class NotificationController {

	@POST
	@Path("/allNotification")
	public Response getAllNotification(@FormParam("email")String Mail) {
		//User user = User.getCurrentActiveUser();
		String urlParams = "Mail="+Mail;
		String retJson = Connection.connect("http://localhost:8888/rest/GetAllNotificationService",
				urlParams, "POST", "application/x-www-form-urlencoded");
		
		JSONParser parser = new JSONParser();
		Vector<Notification> notifications = new Vector<Notification>();
		Map<String, Vector<Notification> > results = new HashMap<String, Vector<Notification>>();
		
		try {
			
			 JSONArray array = (JSONArray) parser.parse(retJson);
			for (int i=0 ;i<array.size();i++)
			{ JSONObject object ;
			object =(JSONObject) array.get(i);
				notifications.add(Notification.ParserNotificationInfo(object.toJSONString()));
				  System.out.print("Sender= "+object.get("ConversationId").toString());
		          System.out.print("Reciver = "+object.get("ReceiverName").toString());
			}
			
			results.put("result", notifications);
			  
			return Response.ok(new Viewable("/jsp/notifications",results)).build();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
