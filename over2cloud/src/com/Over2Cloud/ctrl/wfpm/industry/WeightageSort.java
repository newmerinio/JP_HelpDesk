package com.Over2Cloud.ctrl.wfpm.industry;
public class WeightageSort implements Comparable<WeightageSort> {
    private String id;
    private String deptname;
    private String weightage;

 
    public int compareTo(WeightageSort obj)
    {
    	return this.deptname.compareTo(obj.getDeptname());
    		
    }
    
    @Override
    public String toString() {
        return deptname;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getWeightage() {
		return weightage;
	}

	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}

}
