package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.GroupChatMessage;
import com.FCI.SWE.ServicesModels.OneToOneMessage;
import com.FCI.SWE.ServicesModels.UserEntity;


/**
 * 
 * @author samah
 *
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class MessageServices {

	/**
	 * 
	 * @param ConvID
	 * @param Mail
	 * @param messgcontent
	 * @return Ok
	 */
	@POST
	@Path("/SendMessageService")
	public String SendMessageService(@FormParam("FriendMail") String FriendMail,
			@FormParam("name") String Mail
			, @FormParam("messgcontent") String messgcontent) {
		JSONObject object = new JSONObject();
     
        	OneToOneMessage M=new OneToOneMessage();
    		M.setMsgName(messgcontent);
            M.saveMessage(Mail,FriendMail);
            UserEntity receiver =new UserEntity();
           M.setReceiver(receiver); 
         // M.GetReceivers(Mail, ConVId);
    //	public void Notify(String ConvId, String ReceiverId) {

        M.Notify(Mail,FriendMail);
		object.put("Status", "OK");
        
        return object.toString();
	}	

	
	@POST
	@Path("/SendMessageToGroupService")
	public String SendMessageToGroupService(@FormParam("ConvName") String ConvName,
			@FormParam("Sender") String Mail,
			 @FormParam("messgcontent") String messgcontent) {
		JSONObject object = new JSONObject();
		 
        	GroupChatMessage M=new GroupChatMessage();
    		M.setMsgName(messgcontent);
    		M.getConvId(ConvName) ;
            M.saveMessage(Mail);
            
            M.GetReceivers(Mail);
         //String Sender ,String ConvId)
       
// when notify -> ReceiverID =sender
        
    M.Notify(Mail,ConvName);
		object.put("Status", "OK");
		
        
        return object.toString();
	}	

}
