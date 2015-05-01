package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public  class Public extends PostPrivcy{

	/*public Public(PostBuilder builder) {
		post= builder; 
	}*/
	

	public Public() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public int SaveUserPost(String Feelings ) {
		 System.out.println("SaveUserPost");
		 System.out.println(post.getPost().getPostContent());
		 System.out.println(post.getPost().getPostOwner().getEmail());
		 
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
	public int SaveHashTag(int  PostId) {
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
		return listSize;
	}
	@Override
	public void HashTags(String HashTageName) {
		System.out.println (HashTageName);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query gaeQuery = new Query("HashTagTrends");
		PreparedQuery pq = datastore.prepare(gaeQuery);
	boolean	check =false;
		for ( Entity entity : pq.asIterable()) {
			String Name =entity.getProperty("HashTagName").toString();
			if (Name.equals(HashTageName)){
				String  counter = entity.getProperty("HashTagCounter").toString();
			Integer	counter1 = Integer .parseInt(counter) ;
			 counter1++;
				System.out.println ("Counter = "+counter1);
				entity.setProperty("HashTagCounter", counter1.toString());
				datastore.put(entity);
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
	}
		
	}
}
