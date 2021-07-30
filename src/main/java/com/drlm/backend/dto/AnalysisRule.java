package com.drlm.backend.dto;


import lombok.Data;

import java.util.List;

/**
 * @author zhengm
 */

@Data
public class AnalysisRule {

	private String ruleId;
	private String ruleLabels;
	private String ruleName;
	private String conditionsData;
	private String processingData;
	private List<AnalysisCondition> conditions;
	private List<AnalysisProcessing> processing;
}
