package com.data.dao;

public class Poi {
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getPoiId() {
		return poiId;
	}
	public void setPoiId(String poiId) {
		this.poiId = poiId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCheckinNum() {
		return checkinNum;
	}
	public void setCheckinNum(String checkinNum) {
		this.checkinNum = checkinNum;
	}
	public String getPhotoNum() {
		return photoNum;
	}
	public void setPhotoNum(String photoNum) {
		this.photoNum = photoNum;
	}
	private String stationId;
	private String poiId;
	private String title;
	private String address;
	private String longitude;
	private String latitude;
	private String city;
	private String category;
	private String checkinNum;
	private String photoNum;
	
	

}
