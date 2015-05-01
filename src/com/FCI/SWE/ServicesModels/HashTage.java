package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class HashTage {
private	int    HashTageCounter ;
private	String   HashTageName;

	
	public int getHashTageCounter() {
	return HashTageCounter;
}


public void setHashTageCounter(int hashTageCounter) {
	HashTageCounter = hashTageCounter;
}


public String getHashTageName() {
	return HashTageName;
}


public void setHashTageName(String hashTageName) {
	HashTageName = hashTageName;
}
public static String HashTagTrends()
{  //ArrayList<HashTage > HashTags =new ArrayList<HashTage > ();
   HashTage H=new HashTage();
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query gaeQuery = new Query("HashTagTrends");
	PreparedQuery pq = datastore.prepare(gaeQuery);
	String hash;
	String  count;
	JSONArray array = new JSONArray();
	for (Entity entity : pq.asIterable()) {
	  JSONObject object = new JSONObject();

	hash=entity.getProperty("HashTagName").toString();
	count=entity.getProperty("HashTagCounter").toString(); 
	H.setHashTageCounter(Integer.parseInt(count));
	H.setHashTageName(hash);
	object.put("hashtag", H);
	array.add(object);
	//HashTags.add(H);
		}
	 Collections .sort(array);
	// sort(array,SortBy("HashTagCounter"));
	return array.toString();
	}

	public HashTage() {
		
	}
	

}
