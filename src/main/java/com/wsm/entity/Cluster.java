package com.wsm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.evalua.entity.support.EntityBase;

@Entity
public class Cluster extends EntityBase {
	
	
	
	private String name;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
