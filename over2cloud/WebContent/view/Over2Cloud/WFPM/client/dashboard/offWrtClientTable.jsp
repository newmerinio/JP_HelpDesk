<%@ taglib prefix="s" uri="/struts-tags" %>
<table align="center" style="width: 100%;font-size: 15px;padding: 0px; border:0px solid #000000">
	<tr style="font-weight: normal;border-radius:3px;background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;">
			<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"><b>Status</b></td>
			<td align="left" width=""  style="background: #e6e6e6 url(template/theme/mytheme/images/ui-bg_glass_75_e6e6e6_1x400.png) 50% 50% repeat-x;"><b>Count</b></td>
		</tr>
	
	<s:if test="%{offWrtClientMap != null}">
		<s:iterator value="offWrtClientMap" var="val" status="status">
			<s:if test="%{#status.odd}">
				<tr>
					<td align="left"><font face="Arial, Helvetica, sans-serif" color="#000000"> <b><s:property value="%{key}"/></b></font></td>
						<td align="left"><font face="Arial, Helvetica, sans-serif" color="#000000"> <b><s:property value="%{value}"/></b></font></td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td align="left"><font face="Arial, Helvetica, sans-serif" color="#000000"> <b><s:property value="%{key}"/></b></font></td>
						<td align="left"><font face="Arial, Helvetica, sans-serif" color="#000000"> <b><s:property value="%{value}"/></b></font></td>
				</tr>
			</s:else>
		</s:iterator>
	</s:if>		
</table>
