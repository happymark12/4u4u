<%@page import="_4u4u.model.WantedRoomBean"%>
<%@page import="_4u4u.model.RoomRentBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 廣告內容</title>

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

<style>
h3 {
	margin-bottom: 20px;
	font-size:35px;
}
p,span{
	font-size:25px;
}
/* jssor slider arrow navigator skin 05 css */
/*
        .jssora05l                  (normal)
        .jssora05r                  (normal)
        .jssora05l:hover            (normal mouseover)
        .jssora05r:hover            (normal mouseover)
        .jssora05l.jssora05ldn      (mousedown)
        .jssora05r.jssora05rdn      (mousedown)
        .jssora05l.jssora05lds      (disabled)
        .jssora05r.jssora05rds      (disabled)
        */
.jssora05l, .jssora05r {
	display: block;
	position: absolute;
	/* size of arrow element */
	width: 40px;
	height: 40px;
	cursor: pointer;
	background: url('${pageContext.request.contextPath}/img/a17.png')
		no-repeat;
	overflow: hidden;
}

.jssora05l {
	background-position: -10px -40px;
}

.jssora05r {
	background-position: -70px -40px;
}

.jssora05l:hover {
	background-position: -130px -40px;
}

.jssora05r:hover {
	background-position: -190px -40px;
}

.jssora05l.jssora05ldn {
	background-position: -250px -40px;
}

.jssora05r.jssora05rdn {
	background-position: -310px -40px;
}

.jssora05l.jssora05lds {
	background-position: -10px -40px;
	opacity: .3;
	pointer-events: none;
}

.jssora05r.jssora05rds {
	background-position: -70px -40px;
	opacity: .3;
	pointer-events: none;
}
/* jssor slider thumbnail navigator skin 01 css */
/*.jssort01 .p            (normal).jssort01 .p:hover      (normal mouseover).jssort01 .p.pav        (active).jssort01 .p.pdn        (mousedown)*/
.jssort01 .p {
	position: absolute;
	top: 0;
	left: 0;
	width: 72px;
	height: 72px;
}

.jssort01 .t {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	border: none;
}

.jssort01 .w {
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
	height: 100%;
}

.jssort01 .c {
	position: absolute;
	top: 0px;
	left: 0px;
	width: 68px;
	height: 68px;
	border: #000 2px solid;
	box-sizing: content-box;
	background: url('${pageContext.request.contextPath}/img/t01.png') -800px
		-800px no-repeat;
	_background: none;
}

.jssort01 .pav .c {
	top: 2px;
	_top: 0px;
	left: 2px;
	_left: 0px;
	width: 68px;
	height: 68px;
	border: #000 0px solid;
	_border: #fff 2px solid;
	background-position: 50% 50%;
}

.jssort01 .p:hover .c {
	top: 0px;
	left: 0px;
	width: 70px;
	height: 70px;
	border: #fff 1px solid;
	background-position: 50% 50%;
}

.jssort01 .p.pdn .c {
	background-position: 50% 50%;
	width: 68px;
	height: 68px;
	border: #000 2px solid;
}

