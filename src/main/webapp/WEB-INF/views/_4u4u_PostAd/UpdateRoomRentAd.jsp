<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>修改出租廣告</title>
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
<style>
/* body { */
/* 	text-align: center; */
/* } */
form {
	text-align: center;
	width: 70%;
	margin: 0px auto;
}

#wholeTable {
	width: 100%;
	text-align: left;
	margin: 0px auto;
}

#theFile {
	color: transparent;
	width: 80px;
}

#dropzone {
	float: left;
	width: 430px;
	/*  	padding:  0px 10px 10px 10px; */
	margin: 0px 0px 10px 0px;
}

#choosePicZone {
	display: block;
	width: 400px;
}

textarea {
	width: 400px;
	height: 200px;
	/*讓左邊的字在左上方*/
	vertical-align: top;
}

.first {
	position: relative;
	width: 384px;
	height: 264px;
	margin: 5px 0px;
	float: left;
	text-align: center;
}

.odd {
	position: relative;
	float: left;
	width: 182px;
	height: 127px;
	margin: 5px 0px;
	text-align: center;
}

.even {
	position: relative;
	float: right;
	width: 182px;
	height: 127px;
	margin: 5px 0px;
	text-align: center;
}

button {
	float: left;
	position: absolute;
	top: 0px;
}
</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/updateRoomRentAd.js"></script>

<script
	src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.0.2/dist/tw-city-selector.min.js"></script>

</head>

