<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>

<form action ="/social/Conversation" method= "post"> 
 chat name <input type="text" name ="ConvName" /><br>
<input type="hidden" name="email" value="${it.email}"/>
 
 <input type="submit" value="create group chat ">
</form>

</body>
</html>