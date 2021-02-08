package com.test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test4 {

	private static final  double PI = 3.141592654;       // 圆周率
	private static final  double EARTH_RADIUS1 = 6378.137;    // 地球近似半径
	private static final  double EARTH_RADIUS = 6378137;     // 赤道半径(单位m) 

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
            radLat1 = Math.PI / 2 + Math.abs(radLat1); // south  
        if (radLat1 > 0)  
            radLat1 = Math.PI / 2 - Math.abs(radLat1); // north  
        if (radLon1 < 0)  
            radLon1 = Math.PI * 2 - Math.abs(radLon1); // west  
        if (radLat2 < 0)  
            radLat2 = Math.PI / 2 + Math.abs(radLat2); // south  
        if (radLat2 > 0)  
            radLat2 = Math.PI / 2 - Math.abs(radLat2); // north  
        if (radLon2 < 0)  
            radLon2 = Math.PI * 2 - Math.abs(radLon2); // west  
        
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);  
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);  
        double z1 = EARTH_RADIUS * Math.cos(radLat1);  
  
        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);  
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);  
        double z2 = EARTH_RADIUS * Math.cos(radLat2);  
  
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
        // 余弦定理求夹角  
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));  
        double dist = theta * EARTH_RADIUS;  
        return dist;  
    }
    
   // 两点的距离，计算两点之间的角度（get_angle方法）用到
   static double get_distance(double lat1, double lng1, double lat2, double lng2)
    {
    	double radLat1 = lat1 * PI / 180.0;   // 角度  1˚ = π / 180
    	double radLat2 = lat2 * PI / 180.0;   // 角度  1˚ = π / 180
    	double a = radLat1 - radLat2;// 纬度之差
    	double b = lng1 * PI / 180.0 - lng2* PI / 180.0;  // 经度之差
    	double dst = 2 * Math.asin((Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))));
    	double dst1 = dst * EARTH_RADIUS1;
    	return dst1;
    }
    
    // 计算两点之间角度
    static int get_angle(double lat1, double lng1, double lat2, double lng2)
    {
    	double x = lat1 - lat2;
    	double y = lng1 - lng2;
    	int angle=-1;
    	if (y == 0 && x > 0) angle = 0;
    	if (y == 0 && x < 0) angle = 180;
    	if(x ==0 && y > 0) angle = 90;
    	if(x == 0 && y < 0) angle = 270;
    	if (angle == -1)
    	{
    		double dislat = get_distance(lat1, lng2, lat2, lng2);
    		double dislng = get_distance(lat2, lng1, lat2, lng2);
    		if (x > 0 && y > 0) angle = (int) ((Math.atan2(dislng, dislat) / PI * 180));
    		if (x < 0 && y > 0) angle = (int) ((Math.atan2(dislat, dislng) / PI * 180+90));
    		if (x < 0 && y < 0) angle = (int) ((Math.atan2(dislng, dislat) / PI * 180 + 180));
    		if (x > 0 && y < 0) angle = (int) ((Math.atan2(dislat, dislng) / PI * 180 + 270));
    	}
    	return angle;
    }

    
	public static void main(String[] args) throws Exception {
		
		Connection conn = DBUtil.connectDB();
		String sql = "select * from station";
		PreparedStatement ps = conn.prepareStatement(sql);
		PreparedStatement ps1 = conn.prepareStatement(sql);		
		ResultSet rs = ps.executeQuery();
		ResultSet rs1 = ps1.executeQuery();
		File csv = new File("F:/test1.txt"); // CSV数据文件	
		BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 追加行
		int tag = 0;
		while(rs.next()){
			double la1 = Double.parseDouble(rs.getString("latitude"));
			double lo1 = Double.parseDouble(rs.getString("longitude"));
			String stationid1 = rs.getString("station_id");
			while(rs1.next()){
				double la2 = Double.parseDouble(rs1.getString("latitude"));
				double lo2 = Double.parseDouble(rs1.getString("longitude"));
				String stationid2 = rs1.getString("station_id");
				double m = LantitudeLongitudeDist(lo1,la1,lo2,la2);
				int n = get_angle(la1,lo1,la2,lo2);
				
				if(m<=30000){
					if(n>=0&&n<45){
						tag = 1;
					}else if(n>=45&&n<90){
						tag = 2;
					}else if(n>=90&&n<135){
						tag = 3;
					}else if(n>=135&&n<180){
						tag = 4;
					}else if(n>=180&&n<225){
						tag = 5;
					}else if(n>=225&&n<270){
						tag = 6;
					}else if(n>=270&&n<315){
						tag = 7;
					}else if(n>=315&&n<=360){
						tag = 8;
					}
					if(n!=-1)
						bw.write(stationid1+",");
						bw.write(stationid2+","+tag);
						bw.newLine();
					}				
			}		
			rs1 = ps1.executeQuery();
		}
		bw.close();		
		conn.close();
	}

}
