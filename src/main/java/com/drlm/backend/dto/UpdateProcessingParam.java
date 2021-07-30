package com.drlm.backend.dto;

import lombok.Data;

@Data
public class UpdateProcessingParam implements Comparable<UpdateProcessingParam> {

	private String processingNo;
	
	private String[] values;

	@Override
	public int compareTo(UpdateProcessingParam o) {
		int flag = this.processingNo.compareTo(o.processingNo);
		return flag;
	}
}
