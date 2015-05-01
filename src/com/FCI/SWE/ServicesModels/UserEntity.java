package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.FCI.SWE.Models.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

/**
 * <h1>User Entity class</h1>
 * <p>
 * This class will act as a model for user, it will holds user data
 * </p>
 *
 * @author Mohamed Samir
 * @version 1.0
 * @since 2014-02-12
 */
public class UserEntity {
	private String name;
	private String email;
	private String password;
	private long id;
	 
	/**
	 * Constructor accepts user data
	 * 
	 * @param name
	 *            user name
	 * @param email
	 *            user email
	 * @param password
	 *            user provided password
	 */
	public UserEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public UserEntity() {
		
	}
	
	public void setemail(String Email){
		email = Email;
	}
	
	private void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return password;
	}

	
	/**
	 * 
	 * This static method will form UserEntity class using user name and
	 * password This method will serach for user in datastore
	 * 
	 * @param name
	 *            user name
	 * @param pass
	 *            user password
	 * @return Constructed user entity
	 */

	public static UserEntity getUser(String name, String pass) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("name").toString().equals(name)
					&& entity.getProperty("password").toString().equals(pass)) {
				UserEntity returnedUser = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
				returnedUser.setId(entity.getKey().getId());
				return returnedUser;
			}
		}

		return null;
	}

	/**
	 * This method will be used to save user object in datastore
	 * 
	 * @return boolean if user is saved correctly or not
	 */
	public Boolean saveUser() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("users", list.size() + 2);

		employee.setProperty("name", this.name);
		employee.setProperty("email", this.email);
		employee.setProperty("password", this.password);
		
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	}
	
	public static boolean sendFriendReqest(String  Reciever, String Sender)
	{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		//String Sender=User.getCurrentActiveUser().getEmail();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("RequestFriend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		
		try {
		Entity employee = new Entity("RequestFriend", list.size() + 2);

		employee.setProperty("Sender", Sender);
		employee.setProperty("Reciever", Reciever);
		employee.setProperty("status", "binding");
		
		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return true;
	 
	}
	
	public static String AcceptFriendRequests(String Sender){
		String Reciever=User.getCurrentActiveUser().getEmail();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query gaeQuery = new Query("RequestFriend");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			if (entity.getProperty("Sender").toString().equals(Sender)
					&& entity.getProperty("Reciever").toString().equals(Reciever)
					&&entity.getProperty("status").toString().equals("binding"))
			{
				entity.setProperty("status", "friends");
				datastore.put(entity);

				return "OK";
				}
		}
		return "failed";
	}
	
	
	public static Vector<UserEntity>SearchUser(String uname)
	{  Vector <UserEntity> users=new Vector <UserEntity>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("users");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		for (Entity entity : pq.asIterable()) {
			String currentName =entity.getProperty("name").toString();
			if (currentName.contains(uname)){
			UserEntity user = new UserEntity(entity.getProperty(
						"name").toString(), entity.getProperty("email")
						.toString(), entity.getProperty("password").toString());
			user.setId(entity.getKey().getId());
			users.add(user);

			}
		}
		return users;
		}
	
public static	void Update (String convid, String Receiverid,String notifyType){
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Transaction txn = datastore.beginTransaction();
	Query gaeQuery = new Query("Notification");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
	System.out.println("Size Notification= " + list.size());
	
	try {
	Entity employee = new Entity("Notification", list.size() + 2);

	employee.setProperty("ReceiverName", Receiverid);
	employee.setProperty("ConvId_ReqSender",convid);
//	employee.setProperty("MessageContent", msg);
	employee.setProperty("NotifyType", notifyType);
	datastore.put(employee);
	
	txn.commit();
	}finally{
		if (txn.isActive()) {
	        txn.rollback();
	    }
	}
	
	}



}