* html .jssort01 .c, * html .jssort01 .pdn .c, * html .jssort01 .pav .c
	{ /* ie quirks mode adjust */
	width /**/: 72px;
	height /**/: 72px;
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
											<span style="color: white;font-size:15px"> 歡迎! 管理者&nbsp; </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a href="<c:url value='/logout' />"  class="aa-login ">登出<i class="fa fa-sign-out" aria-hidden="true"></i></a>

										</c:if>

										<c:if test="${LoginOK.state==1||LoginOK.state==2}">
											<span style="color: white;font-size:15px"> Hi ${LoginOK.name} </span>
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
						<li class="active"><a href="<c:url value='/properties' />">搜尋</a></li>
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

	<!-- Start Proerty header  -->

	<section id="aa-property-header" class="search">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-property-header-inner">
						<h2>廣告內容</h2>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Proerty header  -->

	<section id="aa-properties">
		<div class="container">
			<div class="row">
				<div class="col-xs-2">
				</div>
				<div class="col-xs-8">
					<div class="aa-properties-content">
						<!-- Start properties content body -->

						<div class="aa-properties-details">

							<div class="aa-properties-info">
								<div class="row">
<!-- 									<div class="col-xs-1 "></div> -->
									<div class="col-md-8 col-xs-7 text-left">
										<p style="margin-bottom: 20px;color:black;font-size:30px;font-weight:bold;">${findRoomAd.adTitle}${roomRentAd.adTitle}</p>
									</div>
									<div class="col-md-4 col-xs-5 text-right" >
										<p>
<!-- 											<a id="saveAd" href="#">儲存廣告</a> -->
											<a  id="saveAd" class="aa-secondary-btn" href="#" style="font-size:23px">
            										
                                                    <i class="fa fa-heart"></i> 
											儲存廣告</a>
										</p>
									</div>

								<c:if test="${!empty images }">
									<div id="jssor_1"
										style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 800px; height: 456px; overflow: hidden; visibility: hidden; background-color: #24262e;">
										<!-- Loading Screen -->
										<div data-u="loading"
											style="position: absolute; top: 0px; left: 0px;">
											<div
												style="filter: alpha(opacity = 70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
											<div
												style="position: absolute; display: block; background: url('${pageContext.request.contextPath}/img/loading.gif') no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
										</div>
										<div data-u="slides"
											style="cursor: default; position: relative; top: 0px; left: 0px; width: 800px; height: 356px; overflow: hidden;">
											<c:if test="${!empty roomRentAd }">
												<c:forEach var='imgName' varStatus='vs' items='${images}'>
													<c:if test='${vs.first}'>
														<div data-p="144.50">
															<img data-u="image"
																src="/disksource/roomRentAd/${imgName}" /> <img
																data-u="thumb" src="/disksource/roomRentAd/${imgName}" />
														</div>

													</c:if>
													<c:if test='${vs.first!=true}'>
														<div data-p="144.50" style="display: none;">
															<img data-u="image"
																src="/disksource/roomRentAd/${imgName}" /> <img
																data-u="thumb" src="/disksource/roomRentAd/${imgName}" />
														</div>
													</c:if>

												</c:forEach>
											</c:if>
											<c:if test="${!empty findRoomAd }">
												<c:forEach var='imgName' varStatus='vs' items='${images}'>
													<c:if test='${vs.first}'>
														<div data-p="144.50">
															<img data-u="image"
																src="/disksource/wantedRoomAd/${imgName}" /> <img
																data-u="thumb" src="/disksource/wantedRoomAd/${imgName}" />
														</div>

													</c:if>
													<c:if test='${vs.first!=true}'>
														<div data-p="144.50" style="display: none;">
															<img data-u="image"
																src="/disksource/wantedRoomAd/${imgName}" /> <img
																data-u="thumb" src="/disksource/wantedRoomAd/${imgName}" />
														</div>
													</c:if>

												</c:forEach>
											</c:if>
											<a data-u="any" href="http://www.jssor.com"
												style="display: none">Image Gallery</a>
										</div>
										<!-- Thumbnail Navigator -->
										<div data-u="thumbnavigator" class="jssort01"
											style="position: absolute; left: 0px; bottom: 0px; width: 800px; height: 100px;"
											data-autocenter="1">
											<!-- Thumbnail Item Skin Begin -->
											<div data-u="slides" style="cursor: default;">
												<div data-u="prototype" class="p">
													<div class="w">
														<div data-u="thumbnailtemplate" class="t"></div>
													</div>
													<div class="c"></div>
												</div>
											</div>
											<!-- Thumbnail Item Skin End -->
										</div>
										<!-- Arrow Navigator -->
										<span data-u="arrowleft" class="jssora05l"
											style="top: 158px; left: 8px; width: 40px; height: 40px;"></span>
										<span data-u="arrowright" class="jssora05r"
											style="top: 158px; right: 8px; width: 40px; height: 40px;"></span>
									</div>

								</c:if>




								<c:if test="${empty images }">
									<div id="jssor_1"
										style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 800px; height: 456px; overflow: hidden; visibility: hidden; background-color: #24262e;">
										<!-- Loading Screen -->
										<div data-u="loading"
											style="position: absolute; top: 0px; left: 0px;">
											<div
												style="filter: alpha(opacity = 70); opacity: 0.7; position: absolute; display: block; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
											<div
												style="position: absolute; display: block; background: url('${pageContext.request.contextPath}/img/loading.gif') no-repeat center center; top: 0px; left: 0px; width: 100%; height: 100%;"></div>
										</div>
										<div data-u="slides"
											style="cursor: default; position: relative; top: 0px; left: 0px; width: 800px; height: 356px; overflow: hidden;">
											<div data-p="144.50">
												<img data-u="image"
													src="${pageContext.request.contextPath}/img/NoImage.png" />
												<img data-u="thumb"
													src="${pageContext.request.contextPath}/img/NoImage.png" />
											</div>




											<a data-u="any" href="http://www.jssor.com"
												style="display: none">Image Gallery</a>
										</div>
										<!-- Thumbnail Navigator -->
										<div data-u="thumbnavigator" class="jssort01"
											style="position: absolute; left: 0px; bottom: 0px; width: 800px; height: 100px;"
											data-autocenter="1">
											<!-- Thumbnail Item Skin Begin -->
											<div data-u="slides" style="cursor: default;">
												<div data-u="prototype" class="p">
													<div class="w">
														<div data-u="thumbnailtemplate" class="t"></div>
													</div>
													<div class="c"></div>
												</div>
											</div>
											<!-- Thumbnail Item Skin End -->
										</div>
										<!-- Arrow Navigator -->
										<span data-u="arrowleft" class="jssora05l"
											style="top: 158px; left: 8px; width: 40px; height: 40px;"></span>
										<span data-u="arrowright" class="jssora05r"
											style="top: 158px; right: 8px; width: 40px; height: 40px;"></span>
									</div>

								</c:if>



								<c:if test="${!empty roomRentAd }">
									<br>
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/placeholder _use.png" width="20"
														height="20">
									   &nbsp;${area}</h3>

									<a id="iframeControl" href=""><span
										style="color: blue">Google Map 顯示約略地址</span></a>
									<hr style="height: 10px">
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/file.png" width="20"
														height="20">
									&nbsp;廣告描述</h3>
									<p id="adDetail"
										style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${adDescription}</p>
									<c:if test="${! empty needReadMore }">
										<a id="readMore" href="#adDetal"><span style="color: blue">閱讀更多</span></a>
									</c:if>

									<hr style="height: 10px">
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/home.png" width="20"
														height="20">
									&nbsp;物件類型 :
										${roomRentAd.adRentType==0?"整層出租":(roomRentAd.adRentType==1?"獨立套房":(roomRentAd.adRentType==2?"分租套房":"出租房間(有公共空間)"))}</h3>

									<c:if test="${roomRentAd.adRentType==0}">
										<p>總租金:$${roomRentAd.adRentPrice} 
										    <br>      
										         押金:
											${roomRentAd.adDeposit==2?"2個月":(roomRentAd.adDeposit==1?"1個月":"面議")}
										    <br>     
										         格局:
										    <c:if test="${roomRentAd.adRoomNum!=0}">${roomRentAd.adRoomNum}房</c:if>
											<c:if test="${roomRentAd.adToiletNum!=0}">${roomRentAd.adToiletNum}衛</c:if>
											<c:if test="${roomRentAd.adLivingRoomNum!=0}">${roomRentAd.adLivingRoomNum}廳</c:if>
											<c:if test="${roomRentAd.adBalconyNum!=0}">${roomRentAd.adBalconyNum}陽台</c:if>
										</p>
<!-- 										<p> -->
<!-- 											格局: -->
<%-- 											<c:if test="${roomRentAd.adRoomNum!=0}">${roomRentAd.adRoomNum}房</c:if> --%>
<%-- 											<c:if test="${roomRentAd.adToiletNum!=0}">${roomRentAd.adToiletNum}衛</c:if> --%>
<%-- 											<c:if test="${roomRentAd.adLivingRoomNum!=0}">${roomRentAd.adLivingRoomNum}廳</c:if> --%>
<%-- 											<c:if test="${roomRentAd.adBalconyNum!=0}">${roomRentAd.adBalconyNum}陽台</c:if> --%>
<!-- 										</p> -->
										<hr style="height: 10px">
									</c:if>
									<c:if test="${roomRentAd.adRentType!=0}">
										<c:forEach var='room' varStatus='vs'
											items='${roomRentAd.roomItems}'>
											<c:if test="${room.roomState==true}">
												<p>$${room.rentPrice}${room.roomType==0?" (套房)":" (雅房)"}
													押金: ${room.deposit==2?"2個月":(room.deposit==1?"1個月":"面議")}</p>
											</c:if>
											<c:if test="${room.roomState==false}">
												<p style="text-decoration: line-through;">$${room.rentPrice}${room.roomType==0?" (套房)":" (雅房)"}</p>
											</c:if>
										</c:forEach>
										<hr style="height: 10px">
									</c:if>

									<c:if test="${! empty checkInDate  }">
										<h3><img alt="img" src="${pageContext.request.contextPath}/img/calendar.png" width="20"
														height="20"> 
										&nbsp;可入住時間</h3>
										<p style="color:black;">${checkInDate}</p>
										<hr style="height: 10px">
									</c:if>
									<c:if
										test="${! empty roomRentAd.adExtraCost || ! empty agentFee }">

										<h3><img alt="img" src="${pageContext.request.contextPath}/img/plus.png" width="20"
														height="20"> 
<!-- 										<i class="fa fa-star" aria-hidden="true" style="color:black;"></i> -->
										
										&nbsp;額外費用</h3>
										<p>${roomRentAd.adExtraCost}
											<c:if test="${! empty agentFee}">	
									仲介費:${agentFee}
									</c:if>
										</p>
										<hr style="height: 10px">
									</c:if>
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/bigmirror.png" width="20"
														height="20"> 
<!-- 									<i class="fa fa-search-plus" aria-hidden="true" style="color:black"></i> -->
									&nbsp;房間設施</h3>
									<div class="row">

										<c:forEach var='room' varStatus='vs'
											items='${roomRentAd.roomItems}'>
											<c:if test="${room.roomState==true}">

												<div class="col-md-4 col-sm-4 col-xs-12">

													<p>房間${vs.count}
														&nbsp;$${room.rentPrice}${room.roomType==0?" (套房)":" (雅房)"}</p>
													<ul>
														<c:if
															test="${! empty room.rentFloor && ! empty room.rentTotalFloor}">
															<li><p style="display:inline">樓層:
																	${room.rentFloor}/${room.rentTotalFloor}</p></li>
														</c:if>
														<c:if test="${! empty room.area }">
															<li><p style="display:inline">坪數: ${room.area}坪</p></li>
														</c:if>
														<c:if test="${room.hasBalcony }">
															<li><p style="display:inline">陽台</p></li>
														</c:if>
														<c:if test="${room.hasDuplex }">
															<li><p style="display:inline">樓中樓</p></li>
														</c:if>
														<c:if test="${room.hasWash }">
															<li><p style="display:inline">洗衣機</p></li>
														</c:if>
														<c:if test="${room.hasIceBox }">
															<li><p style="display:inline">冰箱</p></li>
														</c:if>
														<c:if test="${room.has4 }">
															<li><p style="display:inline">第四台</p></li>
														</c:if>
														<c:if test="${room.hasGas }">
															<li><p style="display:inline">天然瓦斯</p></li>
														</c:if>
														<c:if test="${room.hasTV }">
															<li><p style="display:inline">電視</p></li>
														</c:if>
														<c:if test="${room.hasWardrobe }">
															<li><p style="display:inline">衣櫃</p></li>
														</c:if>
														<c:if test="${room.hasSofa }">
															<li><p style="display:inline">沙發</p></li>
														</c:if>
														<c:if test="${room.hasHeater }">
															<li><p style="display:inline">熱水器</p></li>
														</c:if>
														
														<c:if test="${room.hasBroadBand }">
															<li><p style="display:inline">網路</p></li>
														</c:if>
														<c:if test="${room.hasDesk }">
															<li><p style="display:inline">桌子</p></li>
														</c:if>
														<c:if test="${room.hasChair }">
															<li><p style="display:inline">椅子</p></li>
														</c:if>
														<c:if test="${room.hasSingleBed }">
															<li><p style="display:inline">單人床</p></li>
														</c:if>
														<c:if test="${room.hasDoubleBed }">
															<li><p style="display:inline">雙人床</p></li>
														</c:if>
														<c:if test="${room.hasColdAir }">
															<li><p style="display:inline">冷氣</p></li>
														</c:if>

													</ul>
												</div>

											</c:if>
										</c:forEach>
									</div>
									<hr style="height: 10px">
									<c:if
										test="${roomRentAd.adRentType=='3'&&roomRentAd.adCurrentPeopleNum!='0'}">
										<h3><i class="fa fa-user-o" aria-hidden="true" style="color:black;"></i>
										&nbsp;當前室友狀況</h3>

										<div class="row">
											<div class="col-md-2 col-xs-3">
												<p>室友:</p>
												<p>抽菸:</p>
												<p>寵物:</p>
												<p>性別:</p>
												<c:if test="${roomRentAd.adCurAllowedSearchbySexOrient}">
													<p>性向:</p>
												</c:if>
												<c:if test="${! empty age }">
													<p>年齡:</p>
												</c:if>



											</div>
											<div class="col-md-10 col-xs-9">
												<p>${roomRentAd.adCurrentPeopleNum}人</p>
												<c:if test="${roomRentAd.adCurSmoke}">
													<p>有</p>
												</c:if>
												<c:if test="${!roomRentAd.adCurSmoke}">
													<p>沒有</p>
												</c:if>
												<c:if test="${roomRentAd.adCurHasPet}">
													<p>有</p>
												</c:if>
												<c:if test="${!roomRentAd.adCurHasPet}">
													<p>沒有</p>
												</c:if>
												<p>${roomRentAd.adCurGender}</p>
												<c:if test="${roomRentAd.adCurAllowedSearchbySexOrient}">
													<p>${sexOrient}</p>
												</c:if>
												<c:if test="${! empty age }">
													<p>${age}</p>
												</c:if>
											</div>

										</div>
										<hr style="height: 10px">
									</c:if>
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/users.png" width="20"
														height="20"> 
 		
									&nbsp;新房客偏好</h3>

									<div class="row">
										<div class="col-md-2 col-xs-3">
											<c:if test="${! empty roomRentAd.adFutureCoupleAccept }">

												<p>情侶:</p>
											</c:if>
											<p>性別:</p>
											<p>抽菸:</p>
											<p>寵物:</p>
											<p>職業:</p>
											<c:if test="${! empty roomMateAge}">

												<p>年齡:</p>
											</c:if>
										</div>
										<div class="col-md-10 col-xs-9">

											<c:if
												test="${! empty roomRentAd.adFutureCoupleAccept && roomRentAd.adFutureCoupleAccept}">
												<p>可</p>
											</c:if>
											<c:if
												test="${! empty roomRentAd.adFutureCoupleAccept && !roomRentAd.adFutureCoupleAccept}">
												<p>不可</p>
											</c:if>
											<p>${flatmateGender}</p>
											<c:if test="${roomRentAd.adFutureSmoke}">
												<p>可</p>
											</c:if>
											<c:if test="${!roomRentAd.adFutureSmoke}">
												<p>不可</p>
											</c:if>
											<c:if test="${roomRentAd.adFuturePet}">
												<p>可</p>
											</c:if>
											<c:if test="${!roomRentAd.adFuturePet}">
												<p>不可</p>
											</c:if>
											<p>${Fjob}</p>
											<c:if test="${! empty roomMateAge}">

												<p>${roomMateAge}</p>
											</c:if>
										</div>

									</div>
									<c:if
										test="${roomRentAd.adRentType==0 && !empty potentialList}">
										<br>
										<h3>可能的合租者</h3>
										<div class="row">
											<c:forEach var="candidate" items="${potentialList}">
												<div class=" col-sm-2 col-xs-3 align-center">
													<a
														href="<c:url value='/_4u4u/findRoomDetail/?adStyle=1&adId=${candidate.get(2)}'/>">
														<img height='80' width='80'
														src='${pageContext.request.contextPath}/_4u4u/getImage?id=${candidate.get(0)}&type=MEMBER'>
														<br>
														<p>${candidate.get(1)}</p>
													</a>
												</div>
											</c:forEach>
										</div>
									</c:if>
									<hr style="height: 10px">
									<div class="row">
										<div class="col-xs-12 text-center">
											<img height='100px' width='100px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${roomRentAd.roomRentMemId.memId}&type=MEMBER'>
											<p>${adOwner}</p>
										</div>
										<div class="col-xs-12 text-center ">
											<a class="btn btn-primary sendMessageAjax"
											style="font-size:25px"	href='<c:url value="/sendMessage/${roomRentAd.roomRentMemId.memId}/0/${roomRentAd.adId}"/>'>發送訊息</a>

										</div>

									</div>
								</c:if>

								<c:if test="${!empty findRoomAd }">
									<br>
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/avatar.png" width="20"
														height="20"> &nbsp; ${findRoomAd.wantedRoomAdMemId.name}</h3>
									<p>${job},&nbsp;${findRoomAd.wantedRoomAdMemId.gender}</p>
									<p>${wantedRoom}&nbsp;${buddyups}</p>
									<hr style="height: 10px">
									<h3 ><img alt="img" src="${pageContext.request.contextPath}/img/money-bag.png" width="20"
														height="20"> &nbsp;
									
									總預算 </h3>
									<p style="color:black;">$${findRoomAd.budget}</p>
									<hr style="height: 10px">
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/file.png" width="20"
														height="20">
									&nbsp;  廣告描述</h3>
																
									<p id="adDetail"
										style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${adDescription}</p>
									<c:if test="${! empty needReadMore }">
										<a id="readMore" href="#adDetal"><span style="color: blue">閱讀更多</span></a>
									</c:if>
									<hr style="height: 10px">
									<c:if test="${! empty checkInDate  }">
										<h3><img alt="img" src="${pageContext.request.contextPath}/img/calendar.png" width="20"
														height="20"> 
										&nbsp;
										可入住時間</h3>
										<p>${checkInDate}</p>
										<hr style="height: 10px">
									</c:if>
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/address-findhome.png" width="20"
														height="20">
									&nbsp;  找尋區域:</h3>
									
									
									<p>${lookInArea}</p>
									<hr style="height: 10px">
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/machine-learning.png" width="20"
														height="20">
									&nbsp; 想要的設施:</h3>
									
									
									<c:if test="${findRoomAd.hasWashMachine}">
										<p>洗衣機</p>
									</c:if>
									<c:if test="${findRoomAd.hasRefrigerator}">
										<p>冰箱</p>
									</c:if>
									<c:if test="${findRoomAd.hasTV}">
										<p>電視</p>
									</c:if>
									<c:if test="${findRoomAd.hasAirConditioning}">
										<p>冷氣</p>
									</c:if>
									<c:if test="${findRoomAd.hasWaterHeater}">
										<p>熱水器</p>
									</c:if>
									<c:if test="${findRoomAd.hasInternet}">
										<p>網路</p>
									</c:if>
									<c:if test="${findRoomAd.hasFourthTV}">
										<p>第四台</p>
									</c:if>
									<c:if test="${findRoomAd.hasGas}">
										<p>天然瓦斯</p>
									</c:if>
									<c:if test="${findRoomAd.hasWardrobe}">
										<p>衣櫃</p>
									</c:if>
									<c:if test="${findRoomAd.hasSofa}">
										<p>沙發</p>
									</c:if>
									<c:if test="${findRoomAd.hasTable}">
										<p>桌子</p>
									</c:if>
									<c:if test="${findRoomAd.hasChair}">
										<p>椅子</p>
									</c:if>
									<c:if test="${findRoomAd.hasParking}">
										<p>停車位</p>
									</c:if>
									<c:if test="${findRoomAd.hasBalcony}">
										<p>陽台</p>
									</c:if>
									<c:if test="${findRoomAd.hasSingleBed}">
										<p>單人床</p>
									</c:if>
									<c:if test="${findRoomAd.hasDoubleBed}">
										<p>雙人床</p>
									</c:if>
									<hr style="height: 10px">
									<c:if
										test="${findRoomAd.peopleNumGender=='1男'||findRoomAd.peopleNumGender=='1女'}">
										<h3><img alt="img" src="${pageContext.request.contextPath}/img/contacts.png" width="20"
														height="20">
									&nbsp; 關於我</h3>
	
										
									</c:if>
									<c:if
										test="${findRoomAd.peopleNumGender!='1男'&&findRoomAd.peopleNumGender!='1女'}">
										<h3><img alt="img" src="${pageContext.request.contextPath}/img/teamwork.png" width="20"
														height="20">
									&nbsp; 關於我們</h3>
										
									</c:if>


									<div class="row">
										<div class="col-md-2 col-sm-2 col-xs-3">
											<c:if test="${! empty age }">
												<p>年齡:</p>
											</c:if>
											<p>抽菸:</p>
											<p>寵物:</p>
											<p>職業:</p>
											<p>性別:</p>
											<c:if test="${findRoomAd.agreeAdCondition}">
												<p>性向:</p>
											</c:if>



										</div>
										<div class="col-md-10 col-sm-10 col-xs-9">
											<c:if test="${! empty age }">
												<p>${age}</p>
											</c:if>
											<c:if test="${findRoomAd.allowSmoke}">
												<p>有</p>
											</c:if>
											<c:if test="${!findRoomAd.allowSmoke}">
												<p>沒有</p>
											</c:if>
											<c:if test="${findRoomAd.allowPet}">
												<p>有</p>
											</c:if>
											<c:if test="${!findRoomAd.allowPet}">
												<p>沒有</p>
											</c:if>
											<p>${job}</p>
											<p>${findRoomAd.peopleNumGender}</p>
											<c:if test="${findRoomAd.agreeAdCondition}">
												<p>${sexOrient}</p>
											</c:if>

										</div>

									</div>
									<hr style="height: 10px">
									<h3><img alt="img" src="${pageContext.request.contextPath}/img/care.png" width="25"
														height="25">
									&nbsp; 新室友偏好</h3>
									

									<div class="row">
										<div class="col-md-2 col-sm-2 col-xs-3">
											<p>性別:</p>
											<c:if test="${! empty roomMateAge }">
												<p>年齡:</p>
											</c:if>
											<p>抽菸:</p>
											<p>寵物:</p>
											<p>職業:</p>

											<p>性向:</p>



										</div>
										<div class="col-md-10 col-sm-10 col-xs-9">
											<p>${flatmateGender}</p>
											<c:if test="${! empty roomMateAge }">
												<p>${roomMateAge}</p>
											</c:if>
											<c:if test="${findRoomAd.wantedRoommatesSmoke}">
												<p>可</p>
											</c:if>
											<c:if test="${!findRoomAd.wantedRoommatesSmoke}">
												<p>不可</p>
											</c:if>
											<c:if test="${findRoomAd.wantedRoommatesPet}">
												<p>可</p>
											</c:if>
											<c:if test="${!findRoomAd.wantedRoommatesPet}">
												<p>不可</p>
											</c:if>
											<p>${Fjob}</p>

											<p>${flatmateSexOrient}</p>


										</div>

									</div>
									<c:if test="${! empty buddyups && ! empty potentialList}">
										<br>
										<h3><img alt="img" src="${pageContext.request.contextPath}/img/love.png" width="20"
														height="20">
									&nbsp; 感興趣的合租物件</h3>
												
										<div class="row">
											<c:forEach var="candidate" items="${potentialList}">
												<div class=" col-sm-2 col-xs-3 align-center">
													<a
														href="<c:url value='/_4u4u/roomRentDetail?adStyle=0&adId=${candidate.get(0)}'/>">
														<img height='80' width='80' src='${candidate.get(1)}'>
														<br>
														<p>${candidate.get(2)}</p>
													</a>
												</div>
											</c:forEach>
										</div>
									</c:if>
									<hr style="height: 10px">
									<div class="row">
										<div class="col-xs-12 text-center">
											<img height='100px' width='100px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${findRoomAd.wantedRoomAdMemId.memId}&type=MEMBER'>
											<p>${findRoomAd.wantedRoomAdMemId.name}</p>
										</div>
										<div class="col-xs-12 text-center ">
											<a class="btn btn-primary sendMessageAjax"
										style="font-size:25px"	href='<c:url value="/sendMessage/${findRoomAd.wantedRoomAdMemId.memId}/1/${findRoomAd.findRoomId}"/>'>發送訊息</a>

										</div>

									</div>
								</c:if>


                                
								<!-- <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6851.201919469417!2d-86.11773906635584!3d33.47324776828677!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x888bdb60cc49c571%3A0x40451ca6baf275c7!2s36008+AL-77%2C+Talladega%2C+AL+35160%2C+USA!5e0!3m2!1sbn!2sbd!4v1460452919256" width="100%" height="450" frameborder="0" style="border:0" allowfullscreen></iframe> -->
								<div class="row">
								<br><br>
									<div class="col-xs-6 text-left">
										<p> 
											 <a id="showInterest" href="#" class="aa-secondary-btn">
								             <i class="fa fa-hand-o-right" aria-hidden="true"></i>
											 感興趣</a>
											
										</p>
									</div>
									<div class="col-xs-6 text-right">
									
										<p>
										 <a id="showInterest" href="#" class="aa-secondary-btn">
										<i class="fa fa-ban" aria-hidden="true"></i>
										檢舉廣告</a>
										</p>
									</div>
								</div>
							</div>


						</div>
					</div>
				</div>
				</div>
			<div class="col-xs-2">
			</div>
		</div>
		</div>
	</section>
	<!-- / Properties  -->





	<jsp:include page="../footer.jsp"></jsp:include>
	<script
		src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"
		type="text/javascript"></script>
	<script
		src="${pageContext.request.contextPath}/js/jssor.slider-21.1.6.mini.js"
		type="text/javascript"></script>
	<!-- jQuery library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
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
	<script type="text/javascript">
		jQuery(document).ready(function($) {

			var jssor_1_SlideshowTransitions = [ {
				$Duration : 1200,
				x : 0.3,
				$During : {
					$Left : [ 0.3, 0.7 ]
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : -0.3,
				$SlideOut : true,
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : -0.3,
				$During : {
					$Left : [ 0.3, 0.7 ]
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				$SlideOut : true,
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : 0.3,
				$During : {
					$Top : [ 0.3, 0.7 ]
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : -0.3,
				$SlideOut : true,
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : -0.3,
				$During : {
					$Top : [ 0.3, 0.7 ]
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : 0.3,
				$SlideOut : true,
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				$Cols : 2,
				$During : {
					$Left : [ 0.3, 0.7 ]
				},
				$ChessMode : {
					$Column : 3
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				$Cols : 2,
				$SlideOut : true,
				$ChessMode : {
					$Column : 3
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : 0.3,
				$Rows : 2,
				$During : {
					$Top : [ 0.3, 0.7 ]
				},
				$ChessMode : {
					$Row : 12
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : 0.3,
				$Rows : 2,
				$SlideOut : true,
				$ChessMode : {
					$Row : 12
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : 0.3,
				$Cols : 2,
				$During : {
					$Top : [ 0.3, 0.7 ]
				},
				$ChessMode : {
					$Column : 12
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				y : -0.3,
				$Cols : 2,
				$SlideOut : true,
				$ChessMode : {
					$Column : 12
				},
				$Easing : {
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				$Rows : 2,
				$During : {
					$Left : [ 0.3, 0.7 ]
				},
				$ChessMode : {
					$Row : 3
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : -0.3,
				$Rows : 2,
				$SlideOut : true,
				$ChessMode : {
					$Row : 3
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				y : 0.3,
				$Cols : 2,
				$Rows : 2,
				$During : {
					$Left : [ 0.3, 0.7 ],
					$Top : [ 0.3, 0.7 ]
				},
				$ChessMode : {
					$Column : 3,
					$Row : 12
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				x : 0.3,
				y : 0.3,
				$Cols : 2,
				$Rows : 2,
				$During : {
					$Left : [ 0.3, 0.7 ],
					$Top : [ 0.3, 0.7 ]
				},
				$SlideOut : true,
				$ChessMode : {
					$Column : 3,
					$Row : 12
				},
				$Easing : {
					$Left : $Jease$.$InCubic,
					$Top : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				$Delay : 20,
				$Clip : 3,
				$Assembly : 260,
				$Easing : {
					$Clip : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				$Delay : 20,
				$Clip : 3,
				$SlideOut : true,
				$Assembly : 260,
				$Easing : {
					$Clip : $Jease$.$OutCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				$Delay : 20,
				$Clip : 12,
				$Assembly : 260,
				$Easing : {
					$Clip : $Jease$.$InCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			}, {
				$Duration : 1200,
				$Delay : 20,
				$Clip : 12,
				$SlideOut : true,
				$Assembly : 260,
				$Easing : {
					$Clip : $Jease$.$OutCubic,
					$Opacity : $Jease$.$Linear
				},
				$Opacity : 2
			} ];

			var jssor_1_options = {
				$AutoPlay : true,
				$SlideshowOptions : {
					$Class : $JssorSlideshowRunner$,
					$Transitions : jssor_1_SlideshowTransitions,
					$TransitionsOrder : 1
				},
				$ArrowNavigatorOptions : {
					$Class : $JssorArrowNavigator$
				},
				$ThumbnailNavigatorOptions : {
					$Class : $JssorThumbnailNavigator$,
					$Cols : 10,
					$SpacingX : 8,
					$SpacingY : 8,
					$Align : 360
				}
			};

			var jssor_1_slider = new $JssorSlider$("jssor_1", jssor_1_options);

			/*responsive code begin*/
			/*you can remove responsive code if you don't want the slider scales while window resizing*/
			function ScaleSlider() {
				var refSize = jssor_1_slider.$Elmt.parentNode.clientWidth;
				if (refSize) {
					refSize = Math.min(refSize, 800);
					jssor_1_slider.$ScaleWidth(refSize);
				} else {
					window.setTimeout(ScaleSlider, 30);
				}
			}
			ScaleSlider();
			$(window).bind("load", ScaleSlider);
			$(window).bind("resize", ScaleSlider);
			$(window).bind("orientationchange", ScaleSlider);
			/*responsive code end*/
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
							
// 							$('#iframe').remove();
							$('#iframeControl')
									.on(
											'click',
											function(e) {
										e.preventDefault();
												$('#iframe').remove();
												$('#iframeControl')
														.after(
																`<iframe id='iframe' width='100%' height='450'   style='border:0' src='https://www.google.com.tw/maps?output=embed&q="${detailAddr}"'></iframe>`)

											});
							$('#readMore')
									.click(
											function() {
												if ($('#readMore span').text() == "縮排") {
													$('#adDetail').css(
															'text-overflow',
															'ellipsis');
													$('#adDetail').css(
															'white-space',
															'nowrap');
													$('#adDetail').css(
															'overflow',
															'hidden');
													$('#readMore')
															.html(
																	'<span  style="color:blue">閱讀更多</span>')

												} else {
													$('#adDetail')
															.css(
																	'text-overflow',
																	'');
													$('#adDetail').css(
															'white-space', '');
													$('#adDetail').css(
															'overflow', '');
													$('#readMore')
															.html(
																	'<span  style="color:blue">縮排</span>')
												}

											});
							
$('#saveAd').on('click',function(e){
								
							    e.preventDefault();
							 
							    	let url = window.location.href;
							    	let queryStr = url.substr(url.indexOf('?'));
							    if($.trim($(this).text())=="儲存廣告"){
							    	let passedURL = "/4u4u/saveAd"+$.trim(queryStr);
// 							    	alert(passedURL);
							        $.ajax({
							            type: "GET",
							            url: passedURL,
							            data: "",
							            dataType: "text",
							            success: function (response) {
							            	
							            
							            	if(response=='需要登入'){
							            		 setTimeout(() => {
							            			 $('#modalTitle').text('您需要登入，才能儲存廣告喔!!');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
							            		
// 							            		alert('您需要登入，才能儲存廣告喔!!');
							            		return;
							            	}
							            	if(response=='取消儲存廣告'){
							            		
							                $('#saveAd').html(`<i class="fa fa-heart"></i> 
													取消儲存廣告`)
							            	}
							            	if(response=='同一人'){
							            		 setTimeout(() => {
							            			 $('#modalTitle').text('您為廣告發布人無法使用此功能');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
							            		
// 							                alert('您為廣告發布人無法使用此功能')
								            }
							            	
							            	if(response=='錯誤'){
							            		
// 							               alert('系統繁忙，請稍後儲存')
							            	}
							            	
							            	
							            }
							        });
							    }else{
							     	let passedURL = "/4u4u/cancelSavedAd"+$.trim(queryStr);
							        $.ajax({
							       
							            type: "GET",
							            url: passedURL,
							            data: "",
							            dataType: "text",
							            success: function (response) {
							                
							            	
							            	if(response=='儲存廣告'){
							            		
							                $('#saveAd').html(`<i class="fa fa-heart"></i>儲存廣告`)
							            	}
							            	if(response=='錯誤'){
							            		
// 									               alert('系統繁忙，請稍後取消')
									        }
									            	
							            }
							        });
							    }

							})

							$('#showInterest').on('click',function(e){
							    e.preventDefault();

						    	let url = window.location.href;
						    	let queryStr = url.substr(url.indexOf('?'));
							    if($.trim($(this).text())=="感興趣"){
							    	let passedURL = "/4u4u/showInterest"+$.trim(queryStr);
							        $.ajax({
							            type: "GET",
							            url: passedURL,
							            data: "",
							            dataType: "text",
							            success: function (response) {
							            	if(response=='需要登入'){
							            		 setTimeout(() => {
							            			 $('#modalTitle').text('您需要登入，才能使用感興趣!!');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
// 							            		alert('您需要登入，才能使用感興趣!!');
							            		return;
							            	}
							            	if(response=='您需要找房廣告'){
							            		setTimeout(() => {
							            			 $('#modalTitle').text('您需要有一則找房廣告，才能使用感興趣功能喔!!');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
// 							            	  alert('您需要有一則找房廣告，才能使用感興趣功能喔!!');
							            		return;
							            	}
							            	if(response=='您需要租房廣告'){
							            		setTimeout(() => {
							            			 $('#modalTitle').text('您需要有一則租房廣告，才能使用感興趣功能喔!!');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
// 								            	  alert('您需要有一則租房廣告，才能使用感興趣功能喔!!');
								            		return;
								            	}
							            	if(response=='取消感興趣'){
							            		
							            	$('#showInterest').html(`<i class="fa fa-hand-o-right" aria-hidden="true"></i>
							            	取消感興趣`)
							            	 $('#saveAd').html(`<i class="fa fa-heart"></i>取消儲存廣告`);	
								           }
							            	if(response=='同一人'){
							            		setTimeout(() => {
							            			 $('#modalTitle').text('您為廣告發布人無法使用此功能');
									 		            $('#myModal').modal('show')
									 		        }, 100);
									 		        
									 		        setTimeout(() => {
									 		            $('#myModal').modal('hide')
									 		        }, 3000);
// 								                alert('您為廣告發布人無法使用此功能')
									          }
							            	if(response=='錯誤'){
							            		
// 								            alert('系統繁忙，請稍後執行感興趣功能')
									            	
									        }
							            }
							        });
							    }else{
							    	let passedURL = "/4u4u/cancelShowInterest"+$.trim(queryStr);
							        $.ajax({
							            type: "GET",
							            url: passedURL,
							            data: "",
							            dataType: "text",
							            success: function (response) {
							               
							            	if(response=='感興趣'){
							            		
								                $('#showInterest').html(`<i class="fa fa-hand-o-right" aria-hidden="true"></i>
										            	感興趣`);
								                 $('#saveAd').html(`<i class="fa fa-heart"></i>儲存廣告`);
								           }

							            	if(response=='錯誤'){
// 							            		alert('系統繁忙，請稍後執行取消感興趣功能')
								           }
							               
							            	
							            }
							        });
							    }

							})
							
							checkButtonState();
							function checkButtonState(){
								let url = window.location.href;
						    	let queryStr = url.substr(url.indexOf('?'));
						    	let passedURL = "/4u4u/checkButtonState"+queryStr;
								 $.ajax({
							            type: "GET",
							            url: passedURL,
							            data: "",
							            dataType: "json",
							            success: function (response) {
							            	if(response==""){
							            		 return;
							            	}else{
							            		$('#saveAd').html(`<i class="fa fa-heart"></i>`+response[0]);
							            		$('#showInterest').html(`<i class="fa fa-hand-o-right" aria-hidden="true"></i>
														 `+response[1]);
							            	}
							                
							            }
							        });
								
							} 
							
								
						});
	</script>
	<script type="text/javascript">
	 $(document).ready(function(){
		 $('.sendMessageAjax').on('click',function(e){
			 
			 e.preventDefault();
			 let url  = window.location.href;
			 let startIndex = url.indexOf('?');
			 let finalUrl = "/4u4u/adCheck"+url.substring(startIndex);
			 let thisUrl  = $(this).attr('href');
			 $.ajax({
		            type: "GET",
		            url: finalUrl,
		            data: {},
		            dataType: "json",
		            success: function (response) {
		            	if(response.result=='false'){
		            		 setTimeout(() => {
		            			 $('#modalTitle').text('您需要登入，才能使用此功能!!');
				 		            $('#myModal').modal('show')
				 		        }, 100);
				 		        
				 		        setTimeout(() => {
				 		            $('#myModal').modal('hide')
				 		        }, 3000);
		            		return;
		            		
		            	}else if(response.result=='ok'){
		            		window.location.href = thisUrl;
// 		            		window.location.replace(thisUrl);
		            		return;
		            	}else if(response.result=='adOwner'){
		            		 setTimeout(() => {
		            			 $('#modalTitle').text('您為廣告發佈人，無法使用此功能!!');
				 		            $('#myModal').modal('show')
				 		        }, 100);
				 		        
				 		        setTimeout(() => {
				 		            $('#myModal').modal('hide')
				 		        }, 3000);
		            		return;
		            	}else if(response.result=='earlyBird'){
		            		 setTimeout(() => {
		            			 $('#modalTitle').html('您無法使用此功能<br>升級為VIP才享有早鳥資格!!');
				 		            $('#myModal').modal('show')
				 		        }, 100);
				 		        
				 		        setTimeout(() => {
				 		            $('#myModal').modal('hide')
				 		        }, 3000);
		            		return;
		            	}
		           
		            
		            
		            
		            }
				
				});
			 
			 
			 
		 })
	
	 
	 
	 
	 })
	 
	 
	 
	 
	 
	
	
	</script>
</body>
</html>