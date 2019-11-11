<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
 <title>4U4U | 搜尋</title>

<!-- Favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">


<!-- Font awesome -->
<link href="${pageContext.request.contextPath}/css/fontawesome-free-5.11.2-web/css/all.css" rel="stylesheet">

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
										<span class="fa fa-phone"></span>  (02)2771-2171#1720
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
            <a class="navbar-brand aa-logo-img" href="<c:url value='/' />"><img src="${pageContext.request.contextPath}/img/icon/4U4U_final.png" alt="logo"></a>  
                       <!-- Text based logo -->                             
           <a class="navbar-brand aa-logo" href="<c:url value='/' />">4U4U</a> 
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul id="top-menu" class="nav navbar-nav navbar-right aa-main-nav">
						<!--首頁-->
						<li><a href="<c:url value='/' />">首頁</a></li>
						<!--瀏覽-->
						<li class="active"><a
							href="<c:url value='/properties' />">搜尋</a></li>
						<!--帳戶管理-->
						<li><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown "><a class="dropdown-toggle">發佈廣告 <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href="<c:url value='/PostRoomRentAd' />">出租廣告</a></li>
								<li><a
									href="<c:url value='/PostWantedRoomAd' />">找房廣告</a></li>
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
								<li><a href="<c:url value='/contact' />">聯絡我們</a></li>   <li><a href="<c:url value='/qa' />">常見問與答</a></li>        
							</ul></li>
					</ul>
				</div>
			</div>
		</nav>
	</section>
	<!-- End menu section -->

	<!-- Start Property header  -->

  <section id="aa-property-header" class="search">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-property-header-inner">
            <h2>搜尋</h2>
          </div>
        </div>
      </div>
    </div>
  </section> 
  <!-- End Proerty header  -->

	<!-- Start Properties  -->
	<section id="aa-properties">
		<div class="container-fluid">
			<div class="row" style="padding:30px;">
				<div class="col-md-9">
					<div class="aa-properties-content">
						<!-- start properties content head -->
						<div class="aa-properties-content-head">
							<div class="aa-properties-content-head-left">
								<form action="" class="aa-sort-form">
									<label for="">排序</label> <select name="sortOption"
										id="sortOption">
										<option value="0">預設排序</option>
										<option value="1">金額由低到高</option>
										<option value="2">金額由高到低</option>
										<option value="3">刊登時間由新到舊</option>
										<option value="4">修改時間由新到舊</option>
									</select>
								</form>
								<!--
                <form action="" class="aa-show-form">
                  <label for="">Show</label>
                  <select name="">
                    <option value="1" selected="12">6</option>
                    <option value="2">12</option>
                    <option value="3">24</option>
                  </select>
                </form>
-->
							</div>
							<div class="aa-properties-content-head-right">
								<a id="aa-grid-properties" href="#"><span class="fa fa-th"></span></a>
								<a id="aa-list-properties" href="#"><span class="fa fa-list"></span></a>
							</div>
						</div>
						<!-- Start properties content body -->
						<div class="aa-properties-content-body">
							<ul class="aa-properties-nav" id="targetAds" style="width:100%;">

							</ul>
						</div>
						<!-- Start properties content bottom -->
						<div class="aa-properties-content-bottom">

							<nav>
								<ul style="width: 360px;" class="pagination" id="pagination">

								</ul>

							</nav>

						</div>
					</div>
				</div>
				<!-- Start properties sidebar -->
				<div class="col-md-3">
					<aside class="aa-properties-sidebar">
						<!-- Start Single properties sidebar -->
						<!--
            <div id="container">
    <div id="filterDiv">
