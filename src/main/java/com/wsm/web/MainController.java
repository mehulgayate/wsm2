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
}
