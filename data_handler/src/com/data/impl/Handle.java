package com.data.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.BranchElement;

import com.data.dao.Weather;

public class Handle {
	
	public static class DBUtil {    
		
	    private static final String URI = "jdbc:mysql://localhost:3306/test?"  
	            + "user=root&password=root&useUnicode=true&characterEncoding=UTF-8";        
	    private static final String DRIVER = "com.mysql.jdbc.Driver";        
	    public static Connection connectDB() throws Exception { 
	        Class.forName(DRIVER);    
	        Connection conn = DriverManager.getConnection(URI);            
	        return conn;  
	    }        
	}  

	public static void main(String[] args) throws Exception{
		
		List<Weather> list = new ArrayList<Weather>();
		
		//��ȡ����
		Connection conn = DBUtil.connectDB();
		//���sql���
		String sql = "select * from airquality_14_08";
		PreparedStatement ps = conn.prepareStatement(sql);
		//���ز�ѯ���
		ResultSet rs = ps.executeQuery();
		//�����ѯ�������ݴ���weather������
		while(rs.next()){
			Weather weather = new Weather();
			weather.setStation_id(rs.getString("station_id"));
			/*//����time
			//2014-05-01 00:00:00
			String s = rs.getString("time");
			String ss = s.substring(4, s.length() - 6).replace(" ", "").replace("-", "");
			weather.setTime(ss);*/
			weather.setTime(rs.getString("time"));
			weather.setCO_Concentration(rs.getString("CO_Concentration"));
			weather.setNO2_Concentration(rs.getString("NO2_Concentration"));
			weather.setO3_Concentration(rs.getString("O3_Concentration"));
			weather.setPM10_Concentration(rs.getString("PM10_Concentration"));
			weather.setPM25_Concentration(rs.getString("PM25_Concentration"));
			weather.setSO2_Concentration(rs.getString("SO2_Concentration"));
			weather.setId(rs.getInt("id"));
			list.add(weather);
		}
		
		double Ih,Il,Ch,Cl,INO2 = 0,IO3 = 0,IPM10 = 0,IPM25 = 0,ISO2 = 0,CNO2,CO3,CPM10,CPM25,CSO2;
	
		
		for (Weather w : list) {
			
			if(w.getNO2_Concentration()!=null&&!"null".equalsIgnoreCase(w.getNO2_Concentration())){
				CNO2 = Double.parseDouble(w.getNO2_Concentration());
				//����NO2��AQI
				if(CNO2>=0&&CNO2<40){
					Ih = 50;
					Il = 0;
					Ch = 40;
					Cl = 0;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=40&&CNO2<80){
					Ih = 100;
					Il = 50;
					Ch = 80;
					Cl = 40;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=80&&CNO2<180){
					Ih = 150;
					Il = 100;
					Ch = 180;
					Cl = 80;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=180&&CNO2<280){
					Ih = 200;
					Il = 150;
					Ch = 280;
					Cl = 180;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=280&&CNO2<565){
					Ih = 200;
					Il = 300;
					Ch = 565;
					Cl = 280;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=565&&CNO2<750){
					Ih = 400;
					Il = 300;
					Ch = 750;
					Cl = 565;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}else if(CNO2>=750&&CNO2<=940){
					Ih = 500;
					Il = 400;
					Ch = 940;
					Cl = 750;
					INO2 = (Ih-Il)/(Ch-Cl)*(CNO2-Cl)+Il;
				}
			}else{
				INO2 = 0;
			}	
			if(w.getO3_Concentration()!=null&&!"null".equalsIgnoreCase(w.getO3_Concentration())){
				CO3 = Double.parseDouble(w.getO3_Concentration());
				//����O3��AQI
				if(0<=CO3&&CO3<160){
					Ih = 50;
					Il = 0;
					Ch = 160;
					Cl = 0;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(160<=CO3&&CO3<200){
					Ih = 100;
					Il = 50;
					Ch = 200;
					Cl = 160;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(200<=CO3&&CO3<300){
					Ih = 150;
					Il = 100;
					Ch = 300;
					Cl = 200;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(300<=CO3&&CO3<400){
					Ih = 200;
					Il = 150;
					Ch = 400;
					Cl = 300;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(400<=CO3&&CO3<800){
					Ih = 300;
					Il = 200;
					Ch = 800;
					Cl = 400;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(800<=CO3&&CO3<1000){
					Ih = 400;
					Il = 300;
					Ch = 1000;
					Cl = 800;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}else if(1000<=CO3&&CO3<=1200){
					Ih = 500;
					Il = 400;
					Ch = 1200;
					Cl = 1000;
					IO3 = (Ih-Il)/(Ch-Cl)*(CO3-Cl)+Il;
				}
			}else{
				IO3 = 0;
			}
			if(w.getPM10_Concentration()!=null&&!"null".equalsIgnoreCase(w.getPM10_Concentration())){
				CPM10 = Double.parseDouble(w.getPM10_Concentration());
				//����PM10��AQI
				if(0<=CPM10&&CPM10<50){
					Ih = 50;
					Il = 0;
					Ch = 50;
					Cl = 0;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(50<=CPM10&&CPM10<150){
					Ih = 100;
					Il = 50;
					Ch = 150;
					Cl = 50;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(150<=CPM10&&CPM10<250){
					Ih = 150;
					Il = 100;
					Ch = 250;
					Cl = 150;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(250<=CPM10&&CPM10<350){
					Ih = 200;
					Il = 150;
					Ch = 350;
					Cl = 250;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(350<=CPM10&&CPM10<420){
					Ih = 300;
					Il = 200;
					Ch = 420;
					Cl = 350;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(420<=CPM10&&CPM10<500){
					Ih = 400;
					Il = 300;
					Ch = 500;
					Cl = 420;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}else if(500<=CPM10&&CPM10<=600){
					Ih = 500;
					Il = 400;
					Ch = 600;
					Cl = 500;
					IPM10 = (Ih-Il)/(Ch-Cl)*(CPM10-Cl)+Il;
				}
			}else{
				IPM10 = 0;
			}
			if(w.getPM25_Concentration()!=null&&!"null".equalsIgnoreCase(w.getPM25_Concentration())){
				CPM25 = Double.parseDouble(w.getPM25_Concentration());
				//����PM25��AQI
				if(0<=CPM25&&CPM25<35){
					Ih = 50;
					Il = 0;
					Ch = 35;
					Cl = 0;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(35<=CPM25&&CPM25<75){
					Ih = 100;
					Il = 50;
					Ch = 75;
					Cl = 35;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(75<=CPM25&&CPM25<115){
					Ih = 150;
					Il = 100;
					Ch = 115;
					Cl = 75;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(115<=CPM25&&CPM25<150){
					Ih = 200;
					Il = 150;
					Ch = 150;
					Cl = 115;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(150<=CPM25&&CPM25<250){
					Ih = 300;
					Il = 200;
					Ch = 250;
					Cl = 150;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(250<=CPM25&&CPM25<350){
					Ih = 400;
					Il = 300;
					Ch = 350;
					Cl = 250;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}else if(350<=CPM25&&CPM25<=500){
					Ih = 500;
					Il = 400;
					Ch = 500;
					Cl = 350;
					IPM25 = (Ih-Il)/(Ch-Cl)*(CPM25-Cl)+Il;
				}
			}else{
				IPM25 = 0;
			}	
			if(w.getSO2_Concentration()!=null&&!"null".equalsIgnoreCase(w.getSO2_Concentration())){
				CSO2 = Double.parseDouble(w.getSO2_Concentration());
				//����SO2��AQI
				if(0<=CSO2&&CSO2<50){
					Ih = 50;
					Il = 0;
					Ch = 50;
					Cl = 0;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(50<=CSO2&&CSO2<150){
					Ih = 100;
					Il = 50;
					Ch = 150;
					Cl = 50;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(150<=CSO2&&CSO2<475){
					Ih = 150;
					Il = 100;
					Ch = 475;
					Cl = 150;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(475<=CSO2&&CSO2<800){
					Ih = 200;
					Il = 150;
					Ch = 800;
					Cl = 475;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(800<=CSO2&&CSO2<1600){
					Ih = 300;
					Il = 200;
					Ch = 1600;
					Cl = 800;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(1600<=CSO2&&CSO2<2100){
					Ih = 400;
					Il = 300;
					Ch = 2100;
					Cl = 1600;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}else if(2100<=CSO2&&CSO2<=2620){
					Ih = 500;
					Il = 400;
					Ch = 2620;
					Cl = 2100;
					ISO2 = (Ih-Il)/(Ch-Cl)*(CSO2-Cl)+Il;
				}
			}else{
				ISO2 = 0;
			}
			
			//ѡȡ����ʵ�AQI
			double I = 0;
			//System.out.println(INO2+", "+IO3+", "+IPM10+", "+IPM25+", "+ISO2);
			if(INO2>I){
				I = INO2;
			}
			if(IO3>I){
				I = IO3;
			}
			if(IPM10>I){
				I = IPM10;
			}
			if(IPM25>I){
				I = IPM25;
			}	
			if(ISO2>I){
				I = ISO2;
			}
			int result = (int)I;
			String resultString = String.valueOf(result);
			int id = w.getId();
			String sql1 = "update airquality_14_08 set AQI=? where id="+id;
			ps = conn.prepareStatement(sql1);
			ps.setString(1, resultString);
			ps.executeUpdate();	
			System.out.println(resultString);
		}		
			
	}	
	
}
