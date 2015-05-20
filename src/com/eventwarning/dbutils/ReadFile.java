package com.eventwarning.dbutils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	public static List<String> readFileByLinestoList(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> list = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            list = new ArrayList<String>();
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	int i=0;
            	String [] tempArr = tempString.split(",");
            	while(i!=tempArr.length){
            		list.add(tempArr[i]);
            		i++;
            	}
                line++;
            }
            System.out.println("读完了：");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return list;
    }
	/**
	 * 获取每行元素个数
	 */
	public static int LineElemNumsCount(String fileName){
		File file = new File(fileName);
        BufferedReader reader = null;
        int count = 0;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            if ((tempString = reader.readLine()) != null) {
                // 显示行号
            	String[] tempArr = tempString.split(",");
            	count = tempArr.length;
                
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return count;
	}
	/**
	 * 获取总行数
	 */
	public static int LineNumsCount(String fileName){
		File file = new File(fileName);
        BufferedReader reader = null;
        int line = 0;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return line;
	}
}
