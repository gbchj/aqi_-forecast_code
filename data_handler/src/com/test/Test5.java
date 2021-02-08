package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test5 {

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
		String sql1 = "select * from weather_quadrant_2";
		String sql2 = "select * from weather_quadrant_2";
		PreparedStatement ps1 = conn.prepareStatement(sql1);
		PreparedStatement ps2 = conn.prepareStatement(sql2);
		ResultSet rs1 = ps1.executeQuery();
		ResultSet rs2 = ps2.executeQuery();
		File csv = new File("F:/test3.txt"); // CSV数据文件
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 追加行

		while (rs1.next()) {

			String stationid = rs1.getString("station_id");
			String time = rs1.getString("time");
			String humidity = rs1.getString("humidity");
			String pressure = rs1.getString("pressure");
			String temperature = rs1.getString("temperature");
			String wind_direction = rs1.getString("wind_direction");
			String wind_speed = rs1.getString("wind_speed");
			String aqi = rs1.getString("AQI");
			
			while(rs2.next()){
				
				
				
			}
			
			
			
			bw.newLine();

		}

		bw.close();

	}

}
