package com.monditech.dto;

import java.sql.Date;

public class scheduling {
	private int id;
	private String dtStart;
	private String dtEnd;
	private String dtStartPerid;
	private String dtEndPerid;
	private String dtStartPresent;
	private String dtEndPresent;
	private String dtStartDelay;
	private String dtEndDelay;
	private Boolean active;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;    
	}
	
	public String getDtStart() {
		return dtStart;
	}
	
	public String setDtStart(String dtStart) {
		return this.dtStart = dtStart;
	}
	
	public String getDtEnd() {
		return dtEnd;
	}
	
	public String setDtEnd(String dtEnd) {
		return this.dtEnd = dtEnd;
	}
	
	public String getDtStartPerid() {
		return dtStartPerid;
	}
	
	public String setDtStartPerid(String dtStartPerid) {
		return this.dtStartPerid = dtStartPerid;
	}
	
	public String getDtEndPerid() {
		return dtEndPerid;
	}
	
	public String setDtEndPerid(String dtEndPerid) {
		return this.dtEndPerid = dtEndPerid;
	}
	
	public String getDtStartPresent() {
		return dtStartPresent;
	}
	
	public String setDtStartPresent(String dtStartPresent) {
		return this.dtStartPresent = dtStartPresent;
	}
	
	public String getDtEndPresent() {
		return dtEndPresent;
	}
	
	public String setDtEndPresent(String dtEndPresent) {
		return this.dtEndPresent = dtEndPresent;
	}
	
	public String getDtStartDelay() {
		return dtStartDelay;
	}
	
	public String setDtStartDelay(String dtStartDelay) {
		return this.dtStartDelay = dtStartDelay;
	}
	
	public String getDtEndDelay() {
		return dtEndDelay;
	}
	
	public String setDtEndDelay(String dtEndDelay) {
		return this.dtEndDelay = dtEndDelay;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public Boolean setActive(Boolean active) {
		return this.active = active;
	}
	
	
}
