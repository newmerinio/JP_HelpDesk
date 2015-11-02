function getEmpNameMobViaAjax(destAjaxDiv)
 {
	$.ajax({
	    type : "post",
	    url : "/sfa/view/hradmin_pages/employee_admin/EmpNameAjax.action?destination="+destAjaxDiv,
	    success : function(empNamedata) {
        $("#"+destAjaxDiv).html(empNamedata);
       
	},
	   error: function() {
            alert("error");
        }
	 });
}


function getEmpDataViaAjax(destination)
{
	var empNameNMob=document.getElementById("empName"+destination).value;
	var splitempNameNMob=empNameNMob.split("-");
	var empName=splitempNameNMob[0];
	var empMob=splitempNameNMob[1];
	document.getElementById(destination+"mobone").value=empMob;
}

function AjexonclickLevel(desigLevelvalue33,level,divforcontry)
{
	var Level=level;
	var Contry=document.getElementById("country").value; 
	if(Contry==-1){AjexonContryState(divforcontry,"xyz");}
	else{AjexonContryState(divforcontry,Contry);}
	
	$.ajax
	  (
	    {
	        type: "post",
	        
	        url : "/sfa/view/hradmin_pages/employee_admin/desigLevelAjex.action?level="+Level,
	        success : function(LevelDesigValue) 
	        {
	    	$("#"+desigLevelvalue33).html(LevelDesigValue);},
	        error: function()
	        {alert("error");}
	    }
	  );
}

function AjexonContryState(contrydiv,contry)
{
	var level=document.getElementById("selectlevel").value;
	if(level==4 || level==-1){}
	else
	{
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/view/hradmin_pages/employee_admin/CountryAjex.action?conutry="+contry,
		        success : function(contrydesigvalue) 
		        {
		    	$("#"+contrydiv).html(contrydesigvalue);},
		        error: function()
		        {alert("error");}
		    }
		  );
	}
	
}

function AjexonZoneArea(areawiseuser,areaid)
{
	var desig=document.getElementById("desg").value;
	AjexdesignationForparti('desigwiseuser',desig);
	var level=document.getElementById("selectlevel").value;
	if(level==3 || level==-1)
	{
		
	}
	else
	{
		$.ajax
		  (
		    {
		    	
		        type: "post",
		        url : "/sfa/view/hradmin_pages/employee_admin/areaAjex.action?areaid="+areaid,
		        success : function(areavalue) 
		        {
		    	$("#"+areawiseuser).html(areavalue);},
		        error: function()
		        {alert("error");}
		    }
		  );
	}
	
}
function onclick33()
{

}
function areaforEmployee(area)
{
	var desig=document.getElementById("desg").value;
	AjexdesignationForparti('desigwiseuser',desig,area);

}

function AjexdesignationForparti(desigwiseuser,desigvalue,area)
{ 
	var level=document.getElementById("selectlevel").value;
	var location=document.getElementById("country").value;
	var zone=document.getElementById("zone").value;
	
	alert("location::"+location+"zone:::"+zone+"area:::"+area);
	if(level==-1)
	{
		alert("Please Select The Level Option Of Employee");
	}else 
	{
		$.ajax
		  (
		    {
		        type: "post",
		        url : "/sfa/view/hradmin_pages/employee_admin/desigAjex.action?desg="+desigvalue+"&level="+level+"&country="+location+"&zone="+zone+"&area="+area,
		        success : function(Desigvalue) 
		        {
		    	$("#"+desigwiseuser).html(Desigvalue);},
		        error: function()
		        {alert("error");}
		    }
		  );
		
	}
	
}

	
