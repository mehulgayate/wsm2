package com.wsm.entity;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;

@Entity
public class ClusterEvent extends EntityBase{
	
	private Long timeWithBoosting;
	private Long timeWithoutBoosting;
	
	
	public Long getTimeWithBoosting() {
		return timeWithBoosting;
	}
	public void setTimeWithBoosting(Long timeWithBoosting) {
		this.timeWithBoosting = timeWithBoosting;
	}
	public Long getTimeWithoutBoosting() {
		return timeWithoutBoosting;
	}
	public void setTimeWithoutBoosting(Long timeWithoutBoosting) {
		this.timeWithoutBoosting = timeWithoutBoosting;
	}
	
	
}
