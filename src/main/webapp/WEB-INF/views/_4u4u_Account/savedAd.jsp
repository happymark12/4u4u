<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 已儲存廣告</title>

<!-- Favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">


<!-- Font awesome -->
<link href="${pageContext.request.contextPath}/css/fontawesome-free-5.11.2-web/css/all.css" rel="stylesheet">

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
<link href="https://fonts.googleapis.com/css?family=Vollkorn"
	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans"
	rel="stylesheet" type="text/css">


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


<style>
.demo2 + .tooltip > .tooltip-inner {
background-color: orange;
color: #555;
font-size: 15px;
padding: 5px 10px;
box-shadow: 1px 1px 1px #aaa;
}
.demo2 + .tooltip.bottom {
margin-top: 5px;
}
.demo2 + .tooltip.bottom > .tooltip-arrow {
border-bottom: 5px solid orange;
}
</style>
</head>
<body class="aa-price-range">
	<!-- Pre Loader -->
	<div id="aa-preloader-area">
		<div class="pulse"></div>
	</div>
	<!-- SCROLL TOP BUTTON -->
	<a class="scrollToTop" href="#"><i class="fa fa-angle-double-up"></i></a>
	<!-- END SCROLL TOP BUTTON -->




	<!-- Start header section -->
	<header id="aa-header">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-header-area">
						<div class="row">
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="aa-header-left">
									<div class="aa-telephone-no">
										<span class="fa fa-phone"></span> (02)2771-2171#1720
									</div>
									<div class="aa-email hidden-xs">
										<span class="fa fa-envelope-o"></span> 4u4ufindhome@gmail.com
									</div>
								</div>
							</div>
							<div class="col-md-6 col-sm-6 col-xs-6">
								<div class="aa-header-right">
									<c:if test="${ empty LoginOK }">
										<span style="color: red;">${MsgOK.InsertOK}${AccountBanned}${ActivateAccount}</span>
										&nbsp;&nbsp;<a href="<c:url value='/register' />"
											class="aa-register"><i class="fa fa-user-plus" aria-hidden="true"></i>註冊</a>
										<a href="<c:url value='/login' />" class="aa-login"><i class="fa fa-sign-in" aria-hidden="true"></i>登入</a>

									</c:if>
									<%
										// 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
										session.removeAttribute("AccountBanned");
										session.removeAttribute("ActivateAccount");
										session.removeAttribute("MsgOK");
									%>

									<c:if test="${! empty LoginOK }">



										<c:if test="${LoginOK.state==5}">
											<span style="color: white"> 歡迎! 管理者&nbsp; </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a href="<c:url value='/logout' />"  class="aa-login ">登出<i class="fa fa-sign-out" aria-hidden="true"></i></a>

										</c:if>

										<c:if test="${LoginOK.state==1||LoginOK.state==2}">
											<span style="color: white"> Hi ${LoginOK.name} </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a href="<c:url value='/logout' />"  class="aa-login ">
												登出<i class="fa fa-sign-out" aria-hidden="true"></i> </a>

										</c:if>

									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<!-- End header section -->

	<!-- Start menu section -->
	<section id="aa-menu-area">
		<nav class="navbar navbar-default main-navbar" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<!-- FOR MOBILE VIEW COLLAPSED BUTTON -->
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<!-- LOGO -->
					<!-- Image based logo -->
					<a class="navbar-brand aa-logo-img" href="<c:url value='/' />"><img
						src="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
						alt="logo"></a>
					<!-- Text based logo -->
					<a class="navbar-brand aa-logo" href="<c:url value='/' />">4U4U</a>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul id="top-menu" class="nav navbar-nav navbar-right aa-main-nav">
						<!--首頁-->
						<li><a href="<c:url value='/' />">首頁</a></li>
						<!--瀏覽-->
						<li><a href="<c:url value='/properties' />">搜尋</a></li>
						<!--帳戶管理-->
						<li class="active"><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown "><a class="dropdown-toggle">發佈廣告 <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="<c:url value='/PostRoomRentAd' />">出租廣告</a></li>
								<li><a href="<c:url value='/PostWantedRoomAd' />">找房廣告</a></li>
							</ul></li>
						<!--活動-->
						<li><a href="<c:url value='/activity' />">活動</a></li>

						<!--關於我們-->
						<li class="dropdown"><a class="dropdown-toggle">關於我們 <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<!-- 								<li><a href="blog-archive.html">使用說明</a></li> -->
								<li><a href="<c:url value='/whyuse' />">為何使用</a></li>
								<!-- 								<li><a href="blog-single.html">告訴好友</a></li> -->
								<li><a href="<c:url value='/contact' />">聯絡我們</a></li>
								<li><a href="<c:url value='/qa' />">常見問與答</a></li>
							</ul></li>
					</ul>
				</div>
			</div>
		</nav>
	</section>
	<!-- End menu section -->



	<section id="aa-property-header" class="rent">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-property-header-inner">
						<h2>已保存的廣告</h2>
						<br>
						<!--			  <h3 style="color: #fff">房間出租廣告</h3>-->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Proerty header  -->

	<!-- Start Properties  -->
	<section id="aa-properties">
		<div class="container saved">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-properties-content">
						<!-- start properties content head -->
						<div class="aa-properties-content-head text-center">
							<div class="aa-properties-content-head-left">
								<form action="" class="aa-sort-form">
									<label> <input type="checkbox"  id="selectAllButton"
										style="zoom: 130%; vertical-align: middle; margin-bottom: 7px;"><span>全選</span></label>

								</form>

							</div>
							<div class="aa-properties-content-head-left">
								<input type="submit" class="delete-btn" value="刪除所選廣告" id="deleteButton" disabled>
							</div>

							<label class="room-ad"><a href="0" class="active" id="roomRentAd" >房間出租廣告&nbsp;
							</a> | <a href="1" id="wantedRoomAd" >&nbsp;徵求房間廣告</a></label>
							<div class="aa-properties-content-head-right">
								<label for="">排序</label> <select name="sortOption"
									id="sortOption">
									<option value="0">預設排序</option>
									<option value="1">金額由低到高</option>
									<option value="2">金額由高到低</option>
									<option value="3">刊登時間由新到舊</option>
									<option value="4">修改時間由新到舊</option>
								</select>

							</div>
						</div>
						<!-- Start properties content body -->
						<div class="aa-properties-content-body" style="margin-left: 0px">

							<ul class="aa-properties-nav saved" id="targetAds" style="width:100%;">
	


							</ul>

						</div>
						<!-- Start properties content bottom -->
						<div class="aa-properties-content-bottom">
							<nav>
								<ul style="width: 360px;" class="pagination" id="pagination">
						
								</ul>

							</nav>
						</div>
						<div style="height:540px;display:none;" id="emptyDiv"></div>
					</div>
				</div>
			</div>
		</div>
	</section>




	<jsp:include page="../footer.jsp"></jsp:include>





	<!-- jQuery library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<!-- slick slider -->
	<!-- Price picker slider -->
	<!-- mixit slider -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.mixitup.js"></script>
	<!-- Add fancyBox -->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.fancybox.pack.js"></script>
	<!-- Custom js -->
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/slick.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/nouislider.js"></script>
	<!-- 	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/savedAd.js"></script>


</body>
</html>