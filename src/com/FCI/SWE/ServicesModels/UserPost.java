package com.FCI.SWE.ServicesModels;

public class UserPost implements PostBuilder{
  String Feelings;
  
Post post= new Post();
PostPrivcy privcy ;

 public String getFeelings() {
		
	return Feelings;
}

public void setFeelings(String feelings) {
	Feelings = feelings;
}

	public UserPost(String feeling ) {
		super();
		Feelings=feeling;
	}

	
	@Override
	public void BuildePostOwner(UserEntity U) {
      post.setPostOwner(U);
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
	String p =post.getPrivcy();
		System.out.println(p);
		try {

			privcy = (PostPrivcy) Class.forName("com.FCI.SWE.ServicesModels."+p).newInstance();
			privcy.setPost(this);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	int PostId=	privcy.SaveUserPost(this.Feelings);
	int hashTagId=privcy.SaveHashTag(PostId);
	System.out.println("HashTage In Post ="+post.getHahTag());
	privcy.HashTags(post.getHahTag());
		
}	
}
