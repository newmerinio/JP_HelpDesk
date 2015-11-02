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
$.subscribe('beforeOfferingLevel1',function(event,data)
		{
			setParameters("offeringlevel1", "uploadExcel", "mapped_offering_level1", "offering_level1","1","");
		}); 
$.subscribe('beforeOfferingLevel2',function(event,data)
		{
			setParameters("offeringlevel2", "uploadExcel", "mapped_offering_level2", "offering_level2","2",$("#verticalname").val());
		});
$.subscribe('beforeOfferingLevel3',function(event,data)
		{
			setParameters("offeringlevel3", "uploadExcel", "mapped_offering_level3", "offering_level3","3",$("#offeringname").val());
		});
$.subscribe('beforeOfferingLevel4',function(event,data)
		{
			setParameters("offeringlevel4", "uploadExcel", "mapped_offering_level4", "offering_level4","4",$("#subofferingname").val());
		});
$.subscribe('beforeOfferingLevel5',function(event,data)
		{
			setParameters("offeringlevel5", "uploadExcel", "mapped_offering_level5", "offering_level5","5",$("#variantname").val());
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

$.subscribe('refreshAllForUpload',function(event,data)
		{
			excelUpload();
		});

function okButton()
{
	$('#AssetBasicDialog').dialog('close');
	excelUpload();
}

function excelUpload()
{
	$("#data_part").html("<br><br><br><br><br><br><br><br><br><br><br><br><br><br><center><img src=images/ajax-loaderNew.gif></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/offering/beforeOfferingUpload.action",
	    success : function(subdeptdata) {
       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
            alert("error");
        }
	 });
}


</SCRIPT>
<script type="text/javascript">
function returnOffering()
{
	$("#data_part").html("<br><br><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center>");
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/wfpm/offering/beforeOfferingView.action?modifyFlag=0&deleteFlag=0",
	    success : function(subdeptdata) {
	       $("#"+"data_part").html(subdeptdata);
	},
	   error: function() {
	            alert("error");
	        }
	 });
}
</script>
<script type="text/javascript">
function resetForm(formId)
{
	$('#'+formId).trigger("reset");
}
</script>
<STYLE type="text/css">
.user_form_text_new{width:200px; text-align:left; margin-right:10px; font-size:12px;  font-weight:600; float:left; line-height:40px;}
</STYLE>
</head>
<body>
<div class="clear"></div>
<div class="list-icon">
	<div class="head">Offering</div><div class="head"><img alt="" src="images/forward.png" style="margin-top:20%; float: left;"></div><div class="head">Excel Upload</div>
</div>
<div class="container_block">
<div style=" float:left; padding:10px 1%; width:98%;">

