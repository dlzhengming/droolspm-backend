package com.drlm.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateRuleParam {

	private String ruleId;
	private List<UpdateConditionParam> conditions;
	private List<UpdateProcessingParam> processing;
}
