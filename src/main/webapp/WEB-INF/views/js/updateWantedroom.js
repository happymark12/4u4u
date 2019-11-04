    
        $(document).ready(function(){          
            // //年齡範圍(單數)
            for(var i = 18;i<=100;i++){
                $('#age').append("<option>"+i+"</option>")  
            }
            // //年齡範圍(複數)
            for(var i = 18;i<=100;i++){
                $('#ageMin').append("<option>"+i+"</option>")  
            }
            for(var i = 18;i<=100;i++){
                $('#ageMax').append("<option>"+i+"</option>")  
            }
            // //想要室友的年齡範圍
            for(var i = 18;i<=100;i++){
                $('#roommatesAgeMin').append("<option>"+i+"</option>")  
            }
            for(var i = 18;i<=100;i++){
                $('#roommatesAgeMax').append("<option>"+i+"</option>")  
            }            
            //信封上限
            for(var i =1;i<=50;i++){                
                    $('#emailMax').append("<option>"+i+"</option>")                        
            }
//            //動態新增房間類型(套房幾間)
//            for(var i =1;i<=2;i++){
//                $('#severalSuites').append("<option>"+i+"</option>");
//            }           
//            //動態新增房間類型(雅房幾間)
//            for(var i =1;i<=2;i++){
//                $('#severalRooms').append("<option>"+i+"</option>");
//            }         
            
            $('#severalSuites').change(function(){
            	if($('#severalSuites').val()!=""){
            		$('#severalRooms').removeAttr('required');
            		
            	}
            	if($('#severalSuites').val()==""&&$('#severalRooms').val()==""){
            		$('#severalSuites').attr('required',true);
            		
            	}
            	console.log('severalSuites'+$('#severalSuites').attr('required'))
            	console.log('severalRooms'+$('#severalRooms').attr('required'))
            })
            
            $('#severalRooms').change(function(){
            	if($('#severalRooms').val()!=""){
            		$('#severalSuites').removeAttr('required');
            		
            	}
            	if($('#severalSuites').val()==""&&$('#severalRooms').val()==""){
            		$('#severalSuites').attr('required',true);
            		
            	}
            	console.log('severalSuites'+$('#severalSuites').attr('required'))
            	console.log('severalRooms'+$('#severalRooms').attr('required'))
            })
            
            //動態選擇=>當合租意願選擇勾願意時才出現合租額外描述
            $("#agreeShare").click(function(){
            if($('#agreeShareDescriptionDiv').css("display")=="none"){
                $('#agreeShareDescriptionDiv').css("display","");
            }else{
                $('#agreeShareDescriptionDiv').css("display","none");
                }  
            })      
            //動態新增鄉鎮區   
            $('#districtBr').hide();
            $('select[name="county"]').change(function(){
                $('#districtBr').show();
               if($('#districtSpan').children().length>0){
                $('#districtSpan').children().each(function(index){
                    $(this).remove();
            });
               }
                $('select[name="district"]').children().each(function(index){
                    if(index==0){return;}
                    else{
                        $('#districtSpan').append(`<input type="checkbox" name="districtList" value="`+$(this).val()+`"><span>`+$(this).val()+`</span>`);
                    }
                    
               
                 });
            });    
            
          
            
            
            
        })         
 
   

        //上傳圖片
         $(document).ready(function(){
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
	        //動態選擇=>(當性別人數選擇為單數時,年齡只出現單數,複數則出現年齡範圍)
	        function selectGenderToAge(values){
	        if(values=="1男"||values=="1女"){
	            $("#ageDiv").show();
	            $("#ageRange").hide();           
	        }else{
	            $("#ageDiv").hide();
	            $("#ageRange").show();
	        }      
	        }
	        
    