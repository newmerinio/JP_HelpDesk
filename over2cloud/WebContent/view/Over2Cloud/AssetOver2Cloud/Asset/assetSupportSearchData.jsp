<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="s" uri="/struts-tags" %>
    
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
	                              cssStyle="margin-top: -28px;height: 28PX;width: 143px;"
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
                                onchange="searchResult('id',this.value,'eq');disableFields('serialno');"
                                cssStyle="margin-top: -29px;margin-right: 1px;margin-left: 145px;height: 28PX;width: 113px;"
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
                                onchange="getDocu('assetDetailsDiv');searchResult('id',this.value,'eq');disableFields('assetname1');"
                                cssStyle="margin-top: -30px;margin-right: 1px;margin-left: 259px;height: 28PX;width: 111px;"

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
                               onchange="getDocu('assetDetailsDiv');searchResult('id',this.value,'eq');disableFields('mfgserialno');"
                               cssStyle="margin-top: -30px;margin-right: 2px;margin-left: 372px;height: 28PX;width: 133px;"
                                >
                               </s:select>
                    
				           </s:if>
			           </s:iterator>
			           <!--
			           Search : <s:textfield
			                   name="search" 
			                   id="search" 
			                   onchange="searchResult('Search',this.value,'eq');getDocu('assetDetailsDiv');" 
			                   maxlength="50"  
			                   placeholder="Enter Data"  
			                   cssClass="button"
			                   cssStyle="margin-top: -30px;margin-right: -180px;margin-left: 597px;"
			                   />-->
			                   