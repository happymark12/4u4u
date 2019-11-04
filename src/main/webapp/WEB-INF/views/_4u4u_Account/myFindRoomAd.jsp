<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="_4u4u.model.WantedRoomBean"%>
<%@page import="_4u4u.model.RoomRentBean"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 我的房間出租廣告</title>

<!-- 4u4uicon -->
<link rel="shortcut icon"
	href="${pageContext.servletContext.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">


<!-- Font awesome -->
<link href="${pageContext.request.contextPath}/css/fontawesome-free-5.11.2-web/css/all.css" rel="stylesheet">

<link
	href="${pageContext.servletContext.contextPath}/css/font-awesome.css"
	rel="stylesheet">
<!-- Bootstrap -->
<link href="${pageContext.servletContext.contextPath}/css/bootstrap.css"
	rel="stylesheet">
<!-- slick slider -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/slick.css">
<!-- price picker slider -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/css/nouislider.css">
<!-- Fancybox slider -->
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/jquery.fancybox.css"
	type="text/css" media="screen" />
<!-- Theme color -->
<link id="switcher"
	href="${pageContext.servletContext.contextPath}/css/theme-color/default-theme.css"
	rel="stylesheet">

<!-- Main style sheet -->
<link href="${pageContext.servletContext.contextPath}/css/style.css"
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
		<div
			style="font: bold 24px verdana; text-align: center; position: absolute; left: 46.5%; top: 40%;">
			${MsgOK.UpdateOK}${AdDeleteMsg}</div>
	</div>
	<%
		session.removeAttribute("AdDeleteMsg");
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
											class="aa-register">註冊</a>
										<a href="<c:url value='/login' />" class="aa-login">登入</a>

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
	<!-- Start Proerty header  -->

	<section id="aa-property-header" class="my-seek">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-property-header-inner">
						<h2>我的廣告</h2>
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
		<div class="container my-ad">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-properties-content">

						<!-- start properties content head -->
						<div class="aa-properties-content-head text-center">
							<div class="aa-properties-content-head-left">
								<form action="" class="aa-sort-form">
									<!--
					<label>
					<input type="checkbox" value="" name="all" style="zoom:130%;vertical-align:middle; margin-bottom:7px; "><span>全選</span></label>
