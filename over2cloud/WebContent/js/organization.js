//ajax for getting the main organization details in level 2

var empFalg;
var a;
var tempUSer=null;
function userCust(userFlag)
{
	tempUSer=userFlag;
	alert(tempUSer);
}
function selectorgLevelORG(orgselectedLevel,employeeFalg)
{
	empFalg=employeeFalg;
	a=orgselectedLevel;
	document.getElementById("orgLevel").value=orgselectedLevel;
	document.getElementById("hideThis").style.display="none";
	document.getElementById("showhis").style.display="block";
}

function selectorgLevelORG1(orgselectedLevel,employeeFalg)
{
	empFalg=employeeFalg;
	a=orgselectedLevel;
	document.getElementById("orgLevel").value=orgselectedLevel;
	document.getElementById("hideThis1").style.display="none";
	document.getElementById("showhis1").style.display="block";
}

function selectorgLevelORG2(orgselectedLevel,employeeFalg)
{
	empFalg=employeeFalg;
	a=orgselectedLevel;
	document.getElementById("orgLevel").value=orgselectedLevel;
	document.getElementById("hideThis2").style.display="none";
	document.getElementById("showhis2").style.display="block";
}


function getCompanyViaAjax()
{
	   $.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/organizationDetails.action",
		    success : function(companyData) {
			$("#ajaxOrganizationDetails").html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
			
}
function allRegisteredLocation()
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/commonModules/registredLevel2InDropDowns.action",
	    success : function(companyData) {
		$("#regsiteredLoc").html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getallLevel3Regsitration()
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/commonModules/getallLevel3Regsitration.action",
	    success : function(companyData) {
		$("#regsiteredLevel3").html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
function getallLevel4Regsitration()
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/commonModules/getallLevel4Regsitration.action",
	    success : function(companyData) {
		$("#regsiteredLevel4").html(companyData);
	},
	   error: function() {
	        alert("error");
	    }
	 });
}
//dept & sub-dept ajax calling for hierarchy
var userFalg;//for dept data fatching
var flag;//for sub dept data fatching
var getEmpFlagWithSubDept;//for employee data fatching based on sub dept
var level4Div;//for level 4 data display
var level5Div;//for level 5 ddata div
var subDeptId;//for sub dept data div
var deptDiv;//for dept data div
var categoryFlag;//for getting the category list on click of sub dept or dept
function getNextLowerLevel3ForDept(orgnId,userData,flagTemp,getEmpFlagWithSubDeptTemp,divID,deptDiv1,level4Div1,level5Div1,subDeptId1,categoryFlag1)
{
	userFalg=userData;
	flag=flagTemp;
	level4Div=level4Div1;
	level5Div=level5Div1;
	subDeptId=subDeptId1;
	getEmpFlagWithSubDept=getEmpFlagWithSubDeptTemp;
	deptDiv=deptDiv1;
	categoryFlag=categoryFlag1;
	var orgnLevel=document.getElementById("orgLevel").value;
	document.formone.levelOforganization.value="2";
	//var a=document.formone.level.value;
	if(orgnLevel>2)
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getNextLowerLevel3ForDept.action?orgIdInLevel="+orgnId,
		    success : function(companyData) {
			$("#"+divID).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==2 && userFalg=="b")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDeptData.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(companyData) {
			$("#"+deptDiv).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==2 && userFalg=="b" && flag=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDesignation.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(desgData) {
			$("#designID").html(desgData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function getNextLowerLevel4ForDept(orgnId,divId,deptDiv1)
{
	var orgnLevel=document.getElementById("orgLevel").value;
	document.formone.levelOforganization.value="3";
	//var a=document.formone.level.value;
	if(orgnLevel>3)
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getNextLowerLevel4ForDept.action?orgIdInLevel="+orgnId,
		    success : function(companyData) {
			$("#"+level4Div).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==3 && userFalg=="b")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDeptData.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(companyData) {
			$("#"+deptDiv).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==3 && userFalg=="b" && flag=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDesignation.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(desgData) {
			$("#designID").html(desgData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function getNextLowerLevel5ForDept(orgnId,divId,deptDiv1)
{
	var orgnLevel=document.getElementById("orgLevel").value;
	document.formone.levelOforganization.value="4";
	//var a=document.formone.level.value;
	if(orgnLevel>4)
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getNextLowerLevel5ForDept.action?orgIdInLevel="+orgnId,
		    success : function(desgData) {
			$("#"+level5Div).html(desgData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==4 && userFalg=="b")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDeptData.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(companyData) {
			$("#"+deptDiv).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==4 && userFalg=="b" && flag=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDesignation.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(desgData) {
			$("#designID").html(desgData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}
function getDeptData(orgnId,deptDivId1)
{
	var orgnLevel=document.getElementById("orgLevel").value;
	document.formone.levelOforganization.value="5";
	//var a=document.formone.level.value;
	if(a==5 && userFalg=="b")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDeptData.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(companyData) {
			$("#"+deptDiv).html(companyData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	if(a==5 && userFalg=="b" && flag=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getDesignation.action?orgIdInLevel="+a+"&selectedorgId="+orgnId,
		    success : function(desgData) {
			$("#designID").html(desgData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
}

//get Subdept list based on the selected dept id
function getSubDept(deptID,status,divId)
{
	var deptLevel=document.getElementById("deptHierarchy").value;
	//alert("deptLevel"+deptLevel+"  flag "+flag+"  getEmpFlagWithSubDept  "+getEmpFlagWithSubDept);
	//alert("categoryFlag"+categoryFlag);
	if(flag=="1" || flag=="2" && deptLevel >1)
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/commonModules/getSubDept.action?deptID="+deptID,
		    success : function(subDeptData) {
			$("#"+subDeptId).html(subDeptData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(flag=="1" || flag=="2" && deptLevel==1 && getEmpFlagWithSubDept=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/hr/getEmpWithDept.action?subDeptID="+deptID+"&empFalg="+empFalg+"&tempUSer="+tempUSer,
		    success : function(EmpData) {
			$("#AjaxDivp").html(EmpData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(categoryFlag=="1" && deptLevel==1){
		alert("iinsde the getting the category data on dept basis");
	}
}


function getEmpListWithSubDeptName(subDeptID)
{
	if(getEmpFlagWithSubDept=="1")
	{
		$.ajax({
		    type : "post",
		    url : "/cloudapp/view/Over2Cloud/hr/getEmpWithSubDept.action?subDeptID="+subDeptID+"&empFalg="+empFalg+"&tempUSer="+tempUSer,
		    success : function(EmpData) {
			$("#AjaxDivp").html(EmpData);
		},
		   error: function() {
		        alert("error");
		    }
		 });
	}
	else if(categoryFlag=="1"){
		alert("iinsde the getting the category data on sub-dept basis");
	}
}

function getMobileNumber(id)
{
	$.ajax({
	    type : "post",
	    url : "/cloudapp/view/Over2Cloud/hr/getEmpMobileNumber.action?id="+id,
	    success : function(empMobData) {
		$("#AjaxDivMohb").html(empMobData);
		document.formone.name.value=document.formone.empnnna.value;
	},
	   error: function() {
	        alert("error");
	    }
	 });
}

//getting the user name exiatnase based on the 