package com.wsm.processor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Report;
import com.wsm.entity.Setting;
import com.wsm.entity.support.Repository;
public class DBClusterer {
	
	private Repository repository;	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	private DataStoreManager dataStoreManager;
	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}

	public void doDBSCAN() throws NullArgumentException, FileNotFoundException, IOException{

		System.out.println("Doing DBSCAN  ****");
		Setting eps=repository.findSettingByName("eps");
		Setting minPts=repository.findSettingByName("minPts");
		List<Report> reports=repository.listAllReports();
		DBSCANClusterer dbscan = new DBSCANClusterer(new Integer(eps.getValue()), new Integer(minPts.getValue()));
	    List<Cluster<DoublePoint>> cluster = dbscan.cluster(getGPS(reports));

	    System.out.println("Cluster size "+cluster.size());
	    int count=0;
	    for(Cluster<DoublePoint> c: cluster){
	    	System.out.println("&&&& cluster "+count);
	    	List<DoublePoint> pints=c.getPoints();
	    	for (DoublePoint doublePoint : pints) {
	    		System.out.println("&&&&&&&&&&&&&&&&& points in cluster("+doublePoint.getPoint()[0]+","+doublePoint.getPoint()[1]+")");
	    		Report report=repository.findReportById(new Double(doublePoint.getPoint()[0]).longValue());
	    		report.setKlStringValue(count+"");
	    		dataStoreManager.save(report);
			}
	        count++;
	    }         
	
	}
	
	private  List<DoublePoint> getGPS(List<Report> reports) throws FileNotFoundException, IOException {

		
		System.out.println("TOtal reports "+reports.size());
	    List<DoublePoint> points = new ArrayList<DoublePoint>();
	    int counter=0;
	    for (Report report : reports) {
	        
	        
	            try {
	                double[] d = new double[2];
	                if(report.getTemprature()!=null){
	                d[0] = Double.parseDouble(report.getId()+"");
	                }else{
	                	continue;
	                }
	                if(report.getHumidity()!=null){
	                d[1] = Double.parseDouble(report.getTemprature()+"");
	                }else{
	                	continue;
	                }
	                points.add(new DoublePoint(d));
	                counter++;
	            } catch (ArrayIndexOutOfBoundsException e) {
	            } catch(NumberFormatException e){
	            }	        
	    }
	    System.out.println("GOT GPS "+points.size());
	    return points;
	}

}
