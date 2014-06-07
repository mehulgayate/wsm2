package com.wsm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.evalua.entity.support.EntityBase;

@Entity
public class Cluster extends EntityBase {
	
	
	
	private String name;
	private ClusterType type;
	private int tempMin;
	private int tempMax;	
	
	public ClusterType getType() {
		return type;
	}
	public void setType(ClusterType type) {
		this.type = type;
	}
	public int getTempMin() {
		return tempMin;
	}
	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}
	public int getTempMax() {
		return tempMax;
	}
	public void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}
	public enum ClusterType{
		K_MEDOID,DBSCAN;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
