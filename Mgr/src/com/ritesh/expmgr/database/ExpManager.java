package com.ritesh.expmgr.database;

public class ExpManager {

	private int id;
	private String curr_date;
	private String tags;
	private String comments;
	private Double rs;
	private String rs_type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurr_date() {
		return curr_date;
	}
	public void setCurr_date(String curr_date) {
		this.curr_date = curr_date;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Double getRs() {
		return rs;
	}
	public void setRs(Double rs) {
		this.rs = rs;
	}
	public String getRs_type() {
		return rs_type;
	}
	public void setRs_type(String rs_type) {
		this.rs_type = rs_type;
	}
	
	
}
