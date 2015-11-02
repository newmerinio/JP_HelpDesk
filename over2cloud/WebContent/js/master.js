
function getSubDeptViaAjax(subDeptAjaxDiv)
{
	alert(document.getElementById("dept").value+"dept");
	var dept=document.getElementById("dept").value;
	$.ajax({
	    type : "post",
	    url : "/sfa/view/admin_pages/common_admin/SubDeptAjax.action?dept="+dept,
	    success : function(subdeptdata) {
        $("#"+subDeptAjaxDiv).html(subdeptdata);
        alert("sucess");
	},
	   error: function() {
            alert("error");
        }
	 });
	
}


/*
 * START
The code below will write
one get offering and suboffering mapped to master............
*/
function getcategoryViaAjax(deptAjaxDiv)
{
var selsubcategory = document.getElementById("selsubcategory").value;
if(selsubcategory!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa/DreamSol/AllMasterforProduct/getcategoryForProduct?selsubcategory='+selsubcategory,
		success : function(data) {
			$('#dropcategory').html(data);
		}	
		})
}
else{
	alert("error");
}
}

function getcategorynfSubcategoryViaAjax(deptAjaxDiv)
{
var selproduct = document.getElementById("selproduct").value;
if(selproduct!=-1){
$.ajax( {
	type : 'POST',
	url : '/sfa/DreamSol/AllMasterforProduct/getcategorysubcategoryForProduct?selproduct='+selproduct,
	success : function(data) {
		$('#dropocategorysubcat').html(data);
	}	
	})
}
else{
alert("error");
}
}
/*
 * END
The code below will write
one get offering and suboffering mapped to master............
*/

function getcategoryViaAjaxfordata()
{
    $.ajax({
    type : "post",
    url : "/sfa/DreamSol/AllMasterforProduct/getcategoryVia.action",
    success : function(categoryData) {
	$("#categoryData").html(categoryData);
},
   error: function() {
        alert("error");
    }
 });
	
	
    
}
function getViaAjaxfordata()
{
    $.ajax({
    type : "post",
    url : "/sfa/DreamSol/AllMasterforProduct/getViaAjaxforproductdata.action",
    success : function(productData) {
	$("#productData").html(productData);
},
   error: function() {
        alert("error");
    }
 });
	
	
    
}

function getViaAjaxforproductsize()
{
    $.ajax({
    type : "post",
    url : "/sfa/DreamSol/AllMasterforProduct/getViaAjaxforproductsizedata.action",
    success : function(productsizeData) {
	$("#productsizeData").html(productsizeData);
},
   error: function() {
        alert("error");
    }
 });
	
	
    
}

