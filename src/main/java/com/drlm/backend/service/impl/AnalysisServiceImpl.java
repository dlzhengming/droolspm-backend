package com.drlm.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.drlm.backend.dto.AnalysisCondition;
import com.drlm.backend.dto.AnalysisProcessing;
import com.drlm.backend.dto.AnalysisRule;
import com.drlm.backend.dto.AnalysisScene;
import com.drlm.backend.mapper.AnalysisMapper;
import com.drlm.backend.service.AnalysisService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/29
 */

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    AnalysisMapper analysisMap;

    @Override
    public String analysisSceneRules(String sceneId) {
        AnalysisScene analysisScene = analysisMap.getSceneRulesBySceneId(sceneId);
        StringBuffer sb = new StringBuffer();
        sb.append("package " + analysisScene.getScenePackage() + " ");
        for (AnalysisRule analysisRule : analysisScene.getAnalysisRuleList()) {
            String conditionsData = analysisRule.getConditionsData();
            Map<String, String> conditionMap = (Map) JSONObject.parseObject(conditionsData);
            sb.append("rule " + analysisRule.getRuleLabels() + " ");
            sb.append("when ");
            for (AnalysisCondition analysisCondition : analysisRule.getConditions()) {
                String conditionsBak = analysisCondition.getConditionsTemplate();
                if (!StringUtils.isEmpty(conditionsBak)) {
                    for (String key : conditionMap.keySet()) {
                        conditionsBak = conditionsBak.replace(key, conditionMap.get(key));
                    }
                    sb.append(conditionsBak + " ");
                }
            }
            String processingData = analysisRule.getProcessingData();
            Map<String, String> processingMap = (Map) JSONObject.parseObject(processingData);
            sb.append("then ");
            for (AnalysisProcessing analysisProcessing : analysisRule.getProcessing()) {
                String processingBak = analysisProcessing.getProcessingTemplate();
                if (!StringUtils.isEmpty(processingBak)) {
                    for (String key : processingMap.keySet()) {
                        processingBak = processingBak.replace(key, processingMap.get(key));
                    }
                    sb.append(processingBak + " ");

                }
            }
            sb.append("end ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
