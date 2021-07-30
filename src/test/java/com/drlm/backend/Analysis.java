package com.drlm.backend;


import com.drlm.backend.dto.UpdateConditionParam;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.*;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/21
 */
public class Analysis {

    @Test
    public void Test1() {

        StringBuffer sb = new StringBuffer();
        sb.append("package book.discount ");
        sb.append("rule rule_1 ");
        sb.append("salience 9 ");
        sb.append("when ");
        sb.append("eval(true) ");
        sb.append("then ");
        sb.append("System.out.println(\"ok\");");
        System.out.println(sb.toString().getBytes());
        System.out.println(sb.toString());


        //String hdfs_path = "hdfs://MacBookPro:9000/test/scene_test/rule_test1.drl";
        String hdfs_path = "hdfs://MacBookPro:9000/drools/scene/20210124075021/rule.drl";
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs;
        FSDataOutputStream outputStream;
        try {
            fs = FileSystem.get(URI.create(hdfs_path), conf);
            Path path = new Path(hdfs_path);
            if (fs.exists(path)) {
                fs.delete(path, false);
            }
            outputStream = fs.create(path);
            if (outputStream != null) {
                outputStream.write("package order.discount rule rule_01 when $order:com.drlm.flink.entity.Order(originalPrice < 100 && originalPrice > 0) then $order.setRealPrice($order.getOriginalPrice()*0.9); end rule rule_02 when $order:com.drlm.flink.entity.Order(originalPrice >= 100) then $order.setRealPrice($order.getOriginalPrice()*0.7); end".getBytes());
            }

            outputStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test2() {
        String hdfs_path = "hdfs://MacBookPro:9000/test/scene_test/rule_test1.drl";
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs;
        try {
            fs = FileSystem.get(URI.create(hdfs_path), conf);
            Path path = new Path(hdfs_path);
            if (!fs.exists(path)) {
                System.out.println("文件不存在");
                return;
            }
            // 使用open方法获得一个输入流
            FSDataInputStream fsDataInputStream = fs.open(path);
            // 使用缓冲流读取文件内容
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream, "UTF-8"));
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                System.out.println(temp);
            }

            bufferedReader.close();
            fsDataInputStream.close();

            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test3() {
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
            recordMetadata = producer.send(new ProducerRecord<>("test", 0, null, "hello")).get();
            System.out.println(recordMetadata.partition());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        producer.close();
    }

    @Test
    public void Test4() {
        String aviator_exp = "hdfs://MacBookPro:9000/aviator/originalPrice/aviator.exp";
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs;
        FSDataOutputStream outputStream;
        try {
            fs = FileSystem.get(URI.create(aviator_exp), conf);
            Path path = new Path(aviator_exp);
            if (fs.exists(path)) {
                fs.delete(path, false);
            }
            outputStream = fs.create(path);
            if (outputStream != null) {
                outputStream.write("originalPrice * 0.8".getBytes());
            }
            outputStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test5() {
        String aviator_exp = "hdfs://MacBookPro:9000/aviator/originalPrice/aviator.exp";
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        FileSystem fs;
        try {
            fs = FileSystem.get(URI.create(aviator_exp), conf);
            Path path = new Path(aviator_exp);
            if (!fs.exists(path)) {
                System.out.println("文件不存在");
                return;
            }
            // 使用open方法获得一个输入流
            FSDataInputStream fsDataInputStream = fs.open(path);
            // 使用缓冲流读取文件内容
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fsDataInputStream, "UTF-8"));
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                System.out.println(temp);
            }
            bufferedReader.close();
            fsDataInputStream.close();
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Test6() {
        List<UpdateConditionParam> conditions = new ArrayList<>();
        UpdateConditionParam u1 = new UpdateConditionParam();
        u1.setConditionNo("20200105");
        conditions.add(u1);

        UpdateConditionParam u2 = new UpdateConditionParam();
        u2.setConditionNo("20200102");
        conditions.add(u2);

        Collections.sort(conditions, (o1, o2) -> o1.getConditionNo().compareTo(o2.getConditionNo()));

        System.out.println(conditions.size());

    }
}
