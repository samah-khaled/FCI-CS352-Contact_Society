package com.FCI.SWE.ServicesModels;

public abstract class  PostPrivcy {
  PostBuilder post ; 

public abstract   int SaveUserPost(String Feelings) ;

public abstract   int SavePagePost(int NumOfSeen, String pageId) ;

public abstract void setPost(PostBuilder p);
public abstract int SaveHashTag(int PostId);
public abstract void HashTags(String HashTageName);


}
