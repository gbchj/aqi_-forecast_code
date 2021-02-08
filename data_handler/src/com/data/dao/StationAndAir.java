package com.data.dao;

public class StationAndAir {

	private String station_id;
	private String time;
	private String latitude;
	private String longitude;
	private String AQI;
	private int id;
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAQI() {
		return AQI;
	}
	public void setAQI(String aQI) {
		AQI = aQI;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public StationAndAir(String station_id, String time, String latitude,
			String longitude, String aQI, int id) {
		super();
		this.station_id = station_id;
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
		AQI = aQI;
		this.id = id;
	}
	public StationAndAir() {
		super();
	}
	@Override
	public String toString() {
		return "StationAndAir [station_id=" + station_id + ", time=" + time
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", AQI=" + AQI + ", id=" + id + "]";
	}
	
	
	
}