-->



						<form action="" method="POST" enctype="multipart/form-data"
							id="searchForm" name="searchForm">

							<h3>篩選條件</h3>
							<hr>
							<span>搜尋類型:</span> <input type="reset" value="重置選項" id="reset"
								style="margin-left: 30px"> <br> <input type="radio"
								value="0" name="searchType" checked>找房<br> <input
								type="radio" value="1" name="searchType">找租客 <br> <input
								type="radio" value="2" name="searchType">找室友(願意合租) <br>
							<br> <span>地點:</span><br>
							<div id="addressPart" class="form-row city-selector-set">

								<!-- <label for="inputCity">縣市</label>  -->
								<select id="county" class=" county" name="county"></select>
								<!-- <label for="inputDistrict">鄉鎮區</label>  -->
								<select id="district" class=" district" name="district"></select>

							</div>
							<br>
							<div id="rentType">
								<span>包含:</span><br> <input type="checkbox" value="3"
									name="rentType3" checked>出租房間(有公共空間) <br> <input
									type="checkbox" name="rentType0" value="0" checked>整層出租<br>
								<input type="checkbox" name="rentType1" value="1" checked>獨立套房<br>
								<input type="checkbox" name="rentType2" value="2" checked>分租套房
							</div>
							<br id="rentTypebr"> <span>租金範圍:</span><br> <input
								name="rentPriceMin" type="number" min="0" max="999999"
								style="width: 80px;" id="minRent" placeholder="min"> to
							<input type="number" name="rentPriceMax" min="0" max="999999"
								style="width: 80px;" id="maxRent" placeholder="max"> <br>
							<br> <span>可遷入日期:</span><br>
							<!-- 							<input type="radio" name="availableDate" value="任何時間" checked>任何時間<br> -->
							<!-- 							<input type="radio" name="availableDate" value="指定日期">指定日期<br> -->
							<input type="date" name="availableDate"> <br>(將會篩選所選時間的前後21天)<br>
							<br>
							<!-- 							<span>最短租期 : </span> <select name="minimumStay"> -->
							<!-- 								<option value="none">請選擇</option> -->
							<!-- 								<option value="1年">1年</option> -->
							<!-- 								<option value="小於3個月">小於3個月</option> -->
							<!-- 								<option value="3個月">3個月</option> -->
							<!-- 								<option value="半年">半年</option> -->
							<!-- 								<option value="2年">2年</option> -->
							<!-- 								<option value="其他">其他</option> -->
							<!-- 							</select> -->
							<span>房間數量 : </span> <select name="roomNum" id="roomNum">
								<option value="1">1房</option>
								<option value="2">2房以上</option>
								<option value="3">3房以上</option>

							</select>
