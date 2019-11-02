		$(document).ready(function() {
			
			if(window.location.href=='http://localhost:8080/4u4u/properties'){
				defaultURL = 'http://localhost:8080/4u4u/properties?searchType=0&rentType3=3&rentType0=0&rentType1=1&rentType2=2&curPage=1';
				history.replaceState({ url: defaultURL }, "", defaultURL);
				sessionStorage.clear();
				sessionStorage.setItem('searchType','0');
				sessionStorage.setItem('rentType0','0');
				sessionStorage.setItem('rentType1','1');
				sessionStorage.setItem('rentType2','2');
				sessionStorage.setItem('rentType3','3');
				window.setTimeout(getRoomRentAdsAJAX,100);
			}else {
				if(sessionStorage.searchType=="0"){
					
					
					getRoomRentAdsAJAX()
				
					$('input[name="searchType"]').filter('[value=0]').prop('checked', true);
					$('#rentType').show();
					$('#rentTypebr').show();
					$('#roomforRentDiv').show();
					$('#roomforRentbr').show();
					
					
					
					
				}else{
					
					getWantedRoomAdsAJAX();		
					
						$('#rentType').hide();
						$('#rentTypebr').hide();
						$('#roomforRentDiv').hide();
						$('#roomforRentbr').hide();
						
				}
				
				
					storage = sessionStorage;
					if(storage.searchType==null){
						// 重新定向
					window.location.replace("http://localhost:8080/4u4u/properties");
					return;
					}
					
					
				
			}
			
			
				
		
				
//				window.onpopstate = function(event){
//					
//					if(sessionStorage.searchType=='0'){
//						window.setTimeout(getRoomRentAdsAJAX,300);
//						
//					}else{	
//						window.setTimeout(getWantedRoomAdsAJAX,300);	
//					}
					// alert(event.state.url)
					// alert("location: " + document.location + ", state: " +
					// JSON.stringify(event.state));
//				};
				
				
				$('#searchButton').on('click', function (e) {
					e.preventDefault();
					currentURL = window.location.href;
// sessionStorage.setItem($(this).attr('name'), $(this).val());
					target = $(this).attr('name') + '=' + $(this).val();
				
					
					if(currentURL==sessionStorage.getItem('url'))
					{
						return;
					}
					sessionStorage.removeItem("curPage");
					if(sessionStorage.searchType=="0"){
						window.setTimeout(getRoomRentAdsAJAX,500);
	
					}else{
						window.setTimeout(getWantedRoomAdsAJAX,500);

					}
				
					history.replaceState({ url: finalURL }, "", finalURL);
					sessionStorage.setItem('url',finalURL);
				})

				// 可入住日期的設定

				// $('input[name="availableDate"]').change(function(){

				// });

				// 搜尋類型改變時,篩選細節會動態變更
				$('input[name="searchType"]').change(function () {
					if ($('input[name="searchType"]:checked').val() == '0') {
						// $('#coupleOption').hide();
						$('#rentType').show();
						$('#rentTypebr').show();
						$('#roomforRentDiv').show();
						$('#roomforRentbr').show();
					} else {
						// $('#coupleOption').show();
						$('#rentType').hide();
						$('#rentTypebr').hide();
						$('#roomforRentDiv').hide();
						$('#roomforRentbr').hide();
					}
				});
				storage = sessionStorage;
				
				$('#minRent,#maxRent').on('propertychange input',function(){
					
					target = $(this).attr('name') + '=' + $(this).val();
					 currentURLRent = window.location.href;
					
					if($.trim($(this).val())==""){
						if($(this).attr('name')=="rentPriceMin"){
							sessionStorage.removeItem('rentPriceMin');
							let indexStart = currentURLRent.indexOf($(this).attr('name'));
							let tempStr = currentURLRent.substring(indexStart);
							let tempIndexStart = tempStr.indexOf('&')
							if (tempIndexStart != -1) {

								currentURLRent = currentURLRent.substring(0, indexStart-1)+ tempStr.substring(tempIndexStart)
							} else {

								currentURLRent = currentURLRent.substring(0, indexStart-1);
							}
							history.replaceState({ url: currentURLRent }, "", currentURLRent);
							return;
						}else{
							sessionStorage.removeItem('rentPriceMax');
							let indexStart = currentURLRent.indexOf($(this).attr('name'));
							let tempStr = currentURLRent.substring(indexStart);
							let tempIndexStart = tempStr.indexOf('&')
							if (tempIndexStart != -1) {

								currentURLRent = currentURLRent.substring(0, indexStart-1)+ tempStr.substring(tempIndexStart)
							} else {

								currentURLRent = currentURLRent.substring(0, indexStart-1);
							}
							history.replaceState({ url: currentURLRent }, "", currentURLRent);
							return;
						}
					}else{
						
						storage.setItem($(this).attr('name'), $(this).val());
					}
					console.log(currentURLRent)
					 
							if (currentURLRent.includes('?')) {
								
								if (currentURLRent.includes($(this).attr('name'))) {
												let indexStart = currentURLRent.indexOf($(this).attr('name'));
												let tempStr = currentURLRent.substring(indexStart);
												let tempIndexStart = tempStr.indexOf('&')
												if (tempIndexStart != -1) {

													finalURL = currentURLRent.substring(0, indexStart) + target + tempStr.substring(tempIndexStart)
												} else {

													finalURL = currentURLRent.substring(0, indexStart) + target;
												}
										
								
									} else { 

										
									finalURL = currentURLRent + '&' + target;

									}
							} else { // currentURL不含?號
								
								finalURL = currentURLRent + '?' + target;
							}
							history.replaceState({ url: finalURL }, "", finalURL);
						
					 });
				
				
				$('input,select').not($("#minRent,#maxRent")).change(function () {
					currentURL = window.location.href;
					
					target = $(this).attr('name') + '=' + $(this).val();
					if ($(this).attr('type') == 'checkbox' && $(this).prop('checked') == true) {
						storage.setItem([$(this).attr('name')], $(this).val())
					} else if ($(this).attr('type') == 'checkbox' && $(this).prop('checked') == false) {
						storage.removeItem($(this).attr('name'));
					}

					if ($(this).attr('type') != 'checkbox') {
						storage.setItem($(this).attr('name'), $(this).val())
						if($.trim(storage.getItem($(this).attr('name')))==""&&$(this).attr('name') != 'county' && $(this).attr('name') != 'district'){
							storage.removeItem($(this).attr('name'));
							
							let indexStart = currentURL.indexOf($(this).attr('name'));
							let tempStr = currentURL.substring(indexStart);
							let tempIndexStart = tempStr.indexOf('&')
							if (tempIndexStart != -1) {

								currentURL = currentURL.substring(0, indexStart-1)+ tempStr.substring(tempIndexStart)
							} else {

								currentURL = currentURL.substring(0, indexStart-1);
							}
							history.replaceState({ url: currentURL }, "", currentURL);
							return;
							
							
						}else if($.trim(storage.getItem($(this).attr('name')))==""&&$(this).attr('name') == 'county'){
							sessionStorage.removeItem('district');
							let indexBegin = currentURL.indexOf('district');
							if (indexBegin != -1) {
								let districtStr = currentURL.substring(indexBegin);

								let tempIndexBegin = districtStr.indexOf('&');
								if (tempIndexBegin != -1) {
									currentURL = currentURL.substring(0, indexBegin) + districtStr.substring(tempIndexBegin + 1);
								} else {
									currentURL = currentURL.substring(0, indexBegin - 1);
								}
							}
							storage.removeItem($(this).attr('name'));
							
							let indexStart = currentURL.indexOf($(this).attr('name'));
							let tempStr = currentURL.substring(indexStart);
							let tempIndexStart = tempStr.indexOf('&')
							if (tempIndexStart != -1) {

								currentURL = currentURL.substring(0, indexStart-1)+ tempStr.substring(tempIndexStart)
							} else {

								currentURL = currentURL.substring(0, indexStart-1);
							}
							history.replaceState({ url: currentURL }, "", currentURL);
							
							return;
							
						}
						
						
						
						
					}
// if (currentURL.includes('&search=Search')) {
// currentURL = currentURL.replace('&'+'search=Search', '');
// history.replaceState({ url: currentURL }, "", currentURL);
// }
					
					if (currentURL.includes(target)) {
						if ($(this).attr('type') == 'checkbox' && $(this).prop('checked') == false) {
							
							if (currentURL.includes('?' + target)) {
								finalURL = currentURL.replace('?' + target, '');
							} else {
								finalURL = currentURL.replace('&' + target, '');
							}
							
							history.replaceState({ url: finalURL }, "", finalURL);
						}
						return;
					} else {
						if (currentURL.includes('?')) {
							if ($(this).attr('type') == 'checkbox' && $(this).prop('checked') == false) {
								return;
							}
							if (currentURL.includes($(this).attr('name'))) {
								if ($(this).attr('name') == 'county' || $(this).attr('name') == 'district') {

									if (currentURL.includes('?' + $(this).attr('name'))) {

										if ($(this).attr('name') == 'county') {


											
											sessionStorage.removeItem('district');
											let indexBegin = currentURL.indexOf('district');
											if (indexBegin != -1) {
												let districtStr = currentURL.substring(indexBegin);

												let tempIndexBegin = districtStr.indexOf('&');
												if (tempIndexBegin != -1) {
													currentURL = currentURL.substring(0, indexBegin) + districtStr.substring(tempIndexBegin + 1);
												} else {
													currentURL = currentURL.substring(0, indexBegin - 1);
												}
											}
											let indexStart = currentURL.indexOf($(this).attr('name'));
											let tempStr = currentURL.substring(indexStart);
											let tempIndexStart = tempStr.indexOf('&')
											if (tempIndexStart != -1) {

												finalURL = currentURL.substring(0, indexStart) + target + tempStr.substring(tempIndexStart);
											} else {

												finalURL = currentURL.substring(0, indexStart) + target;
											}


										}

									} else { // 含問號 但是不是?+name 組合
										if ($(this).attr('name') == 'county') {
											sessionStorage.removeItem('district');
											let indexBegin = currentURL.indexOf('district');
											if (indexBegin != -1) {
												let districtStr = currentURL.substring(indexBegin);

												let tempIndexBegin = districtStr.indexOf('&');
												if (tempIndexBegin != -1) {
													currentURL = currentURL.substring(0, indexBegin) + districtStr.substring(tempIndexBegin + 1);
												} else {
													currentURL = currentURL.substring(0, indexBegin - 1);
												}
											}
										
											
											let indexStart = currentURL.indexOf($(this).attr('name'));
											let tempStr = currentURL.substring(indexStart);
											let tempIndexStart = tempStr.indexOf('&')
											if (tempIndexStart != -1) {

												finalURL = currentURL.substring(0, indexStart) + target + tempStr.substring(tempIndexStart)
											} else {

												finalURL = currentURL.substring(0, indexStart) + target;
											}




										} else {
											let indexStart = currentURL.indexOf($(this).attr('name'));
											let tempStr = currentURL.substring(indexStart);
											let tempIndexStart = tempStr.indexOf('&')
											if (tempIndexStart != -1) {

												finalURL = currentURL.substring(0, indexStart) + target + tempStr.substring(tempIndexStart)
											} else {

												finalURL = currentURL.substring(0, indexStart) + target
											}
										}



									}


								} else { // currentURL含?號;name存在但不是county或district

									let indexStart = currentURL.indexOf($(this).attr('name'));

									let tempStr = currentURL.substring(indexStart);
									let tempIndexStart = tempStr.indexOf('&');
									if (tempIndexStart != -1) {



										finalURL = currentURL.substring(0, indexStart) + target + tempStr.substring(tempIndexStart);;


									} else {

										finalURL = currentURL.substring(0, indexStart) + target;

									}

								}

							} else { // currentURL含?號 但不含name 屬性
								finalURL = currentURL + '&' + target;

							}
						} else { // currentURL不含?號
							if ($(this).attr('type') == 'checkbox' && $(this).prop('checked') == false) {
								return;
							}
							finalURL = currentURL + '?' + target;
						}
					}
					if ($(this).attr('name') == 'sortOption' ) {
						if(sessionStorage.searchType=="0"){
							window.setTimeout(getRoomRentAdsAJAX,500);
						}else{
							window.setTimeout(getWantedRoomAdsAJAX,500);

						}
						
						history.replaceState({ url: finalURL }, "", finalURL);
						return;
					}
					history.replaceState({ url: finalURL }, "", finalURL);
				});

				
				window.setTimeout(refreshPage,500);	
				

				// }).submit(function(){
				// $('#filterZone').hide();
				// // return false;
				// })

				$('#reset').click(function () {
					$('#rentType').show();
					$('#rentTypebr').show();
					$('#roomforRentDiv').show();
					$('#roomforRentbr').show();
					// $('#filterZone').hide();
					resetURL = 'http://localhost:8080/4u4u/properties?searchType=0&rentType3=3&rentType0=0&rentType1=1&rentType2=2&curPage=1';
					 $("#sortOption option").filter(function() {
			                return $(this).val() == '0';
			                }).prop('selected', true);
			            
					history.replaceState({ url: resetURL }, "", resetURL);
					sessionStorage.clear();
					sessionStorage.setItem('searchType','0');
					sessionStorage.setItem('rentType0','0');
					sessionStorage.setItem('rentType1','1');
					sessionStorage.setItem('rentType2','2');
					sessionStorage.setItem('rentType3','3');
					window.setTimeout(getRoomRentAdsAJAX,500);

				
				})

		
	
			
				function getRoomRentAdsAJAX(){
					
					$.ajax({
					url : '/4u4u/_4u4u/controller/ProcessSearchAjax.do', // 要呼叫的網址列或檔案
					type : 'GET', // GET或POST
					data: {url:window.location.href},
					dataType : 'json',
					success : function(data) {
					
// sessionStorage.propertiesResultAjax =data;
						// pagination part
						$('#pagination').children().each(function(){
							$(this).remove();
						})
						if(data.length==0){
							totalPages = 0;	
						}else{
							totalPages = data[0].totalPages;
						}
						
// if(totalPages=="0")
						 pageHtml =`<li id="previousPage"><a href="#" aria-label="Previous" onclick="return false;"> <span aria-hidden="true">&laquo;</span></a></li>`;
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
						
						if(sessionStorage.curPage!=1){
							$('#pagination li').removeClass('active');
							$('#page'+sessionStorage.curPage).addClass('active');
						}
						
						$('#pagination li').not('#totalPage').on('click',function(e){
							e.preventDefault();
							let curPage = parseInt(sessionStorage.curPage);
							let rangeStart = parseInt(sessionStorage.rangeStart);
							let rangeEnd = parseInt(sessionStorage.rangeEnd);
							
							if(parseInt(sessionStorage.totalPages)<1){
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
									window.setTimeout(getRoomRentAdsAJAX,500);
								}else{
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage -1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getRoomRentAdsAJAX,500);

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
									window.setTimeout(getRoomRentAdsAJAX,500);

								}else{
									
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage +1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getRoomRentAdsAJAX,500);

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
								window.setTimeout(getRoomRentAdsAJAX,500);

							}
		
						})
						// Ads part
							$('#targetAds').children().each(function(){
								$(this).remove();
							})
							
								for(let i=0;i<data.length;i++){
									let tempId = 0;
									tempId = "saveAd0"+data[i].adId;
									
									apiData = `<li>
										<article class="aa-properties-item">`;
			                        if(data[i].adImages.length==0){
			                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img src="/4u4u/img/NoImage.png" alt="img"></a>`;
			                        }else{
			                            apiData+= `<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`" class="aa-properties-item-img"> <img
												src="/disksource/roomRentAd/`+data[i].adImages[0]+`" alt="img"></a>`;
			                        }
			                        if(data[i].memberState=='1'){
			                        	 apiData+=`<div class="aa-tag " style="background-color:green">一般</div>`;
			                        }else{
			                        	  apiData+=`<div class="aa-tag for-rent">精選</div>`;
			                        }
			                        apiData+=`<div class="aa-properties-item-content">
			                        	<a href="#" class="property-favorite " id=`+ tempId +` state="儲存廣告" data-toggle="tooltip" title="儲存"  ><i class="fa fa-heart-o"></i></a>
												<div class="aa-properties-info">
													<span>`+data[i].adRentType+`</span>  <span>`+data[i].roomType+`</span>
												</div>` ;       

			                        apiData+=`<div class="aa-properties-about text-center">
													<h3>
														<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`">`+data[i].adTitle+`</a>
													</h3>
													<p >`+data[i].adAddress+`</p> <span class="aa-price">租金: `+data[i].adRentPrice+` 元/月</span>
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
									apiData+=`<a href="/4u4u/_4u4u/roomRentDetail?adStyle=0&adId=`+data[i].adId+`"
														class="aa-secondary-btn">了解更多</a>
												</div>
											</div>
										</article>
									</li>`;
									$('#targetAds').append(apiData)	;	
									$('[data-toggle="tooltip"]').tooltip();
									$(".demo2").tooltip();
									$('#'+tempId).on('click',function(e){
									    e.preventDefault();
										let adType = tempId.substring(6,7);
										let adpk = tempId.substring(7);
										if($(this).attr('state')=='儲存廣告'){//1:已儲存 0:為儲存
										    
											$.ajax({
										            type: "GET",
										            url: "/4u4u/saveAd",
										            data: {adStyle:adType,
										            	   adId:adpk,
										            		},
										            
										            dataType: "text",
										            success: function (response) {
										            	if(response=='需要登入'){
										            		alert('您需要登入，才能儲存廣告喔!!');
										            		return;
										            	}
										            	if(response=='取消儲存廣告'){
										            		
										            		$('#'+tempId).attr('state','取消儲存廣告');
										            		$('#'+tempId).addClass('active');
										            	}
										            	if(response=='同一人'){
										            		
										                alert('您為廣告發布人無法使用此功能')
											            }
										            	
										            	if(response=='錯誤'){
										            		
										               alert('系統繁忙，請稍後儲存')
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
										            		
												             alert('系統繁忙，請稍後取消')
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
			}
				
				
			
			
			
			
			
			function getWantedRoomAdsAJAX(){
				
				$.ajax({
					url : '/4u4u/_4u4u/controller/ProcessSearchAjax.do', // 要呼叫的網址列或檔案
					type : 'GET', // GET或POST
					data:  {url:window.location.href},
					dataType : 'json',
					success : function(data) {
						// pagination part
						$('#pagination').children().each(function(){
							$(this).remove();
						})
						
						if(data.length==0){
							totalPages = 0;	
						}else{
							totalPages = data[0].totalPages;
						}
						 pageHtml =`<li id="previousPage"><a href="#" aria-label="Previous" onclick="return false;"> <span aria-hidden="true">&laquo;</span></a></li>`;
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
						
						if(sessionStorage.curPage!=1){
							$('#pagination li').removeClass('active');
							$('#page'+sessionStorage.curPage).addClass('active');
						}
						$('#pagination li').not('#totalPage').on('click',function(e){
							e.preventDefault();
							let curPage = parseInt(sessionStorage.curPage);
							let rangeStart = parseInt(sessionStorage.rangeStart);
							let rangeEnd = parseInt(sessionStorage.rangeEnd);
							
							if(parseInt(sessionStorage.totalPages)<1){
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
									window.setTimeout(getWantedRoomAdsAJAX,500);
								}else{
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage -1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								window.setTimeout(getWantedRoomAdsAJAX,500);

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
									window.setTimeout(getWantedRoomAdsAJAX,500);

								}else{
									
								$('#pagination li').removeClass('active');
								let beforePage = sessionStorage.curPage;
								sessionStorage.curPage =curPage +1;
								let afterPage = sessionStorage.curPage;
								let  pageURL = window.location.href;
								pageURL = pageURL.replace('curPage='+$.trim(beforePage), 'curPage='+afterPage);
							    history.replaceState({ url: pageURL }, "", pageURL);
								$('#page'+sessionStorage.curPage).addClass('active');
								
								window.setTimeout(getWantedRoomAdsAJAX,500);
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
								window.setTimeout(getWantedRoomAdsAJAX,500);
							}
		
						})
						// Ads part
							$('#targetAds').children().each(function(){
								$(this).remove();
							})
						for(let i=0;i<data.length;i++){
							let tempId = 0;
							tempId = "saveAd1"+data[i].adId;
								apiData = `<li>
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
									</article>
								</li>`;
								$('#targetAds').append(apiData)	;	
								$('[data-toggle="tooltip"]').tooltip();
								$(".demo2").tooltip();
								$('#'+tempId).on('click',function(e){
								    e.preventDefault();
									let adType = tempId.substring(6,7);
									let adpk = tempId.substring(7);
									if($(this).attr('state')=='儲存廣告'){//1:已儲存 0:為儲存
									    
										$.ajax({
									            type: "GET",
									            url: "/4u4u/saveAd",
									            data: {adStyle:adType,
									            	   adId:adpk,
									            		},
									            
									            dataType: "text",
									            success: function (response) {
									            	if(response=='需要登入'){
									            		alert('您需要登入，才能儲存廣告喔!!');
									            		return;
									            	}
									            	if(response=='取消儲存廣告'){
									            		
									            		$('#'+tempId).attr('state','取消儲存廣告');
									            		$('#'+tempId).addClass('active');
									            	}
									            	if(response=='同一人'){
									            		
									                alert('您為廣告發布人無法使用此功能')
										            }
									            	
									            	if(response=='錯誤'){
									            		
									               alert('系統繁忙，請稍後儲存')
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
									            		
											             alert('系統繁忙，請稍後取消')
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
				
			}
			
			function refreshPage(){
				storage = sessionStorage;
				$('input[name="searchType"]').filter('[value='+$.trim(storage.searchType)+']').prop('checked', true);
				
				if(storage.district!=null){
					
						let districtVal = storage.getItem('district');
						$('#county').val(storage.county).change();
						
						$('#district').val(districtVal).change();
				}
				if(storage.district==null&& storage.county!=null){
						$('#county').val(storage.county).change();
					
				}
				
				if(storage.rentType0!=null){
					$('input[name="rentType0"]').prop('checked', true);
				}else{
					$('input[name="rentType0"]').prop('checked', false);
				}
				if(storage.rentType1!=null){
					$('input[name="rentType1"]').prop('checked', true);
				}else{
					$('input[name="rentType1"]').prop('checked', false);
				}
				if(storage.rentType2!=null){
					$('input[name="rentType2"]').prop('checked', true);
				}else{
					$('input[name="rentType2"]').prop('checked', false);
				}
				if(storage.rentType3!=null){
					$('input[name="rentType3"]').prop('checked', true);
				}else{
					$('input[name="rentType3"]').prop('checked', false);
				}
				
				if(storage.rentPriceMin!=null){
					$('#minRent').val(storage.rentPriceMin);
				}
				if(storage.rentPriceMax!=null){
					$('#maxRent').val(storage.rentPriceMax);
				}
				
				if(storage.availableDate!=null){
					$('input[name="availableDate"]').val(storage.getItem('availableDate'));
				}
				
				if(storage.roomNum!=null){
					$('#roomNum option').filter(
							function() {
				       return $(this).val() == storage.roomNum;
				         }).prop('selected', true);

				}
				if(storage.gender!=null){
			$('input[name="gender"]').filter('[value='+$.trim(storage.gender)+']').prop('checked', true);


				}
				if(storage.smoke!=null){
					$('input[name="smoke"]').filter('[value='+$.trim(storage.smoke)+']').prop('checked', true);


				}
				if(storage.pet!=null){
					$('input[name="pet"]').filter('[value='+$.trim(storage.pet)+']').prop('checked', true);

				}
				if(storage.sortOption!=null){
					$('#sortOption option').filter(
					function() {
			     return $(this).val() == storage.sortOption;
					}).prop('selected', true);
				}

				
			}
		});
		
		
		
		
		
