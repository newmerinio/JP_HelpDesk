<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script src="<s:url value="/js/offering.js"/>"></script>
<script src="<s:url value="/js/excel/excel_upload_download.js"/>"></script>
<SCRIPT type="text/javascript">
$.subscribe('beforeClientUpload',function(event,data)
		{
			setParameters("client_basic_data#client_offering_mapping#client_contact_data", "uploadExcel", "mapped_client_configuration", "client_basic_configuration#client_offering_configuration#client_contact_configuration","client","");
		}); 

function setParameters(one, two, three, four, five, six)
{
	$('#tableName').val(one);
	$('#downloadType').val(two);
	$('#mappedTableName').val(three);
	$('#masterTableName').val(four);
	$('#level').val(five);
	$('#productMappedId').val(six);
	
}
$.subscribe('resetAllSelect',function(event,data)
		{
			$('select').find('option:first').attr('selected', 'selected');
		});

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
		      //  alert(colType);  //fieldsType[i]=first_name#t
		        var validationType = fieldtype[i].split("#")[3];
		       // $("#"+fieldsvalues).css("background-color","");
		        errZone.innerHTML="";
		        //alert(fieldsvalues+">>>>fieldsvalues");   
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
			                //$("#"+fieldsvalues).css("background-color","#ff701a");
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


function getCurrentColumn(downloadType,mappedTableName,masterTableName,dialogId,dataDiv,excelName,level)
{
	
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/excel/currentColumn.action",
	    data : "downloadType="+downloadType+"&mappedTableName="+mappedTableName+"&masterTableName="+masterTableName+"&excelName="+excelName+"&level="+level,
	    success : function(data) {
		$("#"+dataDiv).html(data);
		$("#"+dialogId).dialog('open');
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

//takeMeBack
function takeMeBack()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/client/beforeClientView.action",
	    data : "isExistingClient=0&mainHeaderName=Client",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
</SCRIPT>
<STYLE type="text/css">
.user_form_text_new{width:200px; text-align:left; margin-right:10px; font-size:12px;  font-weight:600; float:left; line-height:40px;}
</STYLE>
</head>

<body>
<div class="clear"></div>
<div class="list-icon">
	<!-- <div class="head">Import Client</div> -->
	<div class="head">Client</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:50%; float: left;"></div><div class="head">Excel Upload</div>
</div>

<div style=" float:left; width:100%;">
		<div class="border">
			<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
  		<div id="errZone" style="float:left; margin-left: 7px"></div>        
        </div>
<!-- Offering level 1 --------------------------------------------------------------------------------------------------------------->
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/excel" action="uploadOffering" theme="simple"  
	method="post" enctype="multipart/form-data" >
	
	<div style="float:left; border-color: black; background-color: rgb(255,99,71); color: white;width: 100%; font-size: small; border: 0px solid red; border-radius: 6px;">
             <div id="errZone" style="float:left; margin-left: 7px">
             </div> 
    </div>
	<s:hidden id="tableName" name="tableName" value="client_basic_data#"></s:hidden>
	<s:hidden id="downloadType" name="downloadType"></s:hidden>
	<s:hidden id="mappedTableName" name="mappedTableName" value="mapped_client_configuration"></s:hidden>
	<s:hidden id="masterTableName" name="masterTableName" value="client_basic_configuration#"></s:hidden>
	<s:hidden id="level" name="level" value="client"></s:hidden>
	<s:hidden id="productMappedId" name="productMappedId"></s:hidden>
	
	<div class="menubox">
			<span id="form2MandatoryFields" class="pIds" style="display: none; ">excelValidate#excel#abc#ex#,</span>
				<div class="newColumn">
					<div class="leftColumn">
					Upload Client data:
					</div>
					<div class="rightColumn">
						<s:file name="excel" id="excelValidate"/>					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
					Excel format:
					</div>
					<div class="rightColumn">
						<a id='astUpload1'
						style="cursor: pointer; text-decoration: underline; "	 
					    onclick="getCurrentColumn('uploadExcel','mapped_client_configuration','client_basic_configuration#client_offering_configuration#client_contact_configuration','uploadColumnAstDialog','uploadAstColumnDiv','Client','client')">
						<font color="#194d65">Download Format</font>
						</a>				
					</div> 
				</div>
				
				<div class="clear"></div>
				
				<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				         <sj:submit
			                  
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  onBeforeTopics="beforeClientUpload,validate"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					    
					    <sj:a 
							     	name="Cancel"  
									href="#" 
									targets="result" 
									cssClass="button" 
									indicator="indicator" 
									button="true" 
									onclick="takeMeBack()"
								>
								  	Back
								</sj:a>
			    </div>
			</div>
</s:form>
</div>
</div>
 
 <sj:dialog
                  id="AssetBasicDialog"
                  autoOpen="false"
                  closeOnEscape="true"
                  modal="true"
                  title="Client Basic Detail Upload Via Excel"
                  width="600"
                  height="350"
                  showEffect="slide"
                  hideEffect="explode">
                  <sj:div id="foldeffectExcel" effect="fold">
                     <div id="saveExcel"></div>
                  </sj:div>
                  <div id="createAssetBasic"></div>
        	</sj:dialog>              
<sj:dialog id="uploadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Column Name" 
	loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="uploadAstColumnDiv"></div>
</sj:dialog>

</body>
</html>