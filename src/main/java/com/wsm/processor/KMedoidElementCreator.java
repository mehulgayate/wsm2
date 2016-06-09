package com.wsm.processor;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Cluster;
import com.wsm.entity.Report;
import com.wsm.entity.Cluster.ClusterType;
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
	
	private ClusterCreator clusterCreator;
	

	public void setClusterCreator(ClusterCreator clusterCreator) {
		this.clusterCreator = clusterCreator;
	}
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
	
	public void JsontoReport(JSONObject jsonObject) throws ParseException, IOException{

		JSONObject reports=jsonObject.getJSONObject("weather");
		Iterator<String> iterator=reports.keys();
		Dataset dataset=new Dataset();
		int count=0;
		do{
			String reportKey=iterator.next();
			JSONObject reportObject=reports.getJSONObject(reportKey);
			Iterator<String> innIterator=reportObject.keys();
			
			
			
			

			String fileLine[]=new String[2];
			fileLine[0]=reportKey;
			
			GraphElement graphElement=new GraphElement(count, fileLine);
			graphElement.setId(count);
			
			do{
				String key=innIterator.next();				
				if(key.equals("rain")){					
			
				}else if(key.equals("snow")){
			
				}else if(key.equals("temperature")){
			
					graphElement.setTemp(Float.parseFloat(reportObject.getString(key)));
					
				}else if(key.equals("humidity")){
			
					graphElement.setHumidity(Float.parseFloat(reportObject.getString(key)));
				}else if(key.equals("wdirection")){
			
				}else if (key.equals("wspeed")) {
			
				}else if(key.equals("date")){
			
					graphElement.setDate(dateTimeUtil.provideDate(reportObject.getString(key)));
				}
				else if(key.equals("xml")){
			
					graphElement.setXml(reportObject.getString(key));
				}		

			}while(innIterator.hasNext());
			count++;			
			dataset.add(graphElement);			

		}while(iterator.hasNext());
		
		System.out.println("************ >>>>>>>>>>> CURE staring : ");
		Setting setting=repository.findSettingByName("kMedoidClusterCount");
		if(setting==null){
			System.out.println("************ >>>>>>>>>>> CURE cluster count not set ");
		}
		System.out.println("************ >>>>>>>>>>> CURE cluster count : "+setting.getValue());
		
		kMedoidProcessor.setNumOfClusters(new Integer(setting.getValue()));
		Dataset.setDistanceMatrix(getDistanceMatrics(dataset,count));		
		
		kMedoidProcessor.doClustering(dataset);
		
		System.out.println(" $$$$$$   Centers $$$$");
		int centerCount=0;
		List<Cluster> clusterStrings=repository.listAllClustersByType(ClusterType.K_MEDOID);
		
		for (Cluster cluster : clusterStrings) {
			dataStoreManager.delete(cluster);
		}
		
		for (GraphElement graphElement : kMedoidProcessor.centers) {
			System.out.println(" $$$$$$   Centers "+centerCount+" :---- TempRature : "+graphElement.getTemp()+" ------ Humidity : "+graphElement.getHumidity());
			
			Cluster cluster=new Cluster();
			cluster.setName(centerCount+"");
			cluster.setTempMax(new Float(graphElement.getTemp()).intValue());
			cluster.setType(ClusterType.K_MEDOID);
			dataStoreManager.save(cluster);
			centerCount++;
		}
		
		clusterCreator.crateKMedoidClusters();
		clusterCreator.allocateKMedoidCluster(dataset);
		
		for (GraphElement graphElement : dataset) {
			System.out.println("ID : "+graphElement.getLabel()+" TEMP : "+graphElement.getTemp()+" Humidity: "+graphElement.getHumidity()+" ** Cluster allocated : "+ graphElement.getCalculatedClusternumber());
		}
	}
	
	
	private Map<String, Float> getDistanceMatrics(Dataset dataset,int count) throws ParseException{
		
		System.out.println("**** processing u.mean and u.rep ***");
		Map<String, Float> distanceMatrix=new HashMap<String, Float>();
		int outerCount=0;
		int innerCount=0;
		for (GraphElement graphElement : dataset) {
			innerCount=0;
			for (GraphElement innerGraphElement : dataset) {
				distanceMatrix.put(" "+outerCount+","+innerCount, graphElement.calculateDistance(innerGraphElement));
				//distanceMatrix[outerCount][innerCount]=graphElement.calculateDistance(innerGraphElement);				
				innerCount++;
			}
			outerCount++;
		}
		System.out.println("**** finished processing u.mean and u.rep   ***");
		return distanceMatrix;
		
	}

}
