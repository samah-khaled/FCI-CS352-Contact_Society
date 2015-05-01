package com.FCI.SWE.ServicesModels;

public class PagePost implements PostBuilder {

private int NumOfSeen;
String  PageId;
 PostPrivcy privcy ; 
 
public int getNumOfSeen() {
	return NumOfSeen;
}

Post post=new Post();


	public PagePost(int num,String PId) {
		NumOfSeen =num;
		PageId =PId;
	}
	

	@Override
	public void BuildePostOwner(UserEntity U) {
    post.setPostOwner(U);
		
	}

	public String getPageId() {
		return PageId;
	}


	public void setPageId(String pageId) {
		PageId = pageId;
	}


	@Override
	public void BuildePost(String p) {
post.setPostContent(p);
		
	}

	@Override
	public void BuildePostHashTag(String HashTag) {

		 post.setHahTag(HashTag);
	}

	@Override
	public void BuildeNumOfLikes(int likes) {
		post.setLike(likes);
	}
	@Override
	public void BuildePrivcy(String Privcy) {
		post.setPrivcy(Privcy);
	}

	@Override
	public Post getPost() {
		
		return post;
	}


	@Override
	public void SavePost() {
		try {
			privcy =(PostPrivcy) Class.forName("com.FCI.SWE.ServicesModels."+ post.getPrivcy()).newInstance();
			privcy.setPost(this);
		int PostId=privcy.SavePagePost(this.NumOfSeen ,this.PageId);
			int hashTagId=privcy.SaveHashTag(PostId);
			privcy.HashTags(post.getHahTag());
			
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
