package com.Over2Cloud.ctrl.asset;

import java.awt.Point;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssetGraphAction extends ActionSupport
{
	private static final long serialVersionUID = 4851863957798371834L;
	static final Log log = LogFactory.getLog(AssetCommonAction.class);
	Map session = ActionContext.getContext().getSession();
	private List<Point> points;
	private List<Point> pointsWithNull;
	private Map<Integer, Integer> pointsFromMap;
	private Map<Date, Integer> dateFromMap;
	private Map<Date, Integer> xyaxisMap;
	private Map<Date, Integer> lifeMap;
	private Map<Date, Integer> pastMap;
	private Map<String, Integer> pieDataMap;
	private String minTime;
	private String maxTime;
	private int titleIndex;
	private String millSec;
	private String date;
	private String title;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("unchecked")
	public String createAssetLifeGraph()
	{
		try
		{
			String query = null;
			List dateList = new ArrayList();
			query = "Select receivedon,expectedlife,installon,commssioningon from procurement_detail where assetid=1";
			dateList = cbt.executeAllSelectQuery(query, connectionSpace);
			List<String> tempList = new ArrayList<String>();
			List<String> titleList = new ArrayList<String>();
			titleList.add("Received Date");
			titleList.add("Expire Date");
			titleList.add("Install Date");
			titleList.add("CommissionIng Date");
			if (dateList != null && dateList.size() > 0)
			{
				for (Iterator iterator = dateList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					for (int i = 0; i < object.length; i++)
					{
						if (object[i] != null && !object[i].toString().equals(""))
						{
							tempList.add(object[i].toString());
						}
					}
					xyaxisMap = new LinkedHashMap<Date, Integer>();
					Date convertDate1 = DATE_FORMAT.parse(tempList.get(0));
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.YEAR, 1);
					calendar.setTime(convertDate1);
					minTime = "" + calendar.getTime().getTime();
					for (int i = 1; i <= 31; i++)
					{
						xyaxisMap.put(calendar.getTime(), i);
						calendar.add(Calendar.MONTH, 1);
					}
				}

			}
			query = "Select supportfrom,supportto from asset_support_detail where assetid=1";
			dateList = new ArrayList();
			dateList = cbt.executeAllSelectQuery(query, connectionSpace);
			System.out.println(dateList.size());
			if (dateList != null && dateList.size() > 0)
			{
				for (Iterator iterator = dateList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					for (int i = 0; i < object.length; i++)
					{
						if (object[i] != null && !object[i].toString().equals(""))
						{
							tempList.add(object[i].toString());
							System.out.println(tempList.size() + ">>" + object[i].toString());
						}
					}
				}

			}
			titleList.add("Support From");
			titleList.add("Support To");

			if (tempList != null && tempList.size() > 0)
			{
				tempList.add(DateUtil.getCurrentDateUSFormat());
				titleList.add("Today Date");
				String[] dateArray = new String[tempList.size()];
				String[] titleArray = new String[titleList.size()];
				dateArray = tempList.toArray(new String[tempList.size()]);
				titleArray = titleList.toArray(new String[titleList.size()]);
				for (int i = 0; i < dateArray.length; i++)
				{
					for (int j = i; j < dateArray.length; j++)
					{
						if (!DateUtil.comparetwoDates(dateArray[i], dateArray[j]))
						{
							String temp = dateArray[i];
							dateArray[i] = dateArray[j];
							dateArray[j] = temp;
							temp = titleArray[i];
							titleArray[i] = titleArray[j];
							titleArray[j] = temp;
						}
					}
				}
				System.out.println(dateArray.length + "dateArrayLen");
				lifeMap = new LinkedHashMap<Date, Integer>();
				for (int i = 0; i < dateArray.length; i++)
				{
					System.out.println(dateArray[i]);
					String dateArr[] = dateArray[i].split("-");
					Date convertDate = DATE_FORMAT.parse(dateArray[i]);
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(convertDate);
					lifeMap.put(calendar1.getTime(), Integer.valueOf(dateArr[2].toString()));
				}

				pastMap = new LinkedHashMap<Date, Integer>();
				for (int i = 0; i < dateArray.length; i++)
				{
					String dateArr[] = dateArray[i].split("-");
					Date convertDate = DATE_FORMAT.parse(dateArray[i]);
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(convertDate);
					pastMap.put(calendar1.getTime(), Integer.valueOf(dateArr[2].toString()));
					if (dateArray[i].equals(DateUtil.getCurrentDateUSFormat()))
					{
						break;
					}
				}
				session.put("titleArray", titleArray);
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getClickPointInfo()
	{
		String[] titleArr = (String[]) session.get("titleArray");
		title = titleArr[titleIndex];
		date = cnvrtMillSecToDate(millSec);
		return SUCCESS;
	}

	public String cnvrtMillSecToDate(String strMilSec)
	{
		long mSec = Long.parseLong(strMilSec);
		Date date = new Date(mSec);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}

	public Map<String, String> getDataColumn()
	{
		Map<String, String> columnMap = new LinkedHashMap<String, String>();

		return columnMap;

	}

	public List<Point> getPoints()
	{
		return points;
	}

	public List<Point> getPointsWithNull()
	{
		return pointsWithNull;
	}

	public Map<Integer, Integer> getPointsFromMap()
	{
		return pointsFromMap;
	}

	public Map<Date, Integer> getDateFromMap()
	{
		return dateFromMap;
	}

	public String getMinTime()
	{
		return minTime;
	}

	public String getMaxTime()
	{
		return maxTime;
	}

	public Map<String, Integer> getPieDataMap()
	{
		return pieDataMap;
	}

	public Map<Date, Integer> getLifeMap()
	{
		return lifeMap;
	}

	public void setLifeMap(Map<Date, Integer> lifeMap)
	{
		this.lifeMap = lifeMap;
	}

	public Map<Date, Integer> getXyaxisMap()
	{
		return xyaxisMap;
	}

	public void setXyaxisMap(Map<Date, Integer> xyaxisMap)
	{
		this.xyaxisMap = xyaxisMap;
	}

	public Map<Date, Integer> getPastMap()
	{
		return pastMap;
	}

	public void setPastMap(Map<Date, Integer> pastMap)
	{
		this.pastMap = pastMap;
	}

	public int getTitleIndex()
	{
		return titleIndex;
	}

	public void setTitleIndex(int titleIndex)
	{
		this.titleIndex = titleIndex;
	}

	public String getMillSec()
	{
		return millSec;
	}

	public void setMillSec(String millSec)
	{
		this.millSec = millSec;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

}
