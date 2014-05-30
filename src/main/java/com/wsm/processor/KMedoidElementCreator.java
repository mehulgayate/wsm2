package com.wsm.processor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.evalua.entity.support.DataStoreManager;
import com.sun.tools.jdi.EventSetImpl.Itr;
import com.wsm.entity.Report;
import com.wsm.entity.Report.WindDirection;
import com.wsm.entity.Setting;
import com.wsm.entity.support.Dataset;
import com.wsm.entity.support.GraphElement;
import com.wsm.entity.support.Repository;
import com.wsm.util.DateTimeUtil;

public class KMedoidElementCreator {
	
	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private DateTimeUtil dateTimeUtil;

	@Resource
	private Repository repository;
	
	@Resource
	private KMedoidProcessor kMedoidProcessor;

	public void setkMedoidProcessor(KMedoidProcessor kMedoidProcessor) {
		this.kMedoidProcessor = kMedoidProcessor;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}

	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}	
	
	public void JsontoReport(JSONObject jsonObject) throws ParseException{

		JSONObject reports=jsonObject.getJSONObject("weather");
		Iterator<String> iterator=reports.keys();
		Dataset dataset=new Dataset();
		int count=0;
		do{
			String reportKey=iterator.next();
			JSONObject reportObject=reports.getJSONObject(reportKey);
			Iterator<String> innIterator=reportObject.keys();
			Report report=new Report();
			
			report.setReportId(new Integer(reportKey));

			String fileLine[]=new String[2];
			fileLine[0]=reportKey;
			
			GraphElement graphElement=new GraphElement(new Integer(reportKey), fileLine);
			
			do{
				String key=innIterator.next();				
				if(key.equals("rain")){					
					report.setRain(reportObject.getDouble(key));
				}else if(key.equals("snow")){
					report.setSnow(reportObject.getDouble(key));
				}else if(key.equals("temperature")){
					report.setTemprature(reportObject.getDouble(key));
					graphElement.setTemp(Float.parseFloat(reportObject.getString(key)));
					graphElement.setId(Integer.parseInt(reportObject.getString(key)));
				}else if(key.equals("humidity")){
					report.setHumidity(reportObject.getDouble(key));
					graphElement.setHumidity(Float.parseFloat(reportObject.getString(key)));
				}else if(key.equals("wdirection")){
					report.setWindDirection(WindDirection.valueOf(reportObject.getString(key)));
				}else if (key.equals("wspeed")) {
					report.setWspeed(reportObject.getDouble(key));
				}else if(key.equals("date")){
					report.setDate(dateTimeUtil.provideDate(reportObject.getString(key)));
					graphElement.setDate(dateTimeUtil.provideDate(reportObject.getString(key)));
				}
				else if(key.equals("xml")){
					report.setXmlString(reportObject.getString(key));
				}		

			}while(innIterator.hasNext());
			count++;			
			dataset.add(graphElement);			

		}while(iterator.hasNext());
		
		System.out.println("************ >>>>>>>>>>> K Medoid staring : ");
		Setting setting=repository.findSettingByName("kMedoidClusterCount");
		if(setting==null){
			System.out.println("************ >>>>>>>>>>> K Medoid cluster count not set ");
		}
		System.out.println("************ >>>>>>>>>>> K Medoid cluster count : "+setting.getValue());
		
		kMedoidProcessor.setNumOfClusters(new Integer(setting.getValue()));
		Dataset.setDistanceMatrix(getDistanceMatrics(dataset,count));		
		
		kMedoidProcessor.doClustering(dataset);
		
		System.out.println(" $$$$$$   Centers $$$$");
		int centerCount=1;
		for (GraphElement graphElement : kMedoidProcessor.centers) {
			System.out.println(" $$$$$$   Centers "+centerCount+" :---- TempRature : "+graphElement.getTemp()+" ------ Humidity : "+graphElement.getHumidity());
			centerCount++;
		}
		
		for (GraphElement graphElement : dataset) {
			System.out.println("ID : "+graphElement.getLabel()+" TEMP : "+graphElement.getTemp()+" Humidity: "+graphElement.getHumidity()+" ** Cluster allocated : "+ graphElement.getCalculatedClusternumber());
		}
	}
	
	
	private float[][] getDistanceMatrics(Dataset dataset,int count) throws ParseException{
		
		System.out.println("**** creating distance matrix   ***");
		float[][] distanceMatrix=new float[dataset.size()][dataset.size()];
		int outerCount=0;
		int innerCount=0;
		for (GraphElement graphElement : dataset) {
			innerCount=0;
			for (GraphElement innerGraphElement : dataset) {
				distanceMatrix[outerCount][innerCount]=graphElement.calculateDistance(innerGraphElement);				
				innerCount++;
			}
			outerCount++;
		}
		System.out.println("**** finished creating distance matrix   ***");
		return distanceMatrix;
		
	}

}
