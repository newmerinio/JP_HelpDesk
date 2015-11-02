<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" href="<s:url value="/css/style.css"/>" rel="stylesheet" />
<script type="text/javascript" src="<s:url value="/js/js.js"/>"></script>
<title>Resource ERP: View Qualification</title>
<s:url id="contextz" value="/template/theme" />
<sj:head locale="en" jqueryui="true" jquerytheme="mytheme" customBasepath="%{contextz}"/>
<script type="text/javascript">

function takeActionOnKR(cellvalue, options, rowObject) 
{
	//alert("takeActionOnKR");
    return "<a href='#' onClick='takeAction("+rowObject.take_action+","+rowObject.id+")'>" + cellvalue + "</a>";
    //return "<a href='#' onClick='takeAction("+rowObject.id+")'>" + cellvalue + "</a>";
}

function takeAction(valuepassed,KRID)
{
  if(valuepassed=='0')
  {
	
	alert("value of take action is>>>>   "+valuepassed);
	alert("value of KRID is>>>>   "+KRID);
	
    $.ajax({
	         type : "post",
	         url : "/cloudapp/view/Over2Cloud/KRLibraryOver2Cloud/takeActionOnKR.action?id="+KRID,
	         success : function(data)
	         {
                $("#takeActionDiv").html(data);
                $("#takeActionGrid").dialog('open');
	   		 },
	   		 error: function() 
	   		 {
          	   alert("error");
       		 }
		  });
	}
	else
	{
		$("#takeConfirmationDialog").dialog('open');
	}
  }


</script>
</head>
<body>
<s:url id="KRURLViewGrid" action="getDataForTakeAction" />
<center>
<sjg:grid 
          id="gridedittable"
          caption="KR Take Action"
          href="%{KRURLViewGrid}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorDelete="false"
          navigatorEdit="false"
          rowList="10,15,20,25"
          rowNum="10"
          rownumbers="-1"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="false"   
          loadingText="Requesting Data..."  
          width="800"   
          loadonce="true"
          >
          
          <sjg:gridColumn 
                            name="id"
                            index="id"
                            title="KR Id"
                            sortable="true"
                            width="150"
                            align="center"
                            search="false"
                            frozen="false" 
                            hidden="true"
                            >
          </sjg:gridColumn>
           
          
          <sjg:gridColumn 
                            name="krName"
                            index="krName"
                            title="KR Name"
                            width="100"
                            align="center"
                            frozen="false" 
                            >
          </sjg:gridColumn>
          
           <sjg:gridColumn 
                            name="download_date"
                            index="download_date"
                            title="Downloaded Date"
                            sortable="true"
                            width="150"
                            align="center"
                            >
          </sjg:gridColumn>
           
          
          <sjg:gridColumn 
                            name	=	"download_time"
                            index	=	"download_time"
                            title	=	"Downloaded Time"
                            width	=	"150"
                            align	=	"center"
                            >
          </sjg:gridColumn>
          
          <sjg:gridColumn 
                            name		=	"krName"
                            index		=	"krName"
                            title		=	"Take Action On" 
                            width		=	"100"
                            align		=	"center"
                            formatter   =   "takeActionOnKR"
                            >
          </sjg:gridColumn>
          
   </sjg:grid>
   </center>
   <sj:dialog
          id="takeActionGrid"
          showEffect="slide"
          hideEffect="explode"
          autoOpen="false"
          title="Take Action on KR"
          modal="true"
          width="560"
          height="280"
          draggable="true"
    	  resizable="true"
          >
          <div id="takeActionDiv"></div>

</sj:dialog>

<sj:dialog
          id					=		"takeConfirmationDialog"
          showEffect			=		"slide"
          hideEffect			=		"explode"
          autoOpen				=		"false"
          title					=		"Action Already Taken"
          modal					=		"true"
          width					=		"250"
          height				=		"100"
          draggable				=		"true"
    	  resizable				=		"true"
          >
          <div id="alreadyTaken" style="display:block;"><center>Action Already Taken.</center></div>
</sj:dialog>
</body>
</html>