-->

								</form>

							</div>


							<label class="room-ad"><a
								href="/4u4u/_4u4u/MyAdServlet?memId=${LoginOK.memId}&adStyle=0">房間出租廣告&nbsp;
							</a> | <a
								href="/4u4u/_4u4u/MyAdServlet?memId=${LoginOK.memId}&adStyle=1"
								class="active">&nbsp;徵求房間廣告</a></label>
							<!--
				<div class="aa-properties-content-head-right">
				  <label for="">排序</label>
				  <select name="">
					<option value="0" selected="Default">請選擇</option>  
                    <option value="1">金額由低到高</option>
                    <option value="2">金額由高到低</option>
                    <option value="3">最新廣告</option>
                    <option value="4">最近更新時間</option>
                  </select>

              </div> -->

						</div>
						<!-- Start properties content body -->

						<div class="aa-properties-content-body" style="margin-left: 0px">

							<ul class="aa-properties-nav  my-ad">
								<c:forEach var="wantedRoomAd" items="${wantedRoomAd}"
									varStatus="vs">
									<li class="list">

										<article class="aa-properties-item">
											<c:if test="${wantedRoomAd.adState == true}">
												<a class="aa-properties-item-img"
													href="<c:url value='/_4u4u/findRoomDetail?adStyle=1&adId=${wantedRoomAd.findRoomId}'/>">
													<c:if test="${! empty wantedRoomAd.images}">
														<img alt="img"
															src="/disksource/wantedRoomAd/${wantedRoomAd.images}">
													</c:if> <c:if test="${ empty wantedRoomAd.images}">
														<img alt="img"
															src="${pageContext.servletContext.contextPath}/img/NoImage.png">
													</c:if>
												</a>


											</c:if>
											<c:if test="${wantedRoomAd.adState == false}">
												<a class="aa-properties-item-img disable-img"
													href="<c:url value='/_4u4u/findRoomDetail?adStyle=1&adId=${wantedRoomAd.findRoomId}'/>">
													<c:if test="${! empty wantedRoomAd.images}">
														<img alt="img"
															src="/disksource/wantedRoomAd/${wantedRoomAd.images}">
													</c:if> <c:if test="${ empty wantedRoomAd.images}">
														<img alt="img"
															src="${pageContext.servletContext.contextPath}/img/NoImage.png">
													</c:if>
												</a>
											</c:if>

											<c:if test="${wantedRoomAd.adState == true}">
												<div class="aa-tag for-rent-true">
													<i class="fa fa-smile-o fa-lg"></i> <span>使用中</span>
												</div>
												<div class="aa-properties-item-content">
													<div class="aa-properties-info">
														<span>${LoginOK.name}</span> <span>${job[vs.index]}</span>
														<span>${wantedRoomAd.age}</span> <span>${wantedRoomAd.peopleNumGender}</span>
													</div>
													<div class="aa-properties-about text-center">
														<h3>
															<a
																href="<c:url value='/_4u4u/findRoomDetail?adStyle=1&adId=${wantedRoomAd.findRoomId}'/>">${wantedRoomAd.adTitle}</a>
														</h3>
														<p>${wantedRoom[vs.index]}</p>
														<span class="aa-price"> 總預算 :
															${wantedRoomAd.budget} 元/月</span>
													</div>
													<div class="aa-properties-detial">
														<c:if test="${adState[vs.index].equals('早鳥')}">
															<span class="aa-price demo2" data-placement="bottom"
																title="一般會員需等廣告發佈7天後才能直接聯繫,如需立即聯繫必先升級為VIP會員。"> <i
																class="fas fa-crow fa-lg" style="color: orange"></i>早鳥體驗

															</span>
														</c:if>
														<c:if test="${! adState[vs.index].equals('早鳥')}">
															<span class="aa-price"> <i
																class="fa fa-comments fa-lg" aria-hidden="true"
																style="color: green"></i>可直接聯繫
															</span>
														</c:if>
														
														<a
															class="aa-secondary-btn"
															href="<c:url value='/_4u4u/findRoomDetail?adStyle=1&adId=${wantedRoomAd.findRoomId}'/>">了解更多</a>
													</div>
													<div class="aa-properties-detial">
														<span class="aa-price"> <a class="deleteButton"
															href="<c:url value='/_4u4u/deleteFindRoomAd?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
															class="deldte" style="margin-left: 20px"><i
																class="fa fa-trash fa-lg" style="color: red"></i>刪除</a> <a
															href="<c:url value='/_4u4u/ProcessUpdateWantedRoomAd?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
															class="edit"><i class="fa fa-pencil-square-o fa-lg"
																style="color: #9F68E4"></i>編輯</a>
														</span>
														<c:if test="${wantedRoomAd.adState == true}">
															<a class="stop stop-ad"
																href="<c:url value='/_4u4u/ChangeRoomRentAdState?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
																style="border: 1px solid #FFA500"><i
																class="fa fa-ban" style="color: orange"></i>停用廣告</a>
														</c:if>
														<c:if test="${wantedRoomAd.adState == false}">
															<a class="stop stop-ad"
																href="<c:url value='/_4u4u/ChangeRoomRentAdState?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
																style="border: 1px solid #2B922B"><i
																class="fa fa-power-off" style="color: green"></i>啟用廣告</a>
														</c:if>
													</div>
												</div>
											</c:if>
											<c:if test="${wantedRoomAd.adState == false}">
												<div class="aa-tag for-rent-false">
													<i class="fa fa-frown-o fa-lg"></i> <span>已停用</span>
												</div>
												<div class="aa-properties-item-content disable">
													<div class="aa-properties-info">
														<span>${LoginOK.name}</span> <span>${job[vs.index]}</span>
														<span>${wantedRoomAd.age}</span> <span>${wantedRoomAd.peopleNumGender}</span>
													</div>
													<div class="aa-properties-about text-center">
														<h3>
															<a
																href="<c:url value='/_4u4u/findRoomDetail?adStyle=1&adId=${wantedRoomAd.findRoomId}'/>">${wantedRoomAd.adTitle}</a>
														</h3>
														<p>${wantedRoom[vs.index]}</p>
														<span class="aa-price"> 總預算 :
															${wantedRoomAd.budget} 元/月</span>
													</div>
													<div class="aa-properties-detial">

														<c:if test="${adState[vs.index].equals('早鳥')}">
															<span class="aa-price demo2" data-placement="bottom"
																title="一般會員需等廣告發佈7天後才能直接聯繫,如需立即聯繫必先升級為VIP會員。"> <i
																class="fas fa-crow fa-lg" style="color: orange"></i>早鳥體驗

															</span>
														</c:if>
														<c:if test="${! adState[vs.index].equals('早鳥')}">
															<span class="aa-price"> <i
																class="fa fa-comments fa-lg" aria-hidden="true"
																style="color: green"></i>可直接聯繫
															</span>
														</c:if>
														<a class="aa-secondary-btn" href="#">了解更多</a>
													</div>
													<div class="aa-properties-detial">
														<span class="aa-price"> <a class="deleteButton"
															href="<c:url value='/_4u4u/deleteFindRoomAd?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
															class="deldte" style="margin-left: 20px"><i
																class="fa fa-trash fa-lg" style="color: red"></i>刪除</a> <a
															href="<c:url value='/_4u4u/ProcessUpdateWantedRoomAd?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
															class="edit"><i class="fa fa-pencil-square-o fa-lg"
																style="color: #9F68E4"></i>編輯</a>
														</span>
														<c:if test="${wantedRoomAd.adState == true}">
															<a class="stop stop-ad"
																href="<c:url value='/_4u4u/ChangeRoomRentAdState?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
																style="border: 1px solid #FFA500"><i
																class="fa fa-ban" style="color: orange"></i>停用廣告</a>
														</c:if>
														<c:if test="${wantedRoomAd.adState == false}">
															<a class="stop stop-ad"
																href="<c:url value='/_4u4u/ChangeRoomRentAdState?memId=${LoginOK.memId}&adStyle=${adStyle}&adId=${wantedRoomAd.findRoomId}'/>"
																style="border: 1px solid #2B922B"><i
																class="fa fa-power-off" style="color: green"></i>啟用廣告</a>
														</c:if>
													</div>
												</div>
											</c:if>



										</article>

									</li>
								</c:forEach>
							</ul>

						</div>

						<!-- Start properties content bottom -->
						<div class="aa-properties-content-bottom">
							<nav>
								<ul class="pagination">
									<c:if test="${pageNo > 1}">
										<li><a
											href="<c:url value='MyAdServlet?pageNo=${pageNo-1}&memId=${LoginOK.memId}&adStyle=1'/>"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a>
										</li>
									</c:if>
									<c:if test="${pageNo == 1}">
										<li><a aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
									</c:if>

									<%--中間頁--%>
									<%--顯示6頁中間頁[begin=起始頁,end=最大頁]--%>
									<%--總頁數沒有6頁--%>
									<c:choose>
										<c:when test="${totalPages <= 6}">
											<c:set var="begin" value="1" />
											<c:set var="end" value="${totalPages}" />
										</c:when>
										<%--頁數超過了6頁--%>
										<c:otherwise>
											<c:set var="begin" value="${pageNo - 1}" />
											<c:set var="end" value="${pageNo + 3}" />
											<%--如果begin減1後爲0,設置起始頁爲1,最大頁爲6--%>
											<c:if test="${begin -1 <= 0}">
												<c:set var="begin" value="1" />
												<c:set var="end" value="6" />
											</c:if>
											<%--如果end超過最大頁,設置起始頁=最大頁-5--%>
											<c:if test="${end > totalPages}">
												<c:set var="begin" value="${totalPages - 5}" />
												<c:set var="end" value="${totalPages}" />
											</c:if>
										</c:otherwise>
									</c:choose>

									<c:forEach var="i" begin="${begin}" end="${end}">
										<%--當前頁,選中--%>
										<c:choose>
											<c:when test="${i == pageNo}">
												<li class="active"><a>${i}</a></li>
											</c:when>
											<%--不是當前頁--%>
											<c:otherwise>
												<li><a
													href="<c:url value='MyAdServlet?pageNo=${i}&memId=${LoginOK.memId}&adStyle=1'/>">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>

									<!--總頁數 -->
									<li><a>總頁數:${totalPages}</a></li>

									<c:if test="${pageNo != totalPages}">
										<li><a
											href="<c:url value='MyAdServlet?pageNo=${pageNo+1}&memId=${LoginOK.memId}&adStyle=1'/>"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
									</c:if>
									<c:if test="${pageNo == totalPages}">
										<li><a aria-label="Next"> <span aria-hidden="true">&raquo;</span></a></li>
									</c:if>


								</ul>
								<p>
									<a href="<c:url value='/account'/>">帳戶管理</a>
								</p>
							</nav>
						</div>
						<c:if test="${wantedRoomAd.size()==0}">
							<div style="height: 540px;"></div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</section>

	<input name="findRoomId" type="hidden" id="findRoomId"
		value="${param.findRoomId}>" />

	<jsp:include page="../footer.jsp"></jsp:include>

	<!-- jQuery library -->
	<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
	<script
		src="${pageContext.servletContext.contextPath}/js/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${pageContext.servletContext.contextPath}/js/bootstrap.js"></script>
	<!-- slick slider -->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/js/slick.js"></script>
	<!-- Price picker slider -->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/js/nouislider.js"></script>
	<!-- mixit slider -->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/js/jquery.mixitup.js"></script>
	<!-- Add fancyBox -->
	<script type="text/javascript"
		src="${pageContext.servletContext.contextPath}/js/jquery.fancybox.pack.js"></script>
	<!-- Custom js -->
	<script src="${pageContext.servletContext.contextPath}/js/custom.js"></script>
	<!-- MyAd js -->
	<script src="${pageContext.servletContext.contextPath}/js/myAd.js"></script>
	<script type="text/javascript">
	$(function(){
		$(".demo2").tooltip();
	});
	</script>


</body>
</html>