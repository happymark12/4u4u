<%--
	執行本網頁之前，會先執行_4u4u_init.filter.FindUserPassword.java這個過濾器。執行過濾器目的
	在檢視請求物件是否含有帳號與密碼等資料。
	  
        本網頁 login.jsp 提供登入的畫面，讓使用者輸入帳號與密碼。輸入完畢後，按下Submit按鈕，瀏覽器
        會帳號與密碼給  <Form>標籤action屬性對應的程式: _4u4u.controller.LoginServlet.java，
        由該Servlet來檢查帳號與密碼是否正確。
            
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 登入</title>

<!-- Favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">


<!-- Font awesome -->
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<!-- slick slider -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/slick.css">
<!-- price picker slider -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/nouislider.css">
<!-- Fancybox slider -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery.fancybox.css"
	type="text/css" media="screen" />
<!-- Theme color -->
<link id="switcher"
	href="${pageContext.request.contextPath}/css/theme-color/default-theme.css"
	rel="stylesheet">

<!-- Main style sheet -->
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet">


<!-- Google Font -->
<link href='https://fonts.googleapis.com/css?family=Vollkorn'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>
<body>
	<section id="aa-signin">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-signin-area">
						<div class="aa-signin-form">
							<div class="aa-signin-form-title">
								<span class="aa-property-home">會員登入</span>
								<%--               				 <c:if test="${  empty sessionScope.timeOut }"> --%>
								<!-- 表示使用逾時，重新登入 -->
								<!-- 									<h4>您好!請登入您的帳戶</h4> -->

								<%-- 								</c:if> --%>
								<hr>
								<br> <br>


								<c:if test="${ ! empty sessionScope.timeOut }">
									<!-- 表示使用逾時，重新登入 -->
									<h4>
										<font color='red'>${sessionScope.timeOut}</font>
									</h4>

								</c:if>

								<c:if test="${ ! empty ErrorMsgKey.LoginError}">
									<!-- 表示使用逾時，重新登入 -->
									<h4>
										<font color='red'>${ErrorMsgKey.LoginError}</font>
									</h4>

								</c:if>
							</div>

							<form class="contactform"
								action="<c:url value='/_4u4u/login.do' />" method="POST"
								name="loginForm">
								<div class="aa-single-field"
									style="position: relative; display: inline-block;">
									<!--                   <label for="email">信箱 <span class="required">*</span></label> -->
									<i
										style="position: absolute; left: 10px; top: calc(46% - 1em); color: #7A7A7A"
										class="fa fa-envelope fa-2x"></i>
									<c:if test="${! empty param.userId}">
										<input style="text-indent: 20px;" type="email" required="required" aria-required="true"
											value="${param.userId}" name="userId" placeholder="信箱">
									</c:if>
									<c:if test="${ empty param.userId}">

										<input style="text-indent: 20px;" type="email" required="required" aria-required="true"
											value="${requestScope.user}${param.userId}" name="userId"
											placeholder="信箱">
									</c:if>

									<Font color='red'>${ErrorMsgKey.AccountEmptyError}</Font>
									<div style="height: 40px"></div>
								</div>
								<div class="aa-single-field"
									style="position: relative; display: inline-block;">
									<!--                   <label for="password">密碼 <span class="required">*</span></label> -->
									<i
										style="position: absolute; left: 10px; top: calc(72% - 1em); color: #7A7A7A"
										class="fa fa-key fa-2x"></i>
									<c:if test="${! empty param.pswd}">
										<input style="text-indent: 20px;" type="password" name="pswd" placeholder="密碼"
											value="${param.pswd}">
									</c:if>
									<c:if test="${ empty param.pswd}">
										<input style="text-indent: 20px;" type="password" name="pswd" placeholder="密碼"
											value="${requestScope.password}${param.pswd}">
									</c:if>
									<Font color='red'>${ErrorMsgKey.PasswordEmptyError}</Font>
								</div>
								<div class="aa-single-field">
									<label> <input type="checkbox"
										style="width: 30px; height: 20px; vertical-align: sub;"
										name="rememberMe"
										<c:if test='${requestScope.rememberMe==true}'>
                  
                  checked='checked'
               </c:if>
										value="true"><span>記住我</span>
									</label>
								</div>
								<div class="aa-single-submit">
									<input type="submit" value="登入" class="aa-browse-btn"
										name="submit">
									<p>尚未有我們的帳戶嗎?趕快加入我們吧!</p>
									<br>
									<p>
										<a href="<c:url value='/register' />">立即註冊</a>
									</p>
									<br> <u><a href="<c:url value='/' />">回首頁</a></u>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- jQuery library -->
	<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<!-- slick slider -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/slick.js"></script>
	<!-- Price picker slider -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/nouislider.js"></script>
	<!-- mixit slider -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.mixitup.js"></script>
	<!-- Add fancyBox -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.fancybox.pack.js"></script>
	<!-- Custom js -->
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>

</body>
</html>