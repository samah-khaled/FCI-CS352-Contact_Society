/*package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;


public  abstract  class Message extends Notification{
 
 
  protected  int MsgId;
  protected String Msgcontent;
  protected ArrayList<UserEntity> Receivers =new ArrayList<UserEntity> ();


public abstract int getMsgId() ;
public abstract void setMsgId(int msgId) ;

public abstract  String getMsgName() ;

public abstract ArrayList GetReceivers(String Sender ,String ConvId);

public  abstract void setMsgName(String msgName);
public abstract Boolean saveMessage(String SenderName ,String ReceiverName) ;

public abstract void Notify(String ConvId ,String ReceiverId);
public abstract String GetAllMessages(String ID) ;

}*/