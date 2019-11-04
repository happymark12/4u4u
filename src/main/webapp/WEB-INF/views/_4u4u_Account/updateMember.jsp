<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 修改會員資料</title>

<!-- Favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">


<!-- Font awesome -->
<link
	href="${pageContext.request.contextPath}/css/font-awesome.css"
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
								<span class="aa-property-home" >修改會員資料</span>
<!-- 							
	<h4>歡迎您!建立一個帳戶加入我們吧! </h4> -->
							<hr>
								 <Font color="red" style="font: bold 15px verdona">${MsgMap.passwordError}</Font>
							</div>
							<form class="contactform" ENCTYPE="multipart/form-data"
								method="POST" action="<c:url value='/_4u4u/updateMember?memId=${LoginOK.memId}&state=${LoginOK.state}'/>">
								<div class="aa-single-field">
									<label for="email">信箱 <span class="required">*</span></label> <input
										type="email" required="required" aria-required="true"
										name="eMail" value="${email}" readonly="readonly"> <font size="-1"
										color="#FF0000">${MsgMap.errorEmailEmpty}${MsgMap.errorIDDup}</font>
								</div>
								<div class="aa-single-field">
									<label for="password">密碼 <span class="required">*</span></label>
									<input type="password" name="password" required
										value=""> <font color="red" size="-1">${MsgMap.errorPasswordEmpty}</font>
								</div>
								<div class="aa-single-field">
									<label for="confirm-password">確認密碼 <span
										class="required">*</span></label> <input type="password"
										name="password2" value="" required> <font
										color="red" size="-1">${MsgMap.errorPassword2Empty}</font>
								</div>
								<div class="aa-single-field">
									<label for="name">姓名 <span class="required">*</span></label> <input
										type="text" required="required" aria-required="true"
										name="name" value="${name}"> <font color="red"
										size="-1">${MsgMap.errorName}</font>
								</div>
								<div class="aa-single-field">
									
							<label for="gender">性別<span class="required">*</span></label>
									<c:if test="${! empty gender }">
										<c:if test="${ gender=='男' }">
											<label><input type="radio" name="gender" value="男" checked
												style="margin: 10px 3px 0px 0px">男
      								<input type="radio" name="gender" value="女"
												style="margin: 10px 3px 0px 0px">女</label>
      								<font color="red" size="-1">${MsgMap.errorGenderEmpty}</font>
										</c:if>
										<c:if test="${ gender=='女' }">
											<label><input type="radio" name="gender" value="男" checked
												style="margin: 10px 3px 0px 0px">男
      								<input type="radio" name="gender" value="女" checked
												style="margin: 10px 3px 0px 0px">女</label>
      								<font color="red" size="-1">${MsgMap.errorGenderEmpty}</font>
										</c:if>
									</c:if>
									<c:if test="${ empty gender }">
										<label><input type="radio" name="gender" value="男"
											 required>男
      								<input type="radio" name="gender" value="女"
											required>女</label>
      								<font color="red" size="-1">${MsgMap.errorGenderEmpty}</font>
									</c:if>
								</div>
								<div class="aa-single-field">
									<label for="picture">照片&nbsp;&nbsp;  </label> 
									<label><Input Type="file" name="picture"></label>
										<font color="red" size="-1">${MsgMap.errPicture}</font> 
								</div>
								<div class="aa-single-submit">
									<input type="submit" value="修改會員" name="submit"><br>
<%-- 								 <u><a href="<c:url value='/' />">回首頁</a></u> --%>
								 <u><a href="<c:url value='/account'/>">帳戶管理</a></u>
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
	<script
		src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
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