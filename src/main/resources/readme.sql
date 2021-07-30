CREATE TABLE `product` (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) DEFAULT NULL,
  `db_source` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8

INSERT INTO product(product_name, db_source) VALUES('格力空调', '01');
INSERT INTO product(product_name, db_source) VALUES('海尔冰箱', '01');
INSERT INTO product(product_name, db_source) VALUES('羽绒服', '01');

--金额大于100
{"conditions_no":[{"type": "span","value": "金额"},{"type": "select","defaultValue": "$1", "values": [{"value": ">","label": "大于"},{"value": "<","label": "小于"}]},{"type": "text","value": "$2"}]}

--金额大于100，实际金额0.8处理

-- 场景表
CREATE TABLE `scene_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `scene_name` varchar(50) COMMENT '场景名',
  `scene_package` varchar(50) COMMENT '场景包路径',
  `scene_describe` varchar(50) COMMENT '场景描述',
  PRIMARY KEY (`scene_id`)
) ENGINE=InnoDB CHARSET=utf8;

-- 规则(rule)
CREATE TABLE `rule_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `rule_id` varchar(50) NOT NULL COMMENT '规则ID',
  `rule_labels` varchar(50) NOT NULL COMMENT '规则标签',
  `rule_name` varchar(50) COMMENT '规则名',
  `rule_describe` varchar(50) COMMENT '规则描述',
  `conditions_data` varchar(255) COMMENT '条件数据json',
  `processing_data` varchar(255) COMMENT '处理数据json',
  PRIMARY KEY (`scene_id`,`rule_id`)
) ENGINE=InnoDB CHARSET=utf8;

-- 条件(conditions)
CREATE TABLE `condition_f_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `rule_id` varchar(50) NOT NULL COMMENT '规则ID',
  `conditions_no` varchar(50) COMMENT '条件序号,排序字段',
  `conditions_template` varchar(255) COMMENT '前端',
  PRIMARY KEY (`scene_id`,`rule_id`,`conditions_no`)
) ENGINE=InnoDB CHARSET=utf8;

-- 条件(conditions)
CREATE TABLE `condition_b_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `rule_id` varchar(50) NOT NULL COMMENT '规则ID',
  `conditions_no` varchar(50) COMMENT '条件序号,排序字段',
  `conditions_template` varchar(255) COMMENT '后端模版',
  PRIMARY KEY (`scene_id`,`rule_id`,`conditions_no`)
) ENGINE=InnoDB CHARSET=utf8;

-- 处理(processing)
CREATE TABLE `processing_f_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `rule_id` varchar(50) NOT NULL COMMENT '规则ID',
  `processing_no` varchar(50) COMMENT '处理序号,排序字段',
  `processing_template` varchar(255) COMMENT '后端模版',
  PRIMARY KEY (`scene_id`,`rule_id`,`processing_no`)
) ENGINE=InnoDB CHARSET=utf8;

-- 处理(processing)
CREATE TABLE `processing_b_v1` (
  `scene_id` varchar(20) NOT NULL COMMENT '场景ID',
  `rule_id` varchar(50) NOT NULL COMMENT '规则ID',
  `processing_no` varchar(50) COMMENT '处理序号,排序字段',
  `processing_template` varchar(255) COMMENT '后端模版',
  PRIMARY KEY (`scene_id`,`rule_id`,`processing_no`)
) ENGINE=InnoDB CHARSET=utf8;


INSERT INTO `drools`.`scene_v1` (`scene_id`, `scene_name`, `scene_package`, `scene_describe`) VALUES ('20210124075021', '订单折扣', 'order.discount', '按照原价进行折扣处理');
INSERT INTO `drools`.`rule_v1` (`scene_id`, `rule_id`, `rule_labels`, `rule_name`, `rule_describe`, `conditions_data`, `processing_data`) VALUES ('20210124075021', '20210124080121', 'rule_01', '规则一', '原价大于100元8折处理', '{\"$1\": \">\",\"$2\": \"100\"}', '{\"$1\": \"0.8\"}');
INSERT INTO `drools`.`condition_f_v1` (`scene_id`, `rule_id`, `conditions_no`, `conditions_template`) VALUES ('20210124075021', '20210124080121', '20210124080921', '[{"type":"span","value":"原价"},{"type":"select","defaultValue":"$1","values":[{"value":">","label":"大于"},{"value":"<","label":"小于"}]},{"type":"text","value":"$2"}]');
INSERT INTO `drools`.`processing_f_v1` (`scene_id`, `rule_id`, `processing_no`, `processing_template`) VALUES ('20210124075021', '20210124080121', '20210124080922', '[{"type": "span","value": "实际金额 = 原价 * "},{"type": "text","value": "$1"}]');
INSERT INTO `drools`.`condition_b_v1` (`scene_id`, `rule_id`, `conditions_no`, `conditions_template`) VALUES ('20210124075021', '20210124080121', '20210124080921', '$order:com.drlm.flink.entity.Order(originalPrice > $1)');
INSERT INTO `drools`.`processing_b_v1` (`scene_id`, `rule_id`, `processing_no`, `processing_template`) VALUES ('20210124075021', '20210124080121', '20210124080922', '$order.setRealPrice($order.getOriginalPrice()*$1);');
-- 场景包含规则，规则包含多个条件和处理