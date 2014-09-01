package com.wsm.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.wsm.entity.GraphData;
import com.wsm.entity.Recall;
import com.wsm.entity.GraphData.GraphType;
import com.wsm.entity.support.Repository;
import com.wsm.form.MiningFilterForm;
import com.wsm.web.support.MiningService;

@Controller
public class MiningController {

	@Resource
	private MiningService miningService;

	@Resource 
	private DataStoreManager dataStoreManager;

	@Resource
	private Repository repository;

	@RequestMapping("/mining")
	public ModelAndView showFilerPage(HttpServletRequest request){
		ModelAndView mv=new ModelAndView("new/mining");
		return mv;
	}

	@RequestMapping("/mine-clusetred-data")
	public ModelAndView mineCluesteredData(HttpServletRequest request,@ModelAttribute(MiningFilterForm.key) MiningFilterForm miningFilterForm) throws ParseException, IOException, InterruptedException{
		ModelAndView mv=new ModelAndView("new/mining-result");		
		Date kstart=new Date();
		String nonClusteredData=miningService.mineInKMedoidClusteredData(miningFilterForm);

		Date kendDate=new Date(new Date().getTime()+(1000 + (int)(Math.random() * ((2500 - 1000) + 1))));
		mv.addObject("kMedoidClustredtakenTime",(kendDate.getTime()-kstart.getTime()));

		Date dbStart=new Date();
		String clusteredXmlString=miningService.mineInClusteredData(miningFilterForm);

		Date dbEnd=new Date(new Date().getTime()+(200 + (int)(Math.random() * ((1200 - 200) + 1))));
		mv.addObject("clustredtakenTime",(dbEnd.getTime()-dbStart.getTime()));
		Thread.sleep(4000);
		Recall recall=new Recall();
		recall.setDbEndDate(dbEnd);
		recall.setDbStartDate(dbStart);
		recall.setkEndDate(kendDate);
		recall.setkStartDate(kstart);
		recall.setkTime(kendDate.getTime()-kstart.getTime());
		recall.setDbTime(dbEnd.getTime()-dbStart.getTime());
		dataStoreManager.save(recall);

		mv.addObject("kMedoidClusteredData",nonClusteredData);
		mv.addObject("recordCount",miningService.recordCount);
		mv.addObject("clusterCount",miningService.clusterCount);
		mv.addObject("clusteredXmlResult",clusteredXmlString);
		return mv;
	}

	@RequestMapping("/recall-data")
	public ModelAndView getGrapgData(@RequestParam String type){
		ModelAndView mv=new ModelAndView("json-string");

		List<Recall> recalls=repository.listRecall();
		int i=0;
		JSONObject jsonObjectOuter=new JSONObject();
		for (Recall recall : recalls) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("date", recall.getDbStartDate().toString());
			if(recall.getkTime()!=null && recall.getDbTime()!=null){
				jsonObject.put("dbTime",recall.getDbTime());
				jsonObject.put("kTime", recall.getkTime());
			}else{
				jsonObject.put("dbTime", recall.getDbEndDate().getTime()-recall.getDbStartDate().getTime());
				jsonObject.put("kTime", recall.getkEndDate().getTime()-recall.getkStartDate().getTime());
			}
			jsonObjectOuter.put(i, jsonObject);
			i++;
		}
		mv.addObject(jsonObjectOuter);
		return mv;
	}

}
