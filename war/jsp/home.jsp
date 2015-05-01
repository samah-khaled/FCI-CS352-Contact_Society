<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome b2a ya ${it.name} </p>
<p> This is should be user home page </p>

<form action="/social/UserPost" method= "post">
<textarea name="PostContent" rows="10" cols="40">
</textarea>
 Feeling <input type="text" name ="Feelings" />

<select name="privcy">
<option value="Public">public</option>
<option value="Private">private</option>
<option value="Custom">custom</option> <br> 
 <input type="hidden" name=email value="${it.email}"/>

<input type="submit" value ="Post">

</form>

<form action="/social/PagePost" method= "post">
<textarea name="PostContent" rows="10" cols="40">
</textarea>
 Page Name <input type="text" name ="PageName" />

<select name="privcy">
<option value="Public">public</option>
<option value="Private">private</option>
<option value="Custom">custom</option> <br> 
 <input type="hidden" name=email value="${it.email}"/>

<input type="submit" value ="Post">

</form>



<a href="/social/signout/">Signout</a> <br>

<form action ="/social/friendrequest" method= "post"> 
 friend mail: <input type="text" name ="FriendMail" />

 <input type="hidden" name="SenderMail" value="${it.email}"/>
 
 <input type="submit" value="Send friend request">
</form>
 
	
<form action ="/social/acceptFriend" method= "GET"> 
 <input type="submit" value="check new friend "/>
</form>

<form action ="/social/search" method= "GET"> 
 <input type="submit" value="Search"/>
</form>


<form action ="/social/Conversationpage" method= "Post"> 
<input type="hidden" name="email" value="${it.email}"/>
 <input type="submit" value="Group Message"/>
</form>


<form action ="/social/MessagePage" method= "Post"> 
<input type="hidden" name="OwnerMail" value="${it.email}"/>
 <input type="submit" value=" Message"/>
</form>

<form action ="/social/allNotification" method= "Post"> 
<input type="hidden" name="email" value="${it.email}"/>
 <input type="submit" value="GetNotifications"/>
</form>

<form action ="/social/goTopage" method= "Post"> 
<input type="hidden" name="email" value="${it.email}"/>
 <input type="submit" value="CreatePage"/>
</form>


<form action="/social/HashTagTrends" method= "post">
 <input type="submit" value="Trends"/>
</form>

<form action ="/social/HashTageSearch" method= "Post"> 
<input type="hidden" name="email" value="${it.email}"/>
hash tag <input type="text" name="hashtag" />

 <input type="submit" value="CreatePage"/>
</form>

</body>
</html>