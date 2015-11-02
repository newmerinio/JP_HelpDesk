<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme"
	customBasepath="%{contextz}" />
<script src="<s:url value="/js/offering.js"/>"></script>
<script src="<s:url value="/js/organization.js"/>"></script>
<SCRIPT type="text/javascript">
	
	$.subscribe('level1',function(event,data)
			{
		setTimeout(function(){ $("#foldeffect2").fadeIn(); }, 10);
	    setTimeout(function(){ $("#foldeffect2").fadeOut(); }, 4000);
				$('select').find('option:first').attr('selected', 'selected');
			});

	$.subscribe('close', function(event,data)
	        {
       
		 document.getElementById("indicator2").style.display="none";
	        });
	//Code Starts
		$('#browse').bind('change', function()
	   {
	     var iSize = ($("#browse")[0].files[0].size / 1024);
	     
	     if (iSize / 1024 > 1)
	     {
	        if (((iSize / 1024) / 1024) > 1)
	        {
	            iSize = (Math.round(((iSize / 1024) / 1024) * 100) / 100);
	            $("#krbrowse").html("File Size Is "+ iSize + "Gb");
	        }
	        else
	        {
	            iSize = (Math.round((iSize / 1024) * 100) / 100)
	            $("#krbrowse").html("File Size Is "+ iSize + "Mb");
	        }
	     }
	     else
	     {
	        iSize = (Math.round(iSize * 100) / 100)
	        $("#krbrowse").html("File Size Is "+ iSize  + "kb");
	     }  
	     if(this.files[0].size<=21316300)
			{
			a=true;
			
			}
		else
			{
			a=false;
			}
	  });
	

	

	
	
</script>
<script type="text/javascript">

function viewDept()
{

	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif><br/>Loading...</center>");
	var conP = "<%=request.getContextPath()%>";
	$.ajax({
	    type : "post",
	    url : conP+"/view/Over2Cloud/KRLibraryOver2Cloud/beforeKrView.action?KRSharedbyWithMe=1&modifyFlag=0&deleteFlag=0&formater=1&header=1",
	    success : function(data) 
	    {
			$("#"+"data_part").html(data);
	    },
	    error: function() 
	    {
            alert("error");
        }
	 });
	}
</script>

</head>
			<s:form id="formone" name="formone"
				namespace="/view/Over2Cloud/KRLibraryOver2Cloud" action="afterModifyUploadKR"
				theme="simple" method="post" enctype="multipart/form-data">

					<div class="newColumn">
		              	<div class="leftColumn1"><h4 style="margin-right: 43px;">KR Id :</h4></div>
		              		<div class="rightColumn1">
		                   		<s:textfield name="kr_id" id="kr_id" readonly="true" value="%{id}" maxlength="50" cssClass="textField"  placeholder="Enter Data" cssStyle="margin:0px 0px 10px 0px;"/>
				            </div>
		
				<div class="newColumn">
				<div class="leftColumn1"><h4>File:</h4></div>
				<div class="rightColumn1">
				<s:file name="browse" id="browse"  style="margin-top: -32px;
margin-left: 115px;"/>
				<div id="krbrowse"></div>
				</div>
				<center><img id="indicator2"
					src="<s:url value="/images/indicator.gif"/>" alt="Loading..."
					style="display: none" /></center>
				</div>

				
	<div class="clear"></div>
   							<div class="fields">
							<div style="width: 100%; text-align: center; padding-bottom: 10px;">
								<sj:submit 
								     targets="orglevel1" 
								     clearForm="true"
								     value="  Add  " 
								     effect="highlight"
								     effectOptions="{ color : '#222222'}"
								     effectDuration="5000"
								     button="true"
								     onCompleteTopics="level1"
								     cssClass="button"
								     indicator="indicator2"
								     onBeforeTopics="validate"
							     />
							    <sj:a 
						button="true" href="#"
						onclick="viewDept();"
						>
						Cancel
					</sj:a> <sj:div id="foldeffect2"  effect="fold">
                    <div id="orglevel1"></div>
               </sj:div>
							</div>
						</div>
						<div class="clear"></div>

</s:form></html>