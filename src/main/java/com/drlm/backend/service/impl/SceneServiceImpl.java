package com.drlm.backend.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.drlm.backend.dto.UpdateConditionParam;
import com.drlm.backend.dto.UpdateProcessingParam;
import com.drlm.backend.dto.UpdateRuleParam;
import com.drlm.backend.dto.UpdateRulesParam;
import com.drlm.backend.entities.Condition;
import com.drlm.backend.entities.Processing;
import com.drlm.backend.entities.Rule;
import com.drlm.backend.entities.Scene;
import com.drlm.backend.mapper.SceneMapper;
import com.drlm.backend.service.SceneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.sky.blue.web.JsonResult;
import com.sky.blue.web.JsonResultUtil;
import com.sky.blue.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class SceneServiceImpl implements SceneService {

    @Autowired
    SceneMapper sceneMapper;

    @Override
    public List<Scene> getSceneList() {
        return sceneMapper.getSceneList();
    }


    @Override
    public List<Rule> getRulesBySceneId(String sceneId) {
        List<Rule> list = sceneMapper.getRulesBySceneId(sceneId);
        for (Rule rule : list) {
            Map<String, String> conditionMap = (Map) JSONObject.parseObject(rule.getConditionsData());
            for (Condition condition : rule.getConditions()) {
                String conditionsTemplate = condition.getConditionsTemplate();
                if (!StringUtils.isEmpty(conditionsTemplate)) {
                    for (String key : conditionMap.keySet()) {
                        conditionsTemplate = conditionsTemplate.replace(key, conditionMap.get(key));
                    }
                    condition.setConditionsTemplate(conditionsTemplate);
                }
            }

            Map<String, String> processingMap = (Map) JSONObject.parseObject(rule.getProcessingData());
            for (Processing processing : rule.getProcessing()) {
                String processingTemplate = processing.getProcessingTemplate();
                if (!StringUtils.isEmpty(processingTemplate)) {
                    for (String key : processingMap.keySet()) {
                        processingTemplate = processingTemplate.replace(key, processingMap.get(key));
                    }
                    processing.setProcessingTemplate(processingTemplate);
                }
            }
        }
        return list;
    }

    @Override
    public Boolean updateRules(String sceneId, UpdateRulesParam param) {
        try {
            for (UpdateRuleParam rule : param.getRules()) {
                String ruleId = rule.getRuleId();
                Map<String, String> conditionMap = Maps.newHashMap();
                String conditionStr;
                String processingStr;
                int conditionCount = 1;
                int processingCount = 1;
                List<UpdateConditionParam> conditions = rule.getConditions();
                Collections.sort(conditions);
                for (UpdateConditionParam condition : conditions) {
                    for (int i = 0; i < condition.getValues().length; i++) {
                        conditionMap.put("$" + (conditionCount++), condition.getValues()[i]);
                    }
                }
                conditionStr = new ObjectMapper().writeValueAsString(conditionMap);
                Map<String, String> processingMap = Maps.newHashMap();
                List<UpdateProcessingParam> processing = rule.getProcessing();
                Collections.sort(processing);
                for (UpdateProcessingParam p : processing) {
                    for (int i = 0; i < p.getValues().length; i++) {
                        processingMap.put("$" + (processingCount++), p.getValues()[i]);
                    }
                }
                processingStr = new ObjectMapper().writeValueAsString(processingMap);
                sceneMapper.updateRule(sceneId, ruleId, conditionStr, processingStr);
            }
            return true;
        } catch (Exception e) {
            Map<String, String> errMap = Maps.newHashMap();
            errMap.put("err", e.getMessage());
            throw new ServiceException("501", "服务内部发生错误", errMap);
        }
    }
}