<body>

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
						<li class="dropdown"><a class="dropdown-toggle">發佈廣告 <span
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

	<!-- Start Proerty header  -->
	<section id="aa-property-header" class="search">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-property-header-inner">
						<h2>發佈出租廣告</h2>
						<!-- <ol class="breadcrumb">
            <li><a href="#">HOME</a></li>            
            <li class="active">Blog Details</li>
          </ol> -->
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Proerty header  -->

	<c:if test="${!empty ErrorMsg}">
		<div style="text-align: center; color: red">
			<c:forEach var="mapObject" items="${ErrorMsg}">
    	 ${mapObject.value} <br>

			</c:forEach>
		</div>
	</c:if>
	<p>&nbsp;</p>
	<form
		action="/4u4u/_4u4u/UpdateRoomRentAdServlet?adStyle=${adStyle}&adId=${RoomRentAd.adId}"
		method="post" enctype="multipart/form-data" name="rentForm">
		<table id="wholeTable">
			<tr>
				<td>
					<fieldset>
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">關於廣告
						</legend>
						<br> 出租方式 : <input type="text" id="rentType" name="rentType"
							value="${adRentType}" readonly="readonly">
						<!--                           <select id="rentType" name="rentType"> -->
						<!--                               <option value="room-for-rent" label="出租房間(有公共空間)"></option> -->
						<!--                               <option value="whole-property">整層出租</option> -->
						<!--                               <option value="independent-studio">獨立套房</option> -->
						<!--                               <option value="dependent-studio">分租套房</option> -->
						<!--                           </select>  -->
						<br> <br id="houseCountBr">

						<div id="houseCountDiv">
							房間數量 : <select id="houseCount" name="houseCount" required
								disabled="disabled">
								<option value="room-${adRoomNum}">${adRoomNum}間房</option>
							</select>
							<!--                                   <option value="">請選擇...</option> -->
							<!--                               <select id="houseCount" name="houseCount" required> -->
							<!--                                   <option value="">請選擇...</option> -->
							<%--                                   <option value="room-${adRoomNum}">${adRoomNum}間房</option> --%>
							<!--                                   <option value="room-2">2間房</option> -->
							<!--                                   <option value="room-3">3間房</option> -->
							<!--                                   <option value="room-4">4間房</option> -->
							<!--                                   <option value="room-5">5間房</option> -->
							<!--                                   <option value="room-6">6間房</option> -->
							<!--                                   <option value="room-7">7間房</option> -->
							<!--                                   <option value="room-8">8間房</option> -->
							<!--                                   <option value="room-9">9間房</option> -->
							<!--                                   <option value="room-10">10間房</option> -->
							<!--                                   <option value="room-11">11間房</option> -->
							<!--                                   <option value="room-12">12間房</option> -->
							<!--                               </select> -->
						</div>
						<c:if test="${adRentType == '整層住家'}">
							<br id="whole-property-br">
							<div id="whole-property-div">							
				 請輸入數量 : 		<input type="number" name="wholePropertyToilet"  value="${adToiletNum}" style="width: 50px;" min="1" max="5"> 衛浴
                  				<input type="number" name="wholePropertyLivingRoom" value="${adLivingRoomNum}" style="width: 50px;" min="1" max="5"> 廳
                  				<input type="number" name="wholePropertyBalcony" value="${adBalconyNum}" style="width: 50px;"  min="1" max="5"> 陽台							
							</div>
						</c:if>
						<br> 廣告者身分 : <input type="text" name="adOwner" id="adOwner"
							value="${adOwner}" readonly="readonly">
						<!--                           <select id="adOwner" name="adOwner" required="required"> -->
						<!--                               <option value="">請選擇...</option> -->
						<!--                               <option value="live-out-landlord">房東/無居住在裡面</option> -->
						<!--                               <option value="live-in-landlord" id="live-in-landlord">房東/有居住在裡面</option> -->
						<!--                               <option value="currentTenant" id="currentTenant">目前的租客</option> -->
						<!--                               <option value="agent">房仲</option> -->
						<!--                               <option value="former-flatmate">前租客(即將搬走需要有人取代他)</option> -->
						<!--                           </select>  -->
						<br> <br>
						<div id="peopleCountDiv">
							目前居住人數 : <select name="currentNum" id="peopleCount">
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
							</select> 人在這個房子居住
						</div>
						<br id="peopleCountBr"> 住宅種類 : <input type="text"
							name="propertyType" id="propertyType" value="${adHouseType}"
							readonly="readonly"><br> <br>
						<!--                           	住宅種類 : <select name="propertyType"> -->
						<!--                               <option value="default">請選擇...</option> -->
						<!--                               <option value="0">公寓</option> -->
						<!--                               <option value="1">透天厝</option> -->
						<!--                               <option value="2">電梯大樓</option> -->
						<!--                               <option value="3">華廈</option> -->
						<!--                           </select> <br> <br> -->
						<div id="addressPart" class="form-row city-selector-set">
							出租地址 : <input type="text" id="county" name="county" required
								value="${area}" readonly="readonly"> <input type="text"
								id="address" name="address" required value="${detailAddr}"
								readonly="readonly">
						</div>
						<br> 汽車車位數量 : 
						<c:if test="${! empty adParkingCount}">
							<select name="parkingNum">
								<c:if test="${adParkingCount == '0'}">
									<option value="0" selected>0</option>
								</c:if>
								<c:if test="${adParkingCount != '0'}">
									<option value="0">0</option>
								</c:if>
								<c:if test="${adParkingCount == '1'}">
									<option value="1" selected>1</option>
								</c:if>
								<c:if test="${adParkingCount != '1'}">
									<option value="1">1</option>
								</c:if>
								<c:if test="${adParkingCount == '2'}">
									<option value="2" selected>2</option>
								</c:if>
								<c:if test="${adParkingCount != '2'}">
									<option value="2">2</option>
								</c:if>
								<c:if test="${adParkingCount == '3'}">
									<option value="3" selected>3</option>
								</c:if>
								<c:if test="${adParkingCount != '3'}">
									<option value="3">3</option>
								</c:if>	
							</select> <br><br id="parkingBr"> 
						</c:if>
						<c:if test="${empty adParkingCount}">
							<select name="parkingNum">
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
							</select> <br> <br id="parkingBr"> 
						</c:if>						
						電梯 : 
						<c:if test="${! empty adHasElevator}">
							<c:if test="${adHasElevator == true}">
								<input type="radio" name="elevator" value="true" checked>有 
								<input type="radio" name="elevator" value="false">無 <br><br>
							</c:if>
							<c:if test="${adHasElevator == false}">
								<input type="radio" name="elevator" value="true">有 
								<input type="radio" name="elevator" value="false" checked>無 <br><br>
							</c:if>						
						</c:if>
						<c:if test="${empty adHasElevator}">
							<input type="radio" name="elevator" value="true">有 
							<input type="radio" name="elevator" value="false" checked>無 <br><br>
						</c:if>												
						額外費用(不包含在租金內) : <input type="text" style="width: 600px;"
							name="extraCost" value="${adExtraCost}"> <br> <br
							id="wholePropertyBr">
						<div id="wholePropertyFieldDiv">
							總坪數 : <input type="number" style="width: 80px;"
								name="wholePropertyArea" min="0" max="300"
								value="${adTotalArea}" readonly="readonly">坪<br> <br>
							總租金 : <input id="wholePropertyTotalRent" type="number"
								style="width: 80px;" min="0" max="99999"
								name="wholePropertyTotalRent" readonly="readonly"> 元/月
							&nbsp;&nbsp;押金: <select name="wholePropertyDeposit">
								<option value="2">二個月</option>
								<option value="0">面議</option>
								<option value="1">一個月</option>
							</select>
						</div>
						<br> 最短租期 : <select name="minimumStay">
							<option value="3">1年</option>
							<option value="0">小於3個月</option>
							<option value="1">3個月</option>
							<option value="2">半年</option>
							<option value="4">2年</option>

						</select> <br> <br> <label for="availableDate">可遷入日期 </label> <input
							type="date" name="availableDate" id="availableDate"
							value="${checkInDate}">

					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<table id="roomDetail">
						<c:forEach var="room" items="${RoomRentAd.roomItems}"
							varStatus="vs">
							<tr>
								<td>
									<fieldset>
										<legend style="color: #59abe3; font-weight: bold;">
											房間${vs.index+1}
											<c:if test="${! empty roomState[vs.index]}">
												<c:if test="${roomState[vs.index] == false}">
													<input name="roomState${vs.index+1}" type="checkbox"
														value="false" checked>已出租
												</c:if>
												<c:if test="${roomState[vs.index] == true}">
													<input name="roomState${vs.index+1}" type="checkbox"
														value="false">已出租
												</c:if>
											</c:if>
										</legend>
										<div class="roomTypeDiv">
											房屋現況 : <input type="text" id="roomType${vs.index+1}"
												name="roomType${vs.index+1}" value="${roomType[vs.index]}"
												readonly="readonly"><br> <br>
										</div>
										出租樓層 : <input name="floor${vs.index+1}"
											value="${room.rentFloor}" style="width: 30px;" readonly="readonly">
										&nbsp;&nbsp; 總樓層 : 
										<input type="text" name="totalFloor${vs.index+1}" value="${room.rentTotalFloor}"
											style="width: 30px;" readonly="readonly"><br> <br>
										<span class="cookSelect"> 可否開伙 : <select
											name="canCook${vs.index+1}">
												<option value="true">可</option>
												<option value="false">不可</option>
										</select>
										</span> <br class="cookBr"> <br class="cookBr"> 格局 :
										<c:if test="${! empty balcony[vs.index]}">
											<c:if test="${balcony[vs.index] == true}">
												<input type="checkbox" name="balcony${vs.index+1}" value="true" checked>有陽台
											</c:if>
											<c:if test="${balcony[vs.index] == false}">
												<input type="checkbox" name="balcony${vs.index+1}" value="true">有陽台
											</c:if>
										</c:if>
										<c:if test="${! empty duplex[vs.index]}">
											<c:if test="${duplex[vs.index] == true}">
												<input type="checkbox" name="duplexApartment${vs.index+1}" value="true" checked>樓中樓 <br><br>
											</c:if>
											<c:if test="${duplex[vs.index] == false}">
												<input type="checkbox" name="duplexApartment${vs.index+1}" value="true">樓中樓 <br><br>
											</c:if>
										</c:if> 										
										 坪數 : <input type="text" name="area${vs.index+1}" style="width: 80px;" value="${room.area}" readonly="readonly">坪(請填寫室內實際使用坪數)
										<br><br> 房間設備 : 
										<c:if test="${! empty wash[vs.index]}">
											<c:if test="${wash[vs.index] == true}">
												<input type="checkbox" name="wash${vs.index+1}" value="true" checked>洗衣機
											</c:if>
											<c:if test="${wash[vs.index] == false}">
												<input type="checkbox" name="wash${vs.index+1}" value="true">洗衣機
											</c:if>
										</c:if>
										<c:if test="${! empty icebox[vs.index]}">
											<c:if test="${icebox[vs.index] == true}">
												<input type="checkbox" name="icebox${vs.index+1}" value="true" checked>冰箱
											</c:if>
											<c:if test="${icebox[vs.index] == false}">
												<input type="checkbox" name="icebox${vs.index+1}" value="true">冰箱
											</c:if>
										</c:if>
									 	<c:if test="${! empty four[vs.index]}">
									 		<c:if test="${four[vs.index] == true}">
												<input type="checkbox" name="four${vs.index+1}" value="true" checked>第四台 		
									 		</c:if>
									 		<c:if test="${four[vs.index] == false}">
									 			<input type="checkbox" name="four${vs.index+1}" value="true">第四台
									 		</c:if>
									 	</c:if>
										<c:if test="${! empty gas[vs.index]}">
											<c:if test="${gas[vs.index] == true}">
												<input type="checkbox" name="gas${vs.index+1}" value="true" checked>天然瓦斯
											</c:if>
											<c:if test="${gas[vs.index] == false}">
												<input type="checkbox" name="gas${vs.index+1}" value="true">天然瓦斯
											</c:if>
										</c:if>
										<c:if test="${! empty tv[vs.index]}">
											<c:if test="${tv[vs.index] == true}">
												<input type="checkbox" name="tv${vs.index+1}" value="true" checked>電視
											</c:if>
											<c:if test="${tv[vs.index] == false}">
												<input type="checkbox" name="tv${vs.index+1}" value="true">電視
											</c:if>
										</c:if>
										<c:if test="${! empty wardrobe[vs.index]}">
											<c:if test="${wardrobe[vs.index] == true}">
												<input type="checkbox" name="wardrobe${vs.index+1}" value="true" checked>衣櫃 
											</c:if>
											<c:if test="${wardrobe[vs.index] == false}">
												<input type="checkbox" name="wardrobe${vs.index+1}" value="true">衣櫃 
											</c:if>
										</c:if>
										<c:if test="${! empty sofa[vs.index]}">
											<c:if test="${sofa[vs.index] == true}">
												<input type="checkbox" name="sofa${vs.index+1}" value="true" checked>沙發 
											</c:if>
											<c:if test="${sofa[vs.index] == false}">
												<input type="checkbox" name="sofa${vs.index+1}" value="true">沙發 
											</c:if>
										</c:if>
										<c:if test="${! empty heater[vs.index]}">
											<c:if test="${heater[vs.index] == true}">
												<input type="checkbox" name="heater${vs.index+1}" value="true" checked>熱水器
											</c:if>
											<c:if test="${heater[vs.index] == false}">
												<input type="checkbox" name="heater${vs.index+1}" value="true">熱水器
											</c:if>
										</c:if>
										<c:if test="${! empty broadband[vs.index]}">
											<c:if test="${broadband[vs.index] == true}">
												<input type="checkbox" name="broadband${vs.index+1}" value="true" checked>網路 
											</c:if>
											<c:if test="${broadband[vs.index] == false}">
												<input type="checkbox" name="broadband${vs.index+1}" value="true">網路 
											</c:if>
										</c:if>
										<c:if test="${! empty desk[vs.index]}">
											<c:if test="${desk[vs.index] == true}">
												<input type="checkbox" name="desk${vs.index+1}" value="true" checked>桌子
											</c:if>
											<c:if test="${desk[vs.index] == false}">
												<input type="checkbox" name="desk${vs.index+1}" value="true">桌子
											</c:if>
										</c:if>
										<c:if test="${! empty chair[vs.index]}">
											<c:if test="${chair[vs.index] == true}">
												<input type="checkbox" name="chair${vs.index+1}" value="true" checked>椅子
											</c:if>
											<c:if test="${chair[vs.index] == false}">
												<input type="checkbox" name="chair${vs.index+1}" value="true">椅子
											</c:if>
										</c:if>
										<c:if test="${! empty singlebed[vs.index]}">
											<c:if test="${singlebed[vs.index] == true}">
												<input type="checkbox" name="singlebed${vs.index+1}" value="true" checked>單人床
											</c:if>
											<c:if test="${singlebed[vs.index] == false}">
												<input type="checkbox" name="singlebed${vs.index+1}" value="true">單人床
											</c:if>
										</c:if>
										<c:if test="${! empty doublebed[vs.index]}">
											<c:if test="${doublebed[vs.index] == true}">
												<input type="checkbox" name="doublebed${vs.index+1}" value="true" checked>雙人床
											</c:if>
											<c:if test="${doublebed[vs.index] == false}">
												<input type="checkbox" name="doublebed${vs.index+1}" value="true">雙人床
											</c:if>
										</c:if>
										<c:if test="${! empty coldair[vs.index]}">
											<c:if test="${coldair[vs.index] == true}">
												<input type="checkbox" name="coldair${vs.index+1}" value="true" checked>冷氣
											</c:if>
											<c:if test="${coldair[vs.index] == false}">
												<input type="checkbox" name="coldair${vs.index+1}" value="true">冷氣
											</c:if>
										</c:if>									
										<br><br> 租金 : <input type="number" min="0"
											name="roomRentPrice${vs.index+1}" style="width: 80px;"
											required value="${room.rentPrice}" readonly="readonly">
										元/月 &nbsp;&nbsp;押金: <select name="roomDeposit${vs.index+1}"
											disabled="disabled" id="roomDeposit${vs.index+1}">
											<option value="2">二個月</option>
											<option value="0">面議</option>
											<option value="1">一個月</option>
										</select> <br> <br>
									</fieldset>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<p>&nbsp;</p>
					<fieldset id="futureFlatmate">
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">希望的房客
						</legend>
						<br> 可否抽菸 : 
						<select name="Fsmoke">
							<option value="true">沒有偏好</option>
							<option value="false">否</option>
						</select>
						<br> <br> 職業 :
							<select name="Fjob">
								<option value="4">沒有偏好</option>
								<option value="0">學生</option>
								<option value="1">上班族</option>
							</select>
						<br> <br> 可否養寵物 : 
						<select name="Fpet">
							<option value="false">不可</option>
							<option value="true">可</option>
						</select>
						<br> <br>性別 : 
						<select name="Fgender">
							<option value="2">沒有偏好</option>
							<option value="0">男</option>
							<option value="1">女</option>
						</select><br>

					<c:if test="${adRentType == '出租房間(有公共空間)'}">
						<div id="roomForRentDiv">
							<br> 最小年齡 : 
							<input id="minAge" name="Fminage" type="text" value="${ageMin}" readonly>
