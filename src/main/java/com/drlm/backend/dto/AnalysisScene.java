package com.drlm.backend.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhengm
 */

@Data
public class AnalysisScene {

    private String sceneId;
    private String sceneName;
    private String scenePackage;
    private String sceneDescribe;
    private List<AnalysisRule> analysisRuleList;
}
