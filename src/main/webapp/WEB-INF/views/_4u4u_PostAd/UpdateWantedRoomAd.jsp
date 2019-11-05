<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改找房廣告</title>
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
/*             body{ */
/*                text-align: center; */
/*            } */
           form{
               text-align: center;
               width: 80%;
               margin: 0px auto;
           }
           table.PostAd{
            text-align: left; 
            margin: 0px auto;
           }
        /* #formDiv{
            width: 100%;
            border: 1px red solid;
            padding: 5px;
        } */
        
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

button {
 	float: left; 
 	position:absolute;
 	top:0px;
}
    </style>  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/updateWantedroom.js"></script>
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
											class="aa-register">註冊</a>
										<a href="<c:url value='/login' />"
											class="aa-login">登入</a>
									
									</c:if>
									<% // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
									    session.removeAttribute("AccountBanned");  
									    session.removeAttribute("ActivateAccount"); 
									    session.removeAttribute("MsgOK");
									 %>
									
									<c:if test="${! empty LoginOK }">

									

										<c:if test="${LoginOK.state==5}">
											<span style="color:white"> 歡迎! 管理者&nbsp; </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a style="color:white" href="<c:url value='/logout' />">登出</a>

										</c:if>

										<c:if test="${LoginOK.state==1||LoginOK.state==2}">
											<span style="color:white"> Hi ${LoginOK.name} </span>
											<img height='35px' width='35px' style="border-radius: 50%;"
												src='${pageContext.request.contextPath}/_4u4u/getImage?id=${LoginOK.memId}&type=MEMBER'>
											<a style="color:white" href="<c:url value='/logout' />"> 登出 </a>

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
					
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul id="top-menu" class="nav navbar-nav navbar-right aa-main-nav">
						<!--首頁-->
						<li ><a href="<c:url value='/' />">首頁</a></li>
						<!--瀏覽-->
						<li><a href="<c:url value='/properties' />">搜尋</a></li>
						<!--帳戶管理-->
						<li class="active"><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown "><a class="dropdown-toggle"
							>發佈廣告 <span
								class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a
									href="<c:url value='/PostRoomRentAd' />">出租廣告</a></li>
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
	<!-- End menu section -->

 <!-- Start Proerty header-->

 <section id="aa-property-header" class="search">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-property-header-inner">
            <h2>修改找屋廣告</h2>
          </div>
        </div>
      </div>
    </div>
  </section> 
  <!-- End Proerty header  -->
   <c:if test="${!empty ErrorMsg}">
    <div style="text-align:center;color:red">
		<c:forEach var="mapObject" items="${ErrorMsg}">
    	 ${mapObject.value} <br>

		</c:forEach>
	</div>
    	
    </c:if>
      <p>&nbsp;</p>
         <form action="/4u4u/_4u4u/UpdateWantedRoomAdServlet?adStyle=${adStyle}&adId=${findRoomAd.findRoomId}" id="rentForm" name="rentForm" method="post" enctype="multipart/form-data">  
          <table class="PostAd">
              <tr>
                  <td>
                          <fieldset>
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">關於您</legend><br>
                                  <!-- 當性別人數為單數時 年齡選擇只有單數 ,複數時 則是複數選擇 -->
                                  性別人數:			
                                  <c:if test="${! empty peopleNumGender}">
                                  	<select name="peopleNum_gender" id="peopleNum_gender" onchange="selectGenderToAge(this[selectedIndex].value)" required>
                                      <c:if test="${peopleNumGender == '一男'}">
                                      	<option value="1男" selected>1男</option>                                      
                                      </c:if>
                                      <c:if test="${peopleNumGender != '一男'}">
                                      	<option value="1男">1男</option>
                                      </c:if>                        
                                      <c:if test="${peopleNumGender == '1女'}">
                                      	<option value="1女" selected>1女</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender != '1女'}">
                                      	<option value="1女">1女</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender == '1男1女'}">
                                      	<option value="1男1女" selected>1男1女</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender != '1男1女'}">
                                      	 <option value="1男1女">1男1女</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender == '2男'}">
                                      	<option value="2男" selected>2男</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender != '2男'}">
                                      	<option value="2男">2男</option>
                                      </c:if>
                                      <c:if test="${peopleNumGender == '2女'}">
                                      	<option value="2女" selected>2女</option> 
                                      </c:if>
                                      <c:if test="${peopleNumGender != '2女'}">
                                      	<option value="2女">2女</option> 
                                      </c:if>                                            
                                  	</select><br><br>                                  	
                                  </c:if>
                                  <c:if test="${empty  peopleNumGender}">
                                  	<select name="peopleNum_gender" id="peopleNum_gender" onchange="selectGenderToAge(this[selectedIndex].value)" required>
                                      <option value="">請選擇您的性別...</option>                        
                                      <option value="1男">1男</option>
                                      <option value="1女">1女</option>
                                      <option value="1男1女">1男1女</option>
                                      <option value="2男">2男</option>
                                      <option value="2女">2女</option>        
                                  	</select><br><br>
                                  </c:if>                                  
                                  房間類型:
                                  <c:if test="${! empty suiteQuantity || ! empty roomQuantity}">
                                  	<select name="severalSuites" id="severalSuites" required>
                                  		<c:if test="${suiteQuantity == 1}">
                                  			<option value="">-</option>
                                     		<option value="1" selected>1</option>
                                     		<option value="2">2</option>
                                  		</c:if>
                                  		<c:if test="${suiteQuantity == 2}">
                                  			<option value="">-</option>
                                      		<option value="1">1</option>
                                      		<option value="2" selected>2</option>
                                  		</c:if>
                                  		<c:if test="${suiteQuantity != 1 && suiteQuantity != 2}">
                                  			<option value="">-</option>
                                      		<option value="1">1</option>
                                      		<option value="2">2</option>
                                  		</c:if>                                     
                                  	</select>間套房
                                  <select name="severalRooms" id="severalRooms" required>
                                  		<c:if test="${roomQuantity == 1}">
                                  			<option value="">-</option>
                                      		<option value="1"selected>1</option>
                                      		<option value="2">2</option>
                                  		</c:if>
                                  		<c:if test="${roomQuantity == 2}">
                                  			<option value="">-</option>
                                      		<option value="1">1</option>
                                      		<option value="2" selected>2</option>                                  	
                                  		</c:if>
                                  		<c:if test="${roomQuantity != 1 && roomQuantity !=2}">
                                  			<option value="">-</option>
                                      		<option value="1">1</option>
                                      		<option value="2">2</option>
                                  		</c:if>                                      
                                  	</select>間雅房
                                  	<br><br>
                                  </c:if>
                                  
                                  <c:if test="${empty suiteQuantity && empty roomQuantity}">
                                  <select name="severalSuites" id="severalSuites" required>
                                      <option value="">-</option>
                                      <option value="1">1</option>
                                      <option value="2">2</option>
                                  </select>間套房
                                  <select name="severalRooms" id="severalRooms" required>
                                      <option value="">-</option>
                                      <option value="1">1</option>
                                      <option value="2">2</option>
                                  </select>間雅房
                                  <br><br>
                                  </c:if>
                                  
                                  <!-- 當合租意願選擇不願意時 合租額外描述動態消失-->
                                  合租意願:			
                                  <c:if test="${! empty agreeShare}">
                                  	<c:if test="${agreeShare == true}">
                                  		<input type="checkbox" id="agreeShare" name="agreeShare" value="agree" checked>願意
                                  	</c:if>
                                  	<c:if test="${agreeShare == false}">
                                  		<input type="checkbox" id="agreeShare" name="agreeShare" value="agree">願意
                                  	</c:if>
                                  </c:if>                                  
                                  <br><br>
                                  <div id="addressPart" class="form-row city-selector-set">
                                  想要居住的地區 : 
                                      <label for="inputCity">縣市</label> 
                                      <select class="county" name="county" ></select>
                                      <!-- <label for="inputDistrict">鄉鎮區</label> -->
                                      <select	class=" district" name="district" style="display:none"></select><br><br id="districtBr">   <span id="districtSpan">
                                          
                                      </span>
                                  </div><br>
                                  租金預算:
                                  <input  id="budget" name="budget" type="number" min="0" max="100000000" required  value="${budget}">元/月<br><br>                
                                  可入住日期:
                                  <input type="date" name="checkInDate" id="checkInDate" value="${checkInDate}"><br><br>
                                  居住時間:
                                  <c:if test="${! empty liveTime}">
                                  	<select name="liveTime" id="livetime">
                                  		<c:if test="${liveTime == '小於3個月'}">
                                  			<option value="小於3個月" selected>小於3個月</option>
                                  		</c:if>
                                  		<c:if test="${liveTime != '小於3個月'}">
                                  			<option value="小於3個月">小於3個月</option>
                                  		</c:if>
                                  		<c:if test="${liveTime == '3個月'}">
                                  			<option value="3個月" selected>3個月</option>
                                  		</c:if>
                                  		<c:if test="${liveTime != '3個月'}">
                                  			<option value="3個月">3個月</option>
                                  		</c:if>	
                                  		<c:if test="${liveTime == '半年'}">
                                  			<option value="半年" selected>半年(6個月)</option>   
                                  		</c:if>
                                  		<c:if test="${liveTime != '半年'}">
                                  			<option value="半年">半年(6個月)</option>   
                                  		</c:if>
                                  		<c:if test="${liveTime == '1年'}">
                                  			<option value="1年" selected>1年</option>
                                  		</c:if>
                                  		<c:if test="${liveTime != '1年'}">
                                  			<option value="1年">1年</option>
                                  		</c:if>
                                  		<c:if test="${liveTime == '2年'}">
                                  			<option value="2年" selected>2年</option>
                                  		</c:if>
                                  		<c:if test="${liveTime != '2年'}">
                                  			<option value="2年">2年</option>
                                  		</c:if>                                                 
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${empty liveTime}">
                                  	<select name="liveTime" id="livetime">                
                                      <option value="小於3個月">小於3個月</option>            
                                      <option value="3個月">3個月</option>            
                                      <option value="半年">半年(6個月)</option>           
                                      <option value="1年">1年</option>                    
                                      <option value="2年">2年</option>                
                                  	</select><br><br>
                                  </c:if>                                  
                                  想要的設施:		
                                  <c:if test="${! empty washMachine}">
                                  	<c:if test="${washMachine == true}">
                                  		<input type="checkbox" name="hasWashingMachine" value="true" checked>洗衣機
                                  	</c:if>
                                  	<c:if test="${washMachine == false}">
                                  		<input type="checkbox" name="hasWashingMachine" value="true">洗衣機
                                  	</c:if>
                                  </c:if>	
                                  <c:if test="${! empty refrigerator}">
                                  	<c:if test="${refrigerator == true}">
                                  		<input type="checkbox" name="hasRefrigerator" value="true" checked>冰箱
                                  	</c:if>
                                  	<c:if test="${refrigerator == false}">
                                  		<input type="checkbox" name="hasRefrigerator" value="true">冰箱
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty TV}">
                                  	<c:if test="${TV == true}">
                                  		<input type="checkbox" name="hasTV" value="true" checked>電視
                                  	</c:if>
                                  	<c:if test="${TV == false}">
                                  		<input type="checkbox" name="hasTV" value="true">電視
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty airConditioning}">
                                  	<c:if test="${airConditioning == true}">
                                  		<input type="checkbox" name="hasAirConditioning" value="true" checked>冷氣
                                  	</c:if>
                                  	<c:if test="${airConditioning == false}">
                                  		<input type="checkbox" name="hasAirConditioning" value="true">冷氣
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty waterHeater}">
                                  	<c:if test="${waterHeater == true}">
                                  		<input type="checkbox" name="hasWaterHeater" value="true" checked>熱水器
                                  	</c:if>
                                  	<c:if test="${waterHeater == false}">
                                  		<input type="checkbox" name="hasWaterHeater" value="true">熱水器
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty internet}">
                                  	<c:if test="${internet == true}">
                                  		<input type="checkbox" name="hasInternet" value="true" checked>網路
                                  	</c:if>
                                  	<c:if test="${internet == false}">
                                  		<input type="checkbox" name="hasInternet" value="true">網路
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty fourthTV}">
                                  	<c:if test="${fourthTV == true}">
                                  		<input type="checkbox" name="hasFourthTV" value="true" checked>第四台
                                  	</c:if>
                                  	<c:if test="${fourthTV == false}">
                                  		<input type="checkbox" name="hasFourthTV" value="true">第四台
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty gas}">
                                  	<c:if test="${gas == true}">	
                                  		<input type="checkbox" name="hasGas" value="true" checked>天然瓦斯
                                  	</c:if>
                                  	<c:if test="${gas == false}">
                                  		<input type="checkbox" name="hasGas" value="true">天然瓦斯
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty wardrobe}">
                                  	<c:if test="${wardrobe == true}">
                                  		<input type="checkbox" name="hasWardrobe" value="true" checked>衣櫃
                                  	</c:if>
                                  	<c:if test="${wardrobe == false}">
                                  		<input type="checkbox" name="hasWardrobe" value="true">衣櫃
                                  	</c:if>
                                  </c:if>                
                                  <c:if test="${! empty sofa}">
                                  	<c:if test="${sofa == true}">
                                  		<input type="checkbox" name="hasSofa" value="true" checked>沙發
                                  	</c:if>
                                  	<c:if test="${sofa == false}">
                                  		<input type="checkbox" name="hasSofa" value="true">沙發
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty table}">
                                  	<c:if test="${table == true}">
                                  		<input type="checkbox" name="hasTable" value="true" checked>桌子
                                  	</c:if>
                                  	<c:if test="${table == false}">
                                  		<input type="checkbox" name="hasTable" value="true">桌子
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty chair}">
	                                <c:if test="${chair == true}">
	                                  	<input type="checkbox" name="hasChair" value="true" checked>椅子
                                  	</c:if>
                                  	<c:if test="${chair == false}">
                                  		<input type="checkbox" name="hasChair" value="true">椅子
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty parking}">
                                  	<c:if test="${parking == true}">
                                  		<input type="checkbox" name="hasParking" value="true" checked>停車位
                                  	</c:if>
                                  	<c:if test="${parking == false}">
                                  		<input type="checkbox" name="hasParking" value="true">停車位
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty balcony}">
                                  	<c:if test="${balcony == true}">
                                  		<input type="checkbox" name="hasBalcony" value="true" checked>陽台
                                  	</c:if>
                                  	<c:if test="${balcony == false}">
                                  		<input type="checkbox" name="hasBalcony" value="true">陽台
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty singleBed}">
                                  	<c:if test="${singleBed == true}">
                                  		<input type="checkbox" name="hasSingleBed" value="true" checked>單人床
                                  	</c:if>
                                  	<c:if test="${singleBed == false}">
                                  		<input type="checkbox" name="hasSingleBed" value="true">單人床
                                  	</c:if>
                                  </c:if>
                                  <c:if test="${! empty doubleBed}">
                                  	<c:if test="${doubleBed == true}">
                                  		<input type="checkbox" name="hasDoubleBed" value="true" checked>雙人床   
                                  	</c:if>
                                  	<c:if test="${doubleBed == false}">
                                  		<input type="checkbox" name="hasDoubleBed" value="true">雙人床   
                                  	</c:if>
                                  </c:if>                                 
                                  <br><br>
                                  是否想開火:
                                  <select name="allowCook" id="useFire">
                                      <option value="false">否</option>
                                      <option value="true">可</option>
                                  </select><br><br>
                                  <!-- 當性別人數為單數時,動態出現只有單數(ageDiv)-->
                                  年齡:
                                 	<c:choose>                         
                                 		<c:when test="${peopleNumGender == '1男' || peopleNumGender == '1女'}">
                                 			<span id="ageDiv">
                                    			<input type="number" name="age" id="age" value="${age}" style="width: 40px;" min="18" max="99">歲
                                 			</span>
                                 		</c:when>
                                 		<c:otherwise>
                                 			<c:if test="${peopleNumGender == '1男1女' || peopleNumGender == '2男' || peopleNumGender == '2女' }">
                                 				<span id="ageDiv" style="display: none">
                                 				<input type="number" name="age" id="age" style="width: 40px;" min="18" max="99">歲
                                 				</span>
                                 			</c:if>           
                                 		</c:otherwise>
                                 	</c:choose>
                                  <!-- 當性別人數為複數時,動態出現年齡範圍(ageRange) -->                                 
                                  	<c:choose>
                                  	<c:when test="${peopleNumGender == '1男1女' || peopleNumGender == '2男' || peopleNumGender == '2女'}">
                                  		<span id="ageRange" >
                                  			<input type="number" name="ageMin" id="ageMin" value="${ageMin}"  style="width: 40px;">歲到
                                  			<input type="number" name="ageMax" id="ageMax" value="${ageMax}"  style="width: 40px;">歲
                                  		</span>
                                  	</c:when>
                                  	<c:otherwise>
                                  		<span id="ageRange" style="display: none">
                                  			<input type="number" name="ageMin" max="99" min="18" style="width: 40px;">歲到
                                  			<input type="number" name="ageMax" max="99" min="18" style="width: 40px;">歲	                            		
                                  		</span>
                                  	</c:otherwise>
                                  	</c:choose>
                                 
                                   
                                                                
                                  <br><br> 
                                  職業:				
                                  <c:if test="${! empty job}">
                                  	<select name="job" id="job">
                                  		<c:if test="${job == '學生'}">
                                  			<option value="學生" selected>學生</option>
                                  		</c:if>
                                  		<c:if test="${job != '學生'}">
                                  			<option value="學生">學生</option>
                                  		</c:if>
                                  		<c:if test="${job == '上班族'}">
                                  			<option value="上班族" selected>上班族</option>  
                                  		</c:if>
                                  		<c:if test="${job != '上班族'}">
                                  			<option value="上班族">上班族</option>  
                                  		</c:if>
                                  		<c:if test="${job == '混合'}">
                                  			<option value="混合" selected>混合</option>
                                  		</c:if>	
                                  		<c:if test="${job != '混合'}">
                                  			<option value="混合">混合</option>
                                  		</c:if>
                                  		<c:if test="${job == '其他'}">
                                  			<option value="其他" selected>其他</option>
                                  		</c:if>
                                  		<c:if test="${job != '其他'}">
                                  			<option value="其他">其他</option>
                                  		</c:if>                                   
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${empty job}">
                                  	<select name="job" id="job">
                                      <option value="學生">學生</option>
                                      <option value="上班族">上班族</option>                       
                                      <option value="混合">混合</option>
                                      <option value="其他">其他</option>
                                  	</select><br><br>
                                  </c:if>                                  
                                 是否抽菸:				
                                 <c:if test="${! empty smoke}">
                                 	<select name="allowSmoke" id="allowSmoke">
                                 		<c:if test="${smoke == true}">
                                 			<option value="true" selected>是</option>
                                 			<option value="false">否</option>
                                 		</c:if>
                                 		<c:if test="${smoke == false}">
                                 			<option value="true" >是</option>
                                 			<option value="false" selected>否</option>
                                 		</c:if>                              
                                  	</select><br><br>
                                 </c:if>
                                 <c:if test="${empty smoke}">
                                 	<select name="allowSmoke" id="allowSmoke">                  
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  	</select><br><br>
                                 </c:if>                                  
                                  是否養寵物:		
                                  <c:if test="${! empty pet}">
                                  	<select name="allowPet" id="allowPet">
                                  		<c:if test="${pet == true}">
                                  			<option value="false">否</option>
                                      		<option value="true" selected>是</option>
                                  		</c:if>
                                      	<c:if test="${pet == false}">
                                  			<option value="false" selected>否</option>
                                      		<option value="true" >是</option>
                                  		</c:if>
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${empty pet}">
                                  	<select name="allowPet" id="allowPet">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  	</select><br><br>
                                  </c:if>                                  
                                  性向:
                                  <c:if test="${! empty sexOrient}">
                                  	<select name="sexualOrientation" id="sexualOrientation">
                                  		<c:if test="${sexOrient == '不透漏'}">
                                  			<option value="不透漏" selected>不透漏</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient != '不透漏'}">
                                  			<option value="不透漏">不透漏</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient == '異性戀'}">
                                  			  <option value="異性戀" selected>異性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient != '異性戀'}">
                                  			  <option value="異性戀">異性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient == '同性戀'}">
                                  			<option value="同性戀" selected>同性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient != '同性戀'}">
                                  			<option value="同性戀">同性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient == '雙性戀'}">
                                  			<option value="雙性戀" selected>雙性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient != '雙性戀'}">
                                  			<option value="雙性戀">雙性戀</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient == '混合'}">
                                  			<option value="混合" selected>混合</option>
                                  		</c:if>
                                  		<c:if test="${sexOrient != '混合'}">
                                  			<option value="混合">混合</option>
                                  		</c:if> 
                                  	</select>
                                  </c:if>
                                  <c:if test="${empty sexOrient}">
                                  	<select name="sexualOrientation" id="sexualOrientation">
                                      <option value="不透漏">不透漏</option>
                                      <option value="異性戀">異性戀</option>
                                      <option value="同性戀">同性戀</option>
                                      <option value="雙性戀">雙性戀</option>
                                      <option value="混合">混合</option>
                                  	</select>
                                  </c:if>
                                  
                                  <c:if test="${! empty agreeAdCondition}">
                                  	<c:if test="${agreeAdCondition == true}">
                                  		<input type="checkbox" name="agreeAdCondition" value="true" checked>是否同意成為廣告的搜索條件<br><br>
                                  	</c:if>
                                  	<c:if test="${agreeAdCondition == false}">
                                  		<input type="checkbox" name="agreeAdCondition" value="true">是否同意成為廣告的搜索條件<br><br>
                                  	</c:if>
                                  </c:if>
                                  
                              </fieldset>   <br>     
                  </td>
              </tr>
              <tr>
                  <td>
                   
                          <fieldset id="wantedRoommates">
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">您希望的室友</legend><br>                  
                                  性別:
                                  <c:if test="${! empty flatmateGender}">
                                  	<select name="wantedRoommatesGender" id="wantedRoommatesGender">
                                  		<c:if test="${flatmateGender == '不介意'}">
                                  			<option value="不介意" selected>不介意</option>  
                                  		</c:if>
                                  		<c:if test="${flatmateGender != '不介意'}">
                                  			<option value="不介意">不介意</option>  
                                  		</c:if>
                                  		<c:if test="${flatmateGender == '男'}">
                                  			<option value="男" selected>男</option>
                                  		</c:if>
                                  		<c:if test="${flatmateGender != '男'}">
                                  			<option value="男">男</option>
                                  		</c:if>
                                  		<c:if test="${flatmateGender == '女'}">
                                  			<option value="女" selected>女</option> 
                                  		</c:if>
                                  		<c:if test="${flatmateGender != '女'}">
                                  			<option value="女">女</option> 
                                  		</c:if>
                                  	</select><br><br> 
                                  </c:if>
                                  <c:if test="${empty flatmateGender}">
                                  	<select name="wantedRoommatesGender" id="wantedRoommatesGender">
                                      <option value="不介意">不介意</option>                                    
                                      <option value="男">男</option>                       
                                      <option value="女">女</option>  
                                  	</select><br><br> 
                                  </c:if>
                                                        
                                  年齡:
                                  <select name="roommatesAgeMin" id="roommatesAgeMin">
                                      <option value="default">-</option>
                                   </select>歲到
                                  <select name="roommatesAgeMax" id="roommatesAgeMax">
                                      <option value="default">-</option>
                                  </select>歲
                                  <br><br> 
                                  職業:
                                  <c:if test="${! empty flatmateJob}">
                                  	<select name="wantedRoommatesJob" id="wantedRoommatesJob" >
                                      <c:if test="${flatmateJob == '學生'}">
                                      	<option value="學生" selected>學生</option>
                                      </c:if>
                                      <c:if test="${flatmateJob != '學生'}">
                                      	<option value="學生">學生</option>
                                      </c:if>
                                      <c:if test="${flatmateJob == '上班族'}">
                                      	<option value="上班族" selected>上班族</option>  
                                      </c:if>
                                      <c:if test="${flatmateJob != '上班族'}">
                                      	<option value="上班族">上班族</option>  
                                      </c:if>
                                      <c:if test="${flatmateJob == '不介意'}">
                                      	<option value="不介意" selected>不介意</option>
                                      </c:if>
                                      <c:if test="${flatmateJob != '不介意'}">
                                      	<option value="不介意">不介意</option>
                                      </c:if>
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${ empty flatmateJob}">
                                  	<select name="wantedRoommatesJob" id="wantedRoommatesJob" >
                                      <option value="學生">學生</option>
                                      <option value="上班族">上班族</option>          
                                      <option value="不介意">不介意</option>
                                  	</select><br><br>
                                  </c:if>
                                  
                                  是否抽菸:			
                                  <c:if test="${! empty flatmateSmoke}">
                                  	<select name="wantedRoommatesSmoke" id="wantedRoommatesSmoke">
                                      <c:if test="${flatmateSmoke == true}">
                                      	<option value="false">否</option>
                                      	<option value="true" selected>是</option>
                                      </c:if>
                                      <c:if test="${flatmateSmoke == false}">
                                      	<option value="false" selected>否</option>
                                      	<option value="true">是</option>
                                      </c:if>                                      
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${empty flatmateSmoke}">
                                  	<select name="wantedRoommatesSmoke" id="wantedRoommatesSmoke">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  	</select><br><br>
                                  </c:if>                                  
                                  是否養寵物:		
                                  <c:if test="${! empty flatmatePet}">
                                  	<select name="wantedRoommatesPet" id="wantedRoommatesPet">
                                      <c:if test="${flatmatePet == true}">
                                      	<option value="false">否</option>
                                      	<option value="true" selected>是</option>
                                      </c:if>
                                      <c:if test="${flatmatePet == false}">
                                      	<option value="false" selected>否</option>
                                      	<option value="true">是</option>
                                      </c:if>                                      
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${empty flatmatePet}">
                                  	<select name="wantedRoommatesPet" id="wantedRoommatesPet">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  	</select><br><br>
                                  </c:if>
                                  
                                  性向:
                                  <c:if test="${! empty flatmateSexOrient}">
                                  	<select name="wantedRoommatesSex" id="wantedRoommatesSex">
                                  		<c:if test="${flatmateSexOrient == '不介意'}">
                                  			<option value="不介意" selected>不介意</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient != '不介意'}">
                                  			<option value="不介意">不介意</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient == '異性戀'}">
                                  			<option value="異性戀" selected>異性戀</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient != '異性戀'}">
                                  			<option value="異性戀">異性戀</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient == '同性戀'}">
                                  			<option value="同性戀" selected>同性戀</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient != '同性戀'}">
                                  			<option value="同性戀">同性戀</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient == '雙性戀'}">
                                  			<option value="雙性戀" selected>雙性戀</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient != '雙性戀'}">
                                  			<option value="雙性戀">雙性戀</option>
                                  		</c:if>	
                                  		<c:if test="${flatmateSexOrient == '混合'}">
                                  			<option value="混合" selected>混合</option>
                                  		</c:if>
                                  		<c:if test="${flatmateSexOrient != '混合'}">
                                  			<option value="混合">混合</option>
                                  		</c:if>
                                  	</select><br><br>
                                  </c:if>
                                  <c:if test="${ empty flatmateSexOrient}">
                                  	<select name="wantedRoommatesSex" id="wantedRoommatesSex">
                                      <option value="不介意">不介意</option>
                                      <option value="異性戀">異性戀</option>
                                      <option value="同性戀">同性戀</option>
                                      <option value="雙性戀">雙性戀</option>
                                      <option value="混合">混合</option>
                                  	</select><br><br>
                                  </c:if>
                                  
                              </fieldset><br>
                  </td>
              </tr>
              <tr>
                  <td>
                          <fieldset id="adTitle">
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">廣告詳情</legend><br>
                                  廣告標題:
                                  <input type="text" name="adTitle" required value="${adTitle}"><br><br>
                                  (簡短的介紹)<br><br>
                                  現況描述:
                                  <textarea name="adDescription" rows="10" cols="50" required>${adDescription}</textarea><br><br>
                                  （描述中不允許使用聯繫方式)<br><br>
                                  <!-- 當合租意願選擇願意時,合租額外描述動態新增 -->
