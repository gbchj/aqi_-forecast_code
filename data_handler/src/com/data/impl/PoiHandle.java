package com.data.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.data.dao.Poi;

public class PoiHandle {

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
     * @return 返回的距离，单位km 
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
		String sql = "select * from temp_station_city";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs1;
		ResultSet rs = ps.executeQuery();
		Poi p = new Poi();
		String sql1 = "select * from beijing";
		ps = conn.prepareStatement(sql1);
		rs1 = ps.executeQuery();
		String sql2 = "select * from guangdong";
		ps = conn.prepareStatement(sql2);
		ResultSet rs2 = ps.executeQuery();
		String sql3 = "select * from tianjin";
		ps = conn.prepareStatement(sql3);
		ResultSet rs3 = ps.executeQuery();
		String sql4 = "select * from xianggang";
		ps = conn.prepareStatement(sql4);
		ResultSet rs4 = ps.executeQuery();
		String sql5 = "select * from hebei";
		ps = conn.prepareStatement(sql5);
		ResultSet rs5 = ps.executeQuery();
		String sql6 = "select * from shan1xi";
		ps = conn.prepareStatement(sql6);
		ResultSet rs6 = ps.executeQuery();
		String sql7 = "select * from shandong";
		ps = conn.prepareStatement(sql7);
		ResultSet rs7 = ps.executeQuery();
		String sql8 = "select * from guangxi";
		ps = conn.prepareStatement(sql8);
		ResultSet rs8 = ps.executeQuery();
		int i =0;
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
			/*
			if(rs.getString("name_chinese").equals("北京")){				
				while(rs1.next()){	
					double lon2;
					double lat2;
					try{
						String lo1 = rs1.getString("longitude");
						String la1 = rs1.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}				
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){												
						p.setStationId(s1);	
						String s2 = rs1.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs1.getString("title");
						p.setTitle(s3);
						String s4 = rs1.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs1.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs1.getString("address");
						p.setAddress(s6);
						String s7 = rs1.getString("category");
						p.setCategory(s7);
						String s8 = rs1.getString("city");
						p.setCity(s8);
						String s9 = rs1.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs1.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						ps = conn.prepareStatement(sql11);
						ps.setString(1, p.getStationId());
						ps.setString(2, p.getPoiId());
						ps.setString(3, p.getTitle());
						ps.setString(4, p.getLongitude());
						ps.setString(5, p.getLatitude());
						ps.setString(6, p.getAddress());
						ps.setString(7, p.getCategory());
						ps.setString(8, p.getCity());
						ps.setString(9, p.getCheckinNum());
						ps.setString(10, p.getPhotoNum());
						ps.executeUpdate();	
						i++;
						System.out.println("1:"+i);
					}
				}
			}
			
			if(rs.getString("name_chinese").equals("深圳") || rs.getString("name_chinese").equals("广州") || rs.getString("name_chinese").equals("潮州") || rs.getString("name_chinese").equals("东莞")
					|| rs.getString("name_chinese").equals("佛山") || rs.getString("name_chinese").equals("河源") || rs.getString("name_chinese").equals("惠州") || rs.getString("name_chinese").equals("江门")
					|| rs.getString("name_chinese").equals("揭阳") || rs.getString("name_chinese").equals("茂名") || rs.getString("name_chinese").equals("梅州") || rs.getString("name_chinese").equals("清远")
					|| rs.getString("name_chinese").equals("汕头") || rs.getString("name_chinese").equals("汕尾") || rs.getString("name_chinese").equals("韶关") || rs.getString("name_chinese").equals("阳江")
					|| rs.getString("name_chinese").equals("云浮") || rs.getString("name_chinese").equals("肇庆") || rs.getString("name_chinese").equals("中山") || rs.getString("name_chinese").equals("珠海")){
				
				while(rs2.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs2.getString("longitude");
						String la1 = rs2.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs2.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs2.getString("title");
						p.setTitle(s3);
						String s4 = rs2.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs2.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs2.getString("address");
						p.setAddress(s6);
						String s7 = rs2.getString("category");
						p.setCategory(s7);
						String s8 = rs2.getString("city");
						p.setCity(s8);
						String s9 = rs2.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs2.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();
						i++;
						System.out.println("2:"+i);
					}
				}
			}
			if(rs.getString("name_chinese").equals("天津")){				
				while(rs3.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs3.getString("longitude");
						String la1 = rs3.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){				
						p.setStationId(s1);	
						String s2 = rs3.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs3.getString("title");
						p.setTitle(s3);
						String s4 = rs3.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs3.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs3.getString("address");
						p.setAddress(s6);
						String s7 = rs3.getString("category");
						p.setCategory(s7);
						String s8 = rs3.getString("city");
						p.setCity(s8);
						String s9 = rs3.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs3.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();
						i++;
						System.out.println("3:"+i);
					}
				}
			}*/
			/*
			if(rs.getString("name_chinese").equals("香港")){		
				while(rs4.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs4.getString("longitude");
						String la1 = rs4.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs4.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs4.getString("title");
						p.setTitle(s3);
						String s4 = rs4.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs4.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs4.getString("address");
						p.setAddress(s6);
						String s7 = rs4.getString("category");
						p.setCategory(s7);
						String s8 = rs4.getString("city");
						p.setCity(s8);
						String s9 = rs4.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs4.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();	
						i++;
						System.out.println("4:"+i);
					}
				}
			}*/
			/*
			if(rs.getString("name_chinese").equals("石家庄") || rs.getString("name_chinese").equals("辛集") || rs.getString("name_chinese").equals("唐山") || rs.getString("name_chinese").equals("秦皇岛")
					|| rs.getString("name_chinese").equals("保定") || rs.getString("name_chinese").equals("定州") || rs.getString("name_chinese").equals("张家口") || rs.getString("name_chinese").equals("承德市")
					|| rs.getString("name_chinese").equals("沧州") || rs.getString("name_chinese").equals("廊坊") || rs.getString("name_chinese").equals("衡水")){
				
				while(rs5.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs5.getString("longitude");
						String la1 = rs5.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs5.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs5.getString("title");
						p.setTitle(s3);
						String s4 = rs5.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs5.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs5.getString("address");
						p.setAddress(s6);
						String s7 = rs5.getString("category");
						p.setCategory(s7);
						String s8 = rs5.getString("city");
						p.setCity(s8);
						String s9 = rs5.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs5.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();	
						i++;
						System.out.println("5:"+i);
					}
				}
			}*/
			/*
			if(rs.getString("name_chinese").equals("大同")){
				
				while(rs6.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs6.getString("longitude");
						String la1 = rs6.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs6.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs6.getString("title");
						p.setTitle(s3);
						String s4 = rs6.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs6.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs6.getString("address");
						p.setAddress(s6);
						String s7 = rs6.getString("category");
						p.setCategory(s7);
						String s8 = rs6.getString("city");
						p.setCity(s8);
						String s9 = rs6.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs6.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();	
						i++;
						System.out.println("6:"+i);
					}
				}
			}*/
			
