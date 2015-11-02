package com.Over2Cloud.ctrl.rcaMaster;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.dar.helper.DARReportHelper;

public class RCAMasterHelper
{
	@SuppressWarnings("rawtypes")
    public List getCategory(String moduleName, SessionFactory connectionSpace)
    {
	    List data=null;
		try
        {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
            StringBuilder query=new StringBuilder();
            if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM"))
            {
	            query.append("SELECT id,assetSubCat FROM createasset_type_master ORDER BY assetSubCat ASC ");
            }
            else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM") || moduleName!=null && moduleName.equalsIgnoreCase("FM"))
            {
				query.append(" SELECT cat.id,cat.categoryName FROM feedback_category AS cat ");
				query.append(" INNER JOIN feedback_type AS ft ON ft.id=cat.fbType ");
				query.append(" WHERE ft.moduleName='"+moduleName+"' ");
			}
            else if(moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM"))
            {
            	DARReportHelper DAR=new DARReportHelper();
            	String str[]=DAR.getOfferingName(connectionSpace);
				query.append(" SELECT id,"+str[0]+" FROM "+str[1]+" ORDER BY "+str[0]+" ASC ");
			}
            data=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return data;
    }
	@SuppressWarnings("rawtypes")
    public List getDeptList(SessionFactory connection)
    {
	    List data=null;
		try
        {
			 CommonOperInterface cbt=new CommonConFactory().createInterface();
			 StringBuilder qry=new StringBuilder();
			 qry.append("SELECT dept.id,dept.deptName  FROM department ");
			 qry.append(" AS dept INNER JOIN groupinfo AS grp ");
			 qry.append(" ON dept.groupId = grp.id WHERE grp.groupName='Employee' ORDER BY dept.deptName ASC ");
			 
			 data=cbt.executeAllSelectQuery(qry.toString(), connection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return data;
    }
	@SuppressWarnings("rawtypes")
    public List getSubCategory(String moduleName, String category, SessionFactory connectionSpace)
    {
	    List data=null;
		try
        {
			CommonOperInterface cbt=new CommonConFactory().createInterface();
            StringBuilder query=new StringBuilder();
            if (moduleName!=null && moduleName.equalsIgnoreCase("ASTM") || moduleName!=null && moduleName.equalsIgnoreCase("WFPM") || moduleName!=null && moduleName.equalsIgnoreCase("DREAM_HDM"))
            {
            	query.append(" SELECT cat.id,cat.categoryName FROM feedback_category AS cat ");
				query.append(" INNER JOIN feedback_type AS ft ON ft.id=cat.fbType ");
				query.append(" WHERE ft.moduleName='"+moduleName+"'  AND dept_subdept='"+category+"'");
            }
            else if(moduleName!=null && moduleName.equalsIgnoreCase("HDM") || moduleName!=null && moduleName.equalsIgnoreCase("FM"))
            {
				query.append(" SELECT subcat.id,subcat.subCategoryName FROM feedback_subcategory AS subcat ");
				query.append(" INNER JOIN feedback_category AS cat ON cat.id=subcat.categoryName ");
				query.append(" INNER JOIN feedback_type AS ft ON ft.id=cat.fbType ");
				query.append(" WHERE ft.moduleName='"+moduleName+"' AND cat.id='"+category+"' ");
			}
            data=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return data;
    }
	

}
