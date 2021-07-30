package com.drlm.backend.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.drlm.backend.entities.Rule;
import com.drlm.backend.entities.Scene;

@Mapper
public interface SceneMapper {

	List<Scene> getSceneList();

	List<Rule> getRulesBySceneId(String sceneId);

	int updateRule(@Param("sceneId")String sceneId,
				   @Param("ruleId")String ruleId, 
				   @Param("conditionStr")String conditionStr,
				   @Param("processingStr")String processingStr);
}