			if(rs.getString("name_chinese").equals("济南") || rs.getString("name_chinese").equals("淄博") || rs.getString("name_chinese").equals("德州") ||
					rs.getString("name_chinese").equals("滨州") || rs.getString("name_chinese").equals("东营") || rs.getString("name_chinese").equals("郴州")){				
				while(rs7.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs7.getString("longitude");
						String la1 = rs7.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs7.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs7.getString("title");
						p.setTitle(s3);
						String s4 = rs7.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs7.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs7.getString("address");
						p.setAddress(s6);
						String s7 = rs7.getString("category");
						p.setCategory(s7);
						String s8 = rs7.getString("city");
						p.setCity(s8);
						String s9 = rs7.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs7.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();	
						i++;
						System.out.println("7:"+i);
					}
				}
			}
			/*
			if(rs.getString("name_chinese").equals("梧州") || rs.getString("name_chinese").equals("贺州")){			
				while(rs8.next()){
					double lon2;
					double lat2;
					try{
						String lo1 = rs8.getString("longitude");
						String la1 = rs8.getString("latitude");					
						if(lo1.length()>8)
							lo1 = lo1.substring(0, 8);
						if(la1.length()>8)
							la1 = la1.substring(0, 8);
						lon2 = Double.parseDouble(lo1);
						lat2 = Double.parseDouble(la1);
					}catch(Exception e){continue;}	
					if(LantitudeLongitudeDist(lon1, lat1, lon2, lat2)<=5000){					
						p.setStationId(s1);	
						String s2 = rs8.getString("poiId");
						p.setPoiId(s2);
						String s3 = rs8.getString("title");
						p.setTitle(s3);
						String s4 = rs8.getString("longitude");
						p.setLongitude(s4);
						String s5 = rs8.getString("latitude");
						p.setLatitude(s5);
						String s6 = rs8.getString("address");
						p.setAddress(s6);
						String s7 = rs8.getString("category");
						p.setCategory(s7);
						String s8 = rs8.getString("city");
						p.setCity(s8);
						String s9 = rs8.getString("checkin_num");
						p.setCheckinNum(s9);
						String s10 = rs8.getString("photo_num");
						p.setPhotoNum(s10);					
						String sql11 = "insert into poi(station_id,poiId,title,longitude,latitude,address,category,city,checkin_num,photo_num) values(?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement ps11 = conn.prepareStatement(sql11);
						ps11.setString(1, p.getStationId());
						ps11.setString(2, p.getPoiId());
						ps11.setString(3, p.getTitle());
						ps11.setString(4, p.getLongitude());
						ps11.setString(5, p.getLatitude());
						ps11.setString(6, p.getAddress());
						ps11.setString(7, p.getCategory());
						ps11.setString(8, p.getCity());
						ps11.setString(9, p.getCheckinNum());
						ps11.setString(10, p.getPhotoNum());
						ps11.executeUpdate();	
						i++;
						System.out.println("8:"+i);
					}
				}
			}*/
		}

	}

}
