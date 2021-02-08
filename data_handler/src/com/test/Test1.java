package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class Test1 {

	private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m) 
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
	private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
      
    /** 
     * 基于余弦定理求两经纬度距离 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位m 
     * */  
    public static double LantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
  
        double radLon1 = rad(lon1);  
        double radLon2 = rad(lon2);  
  
        if (radLat1 < 0)  
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south  
        if (radLat1 > 0)  
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north  
        if (radLon1 < 0)  
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west  
        if (radLat2 < 0)  
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south  
        if (radLat2 > 0)  
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north  
        if (radLon2 < 0)  
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west  
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);  
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);  
        double z1 = EARTH_RADIUS * Math.cos(radLat1);  
  
        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);  
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);  
        double z2 = EARTH_RADIUS * Math.cos(radLat2);  
  
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
        //余弦定理求夹角  
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));  
        double dist = theta * EARTH_RADIUS;  
        return dist;  
    }
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = DBUtil.connectDB();
		String sql = "select * from station";
		String sql1 = "select * from station";
		PreparedStatement ps1 = conn.prepareStatement(sql);
		PreparedStatement ps2 = conn.prepareStatement(sql1);
		ResultSet rs = ps1.executeQuery();
		ResultSet rs1 = ps2.executeQuery();

	    File csv = new File("F:/station_20181227_1.txt"); // CSV数据文件			    
	    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
		while(rs.next()){
			double lon1;
			double lat1;
			try{
				String lo = rs.getString("longitude");
				String la = rs.getString("latitude");
				if(lo.length()>8)
					lo = lo.substring(0, 8);
				if(la.length()>8)
					la = la.substring(0, 8);
				 lon1 = Double.parseDouble(lo);
				 lat1 = Double.parseDouble(la);
			}catch(Exception e){
				continue;
			}			
			String s1 = rs.getString("station_id");
			System.out.println(s1);
			//bw.write(s1+",");
			while(rs1.next()){
				double lon2;
				double lat2;
				String s2 = rs1.getString("station_id");
				try{
					String lo = rs1.getString("longitude");
					String la = rs1.getString("latitude");					
					if(lo.length()>8)
						lo = lo.substring(0, 8);
					if(la.length()>8)
						la = la.substring(0, 8);
					lon2 = Double.parseDouble(lo);
					lat2 = Double.parseDouble(la);
				}catch(Exception e){
					continue;
				}			
				double n = LantitudeLongitudeDist(lon1,lat1,lon2,lat2);
				int n1 = (int) Math.floor(n);
				String m = String.valueOf(n1);
				bw.write(m+"."+s2+",");
			}
			System.out.println("---------");
			bw.newLine();
			rs1 = ps2.executeQuery();
		}
		bw.close(); 
		System.out.println("aaa");
	}
	
}
