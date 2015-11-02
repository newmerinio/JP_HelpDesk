function fetchEmployeeVal(val,targetid)
{
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/EmployeeLocation/fetchEmployee.action?deptId="+val,
	    success : function(subdeptdata)
	    {
			$('#'+targetid).html(subdeptdata);
	    },
	    error: function() {
	            alert("error");
	        }
	 });
	
}
function fetchStateMultiple(data,targetid)
{
	var value=$("#"+data).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/EmployeeLocation/fetchStateMulti.action?country="+value,
	    success : function(subdeptdata)
	    {
			$('#'+targetid).html(subdeptdata);
	    },
	    error: function() {
	            alert("error");
	        }
	 });
}
function fetchCityMultiple(data,targetid)
{
	var value=$("#"+data).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/EmployeeLocation/fetchCityMulti.action?state="+value,
	    success : function(subdeptdata)
	    {
			$('#'+targetid).html(subdeptdata);
	    },
	    error: function() {
	            alert("error");
	        }
	 });
}
function fetchTerritoryMultiple(data,targetid)
{
	var value=$("#"+data).val();
	$.ajax({
	    type : "post",
	    url : "view/Over2Cloud/WFPM/EmployeeLocation/fetchTerritoryMulti.action?city="+value,
	    success : function(subdeptdata)
	    {
			$('#'+targetid).html(subdeptdata);
	    },
	    error: function() {
	            alert("error");
	        }
	 });
}