<!-- 							<br> -->
							<!-- <br id="roomforRentbr">
	<div id="roomforRentDiv"> -->
							<!-- 								<span>房間設施: </span><br> <input type="checkbox" -->
							<!-- 									name="hasDuplex" value="true">樓中樓<input type="checkbox" -->
							<!-- 									name="hasWashingMachine" value="true">洗衣機<input -->
							<!-- 									type="checkbox" name="hasAirConditioning" value="true">冷氣<br> -->
							<!-- 								<input type="checkbox" name="hasSingleBed" value="true">單人床<input -->
							<!-- 									type="checkbox" name="hasDoubleBed" value="true">雙人床<input -->
							<!-- 									type="checkbox" name="hasInternet" value="true">網路<br> -->
							<!-- 								<input type="checkbox" name="hasTV" value="true">電視<input -->
							<!-- 									type="checkbox" name="hasBalcony" value="true">陽台<input -->
							<!-- 									type="checkbox" name="hasGas" value="true">天然瓦斯<br> -->
							<!-- 								<input type="checkbox" name="hasRefrigerator" value="true">冰箱<input -->
							<!-- 									type="checkbox" name="hasChair" value="true">椅子<input -->
							<!-- 									type="checkbox" name="hasWaterHeater" value="true">電熱水器<br> -->
							<!-- 								<input type="checkbox" name="hasWardrobe" value="true">衣櫃<input -->
							<!-- 									type="checkbox" name="hasTable" value="true">桌子<input -->
							<!-- 									type="checkbox" name="hasFourthTV" value="true">第四台 <br> -->
							<!-- 								<span>住宅種類 : </span> <select -->
							<!-- 									name="propertyType"> -->
							<!-- 									<option value="default">請選擇...</option> -->
							<!-- 									<option value="公寓">公寓</option> -->
							<!-- 									<option value="透天厝">透天厝</option> -->
							<!-- 									<option value="電梯大樓">電梯大樓</option> -->
							<!-- 									<option value="華廈">華廈</option> -->
							<!-- 								</select>  -->
							<!-- 								<br><span>房間適合 : </span><br> <input -->
							<!-- 									type="radio" name="roomFor" value="男性">男性<br> <input -->
							<!-- 									type="radio" name="roomFor" value="女性">女性<br> <input -->
							<!-- 									type="radio" name="roomFor" value="情侶">情侶 <br> <br> -->
							<!-- 								<span>開伙 : </span> <input type="radio" name="allowCook" -->
							<!-- 									value="true">可以 <input type="radio" name="allowCook" -->
							<!-- 									value="false">不可以<br> <br>  -->
							<!-- <span>房間要有廁所
			: </span> <input type="checkbox" name="hasToilet" value="true">是 -->

							<!-- 								 <span>停車位 : </span> <input type="radio" -->
							<!-- 									name="hasParking" value="true">有 <input type="radio" -->
							<!-- 									name="hasParking" value="false">沒有 -->
							<!-- 									<span>電梯: </span> <input type="radio" name="hasElevator" value="true">有 -->
							<!-- 								<input type="radio" name="hasElevator" value="false">沒有<br> -->
							<!-- 								<span>廣告者身分 : </span> <input type="checkbox" -->
							<!-- 									name="adPostby" value="notagent">非房仲 -->
							<!-- </div> -->
							<!-- 							<span>年齡:</span> <input type="number" min="0" -->
							<!-- 								name="ageMin" max="100" style="width: 80px;" id="minRent1" -->
							<!-- 								placeholder="請選擇..."> to <input type="number" -->
							<!-- 								name="ageMax" min="0" max="100" style="width: 80px;" -->
							<!-- 								id="maxRent1" placeholder="請選擇..."> -->
<!-- 							<br> <span>職業:</span> <input type="radio" name="job" -->
<!-- 								value="0">學生 <input type="radio" name="job" value="1">上班族 -->
							<br> <br> <span>性別 : </span> <input type="radio"
								name="gender" value="0">男性 <input type="radio"
								name="gender" value="1">女性 <br> <br> <span>抽菸
								: </span> <input type="radio" name="smoke" value="true">允許 <input
								type="radio" name="smoke" value="false">不允許<br> <br>
							<span>寵物 : </span> <input type="radio" name="pet" value="true">允許
							<input type="radio" name="pet" value="false">不允許<br>
							<br>
							<!-- 								<span>LGBT(同性,雙性,跨性) -->
							<!-- 								: </span> <br> <input type="checkbox" name="LGBT_Option" -->
							<!-- 								value="true"> 選擇LGBT <br> <br> -->
							<!-- <div id="coupleOption" style="display: none">
		<span>情侶 : </span> <input type="radio" name="allowCouple"
			value="不介意">不介意 <input type="radio" name="allowCouple"
			value="不要情侶">不要情侶
	</div> -->


							<div class="aa-single-advance-search">
								<!-- 								<input type="submit" id="filterZone" value="過濾" -->
								<!-- 									style="font-size: 20px; width: 200px; height: 50px; position: fixed; bottom: 20%; background-color: rgba(255, 255, 255, 0.6); display: none;"> -->
								<input type="submit" id="searchButton" value="搜尋"
									name="search" class="aa-search-btn">
							</div>
						</form>
					</aside>
				</div>
			</div>
		</div>
	</section>

	<!-- / Properties  -->

	<jsp:include page="../footer.jsp"></jsp:include>


	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.0.2/dist/tw-city-selector.min.js"></script>
	<script>
		new TwCitySelector({
			el : '.city-selector-set',
			elCounty : '.county', // 在 el 裡查找 element
			elDistrict : '.district', // 在 el 裡查找 element
			elZipcode : '.zipcode' // 在 el 裡查找 element
		});
	</script>


	<!-- jQuery library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
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
		src="${pageContext.request.contextPath}/js/properties.js"></script>
</body>
</html>