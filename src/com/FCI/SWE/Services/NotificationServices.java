package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.NotificationCommands.Command;
import com.FCI.SWE.ServicesModels.Notification;

@Path("/")
//@Produces("text/html")
@Produces(MediaType.TEXT_PLAIN)

public class NotificationServices {

	@POST
	@Path("/notificationService")
	public String handleNotification(@FormParam("id")String id){
		
		Notification notification = Notification.getNotification(id);
		System.out.println("Type NAME " +notification.getNotificationType());
		
	  String className = notification.getNotificationType();
		try {
		 Command command = (Command) Class.forName("com.FCI.SWE.NotificationCommands."+className).newInstance();
			command.setCommandID(notification.getSender()); // conversation Id
			
          return command.execute();
          
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
				
	}
	
	
	@POST
	@Path("/GetAllNotificationService")
	public String GetAllNotification(@FormParam("Mail")String user){
		return Notification.GetAllNotifications(user);
	}
	
}
