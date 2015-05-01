package com.FCI.SWE.NotificationCommands;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.ServicesModels.GroupChatMessage;
import com.FCI.SWE.ServicesModels.OneToOneMessage;

public  class MessageCommand extends Command{

	public MessageCommand() {
		super();
	}
	
	@Override
	public String execute(){
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject)parser.parse(CommandID);
			String conversation = obj.get("ConversationId").toString();
			JSONObject returnedObj = new JSONObject();
		
			if(conversation.contains("@")){
		OneToOneMessage M= new OneToOneMessage();
		returnedObj.put("data",M.GetAllMessages(conversation));
          }
				else{
			GroupChatMessage M= new GroupChatMessage();
			returnedObj.put("data",M.GetAllMessages(conversation));

			}
			return returnedObj.toJSONString();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
		
	}



	
}
