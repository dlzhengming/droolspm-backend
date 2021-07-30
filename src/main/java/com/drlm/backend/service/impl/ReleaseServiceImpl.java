package com.drlm.backend.service.impl;

import com.drlm.backend.service.ReleaseService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * 新增
 *
 * @author zhengm
 * @date 2021/1/29
 */

@Service
public class ReleaseServiceImpl implements ReleaseService {

    @Override
    public boolean releaseSceneRules(String sceneId, String ruleStr) {
        String hdfs_path = "hdfs://MacBookPro:9000/drools/scene/" + sceneId + "/rule.drl";
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
                outputStream.write(ruleStr.getBytes());
            }
            outputStream.close();
            fs.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
