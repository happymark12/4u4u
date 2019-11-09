
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--以上程式碼告訴IE瀏覽器，IE8/9及以後的版本都會以最高版本IE來渲染頁面。-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 設定畫面寬度width=device-width，設定畫面初始縮放比例initial-scale=1 -->
<title>4U4U | 室友是友</title>



<!-- Pre Loader -->
<!-- 4u4uicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">
<!-- Font awesome字型 -->
<link
	href="${pageContext.request.contextPath}/css/fontawesome-free-5.11.2-web/css/all.css"
	rel="stylesheet">

<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet">
<!-- Bootstrap引導 -->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<!-- slick slider光滑滑塊 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/slick.css">
<!-- price picker slider價格拖曳功能 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/nouislider.css">

<!-- Fancybox slider燈箱效果-彈出層展示外掛 -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/jquery.fancybox.css"
	type="text/css" media="screen" />
<!-- Theme color主題色 -->
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


<style>
.demo2+.tooltip>.tooltip-inner {
	background-color: orange;
	color: #555;
	font-size: 15px;
	padding: 5px 10px;
	box-shadow: 1px 1px 1px #aaa;
}

.demo2+.tooltip.bottom {
	margin-top: 5px;
}

.demo2+.tooltip.bottom>.tooltip-arrow {
	border-bottom: 5px solid orange;
}
</style>
</head>
<body class="aa-price-range">
	<div id="aa-preloader-area">
		<div class="pulse"></div>


		<c:if test="${! empty MsgOK.InsertOK}">
			<div
				style="font: bold 24px verdana; text-align: center; position: absolute; left: 37%; top: 40%;">

				${MsgOK.InsertOK}</div>

		</c:if>
		<c:if
			test="${! empty LoginSuccess || ! empty LogoutOK || ! empty MsgOK.InsertAdOK || ! empty validate}">
			<div
				style="font: bold 24px verdana; text-align: center; position: absolute; left: 46.5%; top: 40%;">

				${LogoutOK} ${LoginSuccess} ${validate} ${MsgOK.InsertAdOK}</div>

		</c:if>
		<!-- 		<div -->
		<!-- 			style="font: bold 24px verdana; text-align: center; position:absolute;left:46.5%;top:40%;"> -->

		<%-- 			${LogoutOK} --%>
		<%-- 			${LoginSuccess} --%>

		<%-- 			${validate}${MsgOK.InsertOK}${MsgOK.InsertAdOK} --%>

		<!-- 			</div> -->

	</div>

	<% // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
 	session.removeAttribute("LogoutOK");
    session.removeAttribute("LoginSuccess");  
    session.removeAttribute("validate");
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
									<% // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
									    session.removeAttribute("AccountBanned");  
									    session.removeAttribute("ActivateAccount"); 
									    session.removeAttribute("MsgOK");
									 %>

									<c:if test="${! empty LoginOK }">


										<c:if test="${LoginOK.state==5}">
											<span style="color: white"> 歡迎! 管理者&nbsp; </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a href="<c:url value='/logout' />" class="aa-login ">登出<i
												class="fa fa-sign-out" aria-hidden="true"></i></a>

										</c:if>

										<c:if test="${LoginOK.state==1||LoginOK.state==2}">
											<span style="color: white"> Hi ${LoginOK.name} </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a href="<c:url value='/logout' />" class="aa-login "> 登出<i
												class="fa fa-sign-out" aria-hidden="true"></i>
											</a>

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
						<li class="active"><a href="<c:url value='/' />">首頁</a></li>
						<!--瀏覽-->
						<li><a href="<c:url value='/properties' />">搜尋</a></li>
						<!--帳戶管理-->
						<li><a href="<c:url value='/account' />">帳戶管理</a></li>
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


	<section id="aa-slider">
		<div class="aa-slider-area">
			<!-- Top slider -->
			<div class="aa-top-slider">
				<!-- Top slider single slide -->
				<div class="aa-top-slider-single">
					<img src="${pageContext.request.contextPath}/img/slider/1.jpg"
						alt="img" style="width: 100%;">
					<!-- Top slider content -->
					<div class="aa-top-slider-content">
						<h2 class="aa-top-slider-title">
							<em class="fa fa-child" aria-hidden="true" style="color: #59ABE3"></em><em
								class="fa fa-child" style="color: #59ABE3" aria-hidden="true"></em>室友是友
						</h2>
						<hr style="background-color: #555; height: 1px; border: none;">
						<span class="aa-top-slider-catg">有時，你雖然看到理想的住房，但是自己無法承擔租金。
							所以尋找其他室友合租可能是最好的解決辦法。這時就要使用4U4U 組隊租屋，
							你可以跟隊友共享搜索、查看和決策內容，找到兩人一起能負擔的住房。</span>
						<!--
            <p class="aa-top-slider-location"><i class="fa fa-map-marker"></i>South Beach, Miami (USA)</p>
            <span class="aa-top-slider-off">30% OFF</span>
            <p class="aa-top-slider-price">$460,000</p>
