<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>



<b>Check taxes to map with selected offering</b>
<br><br>
<s:set var="var" ></s:set>
<div class="form_menubox">
<s:iterator value="taxList" var="item" status="counter">
	<s:if test="#counter.count%2 != 0">			 
		<s:iterator value="item" status="status" var="val2"> 
			<s:if test="%{#status.index == 0}">
				<s:set var="taxId" ><s:property /></s:set>
			</s:if>
			<s:elseif test="%{#status.index == 1}">
				<s:set var="taxName" ><s:property /></s:set>
			</s:elseif>
			<s:elseif test="%{#status.index == 2}">
				<s:set var="flag" value="#val2"></s:set><s:property />
			</s:elseif>
			
			<s:else>
				<div class="inputmain"><div class="user_form_text"><s:property value="taxName"/> [<s:property/>] </div>
		        <div class="user_form_input inputarea" ><span class="needed"></span>
		        <s:if test="#flag == 'true'">
		      	   <input type="checkbox" class="chk" id="taxId" name="taxId" checked="checked" value="<s:property value="taxId"/>" class="form_menu_inputtext" style="margin:0px 0px 10px 0px; padding-bottom:3px;"/>
		        </s:if>
		        <s:else>
		       	   <input type="checkbox" class="chk" id="taxId" name="taxId" value="<s:property value="taxId"/>" class="form_menu_inputtext" style="margin:0px 0px 10px 0px; padding-bottom:3px;"/>
		        </s:else>
		        
		        </div> 
	            </div>
			</s:else>
			 
		</s:iterator>	 
	</s:if>
	<s:else>
		<s:iterator value="item" status="status" >
			<s:if test="%{#status.index == 0}">
				<s:set var="taxId" ><s:property /></s:set>
			</s:if>
			<s:elseif test="%{#status.index == 1}">
				<s:set var="taxName" ><s:property /></s:set>
			</s:elseif>
			<s:elseif test="%{#status.index == 2}">
				<s:set var="flag" ><s:property /></s:set><s:property />
			</s:elseif>
			<s:else>
				<div class="inputmain"><div class="user_form_text1"><s:property value="taxName"/> [<s:property/>] </div>
		        <div class="user_form_input inputarea" ><span class="needed"></span>
		        <s:if test="#flag == 'true'">
		       		 <input type="checkbox" class="chk" id="taxId" name="taxId" checked="checked" value="<s:property value="taxId"/>"  class="form_menu_inputtext" style="margin:0px 0px 10px 0px; padding-bottom:3px;"/>
		        </s:if>
		        <s:else>
		        	 <input type="checkbox" class="chk" id="taxId" name="taxId" value="<s:property value="taxId"/>"  class="form_menu_inputtext" style="margin:0px 0px 10px 0px; padding-bottom:3px;"/>
		        </s:else>
		        </div> 
	            </div>
			</s:else>
			 
		</s:iterator>	 
	</s:else>			 
</s:iterator>

</div>
		   <!-- Buttons -->
<center><img id="indicator2" src="<s:url value="/images/indicator.gif"/>" alt="Loading..." style="display:none"/></center>
				<div class="fields">
				<ul>
				<li class="submit" style="background: none;">
					<div class="type-button">
	                <sj:submit 
	                        targets="orglevel1" 
	                        clearForm="true"
	                        value="  Map Tax  " 
	                        effect="highlight"
	                        effectOptions="{ color : '#222222'}"
	                        effectDuration="5000"
	                        button="true"
	                        onCompleteTopics="level1"
	                        cssClass="submit"
	                        indicator="indicator2"
	                        onBeforeTopics="checkDDN"
	                        />
	               </div>
				</ul>
				<sj:div id="orglevel1"  effect="fold">
                    <div id="orglevel1Div"></div>
               </sj:div>
               </div>

