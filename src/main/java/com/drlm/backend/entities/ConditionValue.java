package com.drlm.backend.entities;

import lombok.Data;

@Data
public class ConditionValue {
	
	private String value;
	private String type;
	private String[] selectList;
	private boolean readOnly;
	private String suffix;
}