<div class="clear"></div>
<div class="border">
<!-- Offering level 1 --------------------------------------------------------------------------------------------------------------->
<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/excel" action="uploadOffering" theme="simple"  method="post"
	enctype="multipart/form-data" >
	<s:hidden id="tableName" name="tableName"></s:hidden>
	<s:hidden id="downloadType" name="downloadType"></s:hidden>
	<s:hidden id="mappedTableName" name="mappedTableName"></s:hidden>
	<s:hidden id="masterTableName" name="masterTableName"></s:hidden>
	<s:hidden id="level" name="level"></s:hidden>
	<s:hidden id="productMappedId" name="productMappedId"></s:hidden>
	
	
	<s:if test="levelOfOffering > 0">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn">
					Upload First level data:
					</div>
					<div class="rightColumn">
						<s:file name="excel" />					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
						<sj:submit
			                  openDialog="AssetBasicDialog"
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  cssClass="submit"
			                  onBeforeTopics="beforeOfferingLevel1"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					</div>
					<div class="rightColumn">
						<a id='astUpload1' style="cursor: pointer;" onclick="getCurrentColumn('uploadExcel','mapped_offering_level1','offering_level1','uploadColumnAstDialog','uploadAstColumnDiv','${offeringLevel1Name}','1')">
       						<font color="#194d65"><U>Download Format</U></font>
       					</a>					
					</div> 
				</div>
			</div>
	</s:if>			
			
	<div class="clear"></div>
	<!-- Offering level 2 --------------------------------------------------------------------------------------------------------->
	<s:if test="levelOfOffering > 1">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn">
						<s:select 
	                              id="verticalname"
	                              name="verticalname" 
	                              list="offeringLevel1"
	                              headerKey="-1"
	                              headerValue="%{offeringLevel1Name}"  
	                              cssClass="textField"
	                              >
	                    </s:select>
					</div>
					<div class="rightColumn">
						<s:file name="excel" />					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
						<sj:submit
			                  openDialog="AssetBasicDialog"
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  cssClass="submit"
			                  onBeforeTopics="beforeOfferingLevel2"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					</div>
					<div class="rightColumn">
						<a id='astUpload2' style="cursor: pointer;"  onclick="getCurrentColumn('uploadExcel','mapped_offering_level2','offering_level2','uploadColumnAstDialog','uploadAstColumnDiv','${offeringLevel2Name}','2')">
       						<font color="#194d65"><U>Download Format</U></font>
       					</a>					
					</div>
				</div>
			</div>
	</s:if>

	<div class="clear"></div>
	<!-- Offering level 3 --------------------------------------------------------------------------------------------------------------->
	<s:if test="levelOfOffering > 2">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn">
						<s:select 
		                              id="offeringname"
		                              name="offeringname" 
		                              list="offeringLevel2"
		                              headerKey="-1"
		                              headerValue="%{offeringLevel2Name}"  
		                              cssClass="textField"
		                              >
		                   </s:select>
					</div>
					<div class="rightColumn">
						<s:file name="excel" />					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
						<sj:submit
			                  openDialog="AssetBasicDialog"
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  cssClass="submit"
			                  onBeforeTopics="beforeOfferingLevel3"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					</div>
					<div class="rightColumn">
						<a id='astUpload3' style="cursor: pointer;"  onclick="getCurrentColumn('uploadExcel','mapped_offering_level3','offering_level3','uploadColumnAstDialog','uploadAstColumnDiv','${offeringLevel3Name}','3')">
       						<font color="#194d65"><U>Download Format</U></font>
       					</a>					
					</div> 
				</div>
			</div>
	</s:if>			
			
	<div class="clear"></div>		
	<!-- Offering level 4 --------------------------------------------------------------------------------------------------------------->
	<s:if test="levelOfOffering > 3">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn">
						<s:select 
		                              id="subofferingname"
		                              name="subofferingname" 
		                              list="offeringLevel3"
		                              headerKey="-1"
		                              headerValue="%{offeringLevel3Name}" 
		                              cssClass="textField"
		                              >
		                   </s:select>
					</div>
					<div class="rightColumn">
						<s:file name="excel" />					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
						<sj:submit
			                  openDialog="AssetBasicDialog"
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  cssClass="submit"
			                  onBeforeTopics="beforeOfferingLevel4"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					</div>
					<div class="rightColumn">
						<a id='astUpload4' style="cursor: pointer;"  onclick="getCurrentColumn('uploadExcel','mapped_offering_level4','offering_level4','uploadColumnAstDialog','uploadAstColumnDiv','${offeringLevel4Name}','4')">
       						<font color="#194d65"><U>Download Format</U></font>
       					</a>					
					</div> 
				</div>
			</div>
	</s:if>

	<div class="clear"></div>
	<!-- Offering level 5 ----------------------------------------------------------------------------------------------------------------------->
	<s:if test="levelOfOffering > 4">
			<div class="menubox">
				<div class="newColumn">
					<div class="leftColumn">
						<s:select 
		                              id="variantname"
		                              name="variantname" 
		                              list="offeringLevel4"
		                              headerKey="-1"
		                              headerValue="%{offeringLevel4Name}" 
		                              cssClass="textField"
		                              >
		                   </s:select>
					</div>
					<div class="rightColumn">
						<s:file name="excel" />					
					</div> 
				</div>
				<div class="newColumn">
					<div class="leftColumn">
						<sj:submit
			                  openDialog="AssetBasicDialog"
			                  value=" Upload "
			                  targets="createAssetBasic"
			                  clearForm="true"
			                  button="true"
			                  onBeforeTopics="beforeOfferingLevel5"
			                  onCompleteTopics="resetAllSelect"
                  				>
        				</sj:submit>
					</div>
					<div class="rightColumn">
						<a id='astUpload5' style="cursor: pointer;"  onclick="getCurrentColumn('uploadExcel','mapped_offering_level5','offering_level5','uploadColumnAstDialog','uploadAstColumnDiv','${offeringLevel5Name}','5')">
       						<font color="#194d65"><U>Download Format</U></font>
       					</a>					
					</div> 
				</div>
			</div>
	</s:if>
	<br>
	
		<div class="clear"></div>
		<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button" style="text-align: center;">
	                        <sj:a 
						     	name="Reset"  
								href="#" 
								cssClass="submit" 
								indicator="indicator" 
								button="true" 
								onclick="resetForm('formone');"
							>
			  				Reset
							</sj:a>
	                <sj:a
						button="true"
						cssClass="submit"
						onclick="returnOffering()"
					>
					Back
					</sj:a>	
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
     </div>

</s:form>
</div>
 <sj:dialog
           id="AssetBasicDialog"
           autoOpen="false"
           closeOnEscape="true"
           modal="true"
           title="Offering Import Status"
           width="600"
           height="350"
           showEffect="slide"
           hideEffect="explode"
           indicator="dialogIndicator"
           buttons="{ 
					'OK':function() { okButton(); }
					}"
  			onCloseTopics="refreshAllForUpload"
           >
           <sj:div id="foldeffectExcel" effect="fold">
              <div id="saveExcel"></div>
           </sj:div>
           <div id="createAssetBasic"></div>
 	</sj:dialog>
    <div id="dialogIndicator" style="display: none;"><center><img src=images/ajax-loaderNew.gif style='margin-top: 15%;'></center></div>   	              
<sj:dialog id="uploadColumnAstDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
	<div id="uploadAstColumnDiv"></div>
</sj:dialog>
</div>
</div>
</body>
</html>