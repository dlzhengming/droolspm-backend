package com.drlm.backend.cotroller;

import com.drlm.backend.dto.ReleaseParam;
import com.drlm.backend.service.AnalysisService;
import com.drlm.backend.service.ReleaseService;
import com.drlm.backend.service.TriggerService;
import com.google.common.collect.Maps;
import com.sky.blue.web.JsonResult;
import com.sky.blue.web.JsonResultUtil;
import com.sky.blue.web.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/27
 */

@RestController
@RequestMapping("/release")
public class ReleaseV1Controller {

    @Autowired
    AnalysisService analysisService;

    @Autowired
    ReleaseService releaseService;

    @Autowired
    TriggerService triggerService;

    @RequestMapping(value = "/sceneId", method = RequestMethod.POST)
    public JsonResult releaseSceneId(@RequestBody ReleaseParam param) {
        try {
            String sceneRules = analysisService.analysisSceneRules(param.getSceneId());
            boolean analysisFlg = releaseService.releaseSceneRules(param.getSceneId(), sceneRules);
            if(analysisFlg){
                triggerService.triggerSceneRules(param.getSceneId());
            }
            return JsonResultUtil.createSuccessResult("OK");
        } catch (ServiceException e) {
            Map errMap = Maps.newHashMap();
            errMap.put("err", e.getMessage());
            throw new ServiceException("501", "服务端发布异常", errMap);
        }
    }
}
