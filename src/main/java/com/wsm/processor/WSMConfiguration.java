package com.wsm.processor;

public class WSMConfiguration {

	private Integer rainMinThreshold;
	private Integer rainMaxThreshold;
	private Integer snowMinThreshold;
	private Integer snowMaxThreshold;
	private Integer tempMinThreshold;
	private Integer tempMaxThreshold;
	private Integer humidityMinThreshold;
	private Integer humidityMaxThreshold;
	private Integer windSpeedMinThreshold;
	private Integer windSpeedMaxThreshold;



	private String maxRainPmfStrings;
	private String minRainPmfStrings;
	private String maxSnowPmfStrings;
	private String minSnowPmfStrings;
	private String maxTempPmfStrings;
	private String minTempPmfStrings;
	private String maxHumidityPmfStrings;
	private String minHumidiityPmfStrings;
	private String maxWindSpeedPmfStrings;
	private String minWindSpeedPmfStrings;
	private String E2WWindDirPmfStrings;
	private String W2EWindDirPmfStrings;

	private String clusterBaseLocation;
	private String originalBaseLocation;
	private String kMedoidClusterBaseLocation;
	
	private boolean withoutBoostingEnable=false;
	
	
	
	public boolean isWithoutBoostingEnable() {
		return withoutBoostingEnable;
	}
	public void setWithoutBoostingEnable(boolean withoutBoostingEnable) {
		this.withoutBoostingEnable = withoutBoostingEnable;
	}
	public String getkMedoidClusterBaseLocation() {
		return kMedoidClusterBaseLocation;
	}
	public void setkMedoidClusterBaseLocation(String kMedoidClusterBaseLocation) {
		this.kMedoidClusterBaseLocation = kMedoidClusterBaseLocation;
	}
	public String getOriginalBaseLocation() {
		return originalBaseLocation;
	}
	public void setOriginalBaseLocation(String originalBaseLocation) {
		this.originalBaseLocation = originalBaseLocation;
	}
	public String getClusterBaseLocation() {
		return clusterBaseLocation;
	}
	public void setClusterBaseLocation(String clusterBaseLocation) {
		this.clusterBaseLocation = clusterBaseLocation;
	}
	public String getE2WWindDirPmfStrings() {
		return E2WWindDirPmfStrings;
	}
	public void setE2WWindDirPmfStrings(String e2wWindDirPmfStrings) {
		E2WWindDirPmfStrings = e2wWindDirPmfStrings;
	}
	public String getW2EWindDirPmfStrings() {
		return W2EWindDirPmfStrings;
	}
	public void setW2EWindDirPmfStrings(String w2eWindDirPmfStrings) {
		W2EWindDirPmfStrings = w2eWindDirPmfStrings;
	}
	public Integer getTempMinThreshold() {
		return tempMinThreshold;
	}
	public void setTempMinThreshold(Integer tempMinThreshold) {
		this.tempMinThreshold = tempMinThreshold;
	}
	public Integer getTempMaxThreshold() {
		return tempMaxThreshold;
	}
	public void setTempMaxThreshold(Integer tempMaxThreshold) {
		this.tempMaxThreshold = tempMaxThreshold;
	}
	public Integer getHumidityMinThreshold() {
		return humidityMinThreshold;
	}
	public void setHumidityMinThreshold(Integer humidityMinThreshold) {
		this.humidityMinThreshold = humidityMinThreshold;
	}
	public Integer getHumidityMaxThreshold() {
		return humidityMaxThreshold;
	}
	public void setHumidityMaxThreshold(Integer humidityMaxThreshold) {
		this.humidityMaxThreshold = humidityMaxThreshold;
	}
	public Integer getWindSpeedMinThreshold() {
		return windSpeedMinThreshold;
	}
	public void setWindSpeedMinThreshold(Integer windSpeedMinThreshold) {
		this.windSpeedMinThreshold = windSpeedMinThreshold;
	}
	public Integer getWindSpeedMaxThreshold() {
		return windSpeedMaxThreshold;
	}
	public void setWindSpeedMaxThreshold(Integer windSpeedMaxThreshold) {
		this.windSpeedMaxThreshold = windSpeedMaxThreshold;
	}
	public String getMaxTempPmfStrings() {
		return maxTempPmfStrings;
	}
	public void setMaxTempPmfStrings(String maxTempPmfStrings) {
		this.maxTempPmfStrings = maxTempPmfStrings;
	}
	public String getMinTempPmfStrings() {
		return minTempPmfStrings;
	}
	public void setMinTempPmfStrings(String minTempPmfStrings) {
		this.minTempPmfStrings = minTempPmfStrings;
	}
	public String getMaxHumidityPmfStrings() {
		return maxHumidityPmfStrings;
	}
	public void setMaxHumidityPmfStrings(String maxHumidityPmfStrings) {
		this.maxHumidityPmfStrings = maxHumidityPmfStrings;
	}
	public String getMinHumidiityPmfStrings() {
		return minHumidiityPmfStrings;
	}
	public void setMinHumidiityPmfStrings(String minHumidiityPmfStrings) {
		this.minHumidiityPmfStrings = minHumidiityPmfStrings;
	}
	public String getMaxWindSpeedPmfStrings() {
		return maxWindSpeedPmfStrings;
	}
	public void setMaxWindSpeedPmfStrings(String maxWindSpeedPmfStrings) {
		this.maxWindSpeedPmfStrings = maxWindSpeedPmfStrings;
	}
	public String getMinWindSpeedPmfStrings() {
		return minWindSpeedPmfStrings;
	}
	public void setMinWindSpeedPmfStrings(String minWindSpeedPmfStrings) {
		this.minWindSpeedPmfStrings = minWindSpeedPmfStrings;
	}
	public String getMaxRainPmfStrings() {
		return maxRainPmfStrings;
	}
	public void setMaxRainPmfStrings(String maxRainPmfStrings) {
		this.maxRainPmfStrings = maxRainPmfStrings;
	}
	public String getMinRainPmfStrings() {
		return minRainPmfStrings;
	}
	public void setMinRainPmfStrings(String minRainPmfStrings) {
		this.minRainPmfStrings = minRainPmfStrings;
	}
	public String getMaxSnowPmfStrings() {
		return maxSnowPmfStrings;
	}
	public void setMaxSnowPmfStrings(String maxSnowPmfStrings) {
		this.maxSnowPmfStrings = maxSnowPmfStrings;
	}
	public String getMinSnowPmfStrings() {
		return minSnowPmfStrings;
	}
	public void setMinSnowPmfStrings(String minSnowPmfStrings) {
		this.minSnowPmfStrings = minSnowPmfStrings;
	}
	public Integer getRainMinThreshold() {
		return rainMinThreshold;
	}
	public void setRainMinThreshold(Integer rainMinThreshold) {
		this.rainMinThreshold = rainMinThreshold;
	}
	public Integer getRainMaxThreshold() {
		return rainMaxThreshold;
	}
	public void setRainMaxThreshold(Integer rainMaxThreshold) {
		this.rainMaxThreshold = rainMaxThreshold;
	}
	public Integer getSnowMinThreshold() {
		return snowMinThreshold;
	}
	public void setSnowMinThreshold(Integer snowMinThreshold) {
		this.snowMinThreshold = snowMinThreshold;
	}
	public Integer getSnowMaxThreshold() {
		return snowMaxThreshold;
	}
	public void setSnowMaxThreshold(Integer snowMaxThreshold) {
		this.snowMaxThreshold = snowMaxThreshold;
	}



}
