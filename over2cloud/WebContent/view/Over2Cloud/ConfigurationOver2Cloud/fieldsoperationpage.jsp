<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>


<script type="text/javascript">
$.subscribe('closeDialog', function(event,data)
{
	 $("#mybuttondialog").dialog('close');
});
function selectcheck1(){ 
	$("#chk2").prop('checked', false);
}
function selectcheck2(){ 
	$("#chk1").prop('checked', false);
}
var v=true;
function editField(){
	if(v){
		$("#textfield").css('display', 'block');
		v=false;}
	else{
		$("#textfield").css('display', 'none');
		v=true;}
}
function displayViewProp(value,DIV)
{
	if(value=='false')
	{
		$("#"+DIV).show();	
	}
	else
	{
		$("#"+DIV).hide();	
	}
}
function showType(value) 
{
	if(value=='T' || value =='TA' )
	{
		$("#len").show();
		$("#drop").hide();
		$("#cal").hide();
		$("#file").hide();
		$("#size").hide();
	}
	else if(value=='D' || value == 'R' || value=='C')
	{
		$("#len").hide();
		$("#drop").show();
		$("#cal").hide();
		$("#file").hide();
		$("#size").hide();
	}
	else if(value=='Cal')
	{
		$("#len").hide();
		$("#drop").hide();
		$("#cal").show();
		$("#file").hide();
		$("#size").hide();
		$("#normalVal").hide();
	}
	else if(value=='F')
	{
		$("#size").show();
		$("#len").hide();
		$("#drop").hide();
		$("#cal").hide();
		$("#file").show();
		$("#normalVal").hide();
	}
	else
	{
		$("#len").hide();
		$("#drop").hide();
		$("#cal").hide();
		$("#file").hide();
		$("#size").hide();
		$("#normalVal").show();
	}

}
function onload()
{
	var col=$("#col").val();
	if(col=='T' || col=='F')
	{
		$("#len").show();
	}
}
onload();
</script>
<div id="savedata">
<div class="container_block"><div style=" float:left; padding:20px 0%; width:95%;">
<div class="form_inner" id="form_reg" style="width:100%; padding:0;">
<div style="float:left; width:100%;">

		<s:form id="formoness" name="formoness" cssClass="cssn_form" action="editefieldoperationwithAll" namespace="/view/CloudApps" theme="simple"  method="post"enctype="multipart/form-data" >
         <div class="form_menubox11" style="display: none">
          <span id="id"><s:property value="%{#parameters.id}"/></span>
            <span id="mapedtable"><s:property value="%{#parameters.mapedtable}"/></span>
            <s:textfield name="mapedtable" value="%{#parameters.mapedtable}"/>
             <s:textfield name="id" value="%{#parameters.id}"/>
             <s:textfield name="dataFor" value="%{#parameters.dataFor}"/>  
             <s:textfield name="comonmaped" value="%{#parameters.comonmaped}"/>
           <s:textfield id="col" value="%{#parameters.colType}" cssStyle="width:16%"/>
                    <s:textfield id="length" value="%{#parameters.length}"/>
                       <s:textfield name="dataFor" value="%{#parameters.dataFor}"/>
            </div>
             
        <%--  <div class="form_menubox11">
		          <div class="user_form_text11">Active</div>
		           <div class="user_form_input_chk"><s:checkbox name="titles" id="chk1"  onclick="selectcheck1();"/>
		           </div>
		     </div>
		         <s:if test="%{mandatoryFalg}">
		         <div class="form_menubox11">
		          <div class="user_form_text11">Inactive</div>
		         <div class="user_form_input_chk"><s:checkbox name="titless" id="chk2"   onclick="selectcheck2();"/>
		         </div>
				 </div>
				 </s:if> --%>
				
				  <%--    <div class="form_menubox11">
		          <div class="user_form_text11">New Field</div>
		         <div class="user_form_input_chk"><s:checkbox name="levelcheked" id="levelcheked" onclick="editField();"/></div>
				</div> --%>
			<%-- 	<div id="textfield" style="display: none;">
				 <div class="form_menubox11">
		          <div class="user_form_text11">Field Name</div>
		         <div class="user_form_input_chk"><s:textfield name="field_name" id="field_name"/></div>
		         </div>
		          <div class="form_menubox11">
		          <div class="user_form_text11">Field Type</div>
		         <div class="user_form_input_chk">
		         <s:select list="#{'T':'Text Box','D':'Drop Down','F':'File','Time':'Calender'}" name="field_type" id="field_type" cssStyle="width:95%;"></s:select>
		         </div>
				</div></div> --%>
				
                <div class="form_menubox11">
		          <div class="user_form_text11">Name:</div>
		           <div class="user_form_input_chk">
		            <s:textfield name="field_name" id="field_value" cssClass="textField" value="%{#parameters.levelname}"/></div>
		         </div>
		     <div class="form_menubox11">
		          <div class="user_form_text11">Mandatory:</div>
		         <div class="user_form_input_chk"><s:select name="mandatroy" id="mandatory" list="#{'1':'Yes','0':'No'}" /></div>
				</div>
				
				  <div class="form_menubox11">
		          <div class="user_form_text11">Type:</div>
		         <div class="user_form_input_chk">
		         <s:select list="#{'T':'Text Box','D':'Drop Down','F':'File','Cal':'Calender','R':'Radio','C':'Checkbox','PW':'Password','TA':'Text Area'}" name="colType" id="field_type" headerKey="-1" headerValue="Select Type" cssStyle="width:95%;" onchange="showType(this.value);"></s:select>
		         </div>
				</div>
				 <div class="form_menubox11" style="display: none;" id="file">
		         <div class="user_form_text11">Validation:</div>
		         <div class="user_form_input_chk"><s:select name="validationType" id="validation1" headerKey="-1" headerValue="Select Validation" list="#{'PDF':'PDF','Excel':'Excel','Image':'Image','Document':'Document'}" /></div>
				</div>
				  <div class="form_menubox11" style="display: none;" id="len">
		          <div class="user_form_text11">Length:</div>
		         <div class="user_form_input_chk"><s:textfield name="field_length" id="length" value="%{#parameters.length}"  cssClass="textField"/></div>
				</div>
				 <div class="form_menubox11" style="display: none;" id="size">
		          <div class="user_form_text11">Size:</div>
		         <div class="user_form_input_chk"><s:textfield name="field_length1" id="length" placeholder="Size in KB"  cssClass="textField"/></div>
				</div>
				  <div class="form_menubox11" style="display: none;" id="cal">
		          <div class="user_form_text11">Calender:</div>
		         <div class="user_form_input_chk">
		         <s:select list="#{'Date':'Date','Time':'Time','Date/Time':'Both'}" headerKey="-1" headerValue="Select Calender" name="colType1" id="field_type" cssStyle="width:95%;"></s:select>
		         </div>
		            <div class="form_menubox11">
		          <div class="user_form_text11">Validation:</div>
		         <div class="user_form_input_chk"><s:select name="validationType" id="validation1" headerKey="-1" headerValue="Select Validation" list="#{'DD-MM-YYYY':'DD-MM-YYYY','MM-DD-YYYY':'MM-DD-YYYY','YYYY-DD-MM':'YYYY-DD-MM','YYYY-MM-DD':'YYYY-MM-DD','MM-YYYY':'MM-YYYY','YYYY-MM':'YYYY-MM'}" /></div>
				</div>
				</div>
				
				 <div class="form_menubox11" style="display: none;" id="drop">
		          <div class="user_form_text11">Drop Down:</div>
		           <div class="user_form_input_chk">
		            <s:textfield name="drop_down_val" id="field_value" cssClass="textField" placeholder="Enter Value in CSV"/></div>
		         </div>
		            <div class="form_menubox11" id="normalVal" style="display: block; ">
		          <div class="user_form_text11">Validation:</div>
		         <div class="user_form_input_chk"><s:select name="validationType" id="validation" headerKey="-1" headerValue="Select Validation" list="#{'a':'Alphabet','n':'Numeric','an':'Alphanumeric','sc':'No Blank','e':'Email','m':'Mobile'}" /></div>
				</div>
				
				  <div class="form_menubox11">
		          <div class="user_form_text11">View:</div>
		         <div class="user_form_input_chk"><s:select name="hideOrShow" id="hideShow" list="#{'false':'Yes','true':'No'}" headerKey="true" headerValue="Select Hide Show" onchange="displayViewProp(this.value,'viewprop');"/></div>
		         
				</div>
		         
		         <div id="viewprop" style="display: none;">
		         	  <div class="form_menubox11">
		          <div class="user_form_text11">Alignment:</div>
		         <div class="user_form_input_chk"><s:select name="align" id="align" headerKey="center" headerValue="Select Align" list="#{'center':'Center','left':'Left','right':'Right'}" /></div>
				</div>
				  <div class="form_menubox11">
		          <div class="user_form_text11">Searchable:</div>
		         <div class="user_form_input_chk"><s:select name="search" id="search" headerKey="true" headerValue="Select Search" list="#{'true':'Yes','false':'No'}" /></div>
				</div>
				  <div class="form_menubox11">
		          <div class="user_form_text11">Width:</div>
		         <div class="user_form_input_chk"><s:textfield name="width" id="width" cssClass="textField" value="200" placeholder="Enter Width" /></div>
				</div>
				  <div class="form_menubox11">
		          <div class="user_form_text11">Editable:</div>
		         <div class="user_form_input_chk"><s:select name="editable" id="edit" list="#{'true':'Yes','false':'No'}" headerKey="true" headerValue="Select Edit"/></div>
				</div>
		         </div>
		         <sj:submit  targets="target1" 
	                        id="buttonid"
	                        clearForm="true"
	                        value="  Save  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        onCompleteTopics="closeDialog "
	                        button="true"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        cssStyle="margin-left: 100px;">
	             </sj:submit>
			</s:form>
			</div></div>
			</div>
			</div>
			</div>