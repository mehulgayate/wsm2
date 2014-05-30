package com.wsm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.evalua.entity.support.EntityBase;

@Entity
public class GraphData extends EntityBase{
	
	public enum GraphType{
		TROPICAL,DRY,TEMPERATE,CONTINENTAL,POLAR;
	}

	private Double humidity;
	private Double temperature;
	private Date date;

	private GraphType graphType;		
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public GraphType getGraphType() {
		return graphType;
	}
	public void setGraphType(GraphType graphType) {
		this.graphType = graphType;
	}	
	
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}		
}
