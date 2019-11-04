		$(document).ready(function() {
			
			if(window.location.href=='http://localhost:8080/4u4u/interestedAd'){
				defaultURL = 'http://localhost:8080/4u4u/interestedAd?adStyle=0&curPage=1';
				history.replaceState({ url: defaultURL }, "", defaultURL);
				sessionStorage.clear();
				sessionStorage.setItem('adStyle','0');
//				sessionStorage.setItem('curPage','1');
				window.setTimeout(getInterestedRoomRentAdsAJAX,100);
			}else {
				if(sessionStorage.adStyle=="0"){
					getInterestedRoomRentAdsAJAX();
				}else{
					getInterestedWantedRoomAdsAJAX();
				}
					storage = sessionStorage;
					if(storage.adStyle==null){
						// 重新定向
					window.location.replace("http://localhost:8080/4u4u/interestedAd");
					return;
					}
			}
			
// window.onpopstate = function(event){
//					
// if(sessionStorage.searchType=='0'){
// window.setTimeout(getInterestedRoomRentAdsAJAX,300);
//						
// }else{
// window.setTimeout(getInterestedWantedRoomAdsAJAX,300);
// }
					// alert(event.state.url)
					// alert("location: " + document.location + ", state: " +
					// JSON.stringify(event.state));
// };
			
				storage = sessionStorage;				
				
				$('#roomRentAd,#wantedRoomAd').on('click',function(e){
					e.preventDefault();
					let preAdStyle = sessionStorage.adStyle;
					if($(this).attr('href')==preAdStyle){
						return;
					}
					let tempCurPage = sessionStorage.curPage;
					sessionStorage.removeItem("curPage");
					let  tempPageURL = window.location.href;
					tempPageURL = tempPageURL.replace('curPage='+$.trim(tempCurPage), 'curPage=1');
				    history.replaceState({ url: tempPageURL }, "", tempPageURL);
					sessionStorage.setItem('adStyle',$(this).attr('href'));
					let  tempURL = window.location.href;
					let  postAdStyle = $(this).attr('href');
					if(postAdStyle==0){
						$('#roomRentAd').addClass('active');
						$('#wantedRoomAd').removeClass('active');
						window.setTimeout(getInterestedRoomRentAdsAJAX,200);
					}else{
						$('#wantedRoomAd').addClass('active');
						$('#roomRentAd').removeClass('active');
						window.setTimeout(getInterestedWantedRoomAdsAJAX,200);
					}
					let finalURL = tempURL.replace('adStyle='+$.trim(preAdStyle), 'adStyle='+$.trim(postAdStyle));
					history.replaceState({ url: finalURL }, "", finalURL);
				})
				
				
				
				window.setTimeout(refreshPage,500);	
				
				
				 
					  
				
				
				
				
				
				
				
				function getInterestedRoomRentAdsAJAX(){
					$.ajax({
					url : window.location.href, // 要呼叫的網址列或檔案
					type : 'POST', // GET或POST
					data: "",
					dataType : 'json',
					success : function(data) {
					
						// pagination part
						$('#pagination').children().each(function(){
							$(this).remove();
						})
						if(data.length==0){
							if(sessionStorage.curPage==null){
								totalPages = 1;	
								}
								$('#emptyDiv').show();
								if(sessionStorage.curPage!=null&& sessionStorage.curPage!=1 ){
									
								let tmpCurPage = parseInt(sessionStorage.getItem('curPage'));
								let tmpTotalPages = parseInt(sessionStorage.totalPages)-1;
								
								if(tmpCurPage > tmpTotalPages){
									sessionStorage.setItem('curPage',tmpTotalPages);
									if(tmpTotalPages>=5){
										sessionStorage.setItem('rangeEnd',tmpTotalPages);
										sessionStorage.setItem('rangeStart',tmpTotalPages-4);
									}
									let  tempURL = window.location.href;
									tempURL = tempURL.replace('curPage='+$.trim(tmpCurPage), 'curPage='+tmpTotalPages);
								    history.replaceState({ url: tempURL }, "", tempURL);
								    getInterestedRoomRentAdsAJAX();
								    
								    return;
								}
								}
						}else{
							totalPages = data[0].totalPages;
							$('#emptyDiv').hide();
						}
						
						 pageHtml =`<li id="previousPage"><a href="#" aria-label="Previous" onclick="return false;"> <span aria-hidden="true">&laquo;</span></a></li>`;
						 if(sessionStorage.rangeEnd!=null){
								let tempRangeEnd= parseInt(sessionStorage.rangeEnd);
								
								if(tempRangeEnd>5){
									 for(let i = tempRangeEnd-4;i<=totalPages;i++){
											
											if(i>tempRangeEnd){
											pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
											}else{
											pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
						
											}
											
										}
								}else{
									for(let i = 1;i<=totalPages;i++){
										if(i==1){
											pageHtml+=	'<li id="page'+i+'" class="active"><a href="#" >'+i+'</a></li>';
											continue;
										}
										if(i>5){
										pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
										}else{
										pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
					
										}
										
										}
									
								}						
								
							}else{
							 for(let i = 1;i<=totalPages;i++){
								if(i==1){
									pageHtml+=	'<li id="page'+i+'" class="active"><a href="#" >'+i+'</a></li>';
									continue;
								}
								if(i>5){
								pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
								}else{
								pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
			
								}
								
								}
							}
						
						pageHtml+=	`<li id="totalPage"><a  onclick="return false;"> <span aria-hidden="true">總頁數:`+totalPages+`</span></a></li>`;
						pageHtml+=	`<li id="nextPage" ><a href="#" aria-label="Next" onclick="return false;"> <span aria-hidden="true">&raquo;</span></a></li>`;
//	 					
						
						$('#pagination').append(pageHtml);
						
						if(sessionStorage.curPage==null){
							sessionStorage.totalPages=totalPages;
							sessionStorage.curPage=1;
							sessionStorage.rangeStart=1;
							sessionStorage.rangeEnd=5;
						}else{
							sessionStorage.totalPages=totalPages;
						}
						
							$('#pagination li').removeClass('active');
							$('#page'+sessionStorage.curPage).addClass('active');
						
						$('#pagination li').not('#totalPage').on('click',function(e){
							e.preventDefault();
							let curPage = parseInt(sessionStorage.curPage);
							let rangeStart = parseInt(sessionStorage.rangeStart);
							let rangeEnd = parseInt(sessionStorage.rangeEnd);
							
							if(parseInt(sessionStorage.totalPages)<=1){
								return;
							}

							if($(this).attr('id')=='previousPage' && sessionStorage.curPage!=1){
								if(curPage==rangeStart){
									$('#pagination li').removeClass('active');
									$('#page'+rangeEnd).hide();
									sessionStorage.rangeEnd=rangeEnd-1;
									let beforePage = sessionStorage.curPage;
									sessionStorage.curPage =curPage -1;
									let afterPage = sessionStorage.curPage;
									let  pageURL = window.location.href;
									pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
								    history.replaceState({ url: pageURL }, "", pageURL);
									sessionStorage.rangeStart = rangeStart -1;
									$('#page'+sessionStorage.curPage).show().addClass('active');
									window.setTimeout(getInterestedRoomRentAdsAJAX,300);
								}else{
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage -1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getInterestedRoomRentAdsAJAX,300);

								}
								return;
							}
							if($(this).attr('id')=='nextPage' && sessionStorage.curPage!= totalPages ){
								if(curPage==rangeEnd){
									$('#pagination li').removeClass('active');
									$('#page'+rangeStart).hide();
									sessionStorage.rangeEnd=rangeEnd+1;
									let beforePage = sessionStorage.curPage;
									sessionStorage.curPage =curPage +1;
									let afterPage = sessionStorage.curPage;
									let  pageURL = window.location.href;
									pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
								    history.replaceState({ url: pageURL }, "", pageURL);
									sessionStorage.rangeStart = rangeStart +1;
									$('#page'+sessionStorage.curPage).show().addClass('active');
									window.setTimeout(getInterestedRoomRentAdsAJAX,300);

								}else{
									
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage +1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getInterestedRoomRentAdsAJAX,300);

								}
								return;
							}
							if($(this).attr('id')!='previousPage'&&$(this).attr('id')!='nextPage'){
								if(sessionStorage.curPage == $(this).text()){
									return;
								}
								$('#pagination li').removeClass('active');
								
								
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage = $(this).text();
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getInterestedRoomRentAdsAJAX,300);

							}
		
						})
						// Ads part
						$('#targetAds').children().each(function(){
							$(this).remove();
						})
					for(let i=0;i<data.length;i++){
						let tempId = 0;
						tempId = "fond0"+data[i].interestedPrimaryKey;
							apiData = `<li id="`+tempId+`" class="list">
								<article class="aa-properties-item">`;
	                        if(data[i].adImages.length==0){
	                            apiData+= `<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/4u4u/img/NoImage.png" alt="img"></a>`;
	                        }else{
	                            apiData+= `<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img
										src="/disksource/wantedRoomAd/`+data[i].adImages[0]+`" alt="img"></a>`;
	                        }
	                        
	                        apiData+=` <a	href="#" value="`+tempId+`" id="del`+tempId+`" class="times-circle-o" ><i data-toggle="tooltip" data-placement="top" title="刪除" class="fa fa-times" aria-hidden="true" style="
	                        position: absolute;
	                        right: 10px;
	                        font-size: 35px;"></i></a>`;
	                        apiData+=`<div class="interest-tag  sold-out">對『`+data[i].interestedAdTitle+`』感興趣</div>`
	                        if(data[i].age!=null && data[i].age!=""){
	                        apiData+=`<div class="aa-properties-item-content">
										<div class="aa-properties-info">
											<span>`+data[i].name+`</span>  <span>`+data[i].job+`</span> <span>`+data[i].age+`</span><span>`+data[i].peopleNumGender+`</span>
										</div>` ;       
	                        }else{
	                        	 apiData+=`<div class="aa-properties-item-content">
											<div class="aa-properties-info">
												<span>`+data[i].name+`</span>  <span>`+data[i].job+`</span><span>`+data[i].peopleNumGender+`</span>
											</div>` ;
	                        }
	                        apiData+=`<div class="aa-properties-about text-center">
											<h3>
	                        	<a href="/4u4u/_4u4u/findRoomDetail?adStyle=1&adId=`+data[i].adId+`">
												`+data[i].adTitle+`</a> 
											</h3>
											<p>`+data[i].wantedRoomType+`</p>
											   <span class="aa-price">`+data[i].budget+`元/月 </span>             
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
								</article>
							</li>`;
							$('#targetAds').append(apiData)	;
							$('[data-toggle="tooltip"]').tooltip();
							$(".demo2").tooltip();
							 $('#del'+tempId).on('click',function(e){
								e.preventDefault();
													let adType = $(this).attr('value').substring(4,5);
													let adpk = $(this).attr('value').substring(5);
														
													        $.ajax({
													            type: "GET",
													            url: "/4u4u/deleteInterestedAd",
													            data: {adStyle:adType,
													            	   adId:adpk,
											            		},
													            dataType: "text",
													            success: function (response) {
													            	if(response=='成功'){
													            		$('#fond'+adType+adpk).remove();
													            		if($('#targetAds').children().length==0){
													            			$('#emptyDiv').show();
													            		}
													            	}
													            	if(response=='錯誤'){
															             alert('系統繁忙，請稍後刪除')
															        }
													            
													            }
													        });
												});
						}// for loop end
					
					}, // success bracket end

				});// ajax bracket end
			}
				
				
			
			
			
			
			
			function getInterestedWantedRoomAdsAJAX(){
				
				$.ajax({
					url : window.location.href, // 要呼叫的網址列或檔案
					type : 'POST', // GET或POST
					data: "",
					dataType : 'json',
					success : function(data) {
						// pagination part
						$('#pagination').children().each(function(){
							$(this).remove();
						})
						
						if(data.length==0){
							if(sessionStorage.curPage==null){
								totalPages = 1;	
								}
								$('#emptyDiv').show();
								if(sessionStorage.curPage!=null&& sessionStorage.curPage!=1 ){
									
								let tmpCurPage = parseInt(sessionStorage.getItem('curPage'));
								let tmpTotalPages = parseInt(sessionStorage.totalPages)-1;
								
								if(tmpCurPage > tmpTotalPages){
									sessionStorage.setItem('curPage',tmpTotalPages);
									if(tmpTotalPages>=5){
										sessionStorage.setItem('rangeEnd',tmpTotalPages);
										sessionStorage.setItem('rangeStart',tmpTotalPages-4);
									}
									let  tempURL = window.location.href;
									tempURL = tempURL.replace('curPage='+$.trim(tmpCurPage), 'curPage='+tmpTotalPages);
								    history.replaceState({ url: tempURL }, "", tempURL);
								    getInterestedWantedRoomAdsAJAX()();
								    
								    return;
								}
								}
						}else{
							totalPages = data[0].totalPages;
							$('#emptyDiv').hide();
						}
						 pageHtml =`<li id="previousPage"><a href="#" aria-label="Previous" onclick="return false;"> <span aria-hidden="true">&laquo;</span></a></li>`;
						 if(sessionStorage.rangeEnd!=null){
								let tempRangeEnd= parseInt(sessionStorage.rangeEnd);
								
								if(tempRangeEnd>5){
									 for(let i = tempRangeEnd-4;i<=totalPages;i++){
											
											if(i>tempRangeEnd){
											pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
											}else{
											pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
						
											}
											
										}
								}else{
									for(let i = 1;i<=totalPages;i++){
										if(i==1){
											pageHtml+=	'<li id="page'+i+'" class="active"><a href="#" >'+i+'</a></li>';
											continue;
										}
										if(i>5){
										pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
										}else{
										pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
					
										}
										
										}
									
								}						
								
							}else{
							 for(let i = 1;i<=totalPages;i++){
								if(i==1){
									pageHtml+=	'<li id="page'+i+'" class="active"><a href="#" >'+i+'</a></li>';
									continue;
								}
								if(i>5){
								pageHtml+= '<li id="page'+i+'" style="display:none"><a href="#" >'+i+'</a></li>';
								}else{
								pageHtml+= '<li id="page'+i+'" ><a href="#" >'+i+'</a></li>';
			
								}
								
								}
							}
						
						pageHtml+=	`<li id="totalPage"><a onclick="return false;"> <span aria-hidden="true">總頁數:`+totalPages+`</span></a></li>`;
						pageHtml+=	`<li id="nextPage" ><a href="#" aria-label="Next" onclick="return false;"> <span aria-hidden="true">&raquo;</span></a></li>`;
//	 					
						
						$('#pagination').append(pageHtml);
						
						if(sessionStorage.curPage==null){
							sessionStorage.totalPages=totalPages;
							sessionStorage.curPage=1;
							sessionStorage.rangeStart=1;
							sessionStorage.rangeEnd=5;
						}else{
							sessionStorage.totalPages=totalPages;
						}
						
							$('#pagination li').removeClass('active');
							$('#page'+sessionStorage.curPage).addClass('active');
						$('#pagination li').not('#totalPage').on('click',function(e){
							e.preventDefault();
							let curPage = parseInt(sessionStorage.curPage);
							let rangeStart = parseInt(sessionStorage.rangeStart);
							let rangeEnd = parseInt(sessionStorage.rangeEnd);
							
							if(parseInt(sessionStorage.totalPages)<=1){
								return;
							}

							if($(this).attr('id')=='previousPage' && sessionStorage.curPage!=1){
								if(curPage==rangeStart){
									$('#pagination li').removeClass('active');
									$('#page'+rangeEnd).hide();
									sessionStorage.rangeEnd=rangeEnd-1;
									
									let beforePage = sessionStorage.curPage;
									sessionStorage.curPage =curPage -1;
									let afterPage = sessionStorage.curPage;
									let  pageURL = window.location.href;
									pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
								    history.replaceState({ url: pageURL }, "", pageURL);
									sessionStorage.rangeStart = rangeStart -1;
									$('#page'+sessionStorage.curPage).show().addClass('active');
									window.setTimeout(getInterestedWantedRoomAdsAJAX,300);
								}else{
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage -1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getInterestedWantedRoomAdsAJAX,300);

								}
								return;
							}
							if($(this).attr('id')=='nextPage' && sessionStorage.curPage!= totalPages ){
								if(curPage==rangeEnd){
									$('#pagination li').removeClass('active');
									$('#page'+rangeStart).hide();
									sessionStorage.rangeEnd=rangeEnd+1;
									let beforePage = sessionStorage.curPage;
									sessionStorage.curPage =curPage +1;
									let afterPage = sessionStorage.curPage;
									let  pageURL = window.location.href;
									pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
								    history.replaceState({ url: pageURL }, "", pageURL);
									sessionStorage.rangeStart = rangeStart +1;
									$('#page'+sessionStorage.curPage).show().addClass('active');
									window.setTimeout(getInterestedWantedRoomAdsAJAX,300);

								}else{
									
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage +1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								
								window.setTimeout(getInterestedWantedRoomAdsAJAX,300);
								}

								return;
							}
							if($(this).attr('id')!='previousPage'&&$(this).attr('id')!='nextPage'){
								if(sessionStorage.curPage == $(this).text()){
									return;
								}
								$('#pagination li').removeClass('active');
								
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage = $(this).text();
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getInterestedWantedRoomAdsAJAX,300);
							}
		
						})
						// Ads part
						$('#targetAds').children().each(function(){
							$(this).remove();
						})
						
							for(let i=0;i<data.length;i++){
								let tempId = 0;
								tempId = "fond1"+data[i].interestedPrimaryKey;
								
								apiData = `<li id="`+tempId+`" class="list">
									<article class="aa-properties-item">`;
								   apiData+=` <a	href="#" value="`+tempId+`" id="del`+tempId+`" class="times-circle-o"><i data-toggle="tooltip" data-placement="top" title="刪除" class="fa fa-times" aria-hidden="true" style="
								   position: absolute;
								   right: 10px;
								   font-size: 35px;"></i></a>`;
								if(data[i].adImages.length==0){
		                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/4u4u/img/NoImage.png" alt="img"></a>`;
		                        }else{
		                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img
											src="/disksource/roomRentAd/`+data[i].adImages[0]+`" alt="img"></a>`;
		                        }
		                     
		                        apiData+=`<div class="interest-tag  sold-out">對『`+data[i].interestedAdTitle+`』感興趣</div>`
		                        apiData+=`<div class="aa-properties-item-content">
											<div class="aa-properties-info">
												<span>`+data[i].adRentType+`</span>  <span>`+data[i].roomType+`</span>
											</div>` ;       

		                        apiData+=`<div class="aa-properties-about text-center">
												<h3>
		                        	<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`">
													`+data[i].adTitle+`</a>
												</h3>
												<p>`+data[i].adAddress+`</p>
												<span class="aa-price">`+data[i].adRentPrice+`元/月</span>
												
												
											</div>
											<div class="aa-properties-detial">`;
		                        if(data[i].contactState=='2'){
		                        	 apiData+=	` <span class="aa-price">
	                      <i class="fa fa-comments fa-lg" aria-hidden="true" style="color: green"></i>可直接聯繫
	                    </span> `
										}else{
									apiData+=	` <span class="aa-price demo2" data-placement="bottom" title="一般會員需等廣告發佈7天後才能直接聯繫,如需立即聯繫必先升級為VIP會員。" >
	                      <i class="fas fa-crow fa-lg" style="color: orange"></i>早鳥體驗
	                    </span>`		
										}
								apiData+=`<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`"
													class="aa-secondary-btn">了解更多</a>
											</div>
										</div>
									</article>
								</li>`;
								$('#targetAds').append(apiData)	;	
								$('[data-toggle="tooltip"]').tooltip();
								$(".demo2").tooltip();
								$('#del'+tempId).on('click',function(e){
										e.preventDefault();
															let adType = $(this).attr('value').substring(4,5);
															let adpk = $(this).attr('value').substring(5);
															   
															$.ajax({
															            type: "GET",
															            url: "/4u4u/deleteInterestedAd",
															            data: {adStyle:adType,
															            	   adId:adpk,
													            		},
															            dataType: "text",
															            success: function (response) {
															            	if(response=='成功'){
															            		$('#fond'+adType+adpk).remove();
															            		if($('#targetAds').children().length==0){
															            			$('#emptyDiv').show();
															            		}
															            	}
															            	if(response=='錯誤'){
																	             alert('系統繁忙，請稍後刪除')
																	        }
															            
															            }
															        });
														});
								
							}// for loop end
						
				
					}, // success bracket end

				});// ajax bracket end
				
			}
			
			function refreshPage(){
				storage = sessionStorage;
				
				if(storage.adStyle==0){
					$('#roomRentAd').addClass('active');
					$('#wantedRoomAd').removeClass('active');
				}else{
					$('#wantedRoomAd').addClass('active');
					$('#roomRentAd').removeClass('active');
				}
			}
		});
		
		
		
		
		
