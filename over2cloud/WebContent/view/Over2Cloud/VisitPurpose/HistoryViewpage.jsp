<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="clear"></div>
<s:hidden id="complID" value="%{complID}"></s:hidden>
<s:if test="complDetails.size()>0">
<table width="100%" border="1" style="border-collapse: collapse;">
     	<tr bgcolor="#D8D8D8">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Department' || key=='Alloted By'}">
                
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
    
        <tr>
         <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Alloted To' || key=='Started From'}">
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
            </s:if>
        </s:iterator>
        </tr>
        
        <tr bgcolor="#D8D8D8">
          <s:iterator value="%{complDetails}" status="status" >
            <s:if test="%{key=='Frequency' || key=='Ownership'}">
                
                    <td align="left" width="15%"><strong><s:property value="%{key}"/>:</strong></td>
                    <td align="left" width="30%" > <s:property value="%{value}"/></td>
                
            </s:if>
        </s:iterator>
        </tr>
</table>
</s:if>
<div class="clear"></div>
<br><div class="listviewBorder" style="margin-top: 5px;width: 100%;margin-left: 0px;" align="center">
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
    <tbody>
    <tr>
      <td>
      <table class="floatL" border="0" cellpadding="0" cellspacing="0">
        <tbody>
        <tr>
        <td class="pL10"> 
         </td></tr></tbody>
      </table>
      </td>
      <td class="alignright printTitle">
    	 <%-- <sj:a id="sendButton111Download" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadHisory();"></sj:a> --%>
     <sj:a id="sendButtonDownload" cssStyle="height:25px; width:32px"  buttonIcon="ui-icon-arrowthickstop-1-s" cssClass="button" title="Download" button="true"  onclick="downloadHisory();"></sj:a>
      </td>
    </tr>
    </tbody>
    </table>
</div>
</div>
</div>
	<s:url id="fullViewActivity" action="fullViewActivity" escapeAmp="false">
	     <s:param name="complID"  value="%{complID}" />
	</s:url>
	
	    <sjg:grid 
		  id="fullViewHistory"
		  href="%{fullViewActivity}"
          gridModel="masterViewList"
          navigator="true"
          navigatorAdd="false"
          navigatorView="true"
          navigatorEdit="false"
          navigatorDelete="false"
          navigatorSearch="true"
          rowList="150,200,250,300,350"
          rowNum="140"
          viewrecords="true"       
          pager="true"
          pagerButtons="true"
          pagerInput="true"   
          multiselect="true"  
          loadingText="Requesting Data..."  
          navigatorEditOptions="{height:230,width:400}"
          navigatorSearchOptions="{sopt:['eq','bw']}"
          navigatorEditOptions="{reloadAfterSubmit:true}"
          shrinkToFit="false"
          autowidth="true"
          loadonce="true"
          onSelectRowTopics="rowselect"
          >
          
		<s:iterator value="viewPageColumns" id="viewPageColumns" >  
		<s:if test="colomnName=='id'">
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							search="%{search}"
							hidden="true"
							key="true"
		/>
		</s:if>
		<s:else>
		<sjg:gridColumn 
							name="%{colomnName}"
							index="%{colomnName}"
							title="%{headingName}"
							width="%{width}"
							align="%{align}"
							editable="%{editable}"
							formatter="%{formatter}"
							search="%{search}"
							hidden="%{hideOrShow}"
		/>
		</s:else>
		</s:iterator>  
</sjg:grid>
</body>
</html>