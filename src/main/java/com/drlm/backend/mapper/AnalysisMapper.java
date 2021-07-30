package com.drlm.backend.mapper;


import com.drlm.backend.dto.AnalysisScene;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhengm
 */

@Mapper
public interface AnalysisMapper {
	/**
	 * getRulesBySceneId
	 *
	 * @param sceneId
	 * @return List<Rule>
	 * */
	AnalysisScene getSceneRulesBySceneId(String sceneId);
}
