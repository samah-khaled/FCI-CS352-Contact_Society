package com.FCI.SWE.ServicesModels;

public class Director {
	
PostBuilder  Builder ;

	public Director(PostBuilder B) {
		Builder =B;
	}
	
	public void constructPost(String post ,int likes ,UserEntity U ,String HashTag ,String privcy){
		Builder.BuildeNumOfLikes(likes);
		Builder.BuildePost(post);
        Builder.BuildePostOwner(U);
        Builder.BuildePostHashTag(HashTag);
        Builder.BuildePrivcy(privcy);
	}
	

public PostBuilder getGetPost() {
	return Builder;
}

}
