package com.drlm.backend;

import com.alibaba.fastjson.JSONObject;
import com.drlm.backend.dto.AnalysisCondition;
import com.drlm.backend.dto.AnalysisProcessing;
import com.drlm.backend.dto.AnalysisRule;
import com.drlm.backend.dto.AnalysisScene;
import com.drlm.backend.entities.Rule;
import com.drlm.backend.mapper.AnalysisMapper;
import com.drlm.backend.mapper.SceneMapper;
import com.drlm.backend.service.SceneService;
import com.drlm.backend.service.impl.SceneServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/28
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DrlmBackEndApp.class)
public class MybatisTest {

    @Autowired
    AnalysisMapper analysisMapper;

    @Autowired
    SceneMapper sceneMapper;

    @Autowired
    SceneServiceImpl sceneServiceImpl;

    @Test
    public void test02() {
        AnalysisScene analysisScene = analysisMapper.getSceneRulesBySceneId("20210124075021");
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
    }

    @Test
    public void test03() {
        System.out.println("$order:com.drlm.flink.entity.Order(originalPrice $1 $2 && originalPrice $3 $4)".replace("$1", ">").replace("$2", "100"));
    }

    @Test
    public void test04() {
        List<Rule> rulesBySceneId = sceneMapper.getRulesBySceneId("20210124075021");
        System.out.println(rulesBySceneId.size());
    }

    @Test
    public void test05() {
        List<Rule> rulesBySceneId = sceneServiceImpl.getRulesBySceneId("20210124075021");
        System.out.println(rulesBySceneId.size());
    }
}
