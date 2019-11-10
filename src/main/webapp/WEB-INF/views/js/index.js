		$(document).ready(function() {
			
			$.ajax({
				url : '/4u4u/_4u4u/controller/ProcessSearchRoomRentVip.do', // 要呼叫的網址列或檔案
				type : 'GET', // GET或POST
				dataType : 'json',
				success : function(data) {
						for(let i=0;i<data.length;i++){
							let tempId = 0;
							if(data[i].adStyle==false){
							 tempId = "saveAd0"+data[i].adId;
							}else{
							 tempId = "saveAd1"+data[i].adId;	
							}
							apiData = `<div class="col-md-4 col-sm-6 col-xs-12"><article class="aa-properties-item">`;
	                        if(data[i].adImages.length==0){
	                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/4u4u/img/NoImage.png" alt="img"></a>`;
	                        }else{
	                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/disksource/roomRentAd/`+data[i].adImages[0]+`" alt="img"></a>`;
	                        }
	                        apiData+=`<div class="aa-tag for-rent">精選</div><div class="aa-properties-item-content"><a href="#"  id=`+ tempId +` state="儲存廣告" data-toggle="tooltip" title="儲存" class="property-favorite" ><i class="fa fa-heart-o"></i></a><div class="aa-properties-info"><span>`+data[i].adRentType+`</span>  <span>`+data[i].roomType+`</span></div>` ;       

	                        apiData+=`<div class="aa-properties-about text-center"><h3><a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`">`+data[i].adTitle+`</a></h3><p>`+data[i].adAddress+`</p><span class="aa-price">
	                      		  租金: `+data[i].adRentPrice+`元/月</span></div><div class="aa-properties-detial">`;
	                      		  
	                      		  if(data[i].contactState=='2'){
		                        	 apiData+=	` <span class="aa-price">
	                      <i class="fa fa-comments fa-lg" aria-hidden="true" style="color: green"></i>可直接聯繫
	                    </span> `
										}else{
									apiData+=	`  <span class="aa-price demo2" data-placement="bottom" title="一般會員需等廣告發佈7天後才能直接聯繫,如需立即聯繫必先升級為VIP會員。" >
	                      <i class="fas fa-crow fa-lg" style="color: orange"></i>早鳥體驗
							
	                    </span>`		
										}
											
	                      		apiData+=		`<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`"
												class="aa-secondary-btn">了解更多</a>
										</div>
									</div>
								</article>
							</div>`;
							$('#indexAds').append(apiData);
							$('span').css('fontSize','20px');
							$('p').css('fontSize','18px');
							$('[data-toggle="tooltip"]').tooltip();
							$(".demo2").tooltip();
							$('#'+tempId).on('click',function(e){
							    e.preventDefault();
								let adType = tempId.substring(6,7);
								let adpk = tempId.substring(7);
								if($(this).attr('state')=='儲存廣告'){// 1:已儲存
																	// 0:為儲存
								    
									$.ajax({
								            type: "GET",
								            url: "/4u4u/saveAd",
								            data: {adStyle:adType,
								            	   adId:adpk,
								            		},
								            
								            dataType: "text",
								            success: function (response) {
								            	if(response=='需要登入'){
								            		 setTimeout(() => {
								            			 $('#modalTitle').text('您需要登入，才能儲存廣告喔!!');
										 		            $('#myModal').modal('show')
										 		        }, 100);
										 		        
										 		        setTimeout(() => {
										 		            $('#myModal').modal('hide')
										 		        }, 3000);
								            		
								            		return;
								            	}
								            	if(response=='取消儲存廣告'){
								            		
								            		$('#'+tempId).attr('state','取消儲存廣告');
								            		$('#'+tempId).addClass('active');
								            	}
								            	if(response=='同一人'){
								            		 setTimeout(() => {
								            			 $('#modalTitle').text('您為廣告發布人無法使用此功能');
										 		            $('#myModal').modal('show')
										 		        }, 100);
										 		        
										 		        setTimeout(() => {
										 		            $('#myModal').modal('hide')
										 		        }, 3000);
								            		
									            }
								            	
								            	if(response=='錯誤'){
								            		
								            
								            	}
								            	
								            	
								            }
								        });
								    }else{
								        $.ajax({
								            type: "GET",
								            url: "/4u4u/cancelSavedAd",
								            data: {adStyle:adType,
								            	   adId:adpk,
						            		},
								            dataType: "text",
								            success: function (response) {
								            	
								            	if(response=='儲存廣告'){
								            		
								            		$('#'+tempId).attr('state','儲存廣告');
								            		$('#'+tempId).removeClass('active');
								            	}
								            	if(response=='錯誤'){
								            		
										        }
										            	
								            }
								        });
								    }	
							});
						}
						
						for(let i=0;i<data.length;i++){
							let tempId = 0;
							if(data[i].adStyle==false){
							 tempId = "saveAd0"+data[i].adId;
							}else{
								 tempId = "saveAd1"+data[i].adId;	
							}
							let adType = tempId.substring(6,7);
							let adpk = tempId.substring(7);
							 $.ajax({
						            type: "GET",
						            url: "/4u4u/checkButtonState",
						            data: {adStyle:adType,
						            	   adId:adpk,
				            		},
						            dataType: "json",
						            success: function (response) {
						            	if(response==""){
						            		 return;
						            	}else{
						            		if(response[0]=='儲存廣告'){
						            		$('#'+tempId).attr('state','儲存廣告');
						            		$('#'+tempId).removeClass('active');
						            		}else{
						            			$('#'+tempId).attr('state','取消儲存廣告');
							            		$('#'+tempId).addClass('active');
						            		}
						            	}
						                
						            }
						        });
						}
				}, 

			});// ajax bracket end
			
			
			$.ajax({
				url : '/4u4u/_4u4u/controller/ProcessSearchFindRoomVip.do', // 要呼叫的網址列或檔案
				type : 'GET', // GET或POST
				dataType : 'json',
				success : function(data) {	
		for(let i=0;i<data.length;i++){
			let tempId = 0;
			tempId = "saveAd1"+data[i].adId;
				apiData = `
				 <div class="col-md-4 col-sm-6 col-xs-12">
					<article class="aa-properties-item">`;
                if(data[i].adImages.length==0){
                    apiData+= `<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/4u4u/img/NoImage.png" alt="img"></a>`;
                }else{
                    apiData+= `<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img
							src="/disksource/wantedRoomAd/`+data[i].adImages[0]+`" alt="img"></a>`;
                }
                
                if(data[i].memberState=='1'){
                	 apiData+=`<div class="aa-tag " style="background-color:green">一般</div>`;
                }else{
                	  apiData+=`<div class="aa-tag for-rent">精選</div>`;
                }
                
                if(data[i].age!=null && data[i].age!=""){
                apiData+=`<div class="aa-properties-item-content">
                	<a href="#" class="property-favorite " id=`+ tempId +` state="儲存廣告" data-toggle="tooltip" title="儲存" ><i class="fa fa-heart-o"></i></a>
							<div class="aa-properties-info">
								<span>`+data[i].name+`</span>  <span>`+data[i].job+`</span> <span>`+data[i].age+`</span><span>`+data[i].peopleNumGender+`</span>
							</div>` ;       
                }else{
                	 apiData+=`<div class="aa-properties-item-content">
                	 	<a href="#" class="property-favorite " id=`+ tempId +` state="儲存廣告" data-toggle="tooltip" title="儲存"  ><i class="fa fa-heart-o"></i></a>
								<div class="aa-properties-info">
									<span>`+data[i].name+`</span>  <span>`+data[i].job+`</span><span>`+data[i].peopleNumGender+`</span>
								</div>` ;
                }
                apiData+=`<div class="aa-properties-about text-center">
								<h3>
									<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`">`+data[i].adTitle+`</a>
									
								</h3>
									<p>`+data[i].wantedRoomType+`</p>
									<span class="aa-price">`+data[i].budget+` &nbsp;元/月</span>
								
							</div>
							<div class="aa-properties-detial">`;
                if(data[i].contactState=='2'){
                	 apiData+=	` <span class="aa-price">
          <i class="fa fa-comments fa-lg" aria-hidden="true" style="color: green"></i>可直接聯繫
        </span> `
						}else{
					apiData+=	`  <span class="aa-price demo2" data-placement="bottom" title="一般會員需等廣告發佈7天後才能直接聯繫,如需立即聯繫必先升級為VIP會員。" >
          <i class="fas fa-crow fa-lg" style="color: orange"></i>早鳥體驗
			
        </span>`		
						}
				apiData+=`<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`"
									class="aa-secondary-btn">了解更多</a>
							</div>
						</div>
					</article></div>
				`;
				$('#findHomeAds').append(apiData)	;	
				$('span').css('fontSize','20px');
				$('p').css('fontSize','18px');
				$('[data-toggle="tooltip"]').tooltip();
				$(".demo2").tooltip();
				$('#'+tempId).on('click',function(e){
				    e.preventDefault();
					let adType = tempId.substring(6,7);
					let adpk = tempId.substring(7);
					if($(this).attr('state')=='儲存廣告'){// 1:已儲存 0:為儲存
					    
						$.ajax({
					            type: "GET",
					            url: "/4u4u/saveAd",
					            data: {adStyle:adType,
					            	   adId:adpk,
					            		},
					            
					            dataType: "text",
					            success: function (response) {
					             	if(response=='需要登入'){
					            		 setTimeout(() => {
					            			 $('#modalTitle').text('您需要登入，才能儲存廣告喔!!');
							 		            $('#myModal').modal('show')
							 		        }, 100);
							 		        
							 		        setTimeout(() => {
							 		            $('#myModal').modal('hide')
							 		        }, 3000);
					            		
					            		return;
					            	}
					            	if(response=='取消儲存廣告'){
					            		
					            		$('#'+tempId).attr('state','取消儲存廣告');
					            		$('#'+tempId).addClass('active');
					            	}
					            	if(response=='同一人'){
					            		 setTimeout(() => {
					            			 $('#modalTitle').text('您為廣告發布人無法使用此功能');
							 		            $('#myModal').modal('show')
							 		        }, 100);
							 		        
							 		        setTimeout(() => {
							 		            $('#myModal').modal('hide')
							 		        }, 3000);
					            		
						            }
					            	
					            	if(response=='錯誤'){
					            		
					            	}
					            	
					            	
					            }
					        });
					    }else{
					        $.ajax({
					            type: "GET",
					            url: "/4u4u/cancelSavedAd",
					            data: {adStyle:adType,
					            	   adId:adpk,
			            		},
					            dataType: "text",
					            success: function (response) {
					            	
					            	if(response=='儲存廣告'){
					            		
					            		$('#'+tempId).attr('state','儲存廣告');
					            		$('#'+tempId).removeClass('active');
					            	}
					            	if(response=='錯誤'){
					            		
							        }
							            	
					            }
					        });
					    }	
				});
			}
			
			for(let i=0;i<data.length;i++){
				let tempId = 0;
				if(data[i].adStyle==false){
				 tempId = "saveAd0"+data[i].adId;
				}else{
					 tempId = "saveAd1"+data[i].adId;	
				}
				let adType = tempId.substring(6,7);
				let adpk = tempId.substring(7);
				 $.ajax({
			            type: "GET",
			            url: "/4u4u/checkButtonState",
			            data: {adStyle:adType,
			            	   adId:adpk,
	            		},
			            dataType: "json",
			            success: function (response) {
			            	if(response==""){
			            		 return;
			            	}else{
			            		if(response[0]=='儲存廣告'){
			            		$('#'+tempId).attr('state','儲存廣告');
			            		$('#'+tempId).removeClass('active');
			            		}else{
			            			$('#'+tempId).attr('state','取消儲存廣告');
				            		$('#'+tempId).addClass('active');
			            		}
			            	}
			                
			            }
			        });
			
			
			
			
			
			}

	}, // success bracket end

});// ajax bracket end
	
		});
		