/*
 * START
/* This is a comment */
/* FOR geting first brand mapped details for prospective client............
*/
function getMappedofbrandViaAjax(AjaxDiv)
{
var selbrandname = document.getElementById("selbrandname").value;
if(selbrandname!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofBrand?selbrand='+selbrandname,
		success : function(data) {
			$('#dropvalue').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofproductViaAjax(AjaxDiv)
{
var selproduct = document.getElementById("selproduct").value;
if(selproduct!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofproduct?selproduct='+selproduct,
		success : function(data) {
			$('#dropvalueforproduct').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofsubofferingViaAjax(AjaxDiv)
{
var selsuboffering = document.getElementById("selsuboffering").value;
if(selsuboffering!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofsuboffering?selsuboffering='+selsuboffering,
		success : function(data) {
			$('#dropvalueforsuboofering').html(data);
		}	
		})
}
else{
	alert("error");
}
}

/*
 * END
/* This is a comment */
/* FOR geting First brand mapped details for prospective client............
*/

/*
 * START
/* This is a comment */
/* FOR geting Second brand mapped details for prospective client............
*/
function getMappedofbrandViaAjax1(AjaxDiv)
{
var selbrandname = document.getElementById("selbrandname").value;
if(selbrandname!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofBrand1?selbrand='+selbrandname,
		success : function(data) {
			$('#dropvalue1').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofproductViaAjax1(AjaxDiv)
{
var selproduct = document.getElementById("selproduct").value;
if(selproduct!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofproduct1?selproduct='+selproduct,
		success : function(data) {
			$('#dropvalueforproduct1').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofsubofferingViaAjax1(AjaxDiv)
{
var selsuboffering = document.getElementById("selsuboffering").value;
if(selsuboffering!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofsuboffering1?selsuboffering='+selsuboffering,
		success : function(data) {
			$('#dropvalueforsuboofer1').html(data);
		}	
		})
}
else{
	alert("error");
}
}
/*
 * END
/* This is a comment */
/* FOR geting Second brand mapped details for prospective client............
*/


/*
 * START
/* This is a comment */
/* FOR geting THIRD brand mapped details for prospective client............
*/
function getMappedofbrandViaAjax2(AjaxDiv)
{
var selbrandname = document.getElementById("selbrandname").value;
if(selbrandname!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofBrand2?selbrand='+selbrandname,
		success : function(data) {
			$('#dropvalue2').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofproductViaAjax2(AjaxDiv)
{
var selproduct = document.getElementById("selproduct").value;
if(selproduct!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofproduct2?selproduct='+selproduct,
		success : function(data) {
			$('#dropvalueforproduct2').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofsubofferingViaAjax2(AjaxDiv)
{
var selsuboffering = document.getElementById("selsuboffering").value;
if(selsuboffering!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofsuboffering2?selsuboffering='+selsuboffering,
		success : function(data) {
			$('#dropvalueforsuboofering2').html(data);
		}	
		})
}
else{
	alert("error");
}
}

/*
 * END
/* This is a comment */
/* FOR geting THIRD brand mapped details for prospective client............
*/



/*
 * START
/* This is a comment */
/* FOR geting FORTH brand mapped details for prospective client............
*/
function getMappedofbrandViaAjax3(AjaxDiv)
{
var selbrandname = document.getElementById("selbrandname").value;
if(selbrandname!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofBrand3?selbrand='+selbrandname,
		success : function(data) {
			$('#dropvalue3').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofproductViaAjax3(AjaxDiv)
{
var selproduct = document.getElementById("selproduct").value;
if(selproduct!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofproduct3?selproduct='+selproduct,
		success : function(data) {
			$('#dropvalueforproduct3').html(data);
		}	
		})
}
else{
	alert("error");
}
}
function getMappedofsubofferingViaAjax3(AjaxDiv)
{
var selsuboffering = document.getElementById("selsuboffering").value;
if(selsuboffering!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getMappedofsuboffering3?selsuboffering='+selsuboffering,
		success : function(data) {
			$('#dropvalueforsuboofering3').html(data);
		}	
		})
}
else{
	alert("error");
}
}

/*
 * END
/* This is a comment */
/* FOR geting FORTH brand mapped details for prospective client............
*/


/*
 * START
/* This is a comment */
/* FOR Subindustry Mapped details for prospective client............
*/

function getsubindustryViaAjax(AjaxDiv){
var selindustry = document.getElementById("industry").value;
if(selindustry!=-1){
$.ajax( {
		type : 'POST',
		url : '/sfa//DreamSol/AllMasterforclient/getsubindustry?selindustry='+selindustry,
		success : function(data) {
			$('#dropvalueforsub').html(data);
		}	
		})
}
else{
	alert("error");
}
}
/*
 * START
/* This is a comment */
/* FOR Subindustry Mapped details for prospective client............
*/
function getRefferdbyViaAjax(AjaxDiv){
	var refferd_by = document.getElementById("refferd_by").value;
	if(refferd_by!=-1){
	$.ajax( {
			type : 'POST',
			url : '/sfa//DreamSol/AllMasterforclient/getClientnameFromRefferd_by?refferd_by='+refferd_by,
			success : function(data) {
				$('#clientListvalue').html(data);
			}	
			})
	}
	else{
		alert("error");
	}
	}
