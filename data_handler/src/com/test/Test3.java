package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.test.Test1.DBUtil;

public class Test3 {

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
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = DBUtil.connectDB();
		String sql = "select * from poi_data_top10 ORDER BY stationid,poidistance";
		PreparedStatement ps1 = conn.prepareStatement(sql);
		ResultSet rs = ps1.executeQuery();
		String stationid,poitype,poitypecode,poidistance;
		File csv = new File("F:/poi_distance.csv"); // CSV数据文件			    
 	    BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
		while(rs.next()){
			int tmp = Integer.parseInt(rs.getString("new_rank"));
			if(tmp==10){
				stationid = rs.getString("stationid");
				poitype = rs.getString("poitype");
				poitypecode = rs.getString("poitypecode");
				poidistance = rs.getString("poidistance");
				bw.write(stationid+","+poitype+","+poitypecode+","+poidistance+",");
				bw.newLine();
			}else{
				stationid = rs.getString("stationid");
				poitype = rs.getString("poitype");
				poitypecode = rs.getString("poitypecode");
				poidistance = rs.getString("poidistance");
				bw.write(stationid+","+poitype+","+poitypecode+","+poidistance+",");
			}
		}
		bw.close();
	}

}
