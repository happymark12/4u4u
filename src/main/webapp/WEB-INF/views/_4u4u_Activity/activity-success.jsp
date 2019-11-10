<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 活動報名成功</title>

<!-- Pre Loader -->
<!-- 4u4uicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">
<!-- Font awesome字型 -->
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


</head>
<body class="aa-price-range">
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
						<li><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown "><a class="dropdown-toggle">發佈廣告
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="<c:url value='/PostRoomRentAd' />">出租廣告</a></li>
								<li><a href="<c:url value='/PostWantedRoomAd' />">找房廣告</a></li>
							</ul></li>
						<!--活動-->
						<li class="active"><a href="<c:url value='/activity' />">活動</a></li>

						<!--關於我們-->
						<li class="dropdown"><a class="dropdown-toggle">關於我們
								<span class="caret"></span>
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
  <section id="aa-property-header" class="activity-success">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-property-header-inner">
              <h2>報名成功</h2>
           
          </div>
        </div>
      </div>
    </div>
  </section> 
  <!-- End Proerty header  -->
<!-- Start Blog  -->
  <section id="aa-blog" style="clear: both">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-blog-area">
            <div class="row">
              <div class="col-md-12">
                <!-- <div class="aa-blog-content"> -->
                  <div class="row">
                    <div class="col-md-12">
<!--                         <article class="aa-blog-single">  -->
                            <br><br><br><br><br>
                          <figure class="aa-blog-img">
                    
                            <img alt="img" src="${pageContext.request.contextPath}/img/success.png" class="aa-blog-img-ss" > 
                          </figure>
                          <div class="aa-blog-single-content">
                          
                          
                            
                            <p style="font-size: 25px; text-align: center;">
                              您已報名成功<br><br>
                              <a href="<c:url value='/' />" style="font-size: 15px; text-align: center;text-decoration:underline;">
                                  &nbsp; 回首頁 &nbsp; | </a>
                                 <a href="<c:url value='/activity' />"style="font-size: 15px; text-align: center;text-decoration:underline;">&nbsp; 
                                   回活動 &nbsp;</a>
                             
                            </p>

                             <br><br><br><br><br><br> <br><br><br>

     
                          </div> 
<!--                           </article> -->
                      </div>
                      
                    </div>    
              <!-- </div> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  	</div>
  </section>
  <!-- / Blog  -->

	<jsp:include page="/WEB-INF/views/footer.jsp"></jsp:include>

	<!-- jQuery library -->
	<!--   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
	
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