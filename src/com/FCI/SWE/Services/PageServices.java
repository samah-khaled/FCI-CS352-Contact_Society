package com.FCI.SWE.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.FCI.SWE.ServicesModels.Custom;
import com.FCI.SWE.ServicesModels.Director;
import com.FCI.SWE.ServicesModels.Page;
import com.FCI.SWE.ServicesModels.PagePost;
import com.FCI.SWE.ServicesModels.PostBuilder;
import com.FCI.SWE.ServicesModels.PostPrivcy;
import com.FCI.SWE.ServicesModels.Private;
import com.FCI.SWE.ServicesModels.Public;
import com.FCI.SWE.ServicesModels.UserEntity;
import com.FCI.SWE.ServicesModels.UserPost;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class PageServices {

	@POST
	@Path("/CreatPageService")
	
	public String CreatePageService(@FormParam("PageName") String PageName,
            @FormParam("PageType") String PageType
			,@FormParam("pageCategory") String pageCategory,
			@FormParam("PageOwner") String PageOwner) {

		JSONObject object = new JSONObject();
		//String PageName, String PageType, String pageCategory, String PageOwner
    Page newPage= new Page(PageName,PageType,pageCategory,PageOwner);
 
             newPage.setLikesNum(0);
             newPage.setActiveUserNum(0);
        boolean chek=  newPage.savePage();
		if ( chek== false) {
			object.put("Status", "Failed");

		} else {
			object.put("Status", "OK");
			
		}
		return object.toString();
	}

			
	
	
	@POST
	@Path("/CreatPagePostService")	
	public String CreatPostService( @FormParam("email") String email
			,@FormParam ("PostContent") String PostContent ,
			@FormParam ("privcy") String privcy ,
		@FormParam ("PageName") String PageName) {
		
		 String HashTag = "";
			UserEntity PostOwner = new UserEntity( );
			PostOwner .setemail(email);
			Page page= new Page();
		String PageId=page.getPage(PageName);
			
			 if( PostContent.contains("#"))
				{   System.out.println("there hash ");

				//	String HashTag=
				 int i = PostContent.indexOf("#");
				 System.out.println("index =" + i);

				 while (!(PostContent.charAt(i)+"").equals(" ")||!(PostContent.charAt(i)+"").equals(" "))
				 {  System.out.println(PostContent.charAt(i));

					  HashTag =PostContent.charAt(i)+"";
					 i++;
				 }
				 System.out.println(HashTag);
				 }
			 
			PostBuilder PPost=new PagePost(20,PageId);
			Director D =new Director(PPost);
		//	(String post ,int likes ,UserEntity U ,String HashTag ,String privcy)
			D.constructPost(PostContent, 0, PostOwner, HashTag, privcy);
			
			PostBuilder PPost1=D.getGetPost();
		
			PPost1.SavePost();
			
			JSONObject object = new JSONObject();
			
			object.put("Status", "OK");
			return object.toString();
		}
}
