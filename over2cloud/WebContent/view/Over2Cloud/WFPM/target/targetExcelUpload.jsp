<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s"  uri="/struts-tags"%>
    <%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<SCRIPT type="text/javascript">
	function getCurrentColumn(downloadType,moduleName,dialogId,dataDiv)
	{
		$.ajax({
		    type : "post",
		    url : "view/Over2Cloud/wfpm/target/showTargetColumnExcel.action?downloadType="+downloadType+"&download4="+moduleName,
		    success : function(data) {
			$("#"+dataDiv).html(data);
			$("#"+dialogId).dialog('open');
		},
		   error: function() {
		        alert("error");
		    }
		 });
	} 
</SCRIPT>
</head>
<body>
	<div class="clear"></div>
	<div class="list-icon">
		<div class="head"><s:property value="#parameters.headerName"/> </div>
	</div>
	<div style=" float:left; width:100%;">
		<div class="border">
			<div style="float:right; border-color: black; background-color: rgb(255,99,71); color: white;width: 90%; font-size: small; border: 0px solid red; border-radius: 8px;">
				<div id="errZone" style="float:left; margin-left: 7px"></div>
			</div>
			<s:form id="formone" name="formone" namespace="/view/Over2Cloud/wfpm/target" action="uploadOBDTarget" theme="simple"  method="post"enctype="multipart/form-data" >
			<div class="menubox">
				<s:hidden name="upload4" value="assetDetail" />
				<div class="clear"></div>
				<div class="newColumn">
					<div class="leftColumn">Target On</div>
					<div class="rightColumn">
						<s:radio id="targetOn" cssStyle="margin-left:10px" 
							name="targetOn" list="#{'kpi':'KPI','offering':'Offering'}" 
							value="kpi"/>
					</div>
				</div>
				<div class="clear"></div>
				
				<div class="newColumn">
				<span id="form2MandatoryFields" class="pIds" style="display: none;">excelValidate#Select Excel#abc#ex#,</span>
					<div class="leftColumn">Select Excel</div>
					<div class="rightColumn"><s:file name="excel" id="excelValidate"/><div id="errAssetExcel" style="display: inline; width: 20px;"></div></div>
				</div>
				<div class="newColumn">
					<div class="leftColumn">Excel Format</div>
					<div class="rightColumn">
						<sj:a id='astUpload' onclick="getCurrentColumn('downloadExcel','kpiOfferingTarget','downloadColumnAllModDialog','downloadAllModColumnDiv');" href="#">
			       			<font color="#194d65">Download</font>
			       		</sj:a>
					</div>
				</div>
				<div class="clear"></div>
					<!-- Buttons -->
					<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
						<div style="width: 100%; text-align: center; padding-bottom: 10px;">
				         <sj:submit 
				           	value=" Upload "
			                targets="saveExcel"
			                clearForm="true"
			                button="true"
			                onBeforeTopics="validate"
			                onCompleteTopics="complete"
				          />
					    
					    <sj:a 
					     	name="Cancel"  
							href="#" 
							cssClass="button" 
							indicator="indicator" 
							button="true" 
							onclick="returnPage()"
						>
						  	Cancel
						</sj:a>
					    </div>
				</div>
			 </s:form>
			 <sj:dialog id="downloadColumnAllModDialog" modal="true" effect="slide" autoOpen="false" width="300" height="500" title="Lead Generation Column Name" loadingText="Please Wait" overlayColor="#fff" overlayOpacity="1.0" position="['left','top']" >
				<div id="downloadAllModColumnDiv"></div>
			</sj:dialog>
		</div>
	</div>
</body>
</html>