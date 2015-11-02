package com.Over2Cloud.ctrl.questionairConf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.patientactivity.FeedbackDetailPojo;
import com.Over2Cloud.ctrl.patientactivity.PatientActivityPojo;
import com.Over2Cloud.ctrl.patientactivity.PatientDashboardPojo;

@SuppressWarnings("serial")
public class QuestionairConfiguration extends GridPropertyBean
{
	//karnika 09-06-2015
	private String questionSet;
	private String question;
	private String answer;
	private String options;
	private String upload;
	private String mandatory;
	private String subQuestion;
	private String questionSub;
	private String answerSub;
	private String optionsSub;
	private String uploadSub;
	private String mandatorySub;
	private List<Object> viewList;
	private List<Object> viewList2;
	private List<PatientActivityPojo> answersList;
	private List<GridDataPropertyView> gridProps = null;
	private List<GridDataPropertyView> gridProps2 = null;
	List<FeedbackDetailPojo> viewAnswerList;
	CommonOperInterface	coi	= new CommonConFactory().createInterface();
	
	//feedback history
	private String pdate; 
	private String ptime; 
	private String pageFor; 
	private String report; 
	
	public String viewSubmittedFeedback41(){
	//System.out.println("viewSubmittedFeedback41>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");	
	String setNo="NA";
	String date="NA";
	String time="NA";
	String name="NA";
	String ansflag="NA";
	String report="NA";
	String sections="NA";
	viewAnswerList=new ArrayList<FeedbackDetailPojo>();
	
	try
	{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
	AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
	SessionFactory connection = Ob1.getSession();
	// fetch main questions with answers
	String tablename="feedback_form_answers";
	String queryforGroup="SELECT form.empid,Concat(pbd.first_name,' ',pbd.last_name),form.date,form.time,ques.questionSet,form.flag,form.report"
	+ " ,ques.sections FROM feedback_form_answers AS form "
	+ " INNER join patient_basic_data as pbd on pbd.id=form.empId "
	+ " INNER join questionsconfig as ques on ques.id=form.questionId "
	+ " WHERE form.empId='"+ id+"'"
	+ " group by ques.questionSet  ";
	
	//System.out.println("queryforGroup >>"+queryforGroup);
	List groupByData =  cbt.executeAllSelectQuery(queryforGroup, connection);
	for(Iterator itr=groupByData.iterator();itr.hasNext();){
	FeedbackDetailPojo  fdp=new FeedbackDetailPojo();
	Object[] obj=(Object[]) itr.next();
	if(obj!=null){
	name = (obj[1]!=null)?obj[1].toString():"NA";
	fdp.setName(name);
	date = (obj[2]!=null)?obj[2].toString():"NA";
	fdp.setDate(DateUtil.convertDateToIndianFormat(date));
	time = (obj[3]!=null)?obj[3].toString():"NA";
	fdp.setTime(time);
	setNo = (obj[4]!=null)?obj[4].toString():"NA";
	fdp.setSetNo(setNo);
	
	ansflag = (obj[5]!=null)?obj[5].toString():"NA";
	
	report=(obj[6]!=null)?obj[6].toString():"NA";
	fdp.setReport(report);
	
	sections = (obj[7]!=null)?obj[7].toString():"NA";
	fdp.setSections(sections);
	
	//System.out.println("CHKKKK :::: " + ((obj[1]!=null)?obj[1].toString():"NA"));
	List<PatientActivityPojo> ldata=getFeedbackSubmittedData(id,date,setNo,connection);
	//System.out.println("MY CHK :::: "+ ldata.size()); 
	
	
	fdp.setAnswerlist(ldata);
	viewAnswerList.add(fdp);
	}
	}
	
	return SUCCESS;
	}
	catch (Exception e)
	{
	return ERROR;
	}
	
	}

/*
	public List<PatientActivityPojo> getFeedbackSubmittedData(String id,String date, String ansflag){
	answersList =new ArrayList<PatientActivityPojo>();
	try{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
	AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
	SessionFactory connection = Ob1.getSession();
	// fetch main questions with answers
	String tablename="feedback_form_answers";
	
	StringBuilder queryConf  =  new StringBuilder("SELECT ques.id,ques.questions,form.answer FROM "+tablename+" AS form INNER join questionsconfig AS ques ON ques.id=form.questionId WHERE subQuestionId = 'NA' "
	+ " and empid='"+id+"'"
	+ "and form.flag='"+ansflag+"'"
	+ " and form.date='"+date+"'");
	//System.out.println("MAIN>> "+queryConf.toString());
	List confData =  cbt.executeAllSelectQuery(queryConf.toString(), connection);
	if(confData!=null&& confData.size()>0)
	{
	PatientActivityPojo pj=null,pj1=null;
	for (Iterator iterator = confData.iterator(); iterator.hasNext();)
	{
	List<String> optionList = new ArrayList<String>();
	pj=new PatientActivityPojo();
	Object[] object = (Object[]) iterator.next();
	pj.setQuestions(object[1].toString());
	
	List<PatientActivityPojo> test = new ArrayList<PatientActivityPojo>();
	if(object[0]!=null)
	{
	// fetch sub questions of main with answers
	StringBuilder subQuery= new StringBuilder("SELECT subQues.questionsSub,form.answer  FROM "
	+ tablename
	+ " as form INNER join questionssubconfig as subQues on subQues.id=form.subQuestionId WHERE subQuestionId != 'NA' AND form.questionId ="+object[0].toString()+""
	+ " AND empid='"+id+"' ");
	
	subQuery.append(" AND form.flag='"+ansflag+"'");
	subQuery.append(" AND form.date='"+date+"'");
	
	//System.out.println("SubMAin  QUery ::::: "+subQuery.toString());
	List subQuestion = cbt.executeAllSelectQuery(subQuery.toString(), connection);
	if(subQuestion!=null && subQuestion.size()>0)
	{
	int sno1=0;
	for (Iterator iterator2 = subQuestion.iterator(); iterator2.hasNext();)
	{
	List<String> suboptionList = new ArrayList<String>();
	pj1=new PatientActivityPojo();
	Object[] object2 = (Object[]) iterator2.next();
	pj1.setSubquestions(object2[0].toString());
	if(object2[1]!=null)
	 	{
	String[] str = object2[1].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	suboptionList.add(str[i]);
	}
	}
	}
	////System.out.println("suboptionList ::::::: "+suboptionList.toString());
	pj1.setSuboptionsList(suboptionList);
	}
	test.add(pj1);
	}
	pj.setSubQuestion(test);
	}
	}
	else
	{
	pj.setSubQuestion(test);
	}
	if(object[2]!=null)
	{
	String[] str = object[2].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	optionList.add(str[i]);
	}
	}
	}
	pj.setOptionsList(optionList);
	}

	answersList.add(pj);
	//System.out.println(">>>>>>>>>>>>>     "+ answersList.size());
	}
	//	//System.out.println(ansflag +" :::::: "+answerList.size());
	}
	}catch(Exception e){
	e.printStackTrace();
	}
	return answersList;
	}	
	
	*/
	
