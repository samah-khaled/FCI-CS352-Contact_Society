package com.FCI.SWE.ServicesModels;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class Page {
String PageName;
String PageType;
String pageCategory;
int LikesNum;
int ActiveUserNum;
String PageOwner;

public Page( ) {
	
}
	
	public Page(String PageName, String PageType, String pageCategory, String PageOwner) {
				this.PageName = PageName;
				this.PageType = PageType;
				this.pageCategory = pageCategory;
				this.PageOwner = PageOwner;
				
			}
		
			public void setPageName(String PageName){
				this.PageName = PageName;
			}
			
			public void setPageType(String PageType){
				this.PageType = PageType;
			}

			public void setpageCategory(String pageCategory){
				this.pageCategory = pageCategory;
			}
			
			public void setLikesNum(int LikesNum){
				this.LikesNum = LikesNum;
			}
			
			public void setActiveUserNum(int ActiveUserNum){
				this.ActiveUserNum = ActiveUserNum;
			}
			
		 /*public void setPostsSeenNum(intPostsSeenNum){
				this.PostsSeenNum = PostsSeenNum;
			}*/
			
			public void setPageOwner(String PageOwner){
				this.PageOwner = PageOwner;
			}
			
			 public String getPageName() {
				return PageName;
			}

			public String getPageType() {
				return PageType;
			}

			public String getpageCategory(){
				return pageCategory;
			}
			
			public int getLikesNum(){
					return LikesNum;
			}
			
			public int getActiveUserNum(){
					return ActiveUserNum;
			}
			
			public String getPageOwner(){
				return PageOwner;
			}
			
		 /* public String getPostsSeenNum(){
				return PostsSeenNum;
			}*/



			public static String  getPage(String PageName) {
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Query gaeQuery = new Query("Pages");
				PreparedQuery pq = datastore.prepare(gaeQuery);
				for (Entity entity : pq.asIterable()) {
					if (entity.getProperty("PageName").toString().equals(PageName)) {
						Long  x= entity.getKey().getId();	
						System.out.println("PageId= "+x);
						return x.toString();
						
					}
				}
				return null;
			}

			/**
			 * This method will be used to save page object in datastore
			 * 
			 * @return boolean if page is saved correctly or not
			 */
			public Boolean savePage() {
				DatastoreService datastore = DatastoreServiceFactory
						.getDatastoreService();
				Transaction txn = datastore.beginTransaction();
				Query gaeQuery = new Query("Pages");
				PreparedQuery pq = datastore.prepare(gaeQuery);
				List<Entity> list = pq.asList(FetchOptions.Builder.withDefaults());

				System.out.println("Size = " + list.size());
				
				try {
					
				Entity page = new Entity("Pages", list.size() + 2);

				page.setProperty("PageName", this.PageName);
				page.setProperty("PageType", this.PageType);
				page.setProperty("pageCategory", this.pageCategory);
				page.setProperty("LikesNum", this.LikesNum);
				page.setProperty("ActiveUserNum", this.ActiveUserNum);
				page.setProperty("PageOwner", this.PageOwner);
				datastore.put(page);
				txn.commit();
				}finally{
					if (txn.isActive()) {
				        txn.rollback();
				    }
				}
				return true;

			}

}
