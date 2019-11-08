<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>




<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>發佈出租廣告</title>
 <!-- Favicon -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon/4U4U_final.png" type="image/x-icon">
    
    
    <!-- Font awesome -->
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">   
    <!-- slick slider -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/slick.css">
    <!-- price picker slider -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/nouislider.css">
    <!-- Fancybox slider -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.fancybox.css" type="text/css" media="screen" /> 
    <!-- Theme color -->
    <link id="switcher" href="${pageContext.request.contextPath}/css/theme-color/default-theme.css" rel="stylesheet">     

    <!-- Main style sheet -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">    

   
    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Vollkorn" rel="stylesheet" type="text/css">    
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet" type="text/css">
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
position:relative;
	width: 384px;
	height: 264px; 
	margin: 5px 0px;  
	float: left;
	text-align: center;
}

.odd {
position:relative;
	float: left;
	width: 182px;
 	height: 127px;  
	margin: 5px 0px;  
	text-align: center;
}

.even {
position:relative;
	float: right;
	width: 182px;
  	height: 127px;   
 	margin: 5px 0px;  
	text-align: center;
}

button{
 	float: left; 
 	position:absolute;
 	top:0px;
 
}
</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/roomrent.js"></script>

<script
	src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.0.2/dist/tw-city-selector.min.js"></script>

</head>