-->
						<!--            <a href="#" class="aa-top-slider-btn">Read More <span class="fa fa-angle-double-right"></span></a>-->
					</div>
					<!-- / Top slider content -->
				</div>
				<div class="aa-top-slider-single">
					<img src="${pageContext.request.contextPath}/img/slider/2.jpg"
						alt="img">
					<!-- Top slider content -->
					<div class="aa-top-slider-content">
						<h2 class="aa-top-slider-title">
							<i class="fa fa-search" aria-hidden="true" style="color: #59ABE3"></i>搜尋
						</h2>
						<hr style="background-color: #555; height: 1px; border: none;">
						<span class="aa-top-slider-catg">當你發佈租屋廣告後，是時候開始搜尋，請積極主動的去尋找室友或住房。
							因為最好的房間和室友是很受歡迎的，慢了可能就錯過機會了。 你可以搜索列表中的篩選工具，提高搜尋效率。</span>
					</div>
					<!-- / Top slider content -->
				</div>
				<div class="aa-top-slider-single">
					<img src="${pageContext.request.contextPath}/img/slider/3.jpg"
						alt="img">
					<!-- Top slider content -->
					<div class="aa-top-slider-content">
						<h2 class="aa-top-slider-title">
							<i class="fa fa-comments-o fa-lg" aria-hidden="true"
								style="color: #59ABE3"></i>訊息
						</h2>
						<hr style="background-color: #555; height: 1px; border: none;">
						<span class="aa-top-slider-catg">我們內置的訊息系統，讓你可以安心地跟室友／房東聯絡，
							你的個人訊息將會被保密。不論一般會員或VIP會員， 你都可以透過訊息系統收到來自其他人的訊息。</span>
					</div>
					<!-- / Top slider content -->
				</div>
				<div class="aa-top-slider-single">
					<img src="${pageContext.request.contextPath}/img/slider/4.jpg"
						alt="img">
					<!-- Top slider content -->
					<div class="aa-top-slider-content">
						<h2 class="aa-top-slider-title">
							<i class="fa fa-users " aria-hidden="true" style="color: #59ABE3"></i>活動
						</h2>
						<hr style="background-color: #555; height: 1px; border: none;">
						<span class="aa-top-slider-catg">在合租前多了解剛認識的候選室友，
							確保大家都可以開心地生活在一起。你可以透過參加活動約出來互相聊天，很快你就會知道，大家是否合得來。</span>
					</div>
					<!-- / Top slider content -->

				</div>
				<!-- / Top slider single slide -->
			</div>
		</div>
	</section>


	<!-- End slider  -->

	<section id="aa-promo-banner1">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-promo-banner-area">
						<h3>找個好住房</h3>
						<p>讓您睡得安心。</p>

					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- 精選推薦 -->
	<section id="aa-latest-property">
		<div class="container">
			<div class="aa-latest-property-area">
				<div class="aa-title">
					<h2>精選推薦</h2>
					<span></span>
					<p>精選優質好物件，不容錯過!</p>


				</div>
				<div class="aa-latest-properties-content">
					<div class="row" id="indexAds">
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- / 精選推薦 -->



	<!-- 找到您的好室友 -->
	<section id="aa-promo-banner2">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-promo-banner-area">
						<h3>找到您的好室友</h3>
						<p>找尋志趣相投、生活習慣相仿的人一起生活，讓您的居住空間充滿溫暖及歡笑。</p>
						<!--            <a href="#" class="aa-view-btn">了解更多</a>-->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- /找到您的好室友 -->

	<!-- 優質好室友-->
	<section id="aa-agents">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-agents-area">
						<div class="aa-title">
							<h2>優質好室友</h2>
							<span></span>
							<p>找個好室友，讓您快樂天天有</p>
						</div>
						<!-- agents content -->
						<div class="aa-latest-properties-content">
							<div class="row" id="findHomeAds">


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- / 優質好室友-->

	<section id="aa-client-testimonial">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-client-testimonial-area">
						<div class="aa-title">
							<h1>我們的團隊</h1>
						</div>
						<!-- testimonial content -->
						<div class="aa-testimonial-content ">
							<!-- testimonial slider -->
							<ul class="aa-testimonial-slider  ">
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/1.png"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">邱俊華</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">全端大神</p>
											<span>組長</span>
										</div>
									</div>
								</li>
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/2.jpg"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">陳怡儒</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">前端技術總監</p>
											<span>組員</span>
										</div>
									</div>
								</li>
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/3.jpg"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">吳玉婷</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">前端執行總監</p>
											<span>組員</span>
										</div>
									</div>
								</li>
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/4.jpg"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">黃冠中</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">後端技術總監</p>
											<span>組員</span>
										</div>
									</div>
								</li>
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/5.jpg"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">潘學濬</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">後端CEO</p>
											<span>組員</span>
										</div>
									</div>
								</li>
								<li>
									<div class="aa-testimonial-single ">
										<div class="aa-testimonial-img">
											<img src="${pageContext.request.contextPath}/img/team/6.jpg"
												alt="testimonial img">
										</div>
										<div class="aa-testimonial-info">
											<p style="font-size: 40px">金復強</p>
										</div>
										<div class="aa-testimonial-bio">
											<p style="font-size: 25px">後端技術總裁</p>
											<span>組員</span>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<jsp:include page="footer.jsp"></jsp:include>

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
	<!-- 	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/index.js"></script>
</body>
</html>