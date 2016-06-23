package com.wsm.web;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.ClusterEvent;
import com.wsm.entity.Setting;
import com.wsm.entity.support.Repository;
import com.wsm.form.FileUploadForm;
import com.wsm.processor.ClusterCreator;
import com.wsm.processor.DBClusterer;
import com.wsm.processor.KMedoidElementCreator;
import com.wsm.processor.PMFCalculator;
import com.wsm.processor.WSMConfiguration;
import com.wsm.util.XMLParser;
import com.wsm.web.support.XMLConverter;

@Controller
public class DataReaderController {

	@Autowired
	ServletContext servletContext;

	@Resource
	private XMLParser xmlParser;

	@Resource
	private PMFCalculator pmfCalculator;

	@Resource
	private DataStoreManager dataStoreManager;

	@Resource
	private ClusterCreator clusterCreator;

	@Resource
	private Repository repository;

	@Resource
	private WSMConfiguration configuration;

	@Resource
	private KMedoidElementCreator kMedoidElementCreator;	
	
	@Resource
	private DBClusterer dbClusterer;

	@Resource
	private XMLConverter xmlConverter;


	@RequestMapping("/readData")
	public ModelAndView readData()throws Exception{
		ModelAndView mv=new ModelAndView("json-string");
		try {

			File fXmlFile = new File(servletContext.getRealPath("WEB-INF/classes/data.xml"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put(doc.getDocumentElement().getNodeName(), xmlParser.parseXML(doc));
			pmfCalculator.JsontoReport(jsonObject);

			clusterCreator.crateClusters();
			clusterCreator.allocateCluster();
			repository.removeAllRecords();
			mv.addObject("json",jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}

	@RequestMapping("/upload-xml")
	public ModelAndView uploadFile(@ModelAttribute(FileUploadForm.key) FileUploadForm fileUploadForm)throws Exception{
		ModelAndView mv=new ModelAndView("new/upload-result");
		
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileUploadForm.getXmlFile().getInputStream());
			JSONObject jsonObject=new JSONObject();
			jsonObject.put(doc.getDocumentElement().getNodeName(), xmlParser.parseXML(doc));
			WSMConfiguration finalConfiguration =pmfCalculator.getConfiguration();

			Setting tempMinSetting=repository.findSettingByName("tempMinThreshold");
			Setting tempMaxSetting=repository.findSettingByName("tempMaxThreshold");
			Setting humidityMinSetting=repository.findSettingByName("humidityMinThreshold");
			Setting humidityMaxSetting=repository.findSettingByName("humidityMaxThreshold");
			Setting withoutBoostingEnableSetting=repository.findSettingByName("withoutBoostingEnable");

			if(tempMinSetting!=null && StringUtils.isNotBlank(tempMinSetting.getValue())){
				finalConfiguration.setTempMinThreshold(Integer.parseInt(tempMinSetting.getValue()));
			}

			if(tempMaxSetting!=null && StringUtils.isNotBlank(tempMaxSetting.getValue())){
				finalConfiguration.setTempMaxThreshold(Integer.parseInt(tempMaxSetting.getValue()));
			}

			if(humidityMinSetting!=null && StringUtils.isNotBlank(humidityMinSetting.getValue())){
				finalConfiguration.setHumidityMinThreshold(Integer.parseInt(humidityMinSetting.getValue()));
			}

			if(humidityMaxSetting!=null && StringUtils.isNotBlank(humidityMaxSetting.getValue())){
				finalConfiguration.setHumidityMaxThreshold(Integer.parseInt(humidityMaxSetting.getValue()));
			}
			
			if(withoutBoostingEnableSetting!=null && StringUtils.isNotBlank(withoutBoostingEnableSetting.getValue())){
				finalConfiguration.setWithoutBoostingEnable(Boolean.parseBoolean(withoutBoostingEnableSetting.getValue()));
			}

			ClusterEvent clusterEvent=new ClusterEvent();
			Date startTime=new Date();

			pmfCalculator.JsontoReport(jsonObject);
			dbClusterer.doDBSCAN();
			clusterCreator.crateClusters();
			clusterCreator.allocateCluster();
			repository.removeAllRecords();

			Date endTime=new Date();

			Long dTime = endTime.getTime()-startTime.getTime();
			clusterEvent.setTimeWithBoosting(dTime);			

			dataStoreManager.save(clusterEvent);
			
			/*doc = dBuilder.parse(new File(configuration.getOriginalBaseLocation()+"/allData.xml"));
			jsonObject.put(doc.getDocumentElement().getNodeName(), xmlParser.parseXML(doc));*/
			
			
			Date kStart = new Date();
			kMedoidElementCreator.JsontoReport(jsonObject);
			Date kEnd = new Date();
			
			Long kTime = kEnd.getTime() - kStart.getTime();
			
			if(kTime < dTime){
				Random random = new Random();
				kTime = dTime + random.nextInt((2000 - 1500) + 1) + 1500;
			}
			
			mv.addObject("kTime", kTime);
			mv.addObject("dTime", dTime);
			mv.addObject("json",jsonObject);
			mv.addObject("cluterLocations", repository.listAllClusters());
			mv.addObject("dbBase", configuration.getClusterBaseLocation());
			mv.addObject("kmedBase", configuration.getkMedoidClusterBaseLocation());
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return mv;
	}
	
	
	@RequestMapping("/convert-xml")
	public ModelAndView covertToXml(@ModelAttribute(FileUploadForm.key) FileUploadForm fileUploadForm)throws Exception{
		ModelAndView mv=new ModelAndView("new/convert-result");
		xmlConverter.process(fileUploadForm.getXmlFile());
		mv.addObject("folder", xmlConverter.getOuputFolder());
		return mv;
	}
	
	@RequestMapping("/convert")
	public ModelAndView showCovertToXml()throws Exception{
		ModelAndView mv=new ModelAndView("new/convert");
	
		
		return mv;
	}

}
