<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>發佈找房廣告</title>
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
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/wantedroom.js"></script>
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
						<li><a href="<c:url value='/account' />">帳戶管理</a></li>
						<!--發佈廣告-->
						<li class="dropdown active"><a class="dropdown-toggle"
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


 <section id="aa-property-header" class="advertisement-want-room">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-property-header-inner">
            <h2>發佈找屋廣告</h2>
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
         <form action="/4u4u/_4u4u/controller/ProcessWantedRoomAd.do" id="rentForm" name="rentForm" method="post" enctype="multipart/form-data">  
          <table class="PostAd">
              <tr>
                  <td>
                          <fieldset>
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">關於您</legend><br>
                                  <!-- 當性別人數為單數時 年齡選擇只有單數 ,複數時 則是複數選擇 -->
                                  性別人數:
                                  <select name="peopleNum_gender" id="peopleNum_gender" onchange="selectGenderToAge(this[selectedIndex].value)" required>
                                      <option value="">請選擇您的性別...</option>                        
                                      <option value="1男">1男</option>
                                      <option value="1女">1女</option>
                                      <option value="1男1女">1男1女</option>
                                      <option value="2男">2男</option>
                                      <option value="2女">2女</option>        
                                  </select><br><br>
                                  房間類型:
                                  <select name="severalSuites" id="severalSuites" required>
                                      <option value="">-</option>
                                  </select>間套房
                                  <select name="severalRooms" id="severalRooms" required>
                                          <option value="">-</option>
                                  </select>間雅房
                                  <br><br>
                                  <!-- 當合租意願選擇不願意時 合租額外描述動態消失-->
                                  合租意願:
                                  <input type="checkbox" id="agreeShare" name="agreeShare" value="agree">願意
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
                                  <input  id="budget" name="budget" type="number" min="0" max="100000000" required>元/月<br><br>                
                                  可入住日期:
                                  <input type="date" name="checkInDate" id="checkInDate"><br><br>
                                  居住時間:
                                  <select name="liveTime" id="livetime">                
                                      <option value="小於3個月">小於3個月</option>            
                                      <option value="3個月">3個月</option>            
                                      <option value="半年">半年(6個月)</option>           
                                      <option value="1年">1年</option>                    
                                      <option value="2年">2年</option>                
                                  </select><br><br>
                                  想要的設施:
                                  <input type="checkbox" name="hasWashingMachine" value="true">洗衣機
                                  <input type="checkbox" name="hasRefrigerator" value="true">冰箱
                                  <input type="checkbox" name="hasTV" value="true">電視
                                  <input type="checkbox" name="hasAirConditioning" value="true">冷氣
                                  <input type="checkbox" name="hasWaterHeater" value="true">熱水器
                                  <input type="checkbox" name="hasInternet" value="true">網路
                                  <input type="checkbox" name="hasFourthTV" value="true">第四台                
                                  <input type="checkbox" name="hasGas" value="true">天然瓦斯
                                  <input type="checkbox" name="hasWardrobe" value="true">衣櫃
                                  <input type="checkbox" name="hasSofa" value="true">沙發
                                  <input type="checkbox" name="hasTable" value="true">桌子
                                  <input type="checkbox" name="hasChair" value="true">椅子
                                  <input type="checkbox" name="hasParking" value="true">停車位
                                  <input type="checkbox" name="hasBalcony" value="true">陽台
                                  <input type="checkbox" name="hasSingleBed" value="true">單人床
                                  <input type="checkbox" name="hasDoubleBed" value="true">雙人床   
                                  <br><br>
                                  是否想開火:
                                  <select name="allowCook" id="useFire">
                                      <option value="false">否</option>
                                      <option value="true">可</option>
                                  </select><br><br>
                                  <!-- 當性別人數為單數時,動態出現只有單數(ageDiv)-->
                                  年齡:
                                  <span id="ageDiv">
                                          <select name="age" id="age">
                                                  <option value="default">-</option>
                                              </select>歲
                                  </span>
                                  <!-- 當性別人數為複數時,動態出現年齡範圍(ageRange) -->
                                  <span id="ageRange" style="display: none">
                                          <select name="ageMin" id="ageMin">
                                                  <option value="default">-</option>
                                              </select>歲到<select name="ageMax" id="ageMax">
                                                  <option value="default">-</option>
                                              </select>歲
                                  </span>          
                                  <br><br> 
                                  職業:
                                  <select name="job" id="job">
                                      <option value="學生">學生</option>
                                      <option value="上班族">上班族</option>                       
                                      <option value="混合">混合</option>
                                      <option value="其他">其他</option>
                                  </select><br><br>
                                 是否抽菸:
                                  <select name="allowSmoke" id="allowSmoke">                  
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  </select><br><br>
                                  是否養寵物:
                                  <select name="allowPet" id="allowPet">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  </select><br><br>
                                  性向:
                                  <select name="sexualOrientation" id="sexualOrientation">
                                      <option value="不透漏">不透漏</option>
                                      <option value="異性戀">異性戀</option>
                                      <option value="同性戀">同性戀</option>
                                      <option value="雙性戀">雙性戀</option>
                                      <option value="混合">混合</option>
                                  </select>
                                  <input type="checkbox" name="agreeAdCondition" >是否同意成為廣告的搜索條件<br><br>
                              </fieldset>   <br>     
                  </td>
              </tr>
              <tr>
                  <td>
                   
                          <fieldset id="wantedRoommates">
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">您希望的室友</legend><br>                  
                                  性別:
                                  <select name="wantedRoommatesGender" id="wantedRoommatesGender">
                                      <option value="不介意">不介意</option>                                    
                                      <option value="男">男</option>                       
                                      <option value="女">女</option>  
                                  </select><br><br>                       
                                  年齡:
                                  <select name="roommatesAgeMin" id="roommatesAgeMin">
                                      <option value="default">-</option>
                                   </select>歲到
                                  <select name="roommatesAgeMax" id="roommatesAgeMax">
                                      <option value="default">-</option>
                                  </select>歲
                                  <br><br> 
                                  職業:
                                  <select name="wantedRoommatesJob" id="wantedRoommatesJob" >
                                      <option value="學生">學生</option>
                                      <option value="上班族">上班族</option>          
                                      <option value="不介意">不介意</option>
                                  </select><br><br>
                                  是否抽菸:
                                  <select name="wantedRoommatesSmoke" id="wantedRoommatesSmoke">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  </select><br><br>
                                  是否養寵物:
                                  <select name="wantedRoommatesPet" id="wantedRoommatesPet">
                                      <option value="false">否</option>
                                      <option value="true">是</option>
                                  </select><br><br>
                                  性向:
                                  <select name="wantedRoommatesSex" id="wantedRoommatesSex">
                                      <option value="不介意">不介意</option>
                                      <option value="異性戀">異性戀</option>
                                      <option value="同性戀">同性戀</option>
                                      <option value="雙性戀">雙性戀</option>
                                      <option value="混合">混合</option>
                                  </select><br><br>
                              </fieldset><br>
                  </td>
              </tr>
              <tr>
                  <td>
                          <fieldset id="adTitle">
                                  <legend style="font-weight:bold;font-size: 30px;color:#59ABE3;">廣告詳情</legend><br>
                                  廣告標題:
                                  <input type="text" name="adTitle" required><br><br>
                                  (簡短的介紹)<br><br>
                                  現況描述:
                                  <textarea name="adDescription" rows="10" cols="50" required></textarea><br><br>
                                  （描述中不允許使用聯繫方式)<br><br>
                                  <!-- 當合租意願選擇願意時,合租額外描述動態新增 -->
<!--                                    <div id="agreeShareDescriptionDiv" style="display: none"> -->
<!--                                     合租額外描述: -->
<!--                                         <textarea name="agreeShareDescription" id="agreeShareDescription"  rows="10" cols="50"></textarea> -->
<!--                                     </div><br><br> -->
                                  電話:
                                  <input type="tel" name="phone" name="phone" pattern="[0-9]{4}[0-9]{6}"  placeholder="e.g. 0912345678">
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
                                       <option value="default">-</option>                   
                                   </select>
                                   封信)<br><br>
                               </fieldset><br>
                               <input type="submit" value="發佈廣告" style="width:120px;height:40px">
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
</body>
</html>