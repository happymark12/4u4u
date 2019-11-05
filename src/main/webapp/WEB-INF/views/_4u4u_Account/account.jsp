<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 帳戶管理</title>

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


</head>
<body class="aa-price-range">
	<!-- Pre Loader -->
	<div id="aa-preloader-area">
		<div class="pulse"></div>
		<div
			style="font: bold 24px verdana; text-align: center; position:absolute;left:46.5%;top:40%;">
			${MsgOK.UpdateOK}${LoginSuccess}</div>
			
  </div>
  
  <% // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
    session.removeAttribute("LoginSuccess");  
 %>
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
											class="aa-register"><i class="fa fa-user-plus"
											aria-hidden="true"></i>註冊</a>
										<a href="<c:url value='/login' />" class="aa-login"><i
											class="fa fa-sign-in" aria-hidden="true"></i>登入</a>

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
											<a style="color: white" href="<c:url value='/logout' />">登出</a>

										</c:if>

										<c:if test="${LoginOK.state==1||LoginOK.state==2}">
											<span style="color: white"> Hi ${LoginOK.name} </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a style="color: white" href="<c:url value='/logout' />">
												登出 </a>

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



	<div id="account">
		<div id="page">
			<header id="fh5co-header" class="fh5co-cover " role="banner"
				data-stellar-background-ratio="0.5">
				<div class="overlay"></div>
				<div class="container">
					<div class="row">
						<div class="col-md-10 col-md-offset-1 text-center">
							<div class="display-t js-fullheight">
								<div class="display-tc js-fullheight animate-box"
									data-animate-effect="fadeIn">
									<h2 style="margin: 150px">
										<span></span>
									</h2>
									<div class="profile-thumb"
										style="background: url(${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER);"></div>
									<h1>
										<span> <c:choose>
												<c:when
													test="${LoginOK.state==1||LoginOK.state==2||LoginOK.state==5}">
									${LoginOK.name}
								</c:when>
												<c:otherwise>
									請先註冊或登錄才能使用我的帳戶
									</c:otherwise>
											</c:choose>
										</span>
									</h1>
									<h2>
										<c:choose>
											<c:when test="${LoginOK.state==1}">
												<span>一般會員</span>
											</c:when>
											<c:when test="${LoginOK.state==2}">
												<span>VIP會員</span>
											</c:when>
											<c:when test="${LoginOK.state==5}">
												<span>管理者</span>
											</c:when>
											<c:otherwise>
												<span>未註冊會員</span>
											</c:otherwise>
										</c:choose>
									</h2>

									<div class=" text-left">
										<!--
									<h3 style="padding-top: 10px; padding-bottom: 5px;" class="text-center">升級的好處</h3>
								<div style="display: flex">
								<div style="width: 50%; padding: 20px;padding-left: 30px ">
								<ul >
									
									<li>查詢加倍</li>
									<li>免費提供給所有人立即與您聯繫</li>
									
								</ul>
									</div>
								<div style="width: 50%;padding: 20px;padding-left: 30px ">
									<ul >
									
									<li>提升您的廣告在搜索結果中的排名</li>
									<li>不要等待7天-立即聯繫所有廣告</li>
									
									</ul>
									
							    </div>	
										
								</div>
-->
										<div class="form-group text-center">
											<input type="submit" value="立即升級" class="btn btn-primary">

										</div>


									</div>


								</div>
							</div>
						</div>
					</div>
				</div>
			</header>
		</div>







		<div id="fh5co-work" class="fh5co-bg-dark">
			<div class="container">
				<div class="row animate-box">
					<!--
				<div class="col-md-8 col-md-offset-2 text-center fh5co-heading">
					<h2>Work</h2>
				</div>
-->
				</div>
				<div class="row">
					<div class="col-md-6 text-center col-padding animate-box">
						<a href="#" class="work"
							style="background-image: url(img/account/portfolio-1.jpg);">
							<div class="desc">
								<h1>我的留言</h1>
								<span>檢查和管理您的最新消息</span>
							</div>
						</a>
					</div>
					<div class="col-md-6 text-center col-padding animate-box">
						<a href="/4u4u/_4u4u/MyAdServlet?memId=${LoginOK.memId}&adStyle=0"
							class="work"
							style="background-image: url(img/account/portfolio-2.jpg);">
							<div class="desc">
								<h1>我的廣告</h1>
								<span>編輯和管理您的廣告</span>
							</div>
						</a>
					</div>
					<div class="col-md-4 text-center col-padding animate-box">
						<a href="<c:url value='/savedAd' />" class="work"
							style="background-image: url(img/account/portfolio-5.jpg);">
							<div class="desc">
								<h1>已保存的廣告</h1>
								<span>查看和管理您保存的廣告</span>
							</div>
						</a>
					</div>
					<div class="col-md-4 text-center col-padding animate-box">
						<a href="<c:url value='/interestedAd' />" class="work"
							style="background-image: url(img/account/portfolio-3.jpg);">
							<div class="desc">
								<h1>誰對你感興趣</h1>
								<span>查看誰對您的廣告表達了興趣</span>
							</div>
						</a>
					</div>
					<div class="col-md-4 text-center col-padding animate-box">
						<a
							href="/4u4u/_4u4u/processUpdateMember?memId=${LoginOK.memId}&state=${LoginOK.state}"
							class="work"
							style="background-image: url(img/account/portfolio-4.jpg);">
							<div class="desc">
								<h1>修改會員資料</h1>
								<span>在這裡管理您的帳戶信息</span>
							</div>
						</a>
					</div>



				</div>
			</div>
		</div>
	</div>



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

</body>
</html>