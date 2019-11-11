<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">


<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>4U4U | 我的訊息</title>


<!-- Favicon -->
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/img/icon/4U4U_final.png"
	type="image/x-icon">

<!-- Font awesome -->
<link
	href="${pageContext.request.contextPath}/css/fontawesome-free-5.11.2-web/css/all.css"
	rel="stylesheet">
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



<link href="${pageContext.request.contextPath}/css/message.css"
	rel="stylesheet">

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

	<section id="aa-property-header" class="message"
		style="margin-bottom: 65px;">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="aa-property-header-inner">
						<h2>我的訊息</h2>
						<br>

					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- End Proerty header  -->


	<c:if test='${totalNum==0}'>
		<div id="noTextDiv" class="text-center"
			style="height: 500px; clear: both; margin-bottom: 65px;">
			<div style="color: red; font-size: 200px;">
				<i class="fas fa-comment-slash"></i>
			</div>
			<span
				style="line-height: 100px; margin: 0 auto; font-size: 40px; color: blue">您目前無任何訊息</span>
		</div>
	</c:if>
	<c:if test='${totalNum!=0}'>


		<div id="frame" style="margin-bottom: 65px;">

			<div id="sidepanel">

				<div id="contacts">
					<ul>

						<c:forEach var='map' varStatus='vs' items='${contactsMap}'>
							<li class="contact">
								<div class="wrap">

									<div class="img"
										style="background: url('${pageContext.request.contextPath}/_4u4u/getImage?id=${map.id}&type=MEMBER');"></div>
									<div class="meta">
										<p class="time_date">${map.dateTime}</p>
										<p class="name" id="name${map.id}">${map.name}</p>
										<span class="new-message" id="new-message${map.id}">${map.count}</span>

										<p class="preview" id="preview${map.id}">${map.content}</p>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>

			</div>

			<div class="content" style="background-color: #E0E0E0">
				<div id="chatDiv" class="text-center"
					style="background-color: #fff; font-size: 40px;">
					<br>
					<br> <img
						src="${pageContext.request.contextPath}/img/chat.png" width="200">
					<br>
					<div>開始聊天吧</div>
					<br>
					<br>
					<br>
					<br>
				</div>
				<div class="contact-profile">
					<p id="comparePart"></p>
				</div>
				<div class="messages">
					<ul id="messagesUl">


					</ul>
				</div>
				<div class="message-input" id="messageInput">
					<div class="wrap">
						<!-- 				置底按鈕 -->
						<a class="scrollToBottom" href="#"><i
							class="fa fa-angle-double-down"></i></a> <input type="text"
							placeholder="輸入訊息..." id="inputArea" />

						<button class="submit">
							<i class="fa fa-paper-plane" aria-hidden="true"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	</c:if>





	<jsp:include page="../footer.jsp"></jsp:include>





	<!-- jQuery library -->
	<!-- 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->

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
	<!-- socket part -->

	<script type="text/javascript">
    
    $(document).ready(function(){
    	$('.contact').each(function(){
    		let html= $(this).html();
        	let id = html.substring(html.indexOf('?')+1,html.indexOf('&')).substring(3);
    		if($('#new-message'+id).text()=='0'){
    			$('#new-message'+id).hide();
    		}
    		
    	})		
    	$('#messageInput').hide();
    	
    	$('.contact').on('click',function(){
    	
    	 
    	let html= $(this).html();
    	let id = html.substring(html.indexOf('?')+1,html.indexOf('&')).substring(3);
    	let queryUrl = "/4u4u/historyMessages/"+id;
    	var msg = JSON.stringify({'command':'message', 'roomId':'allChannel' ,'name':id,'info':''});
        ws.send(msg);	
    		
    	$('#chatDiv').hide();
    	$('#messageInput').show();
    	$('#new-message'+id).hide();
    	$('.contact').removeClass('active');
    	$('#comparePart').html('<i class="fas fa-user-circle"></i>'+$('#name'+id).text());
    	$('#comparePart').attr('userId',id);
    	$('#messagesUl').html(null);
    	$(this).addClass('active');
     	 $.ajax({
	            type: "GET",
	            url: queryUrl,
	            data: {},
	            dataType: "json",
	            success: function (response) {
	             for(let i=0;i<response.length;i++){
	            	 if(response[i]['class']=='sent'){
	            		 $('#messagesUl').append(`
	            				  <li class="sent">
	            	 				<div class="img"
	            	 					style="background: url(${pageContext.request.contextPath}/_4u4u/getImage?id=`+response[i]['id'] +`&type=MEMBER);"></div>
	            	 				<p>`+response[i]['content']+`</p> <span class="time_date">
	            	 				`+response[i]['dateTime']+` </span>
	            	 			</li>						 
	            				 `)
	            		 
	            	 }else{
	            		 
	            		 apiData = `<li class="replies">
         	 				<div class="row">
     	 					<div class="col-md-6"></div>
     	 					<div class="col-md-6 col-sm-12 col-xs-12">
     	 						<p>`+response[i]['content']+`</p>
     	 						<div class="text-right">
     	 							<div class="row">
     	 								<div class="col-md-12 col-sm-12 col-xs-12">`;
     	 					if(response[i]['isRead']=='yes'){
     	 						apiData+= `<span class="have-read">已讀</span> <span class="time_date ">
										`+response[i]['dateTime'] +`</span>
	 								</div>
	 							</div>
	 						</div>
	 					</div>
	 				</div>
	 			</li>`;
     	 					}else{
     	 						apiData+= ` <span class="time_date ">
								`+response[i]['dateTime'] +` </span>
 								</div>
 							</div>
 						</div>
 					</div>
 				</div>
 			</li>`;
     	 					}			
	            		 $('#messagesUl').append(apiData);
	            		 
	            		 
	            		 
	            	 }
	            	 
				}	 
	            let bottom =   $(document).height()*10000;
	          	$(".messages").animate({ scrollTop: bottom }, "fast");
	             
    		 } 		
     	 })
    	})
    })
	            
	
	            
			
			
    		
 
    	
    	
  
    </script>

	<script type="text/javascript">
	$(document).ready(function(){
		
		$.ajax({
            type: "GET",
            url: "/4u4u/loginCheck",
            data: {},
            dataType: "json",
            success: function (response) {
            	if(response.result=='false'){
            		return;
            	}
            	if(response.result=='true'){
            		userId  = response.userId;
 ws = new WebSocket('ws://192.168.43.252:8080/4u4u/webSocket/INFO={"command":"enter","name":"'+ userId + '","roomId":"allChannel"}');
		        
            	  ws.onopen = WSonOpen;
                  ws.onmessage = WSonMessage;
                  ws.onclose = WSonClose;
            	
            	}
            }
		
		});
		
		 function WSonOpen() {
	            var msg = JSON.stringify({'command':'enter', 'roomId':'allChannel' , 'name': "all",
	                'info': userId + " join chatRoom"})
	            ws.send(msg);
		 
		 
		 };

	        function WSonMessage(event) {
	        	
	        	let url  = window.location.href;
	        
	             info = event.data.split(':')[2];
	        	
	        	if(event.data.includes('私訊')&& url.includes('myMessage')){
	        		let compareId = $.trim(event.data.split(':')[0]);
	        		
	        		 if($('#comparePart').attr('userId')!=undefined && event.data.includes('私訊自己')){
	        			 if($.trim(info)==''){
	        				 return;
	        			 }
	        			 
	        			 let id = $('#comparePart').attr('userId');
	        			 let queryUrl = "/4u4u/historyMessages/"+id;
	        	    	$('#messagesUl').children().each(function(){
	        	    		$(this).remove();
	        	    	})
	        	    	$('#messagesUl').html("");
	        	    	setTimeout(()=>{
	        	    		 $.ajax({
		        		            type: "GET",
		        		            url: queryUrl,
		        		            data: {},
		        		            dataType: "json",
		        		            success: function (response) {
		        		             for(let i=0;i<response.length;i++){
		        		            	 if(response[i]['class']=='sent'){
		        		            		 $('#messagesUl').append(`
		        		            				  <li class="sent">
		        		            	 				<div class="img"
		        		            	 					style="background: url(${pageContext.request.contextPath}/_4u4u/getImage?id=`+response[i]['id'] +`&type=MEMBER);"></div>
		        		            	 				<p>`+response[i]['content']+`</p> <span class="time_date">
		        		            	 				`+response[i]['dateTime']+` </span>
		        		            	 			</li>						 
		        		            				 `)
		        		            		 
		        		            	 }else{
		        		            		 
		        		            		 apiData = `<li class="replies">
		        	         	 				<div class="row">
		        	     	 					<div class="col-md-6"></div>
		        	     	 					<div class="col-md-6 col-sm-12 col-xs-12">
		        	     	 						<p>`+response[i]['content']+`</p>
		        	     	 						<div class="text-right">
		        	     	 							<div class="row">
		        	     	 								<div class="col-md-12 col-sm-12 col-xs-12">`;
		        	     	 					if(response[i]['isRead']=='yes'){
		        	     	 						apiData+= `<span class="have-read">已讀</span> <span class="time_date ">
		        											`+response[i]['dateTime'] +`</span>
		        		 								</div>
		        		 							</div>
		        		 						</div>
		        		 					</div>
		        		 				</div>
		        		 			</li>`;
		        	     	 					}else{
		        	     	 						apiData+= ` <span class="time_date ">
		        									`+response[i]['dateTime'] +` </span>
		        	 								</div>
		        	 							</div>
		        	 						</div>
		        	 					</div>
		        	 				</div>
		        	 			</li>`;
		        	     	 					}			
		        		            		 $('#messagesUl').append(apiData);
		        		            		 
		        		            		 
		        		            		 
		        		            	 }
		        		            	 
		        					}	 
		        		            let bottom =   $(document).height()*10000;
		        		          	$(".messages").animate({ scrollTop: bottom }, "fast");
		        		             
		        	    		 } 		
		        	     	 })
		        			
	        	    	},300);
	        	     	
	        			
	        			
	        		}else if($('#comparePart').attr('userId')==undefined){
	        			 if($.trim(info)==''){
	        				 return;
	        			 }
	        			
	        			if($('#noTextDiv').length==1){
	        				setTimeout(() => {
		            			 $('#modalTitle').text('您有新訊息');
				 		            $('#myModal').modal('show')
				 		        }, 100);
				 		        
				 		        setTimeout(() => {
				 		            $('#myModal').modal('hide')
				 		           window.location.href =url;
				 		        }, 2000);
				 		        
				 		      
	    	        		
	    	        		return;
	    	        	}
	        			let id = $.trim(event.data.split(':')[0]);
	        			let ajaxUrl = "/4u4u/getNewMessageCount/"+id;
        				$.ajax({
        		            type: "GET",
        		            url: ajaxUrl,
        		            data: {},
        		            dataType: "json",
        		            success: function (response) {
        		            	$('#new-message'+id).text(response.result);
        		            	$('#new-message'+id).show();
        		            	if($.trim(info)!=""){
        		        		$('#preview'+compareId).text(info);
        		            	}
        		            }
        				});
        			}else if($('#comparePart').attr('userId')!=undefined && $('#comparePart').attr('userId')!=compareId){
	        			if($.trim(info)==""){
	        				return;
	        			}
        				let id = $.trim(event.data.split(':')[0]);
	        			let ajaxUrl = "/4u4u/getNewMessageCount/"+id;
	        			$.ajax({
	        		            type: "GET",
	        		            url: ajaxUrl,
	        		            data: {},
	        		            dataType: "json",
	        		            success: function (response) {
	        		            	$('#new-message'+id).text(response.result);
	        		            	$('#new-message'+id).show();
	        		            	if($.trim(info)!=""){
	            		        		$('#preview'+compareId).text(info);
	            		            	}
	        		            }
	        				});
	        		}else{
	        			 let id = $('#comparePart').attr('userId');
	        			let queryUrl = "/4u4u/historyMessages/"+id;
	        			$('#messagesUl').children().each(function(){
	        	    		$(this).remove();
	        	    	})
	        	    	$('#messagesUl').text("");
	        			setTimeout(()=>{
	        	     	 $.ajax({
	        		            type: "GET",
	        		            url: queryUrl,
	        		            data: {},
	        		            dataType: "json",
	        		            success: function (response) {
	        		             for(let i=0;i<response.length;i++){
	        		            	 if(response[i]['class']=='sent'){
	        		            		 $('#messagesUl').append(`
	        		            				  <li class="sent">
	        		            	 				<div class="img"
	        		            	 					style="background: url(${pageContext.request.contextPath}/_4u4u/getImage?id=`+response[i]['id'] +`&type=MEMBER);"></div>
	        		            	 				<p>`+response[i]['content']+`</p> <span class="time_date">
	        		            	 				`+response[i]['dateTime']+` </span>
	        		            	 			</li>						 
	        		            				 `)
	        		            		 
	        		            	 }else{
	        		            		 
	        		            		 apiData = `<li class="replies">
	        	         	 				<div class="row">
	        	     	 					<div class="col-md-6"></div>
	        	     	 					<div class="col-md-6 col-sm-12 col-xs-12">
	        	     	 						<p>`+response[i]['content']+`</p>
	        	     	 						<div class="text-right">
	        	     	 							<div class="row">
	        	     	 								<div class="col-md-12 col-sm-12 col-xs-12">`;
	        	     	 					if(response[i]['isRead']=='yes'){
	        	     	 						apiData+= `<span class="have-read">已讀</span> <span class="time_date ">
	        											`+response[i]['dateTime'] +`</span>
	        		 								</div>
	        		 							</div>
	        		 						</div>
	        		 					</div>
	        		 				</div>
	        		 			</li>`;
	        	     	 					}else{
	        	     	 						apiData+= ` <span class="time_date ">
	        									`+response[i]['dateTime'] +` </span>
	        	 								</div>
	        	 							</div>
	        	 						</div>
	        	 					</div>
	        	 				</div>
	        	 			</li>`;
	        	     	 					}			
	        		            		 $('#messagesUl').append(apiData);
	        		            		 
	        		            		 
	        		            		 
	        		            	 }
	        		            	 
	        					}	 
	        		            let bottom =   $(document).height()*10000;
	        		          	$(".messages").animate({ scrollTop: bottom }, "fast");
	        		             
	        	    		 } 		
	        	     	 })
	        			},300);
	        	     	if($.trim(info)!=""){
    		        		$('#preview'+compareId).text(info);
    		            	}
	        			
	        		}
	        	}else{
	        		
	        		console.log(event.data);
	        	}
	        };

	        function WSonClose() {
	        	console.log(userId+"斷線");
	        };

	        function WSonError() {
	        };

	        
	        
	        $('.submit').on('click',function() {
	        	let id = $('#comparePart').attr('userId');
	        	let message = $(".message-input input").val();
	         	 if($.trim(message) == '') {
	         		return;
	         	 }
	         	 var msg = JSON.stringify({'command':'message', 'roomId':'allChannel' ,'name':id,'info':''+message});
                 ws.send(msg);
                 $('#inputArea').val("");
                 $('#preview'+id).text(message);
	       	});

	       	$('#inputArea').on('keydown', function(e) {
	       	  
	       		let id = $('#comparePart').attr('userId');
	       		
	       		if (e.which == 13) {
	       		  
	       		let message = $(".message-input input").val();
	         	 if($.trim(message) == '') {
	         		return;
	         	 }
	         	 var msg = JSON.stringify({'command':'message', 'roomId':'allChannel' ,'name':id,'info':''+message});
                 ws.send(msg);
                 $('#inputArea').val("");
                 $('#preview'+id).text(message);
	       	  }
	       	});
		
	})
	</script>

	<script type="text/javascript">
    
    $(document).ready(function(){
    	  let bottom =   $(document).height()*10000;
    	  $(".messages").animate({ scrollTop: bottom }, "fast");

    <%//     	$("#profile-img").click(function() {
			// 			    	 $("#status-options").toggleClass("active");
			// 			    	});

			// 			    	$(".expand-button").click(function() {
			// 			    	  $("#profile").toggleClass("expanded");
			// 			    	 $("#contacts").toggleClass("expanded");
			// 			    	});

			// 			    	$("#status-options ul li").click(function() {
			// 			    	 $("#profile-img").removeClass();
			// 			    	 $("#status-online").removeClass("active");
			// 			    	 $("#status-away").removeClass("active");
			// 			    	 $("#status-busy").removeClass("active");
			// 			    	 $("#status-offline").removeClass("active");
			// 			    	 $(this).addClass("active");

			// 			    	 if($("#status-online").hasClass("active")) {
			// 			    	  $("#profile-img").addClass("online");
			// 			    	 } else if ($("#status-away").hasClass("active")) {
			// 			    	  $("#profile-img").addClass("away");
			// 			    	 } else if ($("#status-busy").hasClass("active")) {
			// 			    	  $("#profile-img").addClass("busy");
			// 			    	 } else if ($("#status-offline").hasClass("active")) {
			// 			    	  $("#profile-img").addClass("offline");
			// 			    	 } else {
			// 			    	  $("#profile-img").removeClass();
			// 			    	 };

			// 			    	 $("#status-options").removeClass("active");
			// 			    	});
			//     	function newMessage() {
			//     	 message = $(".message-input input").val();
			//     	 if($.trim(message) == '') {
			//     	  return false;
			//     	 }
			//     	 $('<li class="replies"><p>' + message + '</p></li>').appendTo($('.messages ul'));
			//     	 $('.message-input input').val(null);
			//     	 $('.contact.active .preview').html('<span>You: </span>' + message);
			//     	  let bottom = $(document).height()*10000;
			//     	 $(".messages").animate({ scrollTop: bottom}, "fast");
			//     	};

			//     	$('.submit').click(function() {
			//     	  newMessage();
			//     	});

			//     	$(window).on('keydown', function(e) {
			//     	  if (e.which == 13) {
			//     	    newMessage();
			//     	    return false;
			//     	  }
			//     	});%>  	
    	
    	$('.scrollToBottom').hide();
    	var scrollTopBegin=0;
    	var scrollTopEnd=0;
    	$('.messages').on('scrollstart', function() {

    	    scrollTopBegin = $('.messages').scrollTop();

    	    return;
    	  })
    	  sessionStorage.count = 0;
    	  $('.messages').on('scrollend', function() {
    	   
    	    scrollTopEnd = $('.messages').scrollTop();
    	    
    	    sessionStorage.bottom = scrollTopBegin+10;
    	    if(sessionStorage.count=='0'){
    	      sessionStorage.count=1;
    	      return;
    	    }
    	    if(scrollTopBegin-scrollTopEnd>50){
    	        
    	        jQuery('.scrollToBottom').show();
    	        return;
    	      } else {
    	      jQuery('.scrollToBottom').hide();
    	      return;
    	      }
    	      
    	  })

    	  
    	  
    	  var lastScrollAt = Date.now()
    	    , timeout

    	  function scrollStartStop() {
    	      var $this = $(this)
    	    
    	      if (Date.now() - lastScrollAt > 100)
    	          $this.trigger('scrollstart')
    	        
    	      lastScrollAt = Date.now()
    	    
    	      clearTimeout(timeout)
    	    
    	      timeout = setTimeout(function() {
    	          if (Date.now() - lastScrollAt > 99)
    	          $this.trigger('scrollend')
    	      }, 100)
    	  }
    	  
    	  $('.messages').on('scroll', scrollStartStop)
    	  

    	     
    	    //Click event to scroll to top
    	    
    	    jQuery('.scrollToBottom').click(function(e){
    	      e.preventDefault();
    	      $(this).hide();
    	     let Bottom = $(document).height()*1000;
    	      jQuery('.messages').animate({scrollTop : Bottom},800);
    	      return false;
    	    });
    });


    
    </script>


</body>
</html>