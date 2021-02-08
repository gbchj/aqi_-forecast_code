package com.data.dao;

public class Weather {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String station_id;
	private String time;
	private String PM25_Concentration;
	private String PM10_Concentration;
	private String NO2_Concentration;
	private String CO_Concentration;
	private String O3_Concentration;
	private String SO2_Concentration;
	private String AQI;
	
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
	public String getPM25_Concentration() {
		return PM25_Concentration;
	}
	public void setPM25_Concentration(String pM25_Concentration) {
		PM25_Concentration = pM25_Concentration;
	}
	public String getPM10_Concentration() {
		return PM10_Concentration;
	}
	public void setPM10_Concentration(String pM10_Concentration) {
		PM10_Concentration = pM10_Concentration;
	}
	public String getNO2_Concentration() {
		return NO2_Concentration;
	}
	public void setNO2_Concentration(String nO2_Concentration) {
		NO2_Concentration = nO2_Concentration;
	}
	public String getCO_Concentration() {
		return CO_Concentration;
	}
	public void setCO_Concentration(String cO_Concentration) {
		CO_Concentration = cO_Concentration;
	}
	public String getO3_Concentration() {
		return O3_Concentration;
	}
	public void setO3_Concentration(String o3_Concentration) {
		O3_Concentration = o3_Concentration;
	}
	public String getSO2_Concentration() {
		return SO2_Concentration;
	}
	public void setSO2_Concentration(String sO2_Concentration) {
		SO2_Concentration = sO2_Concentration;
	}
	public String getAQI() {
		return AQI;
	}
	public void setAQI(String aQI) {
		AQI = aQI;
	}
	public Weather() {
		super();
	}
	
	public Weather(String station_id, String time, String pM25_Concentration,
			String pM10_Concentration, String nO2_Concentration,
			String cO_Concentration, String o3_Concentration,
			String sO2_Concentration, String aQI) {
		super();
		this.station_id = station_id;
		this.time = time;
		PM25_Concentration = pM25_Concentration;
		PM10_Concentration = pM10_Concentration;
		NO2_Concentration = nO2_Concentration;
		CO_Concentration = cO_Concentration;
		O3_Concentration = o3_Concentration;
		SO2_Concentration = sO2_Concentration;
		AQI = aQI;
	}
	@Override
	public String toString() {
		return "Weather [station_id=" + station_id + ", time=" + time
				+ ", PM25_Concentration=" + PM25_Concentration
				+ ", PM10_Concentration=" + PM10_Concentration
				+ ", NO2_Concentration=" + NO2_Concentration
				+ ", CO_Concentration=" + CO_Concentration
				+ ", O3_Concentration=" + O3_Concentration
				+ ", SO2_Concentration=" + SO2_Concentration + ", AQI=" + AQI
				+ "]";
	}
	
}
