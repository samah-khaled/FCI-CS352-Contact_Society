package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class Private extends PostPrivcy {

/*
	public Private(PostBuilder builder) {
	post= builder; 
	}*/

	public Private() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int SaveUserPost(String Feelings) {
		
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		int listSize=list.size() + 2;
		try {
		Entity employee = new Entity("Posts", list.size() + 2);

		employee.setProperty("PostContent", post.getPost().getPostContent());
		employee.setProperty("PostOwner", post.getPost().getPostOwner().getEmail());
		employee.setProperty("Privcy", post.getPost().getPrivcy());
		employee.setProperty("Likes", post.getPost().getLike());
		employee.setProperty("Feelings", Feelings);
		employee.setProperty("Type", "UserPost");

		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return listSize;
		
	}

	
	@Override
	public int SavePagePost(int NumOfSeen,String PageId) {
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("Posts");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		int listSize=list.size() + 2;
		try {
		Entity employee = new Entity("Posts", list.size() + 2);

		employee.setProperty("PostContent", post.getPost().getPostContent());
		employee.setProperty("PostOwner", post.getPost().getPostOwner().getEmail());
		employee.setProperty("Privcy", post.getPost().getPrivcy());
		employee.setProperty("Likes", post.getPost().getLike());
		employee.setProperty("NumOfSeen",NumOfSeen);
		employee.setProperty("Type", "PagePost");
		employee.setProperty("PageId", PageId);


		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}
		return listSize;
	}

	@Override
	public void setPost(PostBuilder p) {
		post =p;
		
	}

	@Override
	public int SaveHashTag(int PostId) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		Query gaeQuery = new Query("HashTageAndPost");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
		System.out.println("Size = " + list.size());
		int listSize=list.size() + 2;
		try {
		Entity employee = new Entity("HashTageAndPost", list.size() + 2);

		employee.setProperty("HashTage", post.getPost().getHahTag());
		employee.setProperty("PostId", PostId);

		datastore.put(employee);
		txn.commit();
		}finally{
			if (txn.isActive()) {
		        txn.rollback();
		    }
		}	
		return  listSize;
}

	@Override
	public void HashTags(String HashTageName) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashTagTrends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
	boolean	check =false;
		for ( Entity entity : pq.asIterable()) {
			String Name =entity.getProperty("HashTagName").toString();
			if (Name.equals(HashTageName)){
				int counter =(int) entity.getProperty("HashTagCounter");
				counter++;
				entity.setProperty("HashTagCounter", counter);
				check =true;
				break;
			}
			else
			{
				check=false;
			}
			
	}

		
		if (check==false){
			DatastoreService datastore1 = DatastoreServiceFactory
					.getDatastoreService();
			Transaction txn1 = datastore.beginTransaction();
			Query gaeQuery1 = new Query("HashTagTrends");
			PreparedQuery pq1 = datastore1.prepare(gaeQuery);
			List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());
			System.out.println("Size = " + list.size());
			int listSize=list.size() + 2;
			try {
			Entity employee = new Entity("HashTagTrends", list.size() + 2);

			employee.setProperty("HashTagCounter", 1);
			employee.setProperty("HashTagName", HashTageName);

			datastore1.put(employee);
			txn1.commit();
			}finally{
				if (txn1.isActive()) {
			        txn1.rollback();
			    }
		}
			//entity.setProperty("HashTagCounter", 1);		
	}
		
	}
}