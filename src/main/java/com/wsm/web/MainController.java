package com.wsm.web;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.GraphData;
import com.wsm.entity.GraphData.GraphType;
import com.wsm.entity.Setting;
import com.wsm.entity.support.Repository;

@Controller
public class MainController {

	@Resource
	private Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;

	@RequestMapping("/")
	public ModelAndView startup(){
		ModelAndView mv=new ModelAndView("new/index");
		return mv;
	}
	@RequestMapping("/upload-new")
	public ModelAndView uploadNew(){
		ModelAndView mv=new ModelAndView("new/upload");
		return mv;
	}
	@RequestMapping("/graphs")
	public ModelAndView showGraphs(){
		ModelAndView mv=new ModelAndView("new/graph");
		return mv;
	}
	@RequestMapping("/tables")
	public ModelAndView showtables(){
		ModelAndView mv=new ModelAndView("new/table");
		
		mv.addObject("TROPICAL", repository.findCountGraphData(GraphType.TROPICAL));
		mv.addObject("TEMPERATE", repository.findCountGraphData(GraphType.TEMPERATE));
		mv.addObject("POLAR", repository.findCountGraphData(GraphType.POLAR));
		mv.addObject("DRY", repository.findCountGraphData(GraphType.DRY));
		mv.addObject("CONTINENTAL", repository.findCountGraphData(GraphType.CONTINENTAL));
		return mv;
	}

	@RequestMapping("/graph-data")
	public ModelAndView getGrapgData(@RequestParam String type){
		ModelAndView mv=new ModelAndView("json-string");
		
		List<GraphData> graphDatas=repository.listGraphData(GraphType.valueOf(type));
		int i=0;
		JSONObject jsonObjectOuter=new JSONObject();
		for (GraphData graphData : graphDatas) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("temperature", graphData.getTemperature());
			jsonObject.put("himidity", graphData.getHumidity());
			jsonObjectOuter.put(i, jsonObject);
			i++;
		}
		mv.addObject(jsonObjectOuter);
		return mv;
	}	
	@RequestMapping("/settings")
	public ModelAndView showSettings(){
		ModelAndView mv=new ModelAndView("new/setting");
		Setting setting=repository.findSettingByName("kMedoidClusterCount");
		
		if(setting!=null){
			mv.addObject("kMedoidClusterCount", setting.getValue());
		}
		
		Setting tempMinSetting=repository.findSettingByName("tempMinThreshold");
		Setting tempMaxSetting=repository.findSettingByName("tempMaxThreshold");
		Setting humidityMinSetting=repository.findSettingByName("humidityMinThreshold");
		Setting humidityMaxSetting=repository.findSettingByName("humidityMaxThreshold");
		if(tempMinSetting!=null){
		mv.addObject("tempMinThreshold",tempMinSetting.getValue());
		}
		if(tempMaxSetting!=null){
		mv.addObject("tempMaxThreshold",tempMaxSetting.getValue());
		}
		
		if(humidityMinSetting!=null){
		mv.addObject("humidityMinThreshold",humidityMinSetting.getValue());
		}
		if(humidityMaxSetting!=null){
		mv.addObject("humidityMaxThreshold",humidityMaxSetting.getValue());
		}
		return mv;
	}
	
	@RequestMapping("/add-setting")
	public ModelAndView addSettings(@RequestParam String kMedoidClusterCount,
			@RequestParam String tempMinThreshold,
			@RequestParam String tempMaxThreshold,
			@RequestParam String humidityMinThreshold,
			@RequestParam String humidityMaxThreshold,
			@RequestParam String withoutBoostingEnable){
		ModelAndView mv=new ModelAndView("redirect:/settings");
		Setting setting=repository.findSettingByName("kMedoidClusterCount");
		if(setting==null){
			setting=new Setting();
			setting.setName("kMedoidClusterCount");			
		}
		setting.setValue(kMedoidClusterCount);
		
		Setting tempMinSetting=repository.findSettingByName("tempMinThreshold");
		Setting tempMaxSetting=repository.findSettingByName("tempMaxThreshold");
		Setting humidityMinSetting=repository.findSettingByName("humidityMinThreshold");
		Setting humidityMaxSetting=repository.findSettingByName("humidityMaxThreshold");
		Setting withoutBoostingEnableSetting=repository.findSettingByName("withoutBoostingEnable");
		
		if(withoutBoostingEnableSetting==null){
			withoutBoostingEnableSetting=new Setting();
			withoutBoostingEnableSetting.setName("withoutBoostingEnable");
		}
		withoutBoostingEnableSetting.setValue(withoutBoostingEnable);
		
		if(tempMinSetting==null){
			tempMinSetting=new Setting();
			tempMinSetting.setName("tempMinThreshold");
		}
		tempMinSetting.setValue(tempMinThreshold);
		
		if(tempMaxSetting==null){
			tempMaxSetting=new Setting();
			tempMaxSetting.setName("tempMaxThreshold");			
		}
		tempMaxSetting.setValue(tempMaxThreshold);
		
		if(humidityMinSetting==null){
			humidityMinSetting=new Setting();
			humidityMinSetting.setName("humidityMinThreshold");
		}
		humidityMinSetting.setValue(humidityMinThreshold);
		
		
		if(humidityMaxSetting==null){
			humidityMaxSetting=new Setting();
			humidityMaxSetting.setName("humidityMaxThreshold");
		}
		humidityMaxSetting.setValue(humidityMaxThreshold);
		
		dataStoreManager.save(humidityMaxSetting);
		dataStoreManager.save(humidityMinSetting);
		dataStoreManager.save(tempMaxSetting);
		dataStoreManager.save(tempMinSetting);		
		dataStoreManager.save(setting);
		dataStoreManager.save(withoutBoostingEnableSetting);
		return mv;
	}
}
