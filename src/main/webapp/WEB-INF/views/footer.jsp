<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
	
		
	<div class="modal fade"  id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header text-center">
        <h3 class="modal-title"  id="modalTitle">您有新訊息，記得去查看您的留言喔</h3>
<%-- 
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
--%>
        <a type="button" class="btn btn-primary" data-dismiss="modal" >確定</a>
      </div>
<%--       
       <div class="modal-body text-center"> 
       	 <h4 id="modalDetail" >記得去我的留言查看</h4> 
       </div> 
       <div class="modal-footer"> 
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> 
      </div> 
--%>
    </div>
  </div>
</div>
		
		
	  <footer id="aa-footer">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
          <div class="aa-footer-area">
            <div class="row">
              <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="aa-footer-left">
                 <p>Copyright © 2019 4U4U Inc. All rights reserved</p>
                </div>
              </div>
  
              <div class="col-md-6 col-sm-6 col-xs-12">
                <div class="aa-footer-right">
                    <a href="<c:url value='/whyuse' />">為何使用</a>
                    <a href="<c:url value='/qa' />">常見問與答</a>
                    <a href="<c:url value='/contact' />">聯絡我們</a>
                </div>
              </div>            
            </div>
          </div>
        </div>
        </div>
      </div>
    </footer>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    
    <script type="text/javascript">
    $(document).ready(function() {
		let url = window.location.href;
		
		
    	if(!url.includes('sendMessage') && !url.includes('myMessage')){
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
           			
            	ws = new WebSocket('ws://localhost:8080/4u4u/webSocket/INFO={"command":"enter","name":"'+ userId + '","roomId":"allChannel"}');	
		        
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
	        	if(event.data.split(':')[2]==''){
	        		return;
	        	}
	        	let url  = window.location.href;
	        	if(event.data.includes('私訊')&& !url.includes('myMessage')){
	        		 setTimeout(() => {
	        			 $('#modalTitle').text('您有新訊息，記得去查看您的留言喔');
	 		            $('#myModal').modal('show')
	 		        }, 100);
	 		        
	 		        setTimeout(() => {
	 		            $('#myModal').modal('hide')
	 		        }, 3000);
	        	}else{
	        		
	        		console.log(event.data);
	        	}
	        };

	        function WSonClose() {
	        	console.log(userId+"斷線");
	        };

	        function WSonError() {
	        };

    	} 
	});
	
</script>
    