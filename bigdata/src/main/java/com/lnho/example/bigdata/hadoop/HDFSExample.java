package com.lnho.example.bigdata.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class HDFSExample {
    private static String HDFSPath = "hdfs://master:9000";
    private FileSystem fs;

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return
     * @author lnho
     * @date 2016-05-05
     */
    public void read(String filePath) throws IOException {
        Path path = getPath(filePath);

        FSDataInputStream in = null;
        try {
            in = fs.open(path);
            IOUtils.copyBytes(in, System.out, 4096, false);
            in.seek(0);//FSDataInputStream支持随机访问，可以回到文件头
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content  写入内容
     * @return
     * @author lnho
     * @date 2016-05-05
     */
    public void write(String filePath, String content) throws IOException {
        Path path = getPath(filePath);
        System.out.println("Create:" + path);
        InputStream in = new ByteArrayInputStream(content.getBytes());// 要写入的内容 
        FSDataOutputStream out = fs.create(path);
        //FSDataOutputStream out = fs.create(path, () -> System.out.println("."));//获取运行进度
        IOUtils.copyBytes(in, out, 4096, true);
    }

    private Path getPath(String path) {
        return new Path(HDFSPath + path);
    }

    /**
     * 初始化配置
     *
     * @author lnho
     * @date 2016-05-04
     */
    public void init() throws IOException {
        System.out.println("配置初始化。。。");
        Configuration conf = new Configuration();
        fs = FileSystem.get(conf);
    }

    /**
     * 关闭
     *
     * @author lnho
     * @date 2016-05-05
     */
    public void exit() throws IOException {
        fs.close();//关闭hdfs 
    }

    /**
     * 列出目录中的文件
     *
     * @author lnho
     * @date 2016-05-04
     */
    public void ls(String filePath) throws IOException {
        // 列出hdfs上filePath目录下的所有文件和目录
        FileStatus[] statuses = fs.listStatus(getPath(filePath));
        for (FileStatus status : statuses) {
            System.out.println(status.getPath());
        }
    }

    public static void main(String[] args) {
        HDFSExample hdfsExample = new HDFSExample();
        try {
            hdfsExample.init();
            hdfsExample.ls("/");
            hdfsExample.write("/rsaTest.txt", "Hello world");
            hdfsExample.ls("/");
            hdfsExample.read("/rsaTest.txt");
            hdfsExample.exit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
