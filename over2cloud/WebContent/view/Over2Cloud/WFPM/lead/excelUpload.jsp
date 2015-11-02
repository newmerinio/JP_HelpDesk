<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/lead/LeadCommon.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('level1', function(event,data)
	       {
			//document.getElementById("indicator1").style.display="none";
	         setTimeout(function(){ $("#orglevel1").fadeIn(); }, 10);
	         setTimeout(function(){ $("#orglevel1").fadeOut(); }, 4000);
	       });



function clearAllErroDiv()
{
	$("div[id^='error']").each(function() {
  	  // `this` now refers to the current element
	     this.innerHTML="";
  	});
}
</script>
<script type="text/javascript">
$.subscribe('validate', function(event,data)
{
    var mystring = $(".pIds").text();
    var fieldtype = mystring.split(",");
    var pattern = /^\d{10}$/;
    for(var i=0; i<fieldtype.length; i++)
    {
        
        var fieldsvalues = fieldtype[i].split("#")[0];
        var fieldsnames = fieldtype[i].split("#")[1];
        var colType = fieldtype[i].split("#")[2]; 
        var validationType = fieldtype[i].split("#")[3];
        errZone.innerHTML="";
        if(fieldsvalues!="")
        {
           if(colType=="abc")
           {
            	
                if($("#"+fieldsvalues).val()=="")
                {
                	errZone.innerHTML="<div class='user_form_inputError2'>Please Select Excel File For Upload  </div>";
	                setTimeout(function(){ $("#errZone").fadeIn(); }, 10);
	                setTimeout(function(){ $("#errZone").fadeOut(); }, 2000);
	                $("#"+fieldsvalues).focus();
	                $("#"+fieldsvalues).css("background-color","#ff701a");
	                event.originalEvent.options.submit = false;
	                break;   
                }
                if($("#"+fieldsvalues).val()!="")   
                {
                	$('#AssetBasicDialog').dialog('open');
            } 
            

                }  
         }
        
        
     }   
});

function viewLeadMe()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
$.ajax({
    type : "post",
    url :  "view/Over2Cloud/WFPM/Lead/beforeleadView.action?modifyFlag=0&deleteFlag=0&formater=1&lostLead=0",
    success : function(subdeptdata) {
   $("#"+"data_part").html(subdeptdata);
},
   error: function() {
        alert("error");
    }
 });
}
</script>

</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
	<div class="head">Lead</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Excel Upload</div>
		<!-- <div class="head">Import Lead</div> -->
	</div>
	
	<div style=" float:left; width:100%;">
		<div class="border">
			<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
			<s:form id="formtwo" action="uploadExcel" theme="css_xhtml" method="post" enctype="multipart/form-data">
			<div class="menubox">
				<s:hidden name="upload4" value="assetDetail" />
				<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none;">excelValidate#Select Excel#abc#ex#,</span>
					<div class="leftColumn">Select Excel</div>
					<div class="rightColumn"><s:file name="excel" id="excelValidate"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
				</div>
				<div class="newColumn">
					<div class="leftColumn">Excel Format</div>
					<div class="rightColumn">
						<sj:a id='astUpload' onclick="getCurrentColumn('uploadExcel','leadDetail','uploadColumnAstDialog','uploadAstColumnDiv')" href="#">
			       			<font color="#194d65"><U>Download</U></font>
			       		</sj:a>
					</div>
				</div>
				<div class="clear"></div>
					<!-- Buttons -->
					<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				         <sj:submit 
				           	value=" Upload "
			                targets="createAssetBasic"
			                clearForm="true"
			                button="true"
			                onBeforeTopics="validate"
			                cssStyle="margin-right: 72px;"
				          />
					    
					    <sj:a 
							     	name="Cancel"  
									href="#" 
									targets="result" 
									cssClass="button" 
									indicator="indicator" 
									button="true" 
									onclick="viewLeadMe()"
									cssStyle="margin-left: 85px; margin-top: -28px;"
								>
								  	Back
								</sj:a>
					    </div>
				</div>
			</s:form>
		</div>
	</div>
	<sj:dialog id="uploadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" 
		title="Lead Column Name To Choose" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['center']" >
	<div id="uploadAstColumnDiv"></div>
</sj:dialog>
	<sj:dialog
        id="AssetBasicDialog"
        autoOpen="false"
        closeOnEscape="true"
        modal="true"
        title="Lead Details To Upload1111"
        width="1000"
        height="450"
        showEffect="slide"
        hideEffect="explode">
        <sj:div id="foldeffectExcel" effect="fold">
           <div id="saveExcel"></div>
        </sj:div>
        <div id="createAssetBasic"></div>
     </sj:dialog>
</body>
</html>
