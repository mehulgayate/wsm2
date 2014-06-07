package com.wsm.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;
import com.wsm.entity.Cluster.ClusterType;

@Entity
public class Recall extends EntityBase{
	
	private Date dbStartDate;
	private Date dbEndDate;
	private Date kStartDate;
	private Date kEndDate;
	public Date getDbStartDate() {
		return dbStartDate;
	}
	public void setDbStartDate(Date dbStartDate) {
		this.dbStartDate = dbStartDate;
	}
	public Date getDbEndDate() {
		return dbEndDate;
	}
	public void setDbEndDate(Date dbEndDate) {
		this.dbEndDate = dbEndDate;
	}
	public Date getkStartDate() {
		return kStartDate;
	}
	public void setkStartDate(Date kStartDate) {
		this.kStartDate = kStartDate;
	}
	public Date getkEndDate() {
		return kEndDate;
	}
	public void setkEndDate(Date kEndDate) {
		this.kEndDate = kEndDate;
	}
	
}
