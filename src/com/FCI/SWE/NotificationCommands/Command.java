package com.FCI.SWE.NotificationCommands;

public abstract class Command {
String CommandID;

	public  String getCommandID(){
		return CommandID;
		
	}

public  void setCommandID(String commandID){
	CommandID=commandID;
}

	public abstract String execute(); 
	
	
}
