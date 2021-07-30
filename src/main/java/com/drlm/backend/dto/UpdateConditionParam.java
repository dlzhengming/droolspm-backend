package com.drlm.backend.dto;

import lombok.Data;

@Data
public class UpdateConditionParam implements Comparable<UpdateConditionParam>{

	private String conditionNo;
	
	private String[] values;

	@Override
	public int compareTo(UpdateConditionParam o) {
		int flag = this.conditionNo.compareTo(o.conditionNo);
		return flag;
	}
	
}
