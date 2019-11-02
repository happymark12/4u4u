$(document).ready(function(){	
	
	$("#updateButton").click(function(){
		$("#houseCount").prop("disabled",false);
		$("#roomDeposit${vs.index+1}").prop("disabled",false);
	})
	
   function changeAgeGenderOption(){
   $('#ageOption').children().each(function(index){
   $(this).remove();
   })
   $('#ageOption').empty();
   var result =$("#peopleCount").find(":selected").text();
   
   
   if(result==0){
   
   $('#CurrentFlatmate').hide();
   $('#genderOption').attr('required',false);
   }else{
   $('#CurrentFlatmate').show();
   $('#genderOption').attr('required',true);
   }  
   
   if(result==1){
   
	   $('#ageOption').append(`年齡 :<select name="Cage" id="firstAgeOption"><option>-</option></select>`);
   
   for(var i =18;i<=99;i++){
   $('#firstAgeOption').append("<option>"+i+"</option>");
   }
   
   }else{
   
   $('#ageOption').append(`年齡 : <select id="firstAgeOption" name="Cage"><option>-</option></select>  to <select id="secondAgeOption" name="Cage2"><option>-</option></select>`)
   
   for(var i =18;i<=99;i++){
   $('#firstAgeOption').append("<option>"+i+"</option>");
   $('#secondAgeOption').append("<option>"+i+"</option>");
   
   }
   
   
   }
   
   //動態新增現存室友的性別
   $('#genderOption').children().each(function(index){
   $(this).remove();
   });
   
   
   var count = 0;
   var temp = result;
   while(result>0){
   if(count==0){
     if(result==1){
       $('#genderOption').append(`<option value="">請選擇</option>`);
       $('#genderOption').append(`<option>女</option>`);
       $('#genderOption').append(`<option>男</option>`);
       count++;
       result-=count;
       continue;
     }else{
   
       $('#genderOption').append(`<option value="">請選擇</option>`);
       $('#genderOption').append(`<option>`+result+"女"+`</option>`);
       $('#genderOption').append(`<option>`+result+"男"+`</option>`);
       count++;
       result-=count;
       continue;
     }
   }
   $('#genderOption').append(`<option>`+result+"女"+(temp-result)+"男"+`</option>`);
   result-=count;    
   
   }
   
   
   
   }
   
   
       //動態新增現存室友的年齡選項及姓別選項
       changeAgeGenderOption();
      
   
       $("#peopleCount").change(changeAgeGenderOption);
   
       $('#wholePropertyFieldDiv').hide();
       $('#wholePropertyBr').hide();
       
       //動態新增房間細節 (當房間數量變動時))
       $("#houseCount").change(function(){
           $('#roomDetail').children().each(function(index){
               $(this).remove();
           })
           if($("#houseCount").find(":selected").val()=='none'){
               return;
           }else{
           var result =$("#houseCount").find(":selected").val().split('-')[1];
           
            for(var i =1;i<=result;i++){
    $('#roomDetail').append(`
            <tr>
            <td>
                <fieldset>
                <legend>房間`+i+`</legend><br>
            <div class="roomTypeDiv">
            房屋現況 : 
            <select name="roomType`+i+`" class="roomTypeSelect" required>
            <option value="">請選擇...</option>
            <option value="0">套房</option>
            <option value="1">雅房</option>
                
            </select><br><br>
            </div>
            出租樓層 : 
            <input type="text" name="floor`+i+`" placeholder="-1為地下一樓,+4為4樓為頂加" style="width: 200px;">
            總樓層 : 
            <input type="text" style="width: 30px;" name="totalFloor`+i+`"> 樓
            <br><br>
            <span class="cookSelect">
                                可否開伙 : 
                                <select name="canCook`+i+`">
                                    <option value="true">可</option>
                                    <option value="false">不可</option>
                                </select>
                                </span>
            <br class="cookBr"><br class="cookBr">                        
            格局 : 
                <input type="checkbox" name ="balcony`+i+`" value="true">有陽台
                <input type="checkbox" name ="duplexApartment`+i+`" value="true">樓中樓
                <br><br>
                坪數 :
                <input type="text" name="area`+i+`" style="width:80px;"> 坪(請填寫室內實際使用坪數) 
                <br><br>
                房間設備 :
                <input type="checkbox" name="wash`+i+`" value="true">洗衣機
                <input type="checkbox" name="icebox`+i+`" value="true">冰箱
                <input type="checkbox" name="four`+i+`" value="true">第四台
                <input type="checkbox" name="gas`+i+`" value="true">天然瓦斯
                <input type="checkbox" name="tv`+i+`" value="true">電視
                <input type="checkbox" name="wardrobe`+i+`" value="true">衣櫃
                <input type="checkbox" name="sofa`+i+`" value="true">沙發
                <input type="checkbox" name="heater`+i+`" value="true">熱水器
                <input type="checkbox" name="broadband`+i+`" value="true">網路
                <input type="checkbox" name="desk`+i+`" value="true">桌子
                <input type="checkbox" name="chair`+i+`" value="true">椅子
                <input type="checkbox" name="singlebed`+i+`" value="true">單人床
                <input type="checkbox" name="doublebed`+i+`" value="true">雙人床
                <input type="checkbox" name="coldair`+i+`" value="true">冷氣
                <br><br>
            租金 :
            <input type="number" min="0" name="roomRentPrice`+i+`" style="width:80px;" required> 元/月  &nbsp;&nbsp;押金:
            <select name="roomDeposit`+i+`">
                <option value="2">二個月</option>
                <option value="0">面議</option>
                <option value="1">一個月</option>
            </select> 
            <br><br>
            房間狀況:
            <input name="room`+i+`State" type="checkbox" value="false">已出租             
            </fieldset>
            </td>
            </tr>`);
                        }
   
            
            //獨立套房
            if($("#rentType").find(":selected").val()=='independent-studio'){
            $(".roomTypeSelect option").filter(function() {
                return $(this).val() == '0';
                }).prop('selected', true);
            }
           //出租房間|分租套房
           if($("#rentType").find(":selected").val()=='room-for-rent'||$("#rentType").find(":selected").val()=='dependent-studio'){
   
               if($("#rentType").find(":selected").val()=='room-for-rent'){
                   if(result==0){
   
               $('#CurrentFlatmate').hide();
   
               }else{
               $('#CurrentFlatmate').show();
   
               }  
             
               $('#roomForRentDiv').show();
               }else{
               $('#CurrentFlatmate').hide();
               $('#roomForRentDiv').hide();
               }
             
               if($("#rentType").find(":selected").val()=='dependent-studio'){
                       $('#live-in-landlord').hide();
                       $('#currentTenant').hide();
                       $(".roomTypeSelect option").filter(function() {
                           return $(this).val() == '0';
                           }).prop('selected', true);
//                       $('.roomTypeDiv').hide();
                   }else{
//                       $('.roomTypeDiv').show();
                       $('#live-in-landlord').show();
                       $('#currentTenant').show();
                   }
              
                }else{
               $('#live-in-landlord').hide();
               $('#currentTenant').hide();
              
               $('#CurrentFlatmate').hide();
               $('#roomForRentDiv').hide();  
   
              
               }
              
           if($("#rentType").find(":selected").val()=='whole-property'){
               $('#wholePropertyBr').show();
                 $('#wholePropertyFieldDiv').show();
                   if($('#whole-property-div').length>0){
                       $('#whole-property-div').remove();
                       $('#whole-property-br').remove();
       
                   }
                  $('#houseCountDiv').after(` <br id="whole-property-br"><div id="whole-property-div">
                  請輸入數量 : 
                  <input type="number" name="wholePropertyToilet" style="width: 50px;" min="0" max="10" required > 衛浴
                  <input type="number" name="wholePropertyLivingRoom" style="width: 50px;" min="0" max="10" required> 廳
                  <input type="number" name="wholePropertyBalcony" style="width: 50px;" min="0" max="10" required> 陽台
                  `);
                  
                 
                  
                   
                    $('.cookSelect').hide();
                    $('.cookBr').hide();
                  
                   
               }else{
                   $('#wholePropertyFieldDiv').hide();
                   $('#wholePropertyBr').hide();
                   if($('#whole-property-div').length>0){
                       $('#whole-property-div').remove();
                       $('#whole-property-br').remove();
                   }
                   
                   $('.cookSelect').show();
                   $('.cookBr').show();
               }
            
             
            }
       });
       
       //Start
       //身分為房仲時有仲介費
       $("#adOwner").change(function(){  
           
           if($("#adOwner").find(":selected").val()=='agent'){
               $('#peopleCountDiv').before(`<div id="agentFeeDiv">仲介費 : 
              <input type="text" name="agentFee" style="width: 100px;" placeholder="eg.半個月"> </div><br id="agentBr">`);
           }else{
               $('#agentFeeDiv').remove();
               $('#agentBr').remove();
           }
   
   
           if($("#adOwner").find(":selected").val()=='live-in-landlord'){
               $("#peopleCount option").filter(function() {
               return $(this).text() == '1';
               }).prop('selected', true);
               $('#CurrentFlatmate').show();
               changeAgeGenderOption();
               
           }else{
               $("#peopleCount option").filter(function() {
               return $(this).text() == '0';
               }).prop('selected', true);
               $('#CurrentFlatmate').hide();
               changeAgeGenderOption();
           }
           
   });
     
       //動態新增房間細節 (當出租方式變動時)
       $("#rentType").change(function(){
          
           if($("#rentType").find(":selected").val()=='room-for-rent'){
               $("#peopleCountDiv").show();
               $("#peopleCountBr").show();
               $('#roomForRentDiv').show();
               $('#live-in-landlord').show();
               $('#currentTenant').show();
              
               }else{
                   $("#peopleCount option").filter(function() {
               return $(this).text() == '0';
               }).prop('selected', true);
                   $("#peopleCountDiv").hide();
               $("#peopleCountBr").hide();
               $('#roomForRentDiv').hide();
               $('#live-in-landlord').hide();
               $('#currentTenant').hide();
              
               }
             $("#adOwner option").filter(function() {
               return $(this).text() == '請選擇...';
               }).prop('selected', true);
                 
           if($('#agentFeeDiv').length>0){
               $('#agentFeeDiv').remove();
               $('#agentBr').remove();
           }
         
         
           //獨立套房
//           if($("#rentType").find(":selected").val()=='independent-studio'){
//               $("#houseCount option").filter(function() {
//               return $(this).text() == '1間房';
//               }).prop('selected', true);
         
//               $('#houseCount').prop('disabled', true);
               
              
//           }else{
//               $('#houseCount').prop('disabled', false);
             
//           }
           //房間數量
           if($("#houseCount").find(":selected").val()=='none'){
               if($("#rentType").find(":selected").val()=='room-for-rent'){
                   
               }else{
                   $('#CurrentFlatmate').hide();
               }
               
               return;
           }else{
           var result =$("#houseCount").find(":selected").val().split('-')[1];
           $('#roomDetail').children().each(function(index){
               $(this).remove();
           })
           //出租房間或分租套房
           if($("#rentType").find(":selected").val()=='room-for-rent'||$("#rentType").find(":selected").val()=='dependent-studio'){
               if($("#rentType").find(":selected").val()=='room-for-rent'){
               $('#CurrentFlatmate').show();
               $('#roomForRentDiv').show();
             
               }else{
                 
                   $('#CurrentFlatmate').hide();
               $('#roomForRentDiv').hide();
               }
              
               
           }else{
               $('#CurrentFlatmate').hide();
               $('#roomForRentDiv').hide();
   
           }
   
           for(var i =1;i<=result;i++){
                            $('#roomDetail').append(`
            <tr>
            <td>
                <fieldset>
                <legend>房間`+i+`</legend><br>
            <div class="roomTypeDiv">
            房屋現況 : 
            <select name="roomType`+i+`"  class="roomTypeSelect" required>
            <option value="">請選擇...</option>
            <option value="0">套房</option>
            <option value="1">雅房</option>
                
            </select><br><br>
            </div>
            出租樓層 : 
            <input type="text" name="floor`+i+`" placeholder="-1為地下一樓,+4為4樓為頂加" style="width: 200px;">
            總樓層 : 
            <input type="text" style="width: 30px;" name="totalFloor`+i+`"> 樓
            <br><br>
            <span class="cookSelect">
                                可否開伙 : 
                                <select name="canCook`+i+`">
                                    <option value="true">可</option>
                                    <option value="false">不可</option>
                                </select>
                                </span>
            <br class="cookBr"><br class="cookBr">                        
            格局 : 
            <input type="checkbox" name ="balcony`+i+`" value="true">有陽台
            <input type="checkbox" name ="duplexApartment`+i+`" value="true">樓中樓
            <br><br>
            坪數 :
            <input type="text" name="area`+i+`" style="width:80px;"> 坪(請填寫室內實際使用坪數) 
            <br><br>
            房間設備 :
            <input type="checkbox" name="wash`+i+`" value="true">洗衣機
            <input type="checkbox" name="icebox`+i+`" value="true">冰箱
            <input type="checkbox" name="four`+i+`" value="true">第四台
            <input type="checkbox" name="gas`+i+`" value="true">天然瓦斯
            <input type="checkbox" name="tv`+i+`" value="true">電視
            <input type="checkbox" name="wardrobe`+i+`" value="true">衣櫃
            <input type="checkbox" name="sofa`+i+`" value="true">沙發
            <input type="checkbox" name="heater`+i+`" value="true">熱水器
            <input type="checkbox" name="broadband`+i+`" value="true">網路
            <input type="checkbox" name="desk`+i+`" value="true">桌子
            <input type="checkbox" name="chair`+i+`" value="true">椅子
            <input type="checkbox" name="singlebed`+i+`" value="true">單人床
            <input type="checkbox" name="doublebed`+i+`" value="true">雙人床
            <input type="checkbox" name="coldair`+i+`" value="true">冷氣
            <br><br>
            租金 :
            <input type="number" min="0" name="roomRentPrice`+i+`" style="width:80px;" required> 元/月  &nbsp;&nbsp;押金:
            <select name="roomDeposit`+i+`">
                <option value="2">二個月</option>
                <option value="0">面議</option>
                <option value="1">一個月</option>
            </select> 
            <br><br>
            
            </fieldset>
            </td>
            </tr>`);
                        }
   
    };
        //整層出租
        if($("#rentType").find(":selected").val()=='whole-property'){
                   $('#wholePropertyBr').show();
                     $('#wholePropertyFieldDiv').show();
                       if($('#whole-property-div').length>0){
                           $('#whole-property-div').remove();
                           $('#whole-property-br').remove();
           
                       }
                      $('#houseCountDiv').after(` <br id="whole-property-br"><div id="whole-property-div">
                      請輸入數量 : 
                    	  <input type="number" name="wholePropertyToilet" style="width: 50px;" min="0" max="10" required> 衛浴
                          <input type="number" name="wholePropertyLivingRoom" style="width: 50px;" min="0" max="10" required> 廳
                          <input type="number" name="wholePropertyBalcony" style="width: 50px;" min="0" max="10" required> 陽台
                      `);
                      
                     
                      
                       
                        $('.cookSelect').remove();
                        $('.cookBr').remove();
                      $('#wholePropertyTotalRent').attr('required',true);
                       
                   }else{
                       $('#wholePropertyFieldDiv').hide();
                       $('#wholePropertyBr').hide();
                       if($('#whole-property-div').length>0){
                           $('#whole-property-div').remove();
                           $('#whole-property-br').remove();
                       }
                       $('#wholePropertyTotalRent').attr('required',false);
                       $('.cookSelect').show();
                       $('.cookBr').show();
                   }



           if($("#rentType").find(":selected").val()=='whole-property'||$("#rentType").find(":selected").val()=='room-for-rent'){
//               $('.roomTypeDiv').show();
           }else{
        	   $(".roomTypeSelect option").filter(function() {
                   return $(this).val() == '0';
                   }).prop('selected', true);
//               $('.roomTypeDiv').hide();
           }
   
   
       });
   
   
       // 最大最小年齡
       for(var i =18;i<=99;i++){
       $('#minAge').append("<option>"+i+"</option>")
       $('#maxAge').append("<option>"+i+"</option>") 
       }
       // 信封上限
       for(var i =1;i<=50;i++){
          
               $('#maxEmail').append("<option>"+i+"</option>");
           
           
       }
       //信件上限預設12封
       $("#maxEmail option").filter(function() {
        return $(this).text() == 12;
       }).prop('selected', true);
      
     
   
       
       // 附近交通新增按鈕  
           var maxCount = 5;
       $('#addBusOption').click(function(){
          if($('.item1').children().length<maxCount){
        	  let i = $('.item1').children().length;
        	   $("#addBusDataOption").before(
                       `<td>、近&nbsp;&nbsp;<input type="text" name="bus`+i+`">&nbsp;公車站
                        </td>`
                              
                   );
          
           if($('.item1').children().length==5){
                   $('#addBusOption').hide();
                   return;
               }
          }
   });
       
         
   $('#addMRT').click(function(){
          if($('.item2').children().length<maxCount){
        	  let i = $('.item2').children().length;
            $("#addMRTData").before(
               `<td>、近&nbsp;&nbsp;<input type="text" name="MRT`+i+`">&nbsp;捷運站 
                    </td>`
           );
           if($('.item2').children().length==5){
                   $('#addMRT').hide();
                   return;
               }
          }
   });
   $('#addTrain').click(function(){
          if($('.item3').children().length<maxCount){
        	  let i = $('.item3').children().length;
            $("#addTrainData").before(
               `<td>、近&nbsp;&nbsp;<input type="text" name="train`+i+`" >&nbsp;火車站  
               </td>`
           );
               if($('.item3').children().length==5){
                   $('#addTrain').hide();
                   return;
               }
          }
   });
   var imageCount=9;
   $('#uploadSpan').text('可再上傳'+imageCount+'張相片');
   $('#theFile').change(function(e){
      
       let fileArray  =this.files;
       let uploadImageCount = 0;
       uploadImageCount = (fileArray.length>imageCount)?imageCount:(fileArray.length);
       for(let i=0;i<uploadImageCount;i++){
           let readFile = new FileReader();
           readFile.readAsDataURL(fileArray[i]);
           readFile.addEventListener('load',parseFile);
           
       }
       
   
   });
   function parseFile(e){
      
    
       
       let idName = 'Image_'+(new Date().getTime()-(Math.floor((Math.random() * 10000 + 1))));
       
       $('#dropzone').append(`<div><button id="`+idName+`">delete</button><img src="`+this.result+`" ></div>`);
        adjustImageDiv();
       $('#'+idName).click(function(){
           $(this).siblings().remove();
           $(this).parent().remove();
           $(this).remove();
           imageCount++;
           $('#theFile').show();
           $('#uploadSpan').text('可再上傳'+imageCount+'張相片');
           adjustImageDiv();
           console.log(imageCount);
       })
       imageCount--;
       if(imageCount==0){
           $('#uploadSpan').text('你已到達上傳相片的上限')
           $('#theFile').hide();
       }else{
       $('#uploadSpan').text('可再上傳'+imageCount+'張相片');
       }
       console.log(imageCount);
   }
   
   function adjustImageDiv(){
      
       $('#dropzone').children().each(function(index){
           if(index==0){
               $(this).removeClass();
               $(this).addClass('first');
           }else{
               if(index%2!=0){
                   $(this).removeClass();
                   $(this).addClass('odd');
               }else{
                   $(this).removeClass();
                   $(this).addClass('even');
               }
           }
   
       })
   
       $('#dropzone div img').each(function(index){
           if(index==0){
               $(this).css({
                  "max-width":"374px",
                  "max-height":"264px",
               });
           }else{
                   $(this).css({
                   "max-width":"182px",
                   "max-height":"127px",
                   });
           };
   
      
       });
       }
   });
   
