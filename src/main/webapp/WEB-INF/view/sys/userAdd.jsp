<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="/login">登陆</a>
<form method="post" action="${pageContext.request.contextPath}/user/add">
	用户名：<input name="username" type="text"  /><br/>
	密码：<input name="password" type="password"  /><br/>
	真实姓名：<input name="name" type="text"  /><br/>
	邮箱：<input name="email" type="text"  /><br/>
	手机号：<input name="phone" type="text"  /><br/>
	<input type="submit" />
</form>

</body>
</html>