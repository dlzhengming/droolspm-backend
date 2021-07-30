package com.drlm.backend.entities;

import java.util.List;

import lombok.Data;

@Data
public class Rule {

	private String sceneId;
	private String ruleId;
	private String ruleLabels;
	private String ruleName;
	private String ruleDescribe;
	private String conditionsData;
	private String processingData;
	private List<Condition> conditions;
	private List<Processing> processing;
	
}
