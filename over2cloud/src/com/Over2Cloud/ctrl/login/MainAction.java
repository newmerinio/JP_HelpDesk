package com.Over2Cloud.ctrl.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MainAction extends ActionSupport
{
	Map session = ActionContext.getContext().getSession();
	private List<ConfigurationUtilBean> configBean = null;
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	String dbName = (String) session.get("Dbname");
	String validApp = (String) session.get("validApp");
	private String titles = null;
	private String orgImgPath;
	public String execute()
	{
		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;

		return SUCCESS;
	}

	public String mainFrame()
	{

		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;

		String returnResult = ERROR;
		List resultdata = null;
		try
		{
			System.out.println(">>>>>>>>>>Valid app >>>> "+validApp);
			if (validApp != null)
			{
				List<ConfigurationUtilBean> Listdata = new ArrayList<ConfigurationUtilBean>();
				Map<String, Object> paramMap = new HashMap<String, Object>();
				StringBuilder query = new StringBuilder("select id,module_name from allcommontabtable");
				String tempvalidApp[] = validApp.split("#");
				if (tempvalidApp.length > 0)
				{
					query.append(" where ");
				}
				int i = 1;
				for (String H : tempvalidApp)
				{
					if (H != null && !H.equalsIgnoreCase(""))
					{
						if (i == 1)
						{
							query.append("moduleName='" + H + "'");
							i++;
						}
						else
						{
							query.append(" or moduleName='" + H + "'");
						}
					}
				}
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				resultdata = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
				if (resultdata != null)
				{
					for (Iterator iterator = resultdata.iterator(); iterator.hasNext();)
					{
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCal = (Object[]) iterator.next();
						ConfigBeanObj.setId(Integer.parseInt(objectCal[0].toString()));
						ConfigBeanObj.setField_value(objectCal[1].toString());
						Listdata.add(ConfigBeanObj);
					}
				}
				setConfigBean(Listdata);
				setOrgImgPath(new CommonWork().getOrganizationImage((SessionFactory) session.get("connectionSpace")));
			}

			returnResult = SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String getTitles()
	{
		return titles;
	}

	public void setTitles(String titles)
	{
		this.titles = titles;
	}

	public List<ConfigurationUtilBean> getConfigBean()
	{
		return configBean;
	}

	public void setConfigBean(List<ConfigurationUtilBean> configBean)
	{
		this.configBean = configBean;
	}

	public String getOrgImgPath()
	{
		return orgImgPath;
	}

	public void setOrgImgPath(String orgImgPath)
	{
		this.orgImgPath = orgImgPath;
	}
}
