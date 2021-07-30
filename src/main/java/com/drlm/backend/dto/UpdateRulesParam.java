package com.drlm.backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class UpdateRulesParam {
	List<UpdateRuleParam> rules;
}
