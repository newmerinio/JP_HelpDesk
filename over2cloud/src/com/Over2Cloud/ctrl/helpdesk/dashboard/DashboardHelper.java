package com.Over2Cloud.ctrl.helpdesk.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

@SuppressWarnings("serial")
public class DashboardHelper extends GridPropertyBean
{
	List<FeedbackPojo> feedbackList = null;
	
	public String beforeCategoryView()
    { 
    	return SUCCESS;
    }

    
	public String viewCategoryDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				//System.out.println("Category Id is   "+id);
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				
				feedbackList = new ArrayList<FeedbackPojo>();
				
				List data = HUA.getCategoryDetail(id);
					
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					
				 feedbackList = HUH.setCategoryDetail(data);
				 setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}


	public List<FeedbackPojo> getFeedbackList()
	{
		return feedbackList;
	}


	public void setFeedbackList(List<FeedbackPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}
	
	
}
