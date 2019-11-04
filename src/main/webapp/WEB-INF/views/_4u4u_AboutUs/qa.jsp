<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<head>
  <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">    
     <title>4U4U | 常見問與答</title>

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
    <link href='https://fonts.googleapis.com/css?family=Vollkorn' rel='stylesheet' type='text/css'>    
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
    

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  

  </head>
  <body>
  
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
						<li class="dropdown active"><a class="dropdown-toggle"
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
<!-- Start Proerty header  -->
  <section id="aa-property-header" class="qa">
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="aa-property-header-inner">
            <h2>常見問與答</h2>
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

<section id="aa-blog" style="clear: both">
   
                        <div style="text-align: center;width: 100%">
                          <div  style="text-align: left;width: 50%;margin:0px auto">
                        
                        <span  style="text-align: left;width: 50%;font-family: malgun gothic">
                          <h4  style="color: #59abe3">我該如何開始？</h4>
                          <p>您可以在沒有帳戶的情況下在4U4U上搜索和瀏覽所有廣告，
                            但是您需要註冊（免費）才能發佈廣告或聯繫其他用戶。<a href="register.html">現在免費註冊。</a></p> 
                          <h4 style="color: #59abe3">如何發佈廣告？</h4>
                          <p>要在4U4U上發佈廣告，請在主選單中單擊“發佈廣告”。要宣傳房間，請選擇第一個類別“出租廣告”。
                            要找尋房間及室友，請選擇第二個類別“找房廣告”，並填寫詳細訊息，以使人們知道您要尋找的內容。</p>
                          <h4 style="color: #59abe3">如何修改個人資料？</h4>
                          <p>請至帳戶管理頁面，點選修改個人資料。</p>
                          <h4 style="color: #59abe3">什麼是精選廣告？</h4>
                          <p>精選廣告在搜索結果頁上以紅色突出顯示，並且位於同一年齡的免費列表上方。
                            升級還包括“搶先體驗”功能，因此您可以聯繫所有其他用戶（那些廣告室或正在
                            尋找房間的用戶）。升級費用為7天XXXX台幣-如果您擁有多個物業，則升級還
                            可以讓您通過購買額外的房源來管理多個廣告。</p>
                          <h4 style="color: #59abe3">如何將照片上傳到廣告中？</h4>
                          <p>所有用戶最多可以免費添加9張照片到他們的廣告中。您需要登錄到您的帳戶才能執行此操作。<br>

                              運作方式如下：<br>
                              
                              通過選擇頁面頂部導航欄中的“我的帳戶”，然後選擇“我的廣告”，找到您要添加照片的廣告。
                              （您當前廣告的參考編號和標題將在頁面中間列出，並在其下方顯示一些藍色文字）
                              從藍色選項列表中選擇“照片”。然後，您將被帶到照片上傳屏幕
                              點擊“瀏覽”按鈕。這將打開一個彈出窗口，您可以在其中找到並選擇要添加的照片
                              突出顯示要添加的照片，然後單擊“打開”。這將用文本填充瀏覽按鈕旁邊的框
                              點擊“上傳”按鈕
                              上傳後，照片將在頁面下方顯示為小圖像。
                              上傳完所有照片後，您可以更改它們的顯示順序或添加標題。要更改照片的順序，請單擊並按住照片並將其拖到首選位置。</p>
                          <h4 style="color: #59abe3">如何修改廣告？</h4>
                          <p>要修改廣告，請執行以下操作：

                              1.登錄並轉到“帳戶管理”底下的“我的廣告”。<br>
                              2.在“我的已發布廣告”屏幕上，您會看到一個灰色框，標題為我的提供/想要的廣告，帶有廣告的參考編號
                              和標題。<br>
                              3.選擇“編輯”。<br>
                              4.下一頁是“編輯”屏幕。您將能夠更改由編輯框包圍的所有信息。完成所有更改後，滾動到頁面底部。<br>
                              
                              5.選擇“保存”。<br>
                              
                              然後，您的更改將在90分鐘內在網站上生效。</p>
                          <h4 style="color: #59abe3">我需要付什麼錢嗎？</h4>
                          <p>任何人都可以完全免費使用4U4U。<BR>

                              如果您正在尋找一個房間並希望立即與所有廣告聯繫，則可以選擇升級以儘早使用鳥類。如果您選擇不升級，則仍然可以聯繫7天以上的精選廣告和廣告。7天后可以免費聯繫早鳥廣告。
                              <BR>
                              免費廣告在列表中顯示為灰色。升級後的用戶可以立即與您聯繫-廣告發布7天后，其他所有人都可以與您聯繫。
                              <BR>
                              如果您選擇升級，則意味著您的廣告將在列表中顯示為藍色粗體廣告，並且每個人都可以立即與您聯繫。您可以立即與任何房間內想要的廣告聯繫，因為升級也可以讓您早起購票。</p>
                          <h4 style="color: #59abe3">為什麼我還沒有收到回复？</h4>
                          <p>有些用戶檢查電子郵件和/或登錄4U4U的頻率比其他用戶少-您應該耐心等待，至少需要48小時才能回复。
                             <BR>
                              如果用戶出於任何原因決定您不適合該房間，則他們可能會選擇不做出響應，以免感到冒犯。我們嘗試通過提供自動拒絕響應來使人們易於響應。
                              <BR>
                              我們無法控制的其他原因可能是用戶提供了錯誤的電子郵件地址或房間被出租。我們會定期發送提醒，讓用戶很容易告訴我們何時佔用房間，但並非所有人都能及時刪除廣告。我們一直在尋找更好的方法來更早地刪除廣告，以解決此問題。</p>
                          </span>  
                      </div>  
                    </div>
 </section>               
                 
                                
                 
                                

 <jsp:include page="../footer.jsp"></jsp:include>

  <!-- jQuery library -->
  <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
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
	$(document).ready(function(){
	$('#iframe').remove();
		
	 setTimeout(()=>{
         
			$('#iframeControl').append(`<iframe id="iframe" width="100%" height="450"   style="border:0" src="https://www.google.com.tw/maps?output=embed&q=國立臺北科技大學"></iframe>`)

     },300);	
	
	
		
	});
  
  
</script>
  </body>
</html>