	public String getValueWithNullCheck(Object value)
	{
	return (value==null || value.toString().equals(""))?"NA" : value.toString();
	}
	

	public List<PatientActivityPojo> getFeedbackSubmittedData(String id,String date, String ansflag,SessionFactory connection){
	answersList =new ArrayList<PatientActivityPojo>();
	try{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	// fetch main questions with answers
	String tablename="feedback_form_answers";
	
	StringBuilder queryConf  =  new StringBuilder("SELECT ques.subQuestion,ques.questions,form.answer FROM "+tablename+" AS form INNER join questionsconfig AS ques ON ques.id=form.questionId WHERE "
	+ " empid='"+id+"'"
	+ "and ques.questionSet='"+ansflag+"'"
	+ " and form.date='"+date+"'");
	//System.out.println("MAIN>> "+queryConf.toString());
	int i=1;
	List confData =  cbt.executeAllSelectQuery(queryConf.toString(), connection);
	if(confData!=null&& confData.size()>0)
	{
	PatientActivityPojo pj=null,pj1=null;
	for (Iterator iterator = confData.iterator(); iterator.hasNext();)
	{
	List<String> optionList = new ArrayList<String>();
	pj=new PatientActivityPojo();
	Object[] object = (Object[]) iterator.next();
	//pj.setQuestions(object[1].toString());
	
	//List<PatientActivityPojo> test = new ArrayList<PatientActivityPojo>();
	
	//System.out.println(object[0]+"   "+object[1]+"     "+object[2]);
	
	/*if(object[1].toString().equalsIgnoreCase("Relation"))
	{
	//System.out.println("TESTTTTTTTTTTTT!!!!!!!!!!!");
	pj.setSubquestions(getValueWithNullCheck(object[1]));
	pj.setSubanswers(getValueWithNullCheck(object[2].toString().subSequence(0, object[2].toString().length()-1)));
	}
	else{*/
	//	pj.setQuestions(getValueWithNullCheck(object[1]));
	//	pj.setAnswers(getValueWithNullCheck(object[2].toString().subSequence(0, object[2].toString().length()-1)));
	
	/*}*/
	
	
	if(object[0]!=null)
	{
	// fetch sub questions of main with answers
	StringBuilder query= new StringBuilder("");
	List datt=getSubQuestionaire(connection,getValueWithNullCheck(object[0]));
	if(datt!=null && datt.size()>0)
	{
	String sub=datt.get(0).toString();
	if(sub!=null && sub!="")
	{
	query=null;
	query = new StringBuilder("");
	query.append(" SELECT form.answer FROM feedback_form_answers AS form");
	query.append(" INNER join questionsconfig AS ques ON ques.id=form.questionId ");
	query.append(" where ques.id='"+sub+"' and form.date='"+date+"'and form.empId='"+id+"'");
	query.append(" AND ques.questionSet='"+ansflag+"'");
	//query.append(" AND form.date='"+date+"'");
	//System.out.println("QUERYYYYYYYYYYYYYY"+query);
	List ist=coi.executeAllSelectQuery(query.toString(), connection);
	if(ist!=null && ist.size()>0 && ist.get(0)!=null)
	{
	//	String str=ist.get(0).toString();
	//	str=str.subSequence(0,str.length()-1;
	//	//System.out.println(str);
	//	//System.out.println(str.subSequence(0,str.length()-1));
	pj.setSubanswers(ist.get(0).toString().trim().subSequence(0,ist.get(0).toString().trim().length()-1).toString());
	}
	}
	}
	
	
	/*StringBuilder subQuery= new StringBuilder("SELECT subQues.questionsSub,form.answer  FROM "
	+ tablename
	+ " as form INNER join questionssubconfig as subQues on subQues.id=form.subQuestionId WHERE form.questionId ="+object[0].toString()+""
	+ " AND empid='"+id+"' ");
	
	subQuery.append(" AND form.flag='"+ansflag+"'");
	subQuery.append(" AND form.date='"+date+"'");
	
	//System.out.println("SubMAin  QUery ::::: "+subQuery.toString());
	List subQuestion = cbt.executeAllSelectQuery(subQuery.toString(), connection);
	if(subQuestion!=null && subQuestion.size()>0)
	{
	int sno1=0;
	for (Iterator iterator2 = subQuestion.iterator(); iterator2.hasNext();)
	{
	//List<String> suboptionList = new ArrayList<String>();
	//	pj1=new PatientActivityPojo();
	Object[] object2 = (Object[]) iterator2.next();
	pj.setSubquestions(object2[0].toString());
	pj.setSubanswers(object2[1].toString());
	if(object2[1]!=null)
	 	{
	String[] str = object2[1].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	suboptionList.add(str[i]);
	}
	}
	}
	////System.out.println("suboptionList ::::::: "+suboptionList.toString());
	pj1.setSuboptionsList(suboptionList);
	}
	test.add(pj1);
	}
	//pj.setSubQuestion(test);
	}*/
	}
	/*else
	{
	pj.setSubQuestion(test);
	}*/
	
	/*if(object[2]!=null)
	{
	String[] str = object[2].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	optionList.add(str[i]);
	}
	}
	}
	pj.setOptionsList(optionList);
	}*/
	//pj.setAnswers(object[2].toString());
	if(!object[1].toString().equalsIgnoreCase("Relation") && !object[1].toString().equalsIgnoreCase("Duration") && !object[1].toString().equalsIgnoreCase("Mention Value if any"))
	{
	pj.setSno(Integer.toString(i));
	i=i+1;
	}
	pj.setQuestions(getValueWithNullCheck(object[1]));
	pj.setAnswers(object[2].toString().trim().subSequence(0, object[2].toString().trim().length()-1).toString());
	//System.out.println(pj.getSubanswers());
	
	answersList.add(pj);
	//System.out.println(">>>>>>>>>>>>>     "+ answersList.size());
	}
	//	//System.out.println(ansflag +" :::::: "+answerList.size());
	}
	}catch(Exception e){
	e.printStackTrace();
	}
	return answersList;
	}	
	
