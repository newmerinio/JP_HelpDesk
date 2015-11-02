function validateLeadTime(id, scheduletime)
				{
				var userselect = $("#userselect").val();
				$.ajax({
					type : "post",
					url  : "view/Over2Cloud/WFPM/Lead/validateLeadTimeForOffering.action",
					data : "id="+id+"&userselect="+userselect+"&scheduletime="+scheduletime,
					success : function(data){
						document.getElementById("timeValidationLead").style.display="block";
						//$("#timeValidationLead").html(data.timestatuslead);
						 $("#leadtextdataid").val(data.timestatuslead);
					}, 
					error   : function(){
						
					}	
			});
				}

function toggle_visibility(id1,id2) {
	if(document.getElementById(id1).style.display == 'block'){
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"inactive";
		sub1.style.display	=	"none";
	}else{
		var sub1			=	document.getElementById(id1);
		var sub2			=	document.getElementById(id2);
		sub2.className		=	"active";
		sub1.style.display	=	"block";
	}
}

$.subscribe('level2', function(event,data)
        {
          setTimeout(function(){ $("#leadgntion").fadeIn(); }, 10);
          setTimeout(function(){ $("#leadgntion").fadeOut(); }, 5000);
          $('select').find('option:first').attr('selected', 'selected');
          //leadPage(<%=userRights.contains("leac_ADD")? "1":"0"%>,<%=userRights.contains("leac_VIEW")? "1":"0"%>,<%=userRights.contains("leac_MODIFY")? "1":"0"%>,<%=userRights.contains("leac_DELETE")? "1":"0"%>);
          $("#takeActionBtnId").attr("disabled", "disabled");
        });

$.subscribe('cancelleadview',function(event,data)
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/Lead/beforeleadView.action",
	    data: "status="+status,
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	    
	  });
	event.originalEvent.options.submit = false;
});

