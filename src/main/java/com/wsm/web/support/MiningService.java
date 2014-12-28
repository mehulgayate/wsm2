package com.wsm.web.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import com.wsm.entity.Cluster;
import com.wsm.entity.Cluster.ClusterType;
import com.wsm.entity.Report;
import com.wsm.entity.Report.WindDirection;
import com.wsm.entity.support.Repository;
import com.wsm.form.MiningFilterForm;
import com.wsm.processor.WSMConfiguration;
import com.wsm.util.DateTimeUtil;
import com.wsm.util.XMLParser;

public class MiningService {

	public static int recordCount=0;
	public static int clusterCount=0;

	private Repository repository;
	private DateTimeUtil dateTimeUtil;
	private WSMConfiguration configuration;
	private XMLParser xmlParser;	

	public void setXmlParser(XMLParser xmlParser) {
		this.xmlParser = xmlParser;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
		this.dateTimeUtil = dateTimeUtil;
	}
	public void setConfiguration(WSMConfiguration configuration) {
		this.configuration = configuration;
	}

	public String mineInClusteredData(MiningFilterForm miningFilterForm) throws ParseException, IOException{
		StringBuilder stringBuilder=new StringBuilder("<weather>");
		List<String> clustersToSearch=new ArrayList<String>();
		int clustercnt=0;

		if(miningFilterForm.getHumidity()!=null){
			if(miningFilterForm.getHumidity().equals("max")){
				clustersToSearch.add(configuration.getMaxHumidityPmfStrings());
			}else if(miningFilterForm.getHumidity().equals("min")){
				clustersToSearch.add(configuration.getMinHumidiityPmfStrings());
			}
		}

		if(miningFilterForm.getRain()!=null){

			if(miningFilterForm.getRain().equals("max")){
				clustersToSearch.add(configuration.getMaxRainPmfStrings());			
			}else if(miningFilterForm.getRain().equals("min")){
				clustersToSearch.add(configuration.getMinRainPmfStrings());
			}
		}


		if(miningFilterForm.getSnow()!=null){
			if(miningFilterForm.getSnow().equals("max")){
				clustersToSearch.add(configuration.getMaxSnowPmfStrings());
			}else if(miningFilterForm.getSnow().equals("min")){
				clustersToSearch.add(configuration.getMinSnowPmfStrings());
			}
		}
		if(miningFilterForm.getTemp()!=null){
			if(miningFilterForm.getTemp().equals("max")){
				clustersToSearch.add(configuration.getMaxTempPmfStrings());
			}else if(miningFilterForm.getTemp().equals("min")){
				clustersToSearch.add(configuration.getMinTempPmfStrings());
			}
		}
		if(miningFilterForm.getWindSpeed()!=null){
			if(miningFilterForm.getWindSpeed().equals("max")){
				clustersToSearch.add(configuration.getMaxWindSpeedPmfStrings());
			}else if(miningFilterForm.getWindSpeed().equals("min")){
				clustersToSearch.add(configuration.getMinWindSpeedPmfStrings());		
			}
		}
		if(miningFilterForm.getWindDir()!=null){
			if(miningFilterForm.getWindDir().equals("e2w")){
				clustersToSearch.add(configuration.getE2WWindDirPmfStrings());
			}else if(miningFilterForm.getWindDir().equals("w2e")){
				clustersToSearch.add(configuration.getW2EWindDirPmfStrings());
			}
		}
		Date startDate=dateTimeUtil.provideDate(miningFilterForm.getStartDate());
		startDate=dateTimeUtil.getMonthsFirstDate(startDate);
		Date endDate=dateTimeUtil.provideDate(miningFilterForm.getEndDate());

		List<Cluster> allClusters=repository.listAllClusters();

		for (Cluster cluster : allClusters) {
			startDate=dateTimeUtil.provideDate(miningFilterForm.getStartDate());
			startDate=dateTimeUtil.getMonthsFirstDate(startDate);
			for (String string : clustersToSearch) {
				if(cluster.getName().indexOf(string)>=0){
					while(startDate.before(endDate)){
						File file=new File(configuration.getClusterBaseLocation()+"/"+cluster.getName()+"/"+dateTimeUtil.provideDateString(startDate)+".xml");
						System.out.println("File in clusters "+file.getAbsolutePath());
						if(file.exists()){
							stringBuilder.append(getFileContent(file));
							clustercnt++;
						}
						startDate=dateTimeUtil.getNextMonthsDate(startDate);
					}
					break;
				}
			}

		}
		stringBuilder.append("</weather>");		
		clusterCount=clustercnt;
		return stringBuilder.toString();
	}

