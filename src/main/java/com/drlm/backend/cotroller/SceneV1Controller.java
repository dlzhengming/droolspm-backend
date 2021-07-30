package com.drlm.backend.cotroller;

import com.drlm.backend.dto.UpdateRulesParam;
import com.drlm.backend.entities.Rule;
import com.drlm.backend.entities.Scene;
import com.drlm.backend.service.SceneService;
import com.sky.blue.web.JsonResult;
import com.sky.blue.web.JsonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/scene")
public class SceneV1Controller {
    @Autowired
    private SceneService sceneService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Scene> getSceneList() {
        return sceneService.getSceneList();
    }

    @RequestMapping(value = "/{sceneId}/rules", method = RequestMethod.GET)
    public List<Rule> getRulesBySceneId(@PathVariable("sceneId") String sceneId) {
        return sceneService.getRulesBySceneId(sceneId);
    }

    @RequestMapping(value = "/{sceneId}/rules", method = RequestMethod.POST)
    public JsonResult updateRules(@PathVariable("sceneId") String sceneId, @RequestBody UpdateRulesParam param) {
        Boolean aBoolean = sceneService.updateRules(sceneId, param);
        if (aBoolean) {
            return JsonResultUtil.createSuccessResult("OK");
        } else {
            return JsonResultUtil.createErrorResult("Error", "rule value update error");
        }
    }
}