var catnum;
$.subscribe('validate', function(event,data)
		{
			var timValidation = $("#leadtextdataid").val();
			
			 if(timValidation == "You can schedule." )
			 { $("#"+"leadtextdataid").css("background-color","#ff701a");
				 event.originalEvent.options.submit = true;
			}
			else
				{	$("#"+"leadtextdataid").css("background-color","#ff701a");
					event.originalEvent.options.submit = false;
				}
			var mystring=$(".pIds").text();
		    var fieldtype = mystring.split(",");
		    var pattern = /^\d{10}$/;
		    for(var i=0; i<fieldtype.length; i++)
		    {
		       
		        var fieldsvalues = fieldtype[i].split("#")[0];
		        if(catnum == "3" && (fieldsvalues == "statusselect" || fieldsvalues == "productselect" || fieldsvalues == "userselect"))
		        {
			        continue;
		        }
		        else if(catnum == "4" && (fieldsvalues == "userselect" || fieldsvalues == "time"))
		        {
			        continue;
		        }
		        
		        var fieldsnames = fieldtype[i].split("#")[1];
		        var colType = fieldtype[i].split("#")[2];   //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		        $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        if(fieldsvalues!= "" )
		        {
		            if(colType=="D"){
		            if($("#"+fieldsvalues).val()==null || $("#"+fieldsvalues).val()=="" || $("#"+fieldsvalues).val()=="-1")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select "+fieldsnames+" Value from Drop Down </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		            }
		            else if(colType =="T"){
		            if(validationType=="n"){
		            var numeric= /^[0-9]+$/;
		            if(!(numeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Numeric Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }   
		             }
		            else if(validationType=="an"){
		             var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		              }
		            }
		            else if(validationType=="a"){
		            if(!(/^[a-zA-Z ]+$/.test($("#"+fieldsvalues).val()))){
			            //alert("sdhvjsdhbj"+$("#"+fieldsvalues).val());
			            
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alphabate Values of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;    
		              }
		             }
		            else if(validationType=="m"){
		           if($("#"+fieldsvalues).val().length > 10 || $("#"+fieldsvalues).val().length < 10)
		            {
		                errZone.innerHTML="<div class='user_form_inputError2'>Enter 10 digit mobile number ! </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		            }
		            else if (!pattern.test($("#"+fieldsvalues).val())) {
		                errZone.innerHTML="<div class='user_form_inputError2'>Please Entered Valid Mobile Number </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		             }
		                else if(($("#"+fieldsvalues).val().charAt(0)!="9") && ($("#"+fieldsvalues).val().charAt(0)!="8") && ($("#"+fieldsvalues).val().charAt(0)!="7") && ($("#"+fieldsvalues).val().charAt(0)!="6"))
		             {
		                errZone.innerHTML="<div class='user_form_inputError2'>Entered Mobile Number Started with 9,8,7,6.  </div>";
		                $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		                $("#"+fieldsvalues).focus();
		                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		                event.originalEvent.options.submit = false;
		                break;
		               }
		             }
		             else if(validationType =="e"){
		             if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test($("#"+fieldsvalues).val())){
		             }else{
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Valid Email Id ! </div>";
		            $("#"+fieldsvalues).css("background-color","#ff701a");  //255;165;79
		            $("#"+fieldsvalues).focus();
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		             else if(validationType =="w"){
		            
		            
		            
		             }
		           }
		          
		            else if(colType=="TextArea"){
		            if(validationType=="an"){
		            var allphanumeric = /^[A-Za-z0-9 ]{3,20}$/;
		            if(!(allphanumeric.test($("#"+fieldsvalues).val()))){
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Enter Alpha Numeric of "+fieldsnames+"</div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;
		               }
		             }
		            else if(validationType=="mg"){
		             
		             
		             }   
		            }
		            else if(colType=="Time"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Time "+fieldsnames+" Value </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }   
		            }
		            else if(colType=="Date"){
		            if($("#"+fieldsvalues).val()=="")
		            {
		            errZone.innerHTML="<div class='user_form_inputError2'>Please Select Date "+fieldsnames+" Value  </div>";
		            setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
		            setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
		            $("#"+fieldsvalues).focus();
		            $("#"+fieldsvalues).css("background-color","#ff701a");
		            event.originalEvent.options.submit = false;
		            break;   
		              }
		             } 
		        }
		    }       
		});

function setData()
{
	catnum=document.getElementById('clienttypeName').value;
	
	if(catnum=="3")
	{
		document.getElementById("take").style.display="none";
		document.getElementById("hideStatus").style.display="none";
		document.getElementById("nextSchDiv").style.display="block";
	}
	else if(catnum=="4")
	{
		document.getElementById("take").style.display="block";
		document.getElementById("hideStatus").style.display="block";
		document.getElementById("mapEmpDiv").style.display="none";
		document.getElementById("nextSchDiv").style.display="none";
	}
	else
	{
		document.getElementById("take").style.display="block";
		document.getElementById("hideStatus").style.display="block";
		document.getElementById("mapEmpDiv").style.display="block";
		document.getElementById("nextSchDiv").style.display="block";
	}
}

$.subscribe('complete_close',function(event,data){
	$("#dailogbox").dialog('close');
});

function checkData()
{
	
	var catnum=document.getElementById('clienttypeName').value;
	if(catnum=="1" || catnum=="2" || catnum=="4"  )
	{
		$.ajax({
		    tradional: true,
		    type: "POST",
		    url: "view/Over2Cloud/WFPM/Lead/fillType.action",
		 data : {sid:catnum},
		    success: function(data){ 
			
			    var itemsData=data.split('#');
	           $('#statusselect').find('option').remove().end();
	            var list  = document.getElementById("statusselect");
	            list.add(new Option('Select Status', '-1'));
	            for(var i=0;i<itemsData.length-1;i++) {
	            	var items=itemsData[i].split(',');
	            	  list.add(new Option(items[0], items[1]));
	            	}
		       }
		});
	}
}

function resetForm3(formThree)
{
	$('#'+formThree).trigger("reset");
}