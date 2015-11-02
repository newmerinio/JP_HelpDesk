<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<html>
<head>
<s:url  id="contextz" value="/template/theme" />
<script type="text/javascript" src="<s:url value="/js/asset/AssetCommon.js"/>"></script>
<meta   http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div style="" class="listviewButtonLayer" id="listviewButtonLayerDiv">
<div class="pwie">
	<table class="lvblayerTable secContentbb" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				  <td>
					  <table class="floatL" border="0" cellpadding="0" cellspacing="0">
					    <tbody><tr>
					    <td class="pL10"> 
							 <s:iterator value="assetDropMap"  status="status">
					            <s:if test="key=='assettype'"> 
					            
	                              <s:select 
	                              id="%{key}"
	                              list="assetTypeList"
	                              headerKey="-1"
	                              headerValue="Select Asset Type" 
	                              cssClass="button"
	                              onchange="getAssetDetailForSearch(this.value,'serialno','assetname1','mfgserialno');enableField();"
	                               >
	                             </s:select>
					             </s:if>
            			 </s:iterator>
             
			             <s:iterator value="assetDropMap"  status="status">
			             <s:if test="key=='serialno'"> 
                                <s:select 
                                id="%{key}"
                                list="{'No data'}"
                                headerKey="-1"
                                headerValue="Select Code" 
                                cssClass="button"
                                onchange="getDocu('assetDetailsDiv');assetDetailsSupport(this.value,'normal');disableFields('serialno');"
                                 >
                                </s:select>
			           </s:if>
			            </s:iterator>
            
			            <s:iterator value="assetDropMap"  status="status">
				           <s:if test="key=='assetname'"> 
                                <s:select 
                                id="%{key}1"
                                list="{'No data'}"
                                headerKey="-1"
                                headerValue="Select Asset Name" 
                                cssClass="button"
                                onchange="getDocu('assetDetailsDiv');assetDetailsSupport(this.value,'normal');disableFields('assetname1');"
                                 >
                                </s:select>
				           </s:if>
			            </s:iterator>
            
			            <s:iterator value="assetDropMap"  status="status">
				           <s:if test="key=='mfgserialno'"> 
                               <s:select 
                               id="%{key}"
                               list="{'No data'}"
                               headerKey="-1"
                               headerValue="Select Serial No" 
                               cssClass="button"
                               onchange="getDocu('assetDetailsDiv');assetDetailsSupport(this.value,'normal');disableFields('mfgserialno');"
                                >
                               </s:select>
                    
				           </s:if>
			           </s:iterator>
           
			         Search:<s:textfield name="search" id="search" onchange="getDocu('assetDetailsDiv');assetDetailsSupport(this.value,'search');" maxlength="50"  placeholder="Enter Data"  cssClass="button"/>
				         
					     </td></tr></tbody>
					  </table>
				  </td>
				  <!-- Block for insert Right Hand Side Button -->
				  <td class="alignright printTitle">
	      		  </td>
			</tr>
		</tbody>
	</table>
</div>
</div>


           
</body>
</html>