<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>

<body>
<p>  ${it.sendcheck} </p>
<p>  ${it.addcheck}  </p>
<form action ="/social/SendMessageToGroup " method= "post"> 
Conversation name  <input type="text" name="ConvName" />
<input type="hidden" name="OwnerMail" value="${it.OwnerMail}"/>
 <input type="text" name ="messgcontent" />
 <input type="submit" value=" send ">
</form>
<br>

<form action ="/social/AddChatfreind " method= "post"> 
 <input type="text" name ="FriendMail" />
 <input type="hidden" name="ConvId"  value="${it.ConvId}"/>
 <input type="hidden" name="OwnerMail" value="${it.OwnerMail}"/>
 <input type="submit" value="add to chat ">
</form>
<br><br>

</body>
</html>