<!--                                    <div id="agreeShareDescriptionDiv" style="display: none"> -->
<!--                                     合租額外描述: -->
<!--                                         <textarea name="agreeShareDescription" id="agreeShareDescription"  rows="10" cols="50"></textarea> -->
<!--                                     </div><br><br> -->
                                  電話:
                                  <input type="tel" name="phone" name="phone" pattern="[0-9]{4}[0-9]{6}"  placeholder="e.g. 0912345678" value="${phone}">
                                  (我們不會在4u4u上顯示您的號碼或將其傳遞給任何第三方。如果我們需要就您的帳戶與您聯繫或幫助驗證您的詳細信息，我們需要您的號碼)<br><br>                
                                  上傳圖片:
                                  <br><br>
                                  <div id="choosePicZone">
                                          <input type="file" name="imagefile" value="upload-photo" id="theFile" accept="image/png, image/jpeg, image/gif, image/jpg" multiple/><span id="uploadSpan"></span> 
                                          </div>  
                                          <br>
                                          <div id="dropzone"></div>
                                             
                                          <br style="clear: both">
                              </fieldset> <br>
                  </td>
              </tr>
              <tr>
                  <td>
                          <fieldset id="emailRemind">
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">電子郵件提醒</legend><br>             
                                  <label for="instantEmail">立即郵件提醒:</label>
                                  網站上公佈後立即向我發送符合我要求的新房間廣告的電子郵件
                                   (每天最多不超過
                                   <select id="emailMax" name="emailMax">
                                       <option value="${emailMax}">${emailMax}</option>                   
                                   </select>
                                   封信)<br><br>
                               </fieldset><br>
                               <input type="submit" value="修改廣告" style="width:120px;height:40px">
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
  <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>   
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
  <script type="text/javascript">
  	if()
  </script> 
</body>
</html>