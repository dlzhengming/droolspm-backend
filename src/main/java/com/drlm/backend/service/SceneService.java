package com.drlm.backend.service;

import java.util.List;

import com.drlm.backend.entities.Rule;
import com.drlm.backend.entities.Scene;
import com.drlm.backend.dto.UpdateRulesParam;
import com.sky.blue.web.JsonResult;

public interface SceneService {

	List<Scene> getSceneList();

	List<Rule> getRulesBySceneId(String sceneId);

	Boolean updateRules(String sceneId, UpdateRulesParam param);
}