<!-- 							<select id="minAge" name="Fminage"> -->
<!-- 								<option value="default">-</option> -->
<!-- 							</select><br> <br>  -->
							最大年齡 : 
							<input type="text" id="maxAge" name="Fmaxage" value="${ageMax}" readonly>
<!-- 							<select id="maxAge" name="Fmaxage"> -->
<!-- 								<option value="default">-</option> -->
<!-- 							</select> -->
							
							<br><br> 可否接受情侶 : 
							<c:if test="${! empty acceptCouple }">
								<c:if test="${acceptCouple == true}">
									<input type="radio" name="couple" value="false">不接受 
									<input type="radio" name="couple" value="true" checked>接受
								</c:if>
								<c:if test="${acceptCouple == false}">
									<input type="radio" name="couple" value="false" checked>不接受 
									<input type="radio" name="couple" value="true" >接受
								</c:if>								
							</c:if>
							<c:if test="${empty acceptCouple }">
								<input type="radio" name="couple" value="false">不接受 
								<input type="radio" name="couple" value="true">接受
							</c:if>							
						</div>
					</c:if>

					</fieldset>
				</td>
			</tr>
			<tr id="CurrentFlatmate">
				<td>
					<fieldset>
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">目前的房客
						</legend>
						<br> 是否養寵物 : 
						<c:if test="${! empty Cpet}">
							<select name="Cpet">
								<c:if test="${Cpet == true}">
									<option value="false">沒有養寵物</option>
									<option value="true" selected>有養寵物</option>
								</c:if>
								<c:if test="${Cpet == false}">
									<option value="false" selected>沒有養寵物</option>
									<option value="true" >有養寵物</option>
								</c:if>
							</select>
						</c:if>
						<c:if test="${empty Cpet}">
							<select name="Cpet">
								<option value="false">沒有養寵物</option>
								<option value="true">有養寵物</option>
							</select>
						</c:if>						
						<br> <br> 是否有人抽菸 : 
						<c:if test="${! empty CSmoke}">
							<select name="Csmoke">
								<c:if test="${CSmoke == true}">
									<option value="false">沒有</option>
									<option value="true" selected>有</option>
								</c:if>
								<c:if test="${CSmoke == false}">
									<option value="false" selected>沒有</option>
									<option value="true" >有</option>
								</c:if>								
							</select>
						</c:if>
						<c:if test="${ empty CSmoke}">
							<select name="Csmoke">
								<option value="false">沒有</option>
								<option value="true">有</option>
							</select>
						</c:if>						
						<br> <br> 性向 : 
						<select name="CsexOrient">
							<option value="0">不透漏</option>
							<option value="2">同性戀</option>
							<option value="1">異性戀</option>
							<option value="3">雙性戀</option>
							<option value="4">混合</option>
						</select>
						<c:if test="${! empty allowSexOrentSearch}">
							<c:if test="${allowSexOrentSearch ==  true}">
								<input type="checkbox" value="true" name="allowSexOrentSearch" checked>
								願意將性向公開給其他人搜尋(勾選代表願意) <br> <br>
							</c:if>
							<c:if test="${allowSexOrentSearch ==  false}">
								<input type="checkbox" value="true" name="allowSexOrentSearch">
								願意將性向公開給其他人搜尋(勾選代表願意) <br> <br>
							</c:if>							
						</c:if>
						<c:if test="${empty allowSexOrentSearch }">
							<input type="checkbox" value="true" name="allowSexOrentSearch">
							願意將性向公開給其他人搜尋(勾選代表願意) <br> <br>
						</c:if> 
						 性別 : <select
							id="genderOption" name="Cgender">
							<option>請選擇</option>
							<option>男</option>
							<option>女</option>
						</select> <br> <br>

						<div id="ageOption">年齡 :</div>


					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<p>&nbsp;</p>
					<fieldset>
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">廣告詳情</legend>
						<br> 廣告標題 : <input type="text" name="adTitle"
							style="width: 365px;" required value="${adTitle}"><br>
						<br> 現況描述 :
						<textarea name="adDescription" cols="50" rows="10" required>${adDescription}</textarea>
						<br> <br> 聯絡手機 : <input type="tel" id="phone"
							name="phone" pattern="[0-9]{4}[0-9]{6}"
							placeholder="e.g. 0973-345689" value="${phone}"><input
							type="checkbox" value="true" name="phoneAllowAttachAd">
						是否願意將電話放到廣告上 <br> <br> 上傳圖片 :<br> <br>
						<div id="choosePicZone" style="clear: both">
							<input type="file" value="upload-photo" id="theFile"
								name="imageFile"
								accept="image/png, image/jpeg, image/gif, image/jpg" multiple /><span
								id="uploadSpan"></span>
						</div>
						<br style="clear: both">
						<div id="dropzone"></div>
						<br>
					</fieldset>
				</td>

			</tr>
			<tr>
				<td>
					<fieldset>
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">交通資訊</legend>
						<br>
						<table>
							<tr class="item1">
								<td>附近交通：</td>
								<td>近&nbsp;&nbsp;<input type="text" name="bus">&nbsp;公車站

								</td>
								<td id="addBusDataOption"><input type="button" value="新增"
									id="addBusOption"></td>
							</tr>
							<tr class="item2">
								<td></td>
								<td>近&nbsp;&nbsp;<input type="text" name="MRT">&nbsp;捷運站
								</td>
								<td id="addMRTData"><input type="button" value="新增"
									id="addMRT"></td>
							</tr>
							<tr class="item3">
								<td></td>
								<td>近&nbsp;&nbsp;<input type="text" name="train">&nbsp;火車站

								</td>
								<td id="addTrainData"><input type="button" value="新增"
									id="addTrain"></td>
							</tr>


						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<p>&nbsp;</p>
					<fieldset>
						<legend
							style="font-weight: bold; font-size: 30px; color: #59ABE3;">信件提醒設定
						</legend>
						<br> <label for="instantEmail">立即信件提醒 : </label>
						當有找房者的廣告符合我的需求時立即寄送email通知 (上限是每天 <select id="maxEmail"
							name="emailMax">

						</select>封信) <br>

					</fieldset> <br> <input type="submit" value="修改廣告" id="updateButton"
					style="width: 120px; height: 40px">
					<p>&nbsp;</p>
					<p>&nbsp;</p>
				</td>
			</tr>
		</table>




	</form>
	<jsp:include page="../footer.jsp"></jsp:include>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
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