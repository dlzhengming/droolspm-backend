package com.drlm.backend.entities;

import lombok.Data;

@Data
public class MyCondition {

	private Subject subject;
	private SubjectAttr subjectAttr;
	private Operator operator;
	private ConditionValue conditionValue;
}
