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

import com.wsm.entity.Report;
import com.wsm.entity.support.Repository;
public class DBClusterer {
	
	private Repository repository;	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void doDBSCAN() throws NullArgumentException, FileNotFoundException, IOException{

		DBSCANClusterer dbscan = new DBSCANClusterer(.05, 50);
	    List<Cluster<DoublePoint>> cluster = dbscan.cluster(getGPS());

	    for(Cluster<DoublePoint> c: cluster){
	        System.out.println(c.getPoints().get(0));
	    }         
	
	}
	
	private  List<DoublePoint> getGPS() throws FileNotFoundException, IOException {

		List<Report> reports=repository.listAllReports();
		
	    List<DoublePoint> points = new ArrayList<DoublePoint>();
	    for (Report report : reports) {
	        
	        
	            try {
	                double[] d = new double[2];
	                if(report.getTemprature()!=null){
	                d[0] = Double.parseDouble(report.getTemprature()+"");
	                }else{
	                	continue;
	                }
	                if(report.getHumidity()!=null){
	                d[1] = Double.parseDouble(report.getHumidity()+"");
	                }else{
	                	continue;
	                }
	                points.add(new DoublePoint(d));
	            } catch (ArrayIndexOutOfBoundsException e) {
	            } catch(NumberFormatException e){
	            }	        
	    }
	    return points;
	}

}
