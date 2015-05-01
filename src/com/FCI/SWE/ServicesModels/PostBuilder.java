package com.FCI.SWE.ServicesModels;

public interface PostBuilder {
	
	public  void BuildePostOwner(UserEntity U);
	public  void BuildePost(String  post);
	public  void BuildePostHashTag(String HashTag );
	public  void BuildeNumOfLikes(int likes ); 
	public  void BuildePrivcy(String Privcy ); 
	public	Post getPost();
    public 	void SavePost();
	
}
