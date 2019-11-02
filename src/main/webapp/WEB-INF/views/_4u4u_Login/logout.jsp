<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登出</title>
</head>
<body>
<!-- 先將使用者名稱取出 -->
<c:set var="memberName" value="${ LoginOK.name }" />
<!-- 移除放在session物件內的屬性物件 -->
<c:remove var="LoginOK" scope="session" />
<jsp:useBean id='logoutBean' class='_4u4u.model.LogoutBean' scope='page' />
    
<c:set target='${logoutBean}' 
   property='session'    value='${pageContext.session}'/>
   
${logoutBean.logout}

<%
	session = request.getSession();
	session.setAttribute("LogoutOK", "登出成功");
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath()));
%>
<%-- <c:redirect url="/"/> --%>
</body> 
</html>