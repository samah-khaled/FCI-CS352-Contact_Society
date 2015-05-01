<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> ${it.sendcheck} </p>

<form action ="/social/SendMessage " method= "post"> 
Freind Mail <input type="text" name="FriendMail" /><br>
<input type="hidden" name="OwnerMail" value="${it.OwnerMail}"/>

 <input type="text" name ="messgcontent" />
 <input type="submit" value=" send ">
</form>
<br>

<form action ="/social/BackToHome " method= "Get"> 
 <input type="submit" value=" Back ">
</form>
</body>
</html>