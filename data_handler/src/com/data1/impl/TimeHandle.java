package com.data1.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.data.dao.StationAndAir;
import com.data.dao.Weather;

public class TimeHandle {

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

		List<StationAndAir> list = new ArrayList<StationAndAir>();

		// ��ȡ����
		Connection conn = DBUtil.connectDB();
		// ���sql���
		String sql = "select id,time from tmp_result_05";
		PreparedStatement ps = conn.prepareStatement(sql);
		// ���ز�ѯ���
		ResultSet rs = ps.executeQuery();
		// �����ѯ�������ݴ���weather������
		while (rs.next()) {
			StationAndAir saa = new StationAndAir();
			String s = rs.getString("time");
			int id = rs.getInt("id");
			saa.setId(id);
			String ss = s.substring(4, s.length() - 6).replace(" ", "").replace("-", "");
			saa.setTime(ss);
			list.add(saa);
		}
		
		for (StationAndAir w : list) {
			int id = w.getId();
			String sql1 = "update tmp_result_05 set time=? where id="+id;
			ps = conn.prepareStatement(sql1);
			String timess = w.getTime();
			ps.setString(1, timess);
			ps.executeUpdate();	
			System.out.println(timess);
		}

	}

}
