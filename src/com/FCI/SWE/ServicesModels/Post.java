package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;

public  class Post {
   String Content;
   String HahTag;
   UserEntity OwnerUser;
   int like;
   String Privcy;

   public String getPrivcy() {
	return Privcy;
}
public void setPrivcy(String privcy) {
	Privcy = privcy;
}
public int getLike() {
	return like;
}
public void setLike(int like) {
	this.like = like;
}
public String getPostContent() {
	return Content;
}
public void setPostContent(String postContent) {
	Content = postContent;
}
public String getHahTag() {
	return HahTag;
}
public void setHahTag(String hahTag) {
	HahTag = hahTag;
}
public UserEntity getPostOwner() {
	return OwnerUser;
}
public void setPostOwner(UserEntity postOwner) {
	OwnerUser = postOwner;
}
	public Post() { 
		
	}

	
	
	
}