	public String mineFromNonClustredData(MiningFilterForm filterForm){

		try {

			File fXmlFile = new File(configuration.getOriginalBaseLocation()+"/allData.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put(doc.getDocumentElement().getNodeName(), xmlParser.parseXML(doc));
			return searchData(jsonObject,filterForm);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return null;
	}

	public String searchData(JSONObject jsonObject,MiningFilterForm miningFilterForm) throws ParseException{
		StringBuilder stringBuilder=new StringBuilder("<weather>");
		JSONObject reports=jsonObject.getJSONObject("weather");
		Iterator<String> iterator=reports.keys();
		int recCount=0;

		Date startDate=dateTimeUtil.provideDate(miningFilterForm.getStartDate());
		startDate=dateTimeUtil.getMonthsFirstDate(startDate);
		Date endDate=dateTimeUtil.provideDate(miningFilterForm.getEndDate());
		endDate=dateTimeUtil.getMonthsLastDate(endDate);
		endDate=dateTimeUtil.getNextDate(endDate);		

		do{

			String reportKey=iterator.next();
			JSONObject reportObject=reports.getJSONObject(reportKey);
			Date reportDate=dateTimeUtil.provideDate(reportObject.getString("date"));
			if(reportDate.after(startDate) && reportDate.before(endDate)){
				if(miningFilterForm.getHumidity()!=null){
					if(reportObject.has("humidity")){
						if(miningFilterForm.getHumidity().equals("max")){
							if(reportObject.getInt("humidity")>=configuration.getHumidityMaxThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}else if(miningFilterForm.getHumidity().equals("min")){
							if(reportObject.getInt("humidity")<=configuration.getHumidityMinThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}
					}
				}

				if(miningFilterForm.getRain()!=null){
					if(reportObject.has("rain")){
						if(miningFilterForm.getRain().equals("max")){
							if(reportObject.getInt("rain")>=configuration.getRainMaxThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}			
						}else if(miningFilterForm.getRain().equals("min")){
							if(reportObject.getInt("rain")<=configuration.getRainMinThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}
					}
				}
				else if(miningFilterForm.getSnow()!=null){
					if(reportObject.has("snow")){
						if(miningFilterForm.getSnow().equals("max")){
							if(reportObject.getInt("snow")>=configuration.getSnowMaxThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}else if(miningFilterForm.getSnow().equals("min")){
							if(reportObject.getInt("Snow")<=configuration.getSnowMinThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}
					}
				}
				else if(miningFilterForm.getTemp()!=null){
					if(reportObject.has("temperature")){
						if(miningFilterForm.getTemp().equals("max")){
							if(reportObject.getInt("temperature")>=configuration.getTempMaxThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}else if(miningFilterForm.getTemp().equals("min")){
							if(reportObject.getInt("temperature")<=configuration.getTempMinThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}
					}
				}
				else if(miningFilterForm.getWindSpeed()!=null){
					if(reportObject.has("wspeed")){
						if(miningFilterForm.getWindSpeed().equals("max")){
							if(reportObject.getInt("wspeed")>=configuration.getWindSpeedMaxThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}else if(miningFilterForm.getWindSpeed().equals("min")){
							if(reportObject.getInt("wspeed")<=configuration.getWindSpeedMinThreshold()){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}		
						}
					}
				}
				else if(miningFilterForm.getWindDir()!=null){
					if(reportObject.has("wdirection")){
						if(miningFilterForm.getWindDir().equals("e2w")){
							if(reportObject.getString("wdirection").equals("EAST2WEST")){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}else if(miningFilterForm.getWindDir().equals("w2e")){
							if(reportObject.getString("wdirection").equals("WEST2EAST")){
								stringBuilder.append(System.getProperty("line.separator"));
								stringBuilder.append(reportObject.getString("xml"));
								recCount++;
							}
						}
					}
				}
			}
		}while(iterator.hasNext());
		stringBuilder.append("</weather>");
		recordCount=recCount;
		return stringBuilder.toString();
	}

	private String getFileContent(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine;
		StringBuilder stringBuilder=new StringBuilder("");
		while((currentLine = reader.readLine()) != null) {
			String trimmedLine = currentLine.trim();
			if(trimmedLine.indexOf("<weather>")>=0){
				trimmedLine=StringUtils.remove(trimmedLine, "<weather>");
			}
			if(trimmedLine.indexOf("</weather>")>=0){
				trimmedLine=StringUtils.remove(trimmedLine, "</weather>");
			}
			stringBuilder.append(trimmedLine);
		}

		reader.close();
		return stringBuilder.toString();

	}

	public String mineInKMedoidClusteredData(MiningFilterForm miningFilterForm) throws ParseException, IOException{
		StringBuilder stringBuilder=new StringBuilder("<weather>");
		String typeToSearch="";


		if(miningFilterForm.getTemp()!=null){
			if(miningFilterForm.getTemp().equals("max")){
				typeToSearch="max";
			}else if(miningFilterForm.getTemp().equals("min")){
				typeToSearch="min";
			}
		}

		Date startDate=dateTimeUtil.provideDate(miningFilterForm.getStartDate());
		startDate=dateTimeUtil.getMonthsFirstDate(startDate);
		Date endDate=dateTimeUtil.provideDate(miningFilterForm.getEndDate());

		List<Cluster> allClusters=repository.listAllClustersByType(ClusterType.K_MEDOID);

		for (Cluster cluster : allClusters) {
			startDate=dateTimeUtil.provideDate(miningFilterForm.getStartDate());
			startDate=dateTimeUtil.getMonthsFirstDate(startDate);

			System.out.println(typeToSearch.equals("min")  +" "+ cluster.getTempMax()+" "+ configuration.getTempMinThreshold());
			
			
			if(typeToSearch.equals("max") && cluster.getTempMax()>=configuration.getTempMaxThreshold()){
				while(startDate.before(endDate)){
					File file=new File(configuration.getkMedoidClusterBaseLocation()+"/"+cluster.getName()+"/"+dateTimeUtil.provideDateString(startDate)+".xml");
					System.out.println("File in clusters "+file.getAbsolutePath());
					if(file.exists()){
						stringBuilder.append(getFileContent(file));							
					}
					startDate=dateTimeUtil.getNextMonthsDate(startDate);
				}

			}else if( typeToSearch.equals("min") && cluster.getTempMax()<=configuration.getTempMinThreshold() ){
				System.out.println("inisde if &&&&&&&&&&&&^^^^^^^^^^^^^^");
				while(startDate.before(endDate)){
					File file=new File(configuration.getkMedoidClusterBaseLocation()+"/"+cluster.getName()+"/"+dateTimeUtil.provideDateString(startDate)+".xml");
					System.out.println("File in clusters "+file.getAbsolutePath());
					if(file.exists()){
						stringBuilder.append(getFileContent(file));							
					}
					startDate=dateTimeUtil.getNextMonthsDate(startDate);
				}
			}

		}
		stringBuilder.append("</weather>");		
		return stringBuilder.toString();
	}



}
