package com.wsm.processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.Cluster;
import com.wsm.entity.Report;
import com.wsm.entity.support.Repository;
import com.wsm.util.DateTimeUtil;

public class ClusterCreator {


	private Repository repository;

	private WSMConfiguration configuration;

	@Resource
	private DateTimeUtil dateTimeUtil;
	
	@Resource 
	private DataStoreManager dataStoreManager;	

	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}

	public void crateClusters(){
		List<String> clusterStrings=repository.getClusterStrings();

		File baseFolder= new File(configuration.getClusterBaseLocation()+"/conf.wsm");
		File baseAllDataFolder= new File(configuration.getOriginalBaseLocation()+"/conf.wsm");
		baseAllDataFolder.mkdirs();
		baseFolder.mkdirs();
		try {
			baseFolder.createNewFile();
			for (String string : clusterStrings) {
				new File(configuration.getClusterBaseLocation()+"/"+string).mkdir();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void allocateCluster() throws ParseException, IOException{
		List<Report> reports=repository.listAllReports();

		for (Report report : reports) {
			Cluster cluster=repository.findClusterByName(report.getKlStringValue());

			if(cluster==null){
				cluster =new Cluster();
				cluster.setName(report.getKlStringValue());
				dataStoreManager.save(cluster);				
			}
			String fileName=dateTimeUtil.provideDateString(report.getDate());
			File file=new File(configuration.getClusterBaseLocation()+"/"+report.getKlStringValue()+"/"+fileName+".xml");
			File allDatafile=new File(configuration.getOriginalBaseLocation()+"/allData.xml");

			if(!file.exists()){

				file.createNewFile();
				FileWriter fileWritter = new FileWriter(file,true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.append("<weather>");		
				bufferWritter.append(System.getProperty("line.separator"));
				bufferWritter.close();
			}
			if(!allDatafile.exists()){

				allDatafile.createNewFile();
				FileWriter fileWritter = new FileWriter(allDatafile,true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.append("<weather>");
				bufferWritter.append(System.getProperty("line.separator"));
				bufferWritter.close();
			}
			
			File tempFile = new File(configuration.getClusterBaseLocation()+"/"+report.getKlStringValue()+"/temp.xml");

			BufferedReader reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String lineToRemove = "</weather>";
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim append() when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine);
			}

			reader.close();
			writer.close();		
			file.delete();
			tempFile.renameTo(file);		
						
			//Add data to All data File as well
			tempFile = new File(configuration.getOriginalBaseLocation()+"/temp.xml");
			reader = new BufferedReader(new FileReader(allDatafile));
			writer = new BufferedWriter(new FileWriter(tempFile));
			
			lineToRemove = "</weather>";		

			while((currentLine = reader.readLine()) != null) {
			    // trim append() when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(trimmedLine.equals(lineToRemove)) continue;
			    writer.write(currentLine);
			}

			reader.close();
			writer.close();	
			allDatafile.delete();
			tempFile.renameTo(allDatafile);

			
			FileWriter fileWritter = new FileWriter(file,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.append(System.getProperty("line.separator"));
			bufferWritter.append(report.getXmlString());
			bufferWritter.append(System.getProperty("line.separator"));
			bufferWritter.append("</weather>");
			
			bufferWritter.close();
			fileWritter.close();
			
			fileWritter = new FileWriter(allDatafile,true);
			bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.append(System.getProperty("line.separator"));
			bufferWritter.append(report.getXmlString());
			bufferWritter.append(System.getProperty("line.separator"));
			bufferWritter.append("</weather>");
			
			bufferWritter.close();
			fileWritter.close();
		}
		System.out.println("Data Clustring Completed !!!");
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}

	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}
	



}