	@SuppressWarnings("unchecked")
	public List getSubQuestionaire(SessionFactory connection,String queid)
	{
	List<PatientDashboardPojo> fbpList = new ArrayList<PatientDashboardPojo>();
	StringBuilder query = new StringBuilder("");
	PatientDashboardPojo fbp = null;
	query.append(" SELECT questionId FROM questionssubconfig ");
	query.append(" where id='"+queid+"'");
	List dataList=coi.executeAllSelectQuery(query.toString(), connection);
	if (dataList != null && dataList.size() > 0 )
	 {
	fbpList=dataList;
	}
	return fbpList;
	}
	
	
	public String createQuestionConf() 
	{
	if(ValidateSession.checkSession())
	{
	try 
	{
	
	
	return SUCCESS;
	} 
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	public String questionAdd() 
	{
	if(ValidateSession.checkSession())
	{
	try 
	{
	
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	createTableQuestions(cbt);
	createTableSubQuestions(cbt);
	
	Map<String,String> fieldsname = new LinkedHashMap<String,String>();
	
	fieldsname.put("questionSet", questionSet.trim());
	fieldsname.put("questions", question.trim());
	fieldsname.put("answers", answer.trim());
	fieldsname.put("upload", upload.trim());
	fieldsname.put("mandatory", mandatory.trim());
	fieldsname.put("subQuestion", subQuestion.trim());
	fieldsname.put("options", options.trim());
	if(fieldsname!=null && fieldsname.size()>0)
	{
	InsertDataTable ob;
	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	for (Map.Entry<String, String> entry : fieldsname.entrySet())
	{
	    ob=new InsertDataTable();
	                ob.setColName(entry.getKey());
	                ob.setDataName(entry.getValue());
	                insertData.add(ob);
	}
	if(insertData!=null && insertData.size()>0)
	{
	int maxId = cbt.insertDataReturnId("questionsconfig", insertData, connectionSpace);
	insertData.clear();
	fieldsname.clear();
	if(questionSub!=null && answerSub!=null)
	{
	String[] quesSplit= questionSub.split(",");
	String[] ansSplit= answerSub.split(",");
	String[] uploadSplit= uploadSub.split(",");
	String[] mandSplit= mandatorySub.split(",");
	String[] optionSplit= optionsSub.split(",");
	if(quesSplit!=null && quesSplit.length>0)
	{
	int k=0;
	int j=4;
	for (int i = 0; i < quesSplit.length; i++) 
	{
	if(quesSplit[i].trim()!=null && !quesSplit[i].trim().toString().equalsIgnoreCase(""))
	{
	
	StringBuilder test =new StringBuilder();
	if(j<optionSplit.length)
	{
	for (int l = k; l < j; l++) 
	{
	test.append(optionSplit[l].trim()+",");
	}
	}
	k=k+j;
	j=k+4;
	fieldsname.put("questionId", String.valueOf(maxId));
	fieldsname.put("questionsSub", quesSplit[i].trim());
	fieldsname.put("answersSub", ansSplit[i].trim());
	fieldsname.put("uploadSub", uploadSplit[i].trim());
	fieldsname.put("mandatorySub", mandSplit[i].trim());
	fieldsname.put("optionsSub", test.toString());
	for (Map.Entry<String, String> entry : fieldsname.entrySet())
	{
	    ob=new InsertDataTable();
	                ob.setColName(entry.getKey());
	                ob.setDataName(entry.getValue());
	                insertData.add(ob);
	}
	cbt.insertIntoTable("questionssubconfig", insertData, connectionSpace);
	insertData.clear();
	fieldsname.clear();
	}
	}
	}
	}
	addActionMessage("Question saved successfully!");
	}
	}
	return SUCCESS;
	} 
	catch (Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	private void createTableSubQuestions(CommonOperInterface cbt)
	{
	try 
	{
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	TableColumes ob1=null;
	
            ob1=new TableColumes();
            ob1.setColumnname("questionId");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("questionsSub");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("answersSub");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("uploadSub");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("mandatorySub");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
        	ob1=new TableColumes();
            ob1.setColumnname("optionsSub");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	cbt.createTable22("questionssubconfig", Tablecolumesaaa, connectionSpace);
            
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	
	}
	private void createTableQuestions(CommonOperInterface cbt) 
	{
	try 
	{
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	TableColumes ob1=null;
	
            ob1=new TableColumes();
            ob1.setColumnname("questionSet");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("questions");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("answers");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("upload");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("mandatory");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	ob1=new TableColumes();
            ob1.setColumnname("subQuestion");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
        	ob1=new TableColumes();
            ob1.setColumnname("options");
            ob1.setDatatype("varchar(255)");
           	ob1.setConstraint("default NULL");
           	Tablecolumesaaa.add(ob1);
           	
           	cbt.createTable22("questionsconfig", Tablecolumesaaa, connectionSpace);
            
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	
	}
	//fetch answer view
	@SuppressWarnings("rawtypes")
	public String createAnswerConf()
	{
	try
	{
	answersList =new ArrayList<PatientActivityPojo>();
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	AllConnection Conn = new ConnectionFactory("2_clouddb", "127.0.0.1", "dreamsol", "dreamsol", "3306");
	AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
	SessionFactory connection = Ob1.getSession();
	// fetch main questions with answers
	String tablename="feedback_form_answers";
	if(pageFor != null && pageFor.equalsIgnoreCase("history")){
	tablename="history_feedback_form_answers";
	}
	
	StringBuilder queryConf  =  new StringBuilder("SELECT ques.id,ques.questions,form.answer,form.report  FROM "+tablename+" AS form INNER join questionsconfig AS ques ON ques.id=form.questionId WHERE subQuestionId = 'NA' and empid='"+id+"' ");
	if(pdate !=null && !pdate.equalsIgnoreCase("undefined") && !pdate.equalsIgnoreCase("") ){
	queryConf.append(" AND form.date='"+DateUtil.convertDateToUSFormat(pdate)+"'");
	}
	if(ptime !=null && !ptime.equalsIgnoreCase("undefined") && !ptime.equalsIgnoreCase("") ){
	queryConf.append(" AND form.time='"+ptime+"'");
	}
	//System.out.println(queryConf.toString());
	List confData =  cbt.executeAllSelectQuery(queryConf.toString(), connection);
	if(confData!=null&& confData.size()>0)
	{
	PatientActivityPojo pj=null,pj1=null;
	for (Iterator iterator = confData.iterator(); iterator.hasNext();)
	{
	List<String> optionList = new ArrayList<String>();
	pj=new PatientActivityPojo();
	Object[] object = (Object[]) iterator.next();
	pj.setQuestions(object[1].toString());
	//pj.setAnswers(object[2].toString());
	report=(object[3]!=null)?object[3].toString():"NA";
	
	List<PatientActivityPojo> test = new ArrayList<PatientActivityPojo>();
	if(object[0]!=null)
	{
	// fetch sub questions of main with answers
	StringBuilder subQuery= new StringBuilder("SELECT subQues.questionsSub,form.answer  FROM "
	+ tablename
	+ " as form INNER join questionssubconfig as subQues on subQues.id=form.subQuestionId WHERE subQuestionId != 'NA' AND form.questionId ="+object[0].toString()+""
	+ " AND empid='"+id+"' ");
	if(pdate !=null && !pdate.equalsIgnoreCase("undefined") && !pdate.equalsIgnoreCase("") ){
	subQuery.append(" AND form.date='"+DateUtil.convertDateToUSFormat(pdate)+"'");
	}
	if(ptime !=null && !ptime.equalsIgnoreCase("undefined") && !ptime.equalsIgnoreCase("") ){
	subQuery.append(" AND form.time='"+ptime+"'");
	}	
	
	//System.out.println("Sub  QUery ::::: "+subQuery.toString());
	List subQuestion = cbt.executeAllSelectQuery(subQuery.toString(), connection);
	if(subQuestion!=null && subQuestion.size()>0)
	{
	for (Iterator iterator2 = subQuestion.iterator(); iterator2.hasNext();)
	{
	List<String> suboptionList = new ArrayList<String>();
	pj1=new PatientActivityPojo();
	Object[] object2 = (Object[]) iterator2.next();
	pj1.setSubquestions(object2[0].toString());
	
	if(object2[1]!=null)
	{
	String[] str = object2[1].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	suboptionList.add(str[i]);
	}
	}
	}
	//System.out.println("suboptionList ::::::: "+suboptionList.toString());
	pj1.setSuboptionsList(suboptionList);
	}
	test.add(pj1);
	}
	pj.setSubQuestion(test);
	}
	
	}
	else
	{
	pj.setSubQuestion(test);
	}
	if(object[2]!=null)
	{
	String[] str = object[2].toString().split(",");
	if(str!=null )
	{
	for (int i = 0; i < str.length; i++)
	{
	if(!str[i].equalsIgnoreCase(""))
	{
	optionList.add(str[i]);
	}
	}
	}
	pj.setOptionsList(optionList);
	}
	answersList.add(pj);
	//System.out.println(">>>>>>>>>>>>>     "+ report);
	}
	}
	
	return SUCCESS;
	}
	catch (Exception e)
	{
	return ERROR;
	}
	}
	// View Questions
	public String viewQuestionConf()
	{
	try
	{
	viewGridPropertiesQuestions();
	viewGridPropertiesSubQuestions();
	
	return SUCCESS;
	}
	catch (Exception e)
	{
	return ERROR;
	}
	}
	private void viewGridPropertiesSubQuestions()
	{
	try
	{
	gridProps2 = new ArrayList<GridDataPropertyView>();
	
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	gridProps2.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("questionsSub");
	gpv.setHeadingName("Sub Questions");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(250);
	gridProps2.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("answersSub");
	gpv.setHeadingName("Sub Answers");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(200);
	    gridProps2.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("uploadSub");
	gpv.setHeadingName("Sub Upload");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(60);
	    gridProps2.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("mandatorySub");
	gpv.setHeadingName("Sub Mandatory");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(60);
	    gridProps2.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("optionsSub");
	gpv.setHeadingName("Sub Options");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(350);
	    gridProps2.add(gpv);
	}
	catch (Exception e) 
	{
	e.printStackTrace();
	}
    }
	private void viewGridPropertiesQuestions()
	{
	try
	{
	gridProps = new ArrayList<GridDataPropertyView>();
	
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Id");
	gpv.setHideOrShow("true");
	gridProps.add(gpv);
	
	gpv=new GridDataPropertyView();
	gpv.setColomnName("questionSet");
	gpv.setHeadingName("Questions Set");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(60);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("questions");
	gpv.setHeadingName("Questions");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(330);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("answers");
	gpv.setHeadingName("Answers");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(200);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("upload");
	gpv.setHeadingName("Upload");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(80);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("mandatory");
	gpv.setHeadingName("Mandatory");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(90);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("subQuestion");
	gpv.setHeadingName("Sub Question");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(200);
	    gridProps.add(gpv);
	    
	    gpv=new GridDataPropertyView();
	gpv.setColomnName("options");
	gpv.setHeadingName("Options");
	gpv.setEditable("true");
	gpv.setSearch("true");
	gpv.setHideOrShow("false");
	gpv.setWidth(150);
	    gridProps.add(gpv);
	}
	catch (Exception e) 
	{
	e.printStackTrace();
	}
    }
	@SuppressWarnings({  "rawtypes" })
	public String questionsView()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder("");
	query.append("SELECT COUNT(*) FROM questionsconfig");
	List  dataCount1=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	query.setLength(0);
	if(dataCount1.size()>0)
	{
	BigInteger count=new BigInteger("1");
	Object obdata=null;
	for(Iterator it=dataCount1.iterator(); it.hasNext();)
	{
	 obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	if (to > getRecords())
	to = getRecords();
	
	query.append("SELECT id,questionSet,questions,answers,upload,mandatory,subQuestion,options FROM questionsconfig ");
	
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	query.append(" and ");
	//add search  query based on the search operation
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" tr."+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" tr."+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" tr."+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"'");
	}
	}
	else if(getSord()!=null && getSidx()!=null && !getSord().equalsIgnoreCase("") && !getSidx().equalsIgnoreCase(""))
	{
	query.append(" ORDER BY tr."+getSidx()+" "+getSord()+"");
	}
	
	//System.out.println("query.toString() "+query.toString()); 
	List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	
	if(dataCount!=null && dataCount.size()>0)
	{
	viewList=new ArrayList<Object>();
	List<Object> Listhb=new ArrayList<Object>();
	Object[] obdata1=null;
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 obdata1=(Object[])it.next();
	 Map<String, Object> one=new HashMap<String, Object>();
	 if(obdata1[0] != null)
	{
	 one.put("id", (Integer)obdata1[0]);
	}
	 
	 if(obdata1[1] != null)
	{
	 one.put("questionSet",obdata1[1].toString());
	}
	 else 
	{
	one.put("questionSet","NA");
	}
	 if(obdata1[2] != null)
	{
	 one.put("questions",obdata1[2].toString());
	}
	 else 
	{
	one.put("questions","NA");
	}
	 if(obdata1[3] != null)
	{
	 one.put("answers",obdata1[3].toString());
	}
	 else 
	{
	one.put("answers","NA");
	}
	 if(obdata1[4] != null)
	{
	 one.put("upload",obdata1[4].toString());
	}
	 else 
	{
	one.put("upload","NA");
	}
	 if(obdata1[5] != null)
	{
	 one.put("mandatory",obdata1[5].toString());
	}
	 else 
	{
	one.put("mandatory","NA");
	}
	 
	if(obdata1[6] != null)
	{
	one.put("subQuestion",obdata1[6].toString());
	}
	else 
	{
	one.put("subQuestion","NA");
	}
	if(obdata1[7] != null)
	{
	one.put("options",obdata1[7].toString());
	}else 
	{
	one.put("options","NA");
	}
	 
	 Listhb.add(one);
	}
	setViewList(Listhb);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	 }
	   }
	return SUCCESS;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	@SuppressWarnings({  "rawtypes" })
	public String subQuestionsView()
	{
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query=new StringBuilder("");
	query.append("SELECT COUNT(*) FROM questionssubconfig");
	List  dataCount1=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	query.setLength(0);
	if(dataCount1.size()>0)
	{
	BigInteger count=new BigInteger("1");
	Object obdata=null;
	for(Iterator it=dataCount1.iterator(); it.hasNext();)
	{
	 obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	if (to > getRecords())
	to = getRecords();
	
	query.append("SELECT id,questionsSub,answersSub,uploadSub,mandatorySub,optionsSub FROM questionssubconfig WHERE questionId="+getId());
	
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	query.append(" and ");
	//add search  query based on the search operation
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" tr."+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" tr."+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" tr."+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" tr."+getSearchField()+" like '%"+getSearchString()+"'");
	}
	}
	else if(getSord()!=null && getSidx()!=null && !getSord().equalsIgnoreCase("") && !getSidx().equalsIgnoreCase(""))
	{
	query.append(" ORDER BY tr."+getSidx()+" "+getSord()+"");
	}
	//System.out.println(" query.toString() >"+query.toString()); 
	List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	
	if(dataCount!=null && dataCount.size()>0)
	{
	viewList2=new ArrayList<Object>();
	List<Object> Listhb=new ArrayList<Object>();
	Object[] obdata1=null;
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 obdata1=(Object[])it.next();
	 Map<String, Object> one=new HashMap<String, Object>();
	 one.put("id", (Integer)obdata1[0]);
	 one.put("questionsSub",obdata1[1].toString());
	 one.put("answersSub",obdata1[2].toString());
	 one.put("uploadSub",obdata1[3].toString());
	 one.put("mandatorySub",obdata1[4].toString());
	 one.put("optionsSub",obdata1[5].toString());
	 Listhb.add(one);
	}
	setViewList2(Listhb);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	 }
	   }
	return SUCCESS;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	public String getQuestion() {
	return question;
	}
	public void setQuestion(String question) {
	this.question = question;
	}
	public String getAnswer() {
	return answer;
	}
	public void setAnswer(String answer) {
	this.answer = answer;
	}
	public String getOptions() {
	return options;
	}
	public void setOptions(String options) {
	this.options = options;
	}
	public String getUpload() {
	return upload;
	}
	public void setUpload(String upload) {
	this.upload = upload;
	}
	public String getMandatory() {
	return mandatory;
	}
	public void setMandatory(String mandatory) {
	this.mandatory = mandatory;
	}
	public String getSubQuestion() {
	return subQuestion;
	}
	public void setSubQuestion(String subQuestion) {
	this.subQuestion = subQuestion;
	}
	public String getQuestionSub() {
	return questionSub;
	}
	public void setQuestionSub(String questionSub) {
	this.questionSub = questionSub;
	}
	public String getAnswerSub() {
	return answerSub;
	}
	public void setAnswerSub(String answerSub) {
	this.answerSub = answerSub;
	}
	public String getOptionsSub() {
	return optionsSub;
	}
	public void setOptionsSub(String optionsSub) {
	this.optionsSub = optionsSub;
	}
	public String getUploadSub() {
	return uploadSub;
	}
	public void setUploadSub(String uploadSub) {
	this.uploadSub = uploadSub;
	}
	public String getMandatorySub() {
	return mandatorySub;
	}
	public void setMandatorySub(String mandatorySub) {
	this.mandatorySub = mandatorySub;
	}
	public String getQuestionSet() {
	return questionSet;
	}
	public void setQuestionSet(String questionSet) {
	this.questionSet = questionSet;
	}
	public List<PatientActivityPojo> getAnswersList()
	{
	return answersList;
	}
	public void setAnswersList(List<PatientActivityPojo> answersList)
	{
	this.answersList = answersList;
	}
	public List<GridDataPropertyView> getGridProps()
	{
	return gridProps;
	}
	public void setGridProps(List<GridDataPropertyView> gridProps)
	{
	this.gridProps = gridProps;
	}
	public List<Object> getViewList()
	{
	return viewList;
	}
	public void setViewList(List<Object> viewList)
	{
	this.viewList = viewList;
	}
	public List<GridDataPropertyView> getGridProps2()
	{
	return gridProps2;
	}
	public void setGridProps2(List<GridDataPropertyView> gridProps2)
	{
	this.gridProps2 = gridProps2;
	}
	public List<Object> getViewList2()
	{
	return viewList2;
	}
	public void setViewList2(List<Object> viewList2)
	{
	this.viewList2 = viewList2;
	}
	public String getPdate() {
	return pdate;
	}
	public void setPdate(String pdate) {
	this.pdate = pdate;
	}
	public String getPtime() {
	return ptime;
	}
	public void setPtime(String ptime) {
	this.ptime = ptime;
	}
	public String getPageFor() {
	return pageFor;
	}
	public void setPageFor(String pageFor) {
	this.pageFor = pageFor;
	}
	public String getReport() {
	return report;
	}
	public void setReport(String report) {
	this.report = report;
	}


	public List<FeedbackDetailPojo> getViewAnswerList() {
	return viewAnswerList;
	}


	public void setViewAnswerList(List<FeedbackDetailPojo> viewAnswerList) {
	this.viewAnswerList = viewAnswerList;
	}

	
	
	
}