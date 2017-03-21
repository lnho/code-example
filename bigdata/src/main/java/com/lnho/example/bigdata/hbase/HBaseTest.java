package com.lnho.example.bigdata.hbase;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HBaseTest {

    public static Configuration configuration;

    static {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "hadoop180,hadoop183,hadoop182");
        configuration.set("hbase.master", "hadoop180:600000");
    }

    public static void main(String[] args) {
        //System.out.println("linhao" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + (new Random().nextInt(8999) + 1000));
        //createTable("wfinfo_linhao");
        insertData("wfinfo_linhao");
        // QueryAll("wujintao"); 
        // QueryByCondition1("wujintao"); 
        QueryByCondition4("wfinfo_linhao");
        QueryByCondition2("wfinfo_linhao");
        //QueryByCondition3("wujintao");
        //deleteRow("wujintao","abcdef"); 
        //deleteByCondition("wujintao","abcdef");
    }


    public static void createTable(String tableName) {
        System.out.println("start create table ......");
        try {
            HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
            if (hBaseAdmin.tableExists(tableName)) {// 如果存在要创建的表，那么先删除，再创建 
                hBaseAdmin.disableTable(tableName);
                hBaseAdmin.deleteTable(tableName);
                System.out.println(tableName + " is exist,detele....");
            }
            HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
            tableDescriptor.addFamily(new HColumnDescriptor("info"));
            hBaseAdmin.createTable(tableDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end create table ......");
    }


    public static void insertData(String tableName) {
        System.out.println("start insert data ......");
        HTablePool pool = new HTablePool(configuration, 1000);
        HTableInterface table = pool.getTable(tableName);
        String rowkey = "linhao" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + (new Random().nextInt(8999) + 1000);//mayun201606071725368888
        Put put = new Put(rowkey.getBytes());// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值
        put.add("info".getBytes(), "bid".getBytes(), "1111".getBytes());// 本行数据的第一列
        put.add("info".getBytes(), "fid".getBytes(), "1111".getBytes());// 本行数据的第一列
        put.add("info".getBytes(), "mid".getBytes(), "1111".getBytes());// 本行数据的第一列
        put.add("info".getBytes(), "mac".getBytes(), Bytes.toBytes("kkk"));// 本行数据的第一列
        put.add("info".getBytes(), "t".getBytes(), "1111".getBytes());// 本行数据的第一列
        put.add("info".getBytes(), "f".getBytes(), "1111".getBytes());// 本行数据的第一列
        try {
            table.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end insert data ......");
    }


    public static void dropTable(String tableName) {
        try {
            HBaseAdmin admin = new HBaseAdmin(configuration);
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteRow(String tablename, String rowkey) {
        try {
            HTable table = new HTable(configuration, tablename);
            List list = new ArrayList();
            Delete d1 = new Delete(rowkey.getBytes());
            list.add(d1);

            table.delete(list);
            System.out.println("删除行成功!");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void deleteByCondition(String tablename, String rowkey) {
        //目前还没有发现有效的API能够实现根据非rowkey的条件删除这个功能能，还有清空表全部数据的API操作

    }


    public static void QueryAll(String tableName) {
        HTablePool pool = new HTablePool(configuration, 1000);
        HTable table = (HTable) pool.getTable(tableName);
        try {
            ResultScanner rs = table.getScanner(new Scan());
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println("列：" + new String(keyValue.getFamily())
                            + "====值:" + new String(keyValue.getValue()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void QueryByCondition1(String tableName) {

        HTablePool pool = new HTablePool(configuration, 1000);
        HTable table = (HTable) pool.getTable(tableName);
        try {
            Get scan = new Get("abcdef".getBytes());// 根据rowkey查询 
            Result r = table.get(scan);
            System.out.println("获得到rowkey:" + new String(r.getRow()));
            for (KeyValue keyValue : r.raw()) {
                System.out.println("列：" + new String(keyValue.getFamily())
                        + "====值:" + new String(keyValue.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void QueryByCondition2(String tableName) {
        System.out.println("根据mac查询");
        try {
            HTablePool pool = new HTablePool(configuration, 1000);
            HTableInterface table = pool.getTable(tableName);
            Filter filter = new SingleColumnValueFilter(Bytes
                    .toBytes("info"), Bytes.toBytes("mac"), CompareOp.EQUAL, Bytes.toBytes("linhao")); // 当列column1的值为aaa时进行查询
            Scan s = new Scan();
            s.setFilter(filter);
            ResultScanner rs = table.getScanner(s);
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println("列：" + new String(keyValue.getQualifier())
                            + "====值:" + new String(keyValue.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void QueryByCondition4(String tableName) {
        System.out.println("根据startrow和stoprow查询");
        try {
            HTablePool pool = new HTablePool(configuration, 1000);
            HTableInterface table = pool.getTable(tableName);
            Scan s = new Scan();
            s.setStartRow(Bytes.toBytes("525"));
            s.setStopRow(Bytes.toBytes("linhao"));
            ResultScanner rs = table.getScanner(s);
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println("列：" + new String(keyValue.getFamily())
                            + "====值:" + new String(keyValue.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void QueryByCondition3(String tableName) {

        try {
            HTablePool pool = new HTablePool(configuration, 1000);
            HTable table = (HTable) pool.getTable(tableName);

            List<Filter> filters = new ArrayList<Filter>();

            Filter filter1 = new SingleColumnValueFilter(Bytes
                    .toBytes("column1"), null, CompareOp.EQUAL, Bytes
                    .toBytes("aaa"));
            filters.add(filter1);

            Filter filter2 = new SingleColumnValueFilter(Bytes
                    .toBytes("column2"), null, CompareOp.EQUAL, Bytes
                    .toBytes("bbb"));
            filters.add(filter2);

            Filter filter3 = new SingleColumnValueFilter(Bytes
                    .toBytes("column3"), null, CompareOp.EQUAL, Bytes
                    .toBytes("ccc"));
            filters.add(filter3);

            FilterList filterList1 = new FilterList(filters);

            Scan scan = new Scan();
            scan.setFilter(filterList1);
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                System.out.println("获得到rowkey:" + new String(r.getRow()));
                for (KeyValue keyValue : r.raw()) {
                    System.out.println("列：" + new String(keyValue.getFamily())
                            + "====值:" + new String(keyValue.getValue()));
                }
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}