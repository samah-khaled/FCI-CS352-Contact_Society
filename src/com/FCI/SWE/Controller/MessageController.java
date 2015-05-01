package com.FCI.SWE.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
public class MessageController {
		
@GET
@Path("/groupMessage")
public Response groupMessage(){
		 return Response.ok(new Viewable("/jsp/groupMessage")).build();
}
		
@POST
@Path("/sendGroupMessage")
	 public String sendGroupMessage(@FormParam("receviers") String receviers,@FormParam("message") String message){
			return "dskdjs";
	}
@POST
@Path("/Conversationpage")
public Response Conversationpage(@FormParam("email") String Email){
	Map<String, String> map = new HashMap<String, String>();
    map.put("email", Email);
	return Response.ok(new Viewable("/jsp/Conversation",map )).build();
}


@POST
@Path("/MessagePage")
public Response MessagePage(@FormParam("OwnerMail") String Email){
	Map<String, String> map = new HashMap<String, String>();
    map.put("OwnerMail", Email);
	return Response.ok(new Viewable("/jsp/MessagePage",map )).build();
}

/**
 * 
 * @param ReceiverID
 * @param messgcontent
 * @param name
 * @param hidnform
 * @return  SendMsgOrAddFriend 
 */
	@POST
	@Path("/SendMessage")
	public Response SendMessage(@FormParam("FriendMail") String ReceiverID,
			@FormParam("messgcontent") String messgcontent
			,@FormParam("OwnerMail") String name ) {
		System.out.println("Ownername in send msg " +name);

		String serviceUrl = "http://localhost:8888/rest/SendMessageService";
		String urlParameters = "messgcontent=" + messgcontent + "&FriendMail=" + ReceiverID+ "&name="+name
				;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
				obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			Map<String, String> map = new HashMap<String, String>();
			map.put("ConvId",ReceiverID);
			map.put("OwnerMail",name);

			
			if (object.get("Status").equals("OK"))
				
			map.put("sendcheck", "Message Sent Successfly");
			else
				map.put("sendcheck", "Failed");
			
				return Response.ok(new Viewable("/jsp/MessagePage",map)).build(); 

			
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
		
	}
	
	//SendMessageToGroupService
	
	/**
	 * 
	 * @param ReceiverID
	 * @param messgcontent
	 * @param name
	 * @param hidnform
	 * @return  SendMsgOrAddFriend 
	 */
		@POST
		@Path("/SendMessageToGroup")
		public Response SendMessageToGroup(@FormParam("ConvName") String ConvName,
				@FormParam("messgcontent") String messgcontent
				,@FormParam("OwnerMail") String name ) {
			System.out.println("Ownername in send msg " +name);

			String serviceUrl = "http://localhost:8888/rest/SendMessageToGroupService";
			String urlParameters = "messgcontent=" + messgcontent + "&ConvName=" + ConvName+ "&Sender="+name;
			String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONParser parser = new JSONParser();
			Object obj;
			try {
					obj = parser.parse(retJson);
				JSONObject object = (JSONObject) obj;
				Map<String, String> map = new HashMap<String, String>();
				map.put("ConvName",ConvName);
				map.put("OwnerMail",name);

				if (object.get("Status").equals("OK"))
					
				map.put("sendcheck", "Message Sent Successfly");
				else
					map.put("sendcheck", "Failed");
			
					return Response.ok(new Viewable("/jsp/SendMsgOrAddFriend",map)).build(); 
			} catch (ParseException e) {

				e.printStackTrace();
			}
			return null;
		}

}


