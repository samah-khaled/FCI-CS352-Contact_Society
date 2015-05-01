package com.FCI.SWE.Services;

import java.util.ArrayList;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.FCI.SWE.Models.User;
import com.FCI.SWE.ServicesModels.Custom;
import com.FCI.SWE.ServicesModels.Director;
import com.FCI.SWE.ServicesModels.GroupChatMessage;
import com.FCI.SWE.ServicesModels.HashTage;
import com.FCI.SWE.ServicesModels.PagePost;
import com.FCI.SWE.ServicesModels.PostBuilder;
import com.FCI.SWE.ServicesModels.PostPrivcy;
import com.FCI.SWE.ServicesModels.Private;
import com.FCI.SWE.ServicesModels.Public;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPost;

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
@Produces(MediaType.TEXT_PLAIN)
public class UserServices {
		/**
	 * Registration Rest service, this service will be called to make
	 * registration. This function will store user data in data store
	 * 
	 * @param uname
	 *            provided user name
	 * @param email
	 *            provided user email
	 * @param pass
	 *            provided password
	 * @return Status json
	 */
	@POST
	@Path("/RegistrationService")
	public String registrationService(@FormParam("uname") String uname,
			@FormParam("email") String email, @FormParam("password") String pass) {
		UserEntity user = new UserEntity(uname, email, pass);
		user.saveUser(); 
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}

	/**
	 * Login Rest Service, this service will be called to make login process
	 * also will check user data and returns new user from datastore
	 * @param uname provided user name
	 * @param pass provided user password
	 * @return user in json format
	 */
	@POST
	@Path("/LoginService")
	public String loginService(@FormParam("uname") String uname,
			@FormParam("password") String pass) {
		JSONObject object = new JSONObject();
		UserEntity user = UserEntity.getUser(uname, pass);
		if (user == null) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			object.put("name", user.getName());
			object.put("email", user.getEmail());
			object.put("password", user.getPass());
			object.put("id", user.getId());
		}
		return object.toString();
	}
	
	
   @POST
   @Path("/SendFriendRequest")
 public String SendFriendRequest(@FormParam("FriendMail") String FriendMail,
		 @FormParam("SenderMail") String SenderMail) {
		 
			JSONObject object = new JSONObject();
	
		UserEntity.sendFriendReqest(FriendMail,SenderMail);
		
	UserEntity.Update(SenderMail,FriendMail,"FriendRequestCommand");
	
		// (String convid, String Receiverid,String notifyType)
			object.put("Status", "OK");
			return object.toString();		
	}
   /**
    * 
    * @param Fname
    * @return
    */
	 
	    @POST
		@Path("/AcceptFriendRequests")
		public String AcceptFriendRequests(@FormParam("Fname") String Fname) {
			JSONObject object = new JSONObject();
			String check =UserEntity.AcceptFriendRequests(Fname);
			if (check.equals("failed")) {
				
				object.put("Status", "Failed");
				System.out.println("Failed");

			} else if(check.equals("OK")){
				object.put("Status", "OK");
				
			}
			return object.toString();

		}
		
	    /**
	     * 
	     * @param uname
	     * @return
	     */
	    
	    @POST
		@Path("/SearchService")
		public String SearchService(@FormParam("uname") String uname) {
			Vector <UserEntity> users =UserEntity.SearchUser(uname);
			JSONArray result =new JSONArray();
			for(UserEntity user:users){
				JSONObject object = new JSONObject();

				object.put("id", user.getId());
				object.put("name", user.getName());
				object.put("email", user.getEmail());
				result.add(object);

			} 
			return result.toString();

		}
		
	/**
	 * 
	 * @return
	 */
	@POST
	@Path("/signout")
	public String signoutService()
	{ 
		User.SetCurrentActiveUser();
	   JSONObject object = new JSONObject();
	   object.put("status","ok");
	  return object.toString();  
	}
	/**
	 * 
	 * @param ConvName
	 * @param email
	 * @return  OK
	 */
	
	@POST
	@Path("/CreatConversationService")
	public String CreatConversationService(@FormParam("ConvName") String ConvName,
		@FormParam("email") String email) {
		GroupChatMessage M=new GroupChatMessage();
   String ConvId=  M.CreateConversation(ConvName,email);
     M.AddReceiver(email, ConvId) ;
		JSONObject object = new JSONObject();
		object.put("ConvId",ConvId);

		object.put("Status", "OK");
		return object.toString();
	}
	
	@POST
	@Path("/AddChatFriendService")
	public String AddChatFriendService(@FormParam("ConVId") String ConvID,
			@FormParam("FriendMail") String FriendMail) {
		GroupChatMessage M=new GroupChatMessage();
       M.AddReceiver(FriendMail,ConvID);
		JSONObject object = new JSONObject();
		object.put("Status", "OK");
		return object.toString();
	}
	
	
	@POST
	@Path("/CreatUserPostService")
	public String CreatPostService(@FormParam("Feelings") String Feelings,
		@FormParam("email") String email,@FormParam ("PostContent") String PostContent ,
	@FormParam ("privcy") String privcy) {
		 String HashTag = "";
		UserEntity PostOwner = new UserEntity( );
		PostOwner .setemail(email);
		 if( PostContent.contains("#"))
			{ //  System.out.println("there hash ");

			//	String HashTag=
			 int i = PostContent.indexOf("#");
			 //System.out.println("index =" + i);

			 while (!(PostContent.charAt(i)+"").equals(" ")||!(PostContent.charAt(i)+"").equals(" "))
			 {  System.out.println(PostContent.charAt(i));

				  HashTag +=PostContent.charAt(i)+"";
				 i++;
			 }
			 System.out.println(HashTag);
			 }
		 
		PostBuilder UPost=new UserPost(Feelings);
		Director D =new Director(UPost);
	//	(String post ,int likes ,UserEntity U ,String HashTag ,String privcy)
		D.constructPost(PostContent, 0, PostOwner, HashTag, privcy);
		
		PostBuilder UPost1=D.getGetPost();
		UPost1.SavePost();
		
		JSONObject object = new JSONObject();
		
		object.put("Status", "OK");
		return object.toString();
	}
	
	@POST
	@Path("/HashtagTrendsService")
	public String HashTageTrendsService() {
		
		return HashTage.HashTagTrends();
	
	}
	
	
	/*@POST
	@Path("/HashTagStatisticsService")
	public String HashTagStatisticsService() {
		
		return HashTage.HashTagTrends(); 
	
	}*/
	
}