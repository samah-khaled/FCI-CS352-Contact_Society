package com.FCI.SWE.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.HashTage;
import com.FCI.SWE.ServicesModels.Notification;
import com.FCI.SWE.ServicesModels.UserEntity;

/**
 * This class contains REST services, also contains action function for web
 * application
 * 
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 *
 */
@Path("/")
@Produces("text/html")
public class UserController {
	/**
	 * Action function to render Signup page, this function will be executed
	 * using url like this /rest/signup
	 * 
	 * @return sign up page
	 */
	@POST
	@Path("/doSearch")
	public Response usersList(@FormParam("uname") String uname){
		System.out.println(uname);
		String serviceUrl = "http://localhost:8888/rest/SearchService";
		String urlParameters = "uname=" + uname;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		Map<String ,Vector<User>> parsedUsers =new  HashMap<String ,Vector<User>>();
		Vector <User> users =new Vector<User>();
		JSONParser parser =new JSONParser();
		try {
			JSONArray array = (JSONArray) parser.parse(retJson);
			for (int i=0 ;i<array.size();i++)
			{ JSONObject object ;
			object =(JSONObject) array.get(i);
				users.add(User.ParserUserInfo(object.toJSONString()));
			}
			
			parsedUsers.put("usersList", users);
			System.out.println(uname);
			return Response.ok(new Viewable("/jsp/ShowUsers",parsedUsers)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@GET
	@Path("/signup")
	public Response signUp() {
		return Response.ok(new Viewable("/jsp/register")).build();
	}

	
	@POST
	@Path("/BackToHome")
	public Response BackToHome(@FormParam("email") String Email){
		Map<String, String> map = new HashMap<String, String>();
        map.put("email", Email);
		return Response.ok(new Viewable("/jsp/home")).build();
	}
	@GET
	@Path("/acceptFriend")
	public Response acceptFriend() {
		return Response.ok(new Viewable("/jsp/acceptFriend")).build();
	}
	
	@GET
	@Path("/search")
	public Response search(){
		return Response.ok(new Viewable("/jsp/search")).build();
	}
	
	
	
	/**
	 * Action function to render home page of application, home page contains
	 * only signup and login buttons
	 * 
	 * @return enty point page (Home page of this application)
	 */
	@GET
	@Path("/")
	public Response index() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}

	/**
	 * Action function to render login page this function will be executed using
	 * url like this /rest/login
	 * 
	 * @return login page
	 */
	@GET
	@Path("/login")
	public Response login() {
		return Response.ok(new Viewable("/jsp/login")).build();
 	}
/**
 *  Action function to response to signout request 
 * 
 * @return entrypoint 
 */
	@GET
	@Path("/signout")
	public Response SignOut() {
		return Response.ok(new Viewable("/jsp/entryPoint")).build();
	}
	/**
	 * 
	 * Action function to response to signup request, This function will act as
	 * a controller part and it will calls RegistrationService to make
	 * registration
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided user password
	 * @return Status string
	 */
	@POST
	@Path("/response")
	@Produces(MediaType.TEXT_PLAIN)
	public String response(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {

		String serviceUrl = "http://localhost:8888/rest/RegistrationService";
		String urlParameters = "uname=" + uname + "&email=" + email
				+ "&password=" + pass;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
				obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
				return "Registered Successfully";

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Failed";
	}
	/**
	 * Action function to response to login request. This function will act as a
	 * controller part, it will calls login service to check user data and get
	 * user from datastore
	 * 
	 * @param uname
	 *            provided user name
	 * @param pass
	 *            provided user password
	 * @return Home page view
	 */
	@POST
	@Path("/home")
	@Produces("text/html")
	public Response home(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		String urlParameters = "uname=" + uname + "&password=" + pass;
            
		String retJson = Connection.connect(
				"http://localhost:8888/rest/LoginService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			Map<String, String> map = new HashMap<String, String>();
			User user = User.getUser(object.toJSONString());
			map.put("name", user.getName());
			map.put("email", user.getEmail());
			
			return Response.ok(new Viewable("/jsp/home", map)).build(); 
		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
		@POST
		@Path("/checkRequests")
		@Produces("text/html")
		public Response checkRequests(@FormParam("Fname") String Fname )
				{
			String urlParameters2 = "Fname="+ Fname;
	            
			String retJson2 = Connection.connect(
					"http://localhost:8888/rest/AcceptFriendRequests", urlParameters2,
					"POST", "application/x-www-form-urlencoded;charset=UTF-8");

			JSONParser parser2 = new JSONParser();
			Object obj2 = null ;
				try {
					obj2 = parser2.parse(retJson2);
					JSONObject object = (JSONObject) obj2;
					if (object.get("Status").equals("Failed"))
						return null;
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
				return Response.ok(new Viewable("/jsp/accepted")).build();
			
				}
	@POST
	@Path("/friendrequest")
//	@Produces("text/html")
	public Response friendrequest(@FormParam("FriendMail") String FMail,
			@FormParam("SenderMail") String SenderMail) {
		
		String urlParameters = "FriendMail=" + FMail+ "&SenderMail="+SenderMail;
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/SendFriendRequest", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj; 
		try {
			obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("Failed"))
				return null;
			
			return Response.ok(new Viewable("/jsp/home")).build(); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
		}
	/**
	 * 
	 * @param ConvName
	 * @param email
	 * @return
	 */
	@POST
	@Path("/Conversation")
	public Response Conversation(@FormParam("ConvName") String ConvName,
			@FormParam("email") String email) {

		String serviceUrl = "http://localhost:8888/rest/CreatConversationService";
		String urlParameters = "ConvName=" + ConvName 
				+ "&email=" + email;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		try {
				obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK"))
			{ Map<String, String> map = new HashMap<String, String>();
			String ConVId =object.get("ConvId").toString();

			System.out.println("conv Id =" +ConVId);
			
			map.put("ConvId",ConVId);
			map.put("OwnerMail",email);
        System.out.println("Ownername in create conv " +email);
			return Response.ok(new Viewable("/jsp/SendMsgOrAddFriend",map)).build(); 
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		 return Response.ok(new Viewable("/jsp/home")).build(); 
	}


		
	 /**
	  * 
	  * @param ConvID
	  * @param FriendMail
	  * @param name
	  * @return  SendMsgOrAddFriend
	  */
	@POST
	@Path("/AddChatfreind")
	public Response AddChatfreind(@FormParam("ConvId") String ConvID,
			@FormParam("FriendMail") String FriendMail
			,@FormParam("OwnerMail") String name) {
		System.out.println("FriendMail in send msg " +FriendMail);

		String serviceUrl = "http://localhost:8888/rest/AddChatFriendService";
		String urlParameters = "ConVId=" + ConvID+ "&FriendMail="+FriendMail;
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
			map.put("ConvId",ConvID);
			map.put("addcheck", "friend added Successfly");
			map.put("OwnerMail",name);

			return Response.ok(new Viewable("/jsp/SendMsgOrAddFriend",map)).build(); 
			}
			
				map.put("ConvId",ConvID);
				map.put("sendcheck", "Failed");
				map.put("OwnerMail",name);

				return Response.ok(new Viewable("/jsp/SendMsgOrAddFriend",map)).build(); 
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 
	 * @param Feelings
	 * @param email
	 * @param PostContent
	 * @param privcy
	 * @return
	 */
	
	@POST
	@Path("/UserPost")
	public Response User_Post(@FormParam("Feelings") String Feelings,
			@FormParam("email") String email,@FormParam("PostContent") String PostContent ,
			@FormParam("privcy") String privcy) {
		PostContent+=" ";
		System.out.println(privcy);
		String serviceUrl = "http://localhost:8888/rest/CreatUserPostService";
		String urlParameters = "PostContent=" + PostContent 
				+ "&email=" + email + "&Feelings=" + Feelings+ "&privcy=" + privcy;
		
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
			map.put("Check","Done");
			map.put("email",email);

			return Response.ok(new Viewable("/jsp/home",map)).build(); 
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		map.put("Check","Faild");
		map.put("email",email);

		 return Response.ok(new Viewable("/jsp/home",map)).build(); 
	}
	
	@POST
	@Path("/HashTageSearch")
	@Produces("text/html")
	public Response HashTageSearch(@FormParam("hashtag") String HashTage ) {
		String urlParameters = "HashTage=" + HashTage ;

		String retJson = Connection.connect(
				"http://localhost:8888/rest/HashTagStatisticsService", urlParameters,
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Vector <HashTage> HashTages =new Vector <HashTage> ();		
		Map<String, Vector<HashTage> > results = new HashMap <String, Vector<HashTage>>();

		Object obj;
		try {
			 HashTage hash=new HashTage();
			 JSONArray array = (JSONArray) parser.parse(retJson);
			 
			for (int i=0 ;i<1;i++)
			{ JSONObject object ;
			object =(JSONObject) array.get(i);
				 hash= (HashTage) object.get("hashtag");
				 HashTages.add(hash);
			}
			
			results.put("result",HashTages );
			return Response.ok(new Viewable("/jsp/trends",results)).build();
		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@POST
	@Path("/HashTagTrends")
	@Produces("text/html")
	public Response HashTagTrends() {
		
		String retJson = Connection.connect(
				"http://localhost:8888/rest/HashtagTrendsService", "",
				"POST", "application/x-www-form-urlencoded;charset=UTF-8");

		JSONParser parser = new JSONParser();
		Vector <HashTage> HashTages =new Vector <HashTage> ();		
		Map<String, Vector<HashTage> > results = new HashMap <String, Vector<HashTage>>();

		Object obj;
		try {
			 HashTage hash=new HashTage();
			 JSONArray array = (JSONArray) parser.parse(retJson);
			 
			for (int i=0 ;i<1;i++)
			{ JSONObject object ;
			object =(JSONObject) array.get(i);
				 hash= (HashTage) object.get("hashtag");
				 HashTages.add(hash);
			}
			
			results.put("result",HashTages );
			return Response.ok(new Viewable("/jsp/trends",results)).build();
		
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}