<body>
	 
	 <div id="aa-preloader-area">
    <div class="pulse"></div>
    <div
			style="font: bold 24px verdana; text-align: center; position:absolute;left:46.5%;top:40%;">
			${LoginSuccess}</div>
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
										<span style="color:red;">${MsgOK.InsertOK}${AccountBanned}${ActivateAccount}</span>
										&nbsp;&nbsp;<a href="<c:url value='/register' />"
											class="aa-register"><i class="fa fa-user-plus" aria-hidden="true"></i>註冊</a>
										<a href="<c:url value='/login' />" class="aa-login"><i class="fa fa-sign-in" aria-hidden="true"></i>登入</a>
									
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
            <a class="navbar-brand aa-logo-img" href="<c:url value='/' />"><img src="${pageContext.request.contextPath}/img/icon/4U4U_final.png" alt="logo"></a>  
                       <!-- Text based logo -->                             
           <a class="navbar-brand aa-logo" href="<c:url value='/' />">4U4U</a> 
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul id="top-menu" class="nav navbar-nav navbar-right aa-main-nav">
						<!--首頁-->
						<li ><a href="<c:url value='/' />">首頁</a></li>
						<!--瀏覽-->
						<li><a href="<c:url value='/properties' />">搜尋</a></li>
						<!--帳戶管理-->
						<li><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown "><a class="dropdown-toggle active"
							data-toggle="dropdown" >發佈廣告 <span
								class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									class="active" href="<c:url value='/PostRoomRentAd' />">出租廣告</a></li>
								<li><a
									href="<c:url value='/PostWantedRoomAd' />">找房廣告</a></li>
							</ul></li>
						<!--活動-->
						<li ><a href="<c:url value='/activity' />">活動</a></li>
						
						<!--關於我們-->
						<li class="dropdown"><a class="dropdown-toggle"
							>關於我們 <span
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
	
 <section id="aa-property-header" class="advertisement-room">
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
	
	<c:if test="${!empty ErrorMsg}">
	<div style="text-align:center;color:red">
		<c:forEach var="mapObject" items="${ErrorMsg}">
    	 ${mapObject.value} <br>

		</c:forEach>
	</div>
	</c:if>
	 <p>&nbsp;</p>
	 <form action="/4u4u/_4u4u/controller/ProcessRoomRentAd.do"
          method="post" enctype="multipart/form-data" name="rentForm">
          <table id="wholeTable">
              <tr>
                  <td>
                      <fieldset>
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">關於廣告 </legend>
                          <br> 出租方式 : <select id="rentType" name="rentType">
                              <option value="room-for-rent" label="出租房間(有公共空間)"></option>
                              <option value="whole-property">整層出租</option>
                              <option value="independent-studio">獨立套房</option>
                              <option value="dependent-studio">分租套房</option>
                          </select> <br> <br id="houseCountBr">
  
                          <div id="houseCountDiv">
                              房間數量 : <select id="houseCount" name="houseCount" required>
                                  <option value="">請選擇...</option>
                                  <option value="room-1">1間房</option>
                                  <option value="room-2">2間房</option>
                                  <option value="room-3">3間房</option>
                                  <option value="room-4">4間房</option>
                                  <option value="room-5">5間房</option>
                                  <option value="room-6">6間房</option>
                                  <option value="room-7">7間房</option>
                                  <option value="room-8">8間房</option>
                                  <option value="room-9">9間房</option>
                                  <option value="room-10">10間房</option>
                                  <option value="room-11">11間房</option>
                                  <option value="room-12">12間房</option>
                              </select>
                          </div>
                          <br> 廣告者身分 : <select id="adOwner" name="adOwner" required="required">
                              <option value="">請選擇...</option>
                              <option value="live-out-landlord">房東/無居住在裡面</option>
                              <option value="live-in-landlord" id="live-in-landlord">房東/有居住在裡面</option>
                              <option value="currentTenant" id="currentTenant">目前的租客</option>
                              <option value="agent">房仲</option>
                              <option value="former-flatmate">前租客(即將搬走需要有人取代他)</option>
                          </select> <br> <br>
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
                          <br id="peopleCountBr"> 住宅種類 : <select name="propertyType">
                              <option value="default">請選擇...</option>
                              <option value="0">公寓</option>
                              <option value="1">透天厝</option>
                              <option value="2">電梯大樓</option>
                              <option value="3">華廈</option>
                          </select> <br> <br>
                          <div id="addressPart" class="form-row city-selector-set">
                              出租地址 : <label for="inputCity">縣市</label> <select class=" county"
                                  name="county" required></select> <label for="inputDistrict">鄉鎮區</label> <select
                                  class=" district" name="district" required></select> <label
                                  for="inputAdd">地址</label> <input type="text" name="address" required
                                  id="inputAddr" placeholder="e.g.公園路一段3618號二樓">
                          </div>
                          <br> 汽車車位數量 : <select name="parkingNum">
                              <option value="0">0</option>
                              <option value="1">1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                          </select> <br> <br id="parkingBr"> 電梯 : <input type="radio"
                              name="elevator" value="true">有 <input type="radio"
                              name="elevator" value="false" checked>無 <br> <br>
                          額外費用(不包含在租金內) : <input type="text" style="width: 600px;"
                              name="extraCost"> <br> <br id="wholePropertyBr">
                          <div id="wholePropertyFieldDiv">
                              總坪數 : <input type="number" style="width: 80px;"
                                  name="wholePropertyArea" min="0" max="300"> 坪<br> <br>
                              總租金 : <input id="wholePropertyTotalRent" type="number" style="width: 80px;" min="0"
                                  max="99999" name="wholePropertyTotalRent"> 元/月
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
                              type="date" name="availableDate" id="availableDate">
  
                      </fieldset>
                  </td>
              </tr>
              <tr>
                  <td>
                      <table id="roomDetail">
  
                      </table>
                  </td>
              </tr>
              <tr>
                  <td>
                    <p>&nbsp;</p>
                      <fieldset id="futureFlatmate">
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">希望的房客 </legend>
                          <br> 可否抽菸 : <select name="Fsmoke">
                              <option value="true">沒有偏好</option>
                              <option value="false">否</option>
  
                          </select><br> <br> 職業 : <select name="Fjob">
                              <option value="4">沒有偏好</option>
                              <option value="0">學生</option>
                              <option value="1">上班族</option>
  
                          </select><br> <br> 可否養寵物 : <select name="Fpet">
                              <option value="false">不可</option>
                              <option value="true">可</option>
                          </select><br><br>性別 : <select name="Fgender">
                                  <option value="2">沒有偏好</option>
                                  <option value="0">男</option>
                                  <option value="1">女</option>
  
                              </select><br>
                              
  
                          <div id="roomForRentDiv">
                              <br> 最小年齡 : <select id="minAge" name="Fminage">
                                  <option value="default">-</option>
                              </select><br> <br> 最大年齡 : <select id="maxAge" name="Fmaxage">
                                  <option value="default">-</option>
                              </select><br> <br> 可否接受情侶 : <input type="radio" name="couple"
                                  value="false">不接受 <input type="radio" name="couple"
                                  value="true">接受
  
                          </div>
  
                      </fieldset>
                  </td>
              </tr>
              <tr id="CurrentFlatmate">
                  <td>
                      <fieldset>
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">目前的房客 </legend>
                          <br> 是否養寵物 : <select name="Cpet">
                              <option value="false">沒有養寵物</option>
                              <option value="true">有養寵物</option>
  
                          </select><br> <br> 是否有人抽菸 : <select name="Csmoke">
                              <option value="false">沒有</option>
                              <option value="true">有</option>
  
                          </select><br> <br> 性向 : <select name="CsexOrient">
                              <option value="0">不透漏</option>
                              <option value="2">同性戀</option>
                              <option value="1">異性戀</option>
                              <option value="3">雙性戀</option>
                              <option value="4">混合</option>
                          </select> <input type="checkbox" value="true" name="allowSexOrentSearch">
                          願意將性向公開給其他人搜尋(勾選代表願意) <br> <br> 性別 : <select id="genderOption"
                              name="Cgender">
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
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">廣告詳情</legend>
                          <br> 廣告標題 : <input type="text" name="adTitle"
                              style="width: 365px;" required><br> <br> 現況描述 :
                          <textarea name="adDescription" cols="50" rows="10" required></textarea>
                          <br> <br> 聯絡手機 : <input type="tel" id="phone"
                              name="phone" pattern="[0-9]{4}[0-9]{6}"
                              placeholder="e.g. 0973-345689"> <input type="checkbox"
                              value="true" name="phoneAllowAttachAd"> 是否願意將電話放到廣告上 <br>
                          <br> 上傳圖片 :<br> <br>
                          <div id="choosePicZone" style="clear: both">
                              <input type="file" value="upload-photo" id="theFile"
                                  name="imageFile"
                                  accept="image/png, image/jpeg, image/gif, image/jpg" multiple /><span
                                  id="uploadSpan"></span>
                          </div>
                          <br style="clear: both">
                          <div id="dropzone"></div>
                          <br >
                      </fieldset>
                  </td>
  
              </tr>
              <tr >
                  <td>
                      <fieldset>
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">交通資訊</legend>
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
                          <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">信件提醒設定 </legend>
                          <br> <label for="instantEmail">立即信件提醒 : </label>
                          當有找房者的廣告符合我的需求時立即寄送email通知 (上限是每天 <select id="maxEmail"
                              name="emailMax">
  
                          </select>封信) <br>
  
                      </fieldset> <br> <input type="submit" value="發佈廣告" style="width:120px;height:40px">
                      <p>&nbsp;</p>
                      <p>&nbsp;</p>
                  </td>
              </tr>
          </table>
  
  
      </form>
      
      <jsp:include page="../footer.jsp"></jsp:include>
	<script>
            new TwCitySelector({
                el : '.city-selector-set',
                elCounty : '.county', // 在 el 裡查找 element
                elDistrict : '.district', // 在 el 裡查找 element
                elZipcode : '.zipcode' // 在 el 裡查找 element
            });
        </script>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
     
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>   
  <!-- slick slider -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/slick.js"></script>
  <!-- Price picker slider -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/nouislider.js"></script>
   <!-- mixit slider -->
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.mixitup.js"></script>
  <!-- Add fancyBox -->        
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.fancybox.pack.js"></script>
  <!-- Custom js -->
  <script src="${pageContext.request.contextPath}/js/custom.js"></script> 
</body>
</html>