package com.test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		
		try { 
            BufferedReader reader = new BufferedReader(new FileReader("F:/station_20181227_1.txt"));//换成你的文件名
            //reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null; 
            String[] onerow;
            
            File csv = new File("F:/station_20181227_2.txt"); // CSV数据文件			    
    	    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            
            while((line=reader.readLine())!=null){ 
            	onerow = line.split(",");  //默认分割符为逗号，可以不使用逗号 n.equals("")?"0":n
            	Double[]  in= new Double[onerow.length];
            	// 对String数组进行遍历循环，并转换成int类型的数组
            	for (int i = 0; i < onerow.length; i++) {
            		in[i] = Double.parseDouble(onerow[i]);
            	}
                Arrays.sort(in);
                String a1 = in[1].toString();
                String a11 = a1.split("\\.")[0];
                System.out.println(a11);
                String a12 = a1.split("\\.")[1];
                System.out.println(a1+"------"+a11+"------------"+a12);

                String a2 = in[2].toString();
                String a22 = a2.split("\\.")[0];
                String a21 = a2.split("\\.")[1];
                
                String a3 = in[3].toString();
                String a33 = a3.split("\\.")[0];
                String a31 = a3.split("\\.")[1];
                
                String a4 = in[4].toString();
                String a44 = a4.split("\\.")[0];
                String a41 = a4.split("\\.")[1];
                
                String a5 = in[5].toString();
                String a55 = a5.split("\\.")[0];
                String a51 = a5.split("\\.")[1];
                
                String a6 = in[6].toString();
                String a66 = a6.split("\\.")[0];
                String a61 = a6.split("\\.")[1];
                
                String a7 = in[7].toString();
                String a77 = a7.split("\\.")[0];
                String a71 = a7.split("\\.")[1];
                
                String a8 = in[8].toString();
                String a88 = a8.split("\\.")[0];
                String a81 = a8.split("\\.")[1];
                
                bw.write(a12+","+a21+","+a31+","+a41+","+a51+","+a61+","+a71+","+a81);
                bw.newLine();
            } 
            bw.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 

	}

}
