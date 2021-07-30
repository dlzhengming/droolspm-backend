package com.drlm.backend.service.impl;

import com.drlm.backend.service.TriggerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/29
 */

@Service
public class TriggerServiceImpl implements TriggerService {

    @Override
    public boolean triggerSceneRules(String sceneId) {
        String hdfs_path = "hdfs://MacBookPro:9000/drools/scene/" + sceneId + "/rule.drl";
        Properties props = new Properties();

        //broker地址
        props.put("bootstrap.servers", "localhost:9092");
        //指定消息key序列化方式
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //指定消息本身的序列化方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        RecordMetadata recordMetadata = null;
        try {
            recordMetadata = producer.send(new ProducerRecord<>("STATE_TOPIC", 0, null, sceneId)).get();
            System.out.println(recordMetadata.partition());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (producer != null) {
                producer.close();
            }
            return false;
        }

    }
}
