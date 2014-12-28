package com.wsm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class XMLGenerator {

	public static void main(String[] args) throws IOException {

		File file=new File("/wsmData.xml");

		if(!file.exists()){

			file.createNewFile();
			FileWriter fileWritter = new FileWriter(file,true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.append("<weather>");
			bufferWritter.newLine();
			for(int i=0;i<200;i++){
				bufferWritter.append("<report id=\"" +(i++) +"\">"+
						"<date>2014-01-09</date>"+
						"<temperature>"+(-20 + (int)(Math.random() * ((50 - (-20)) + 1)))+"</temperature>"+
						"<humidity>"+(1 + (int)(Math.random() * ((100 - 1) + 1)))+"</humidity>"+
						"<wdirection>WEST2EAST</wdirection>"+
						"<wspeed>30</wspeed>"+
						"<rain>10</rain>"+
						"<snow>0</snow>"+
						"</report>"+
						"<report id=\""+ i+"\">"+
						"<date>2014-01-10</date>"+
						"<temperature>30</temperature>"+
						"<humidity>70</humidity>"+
						"<wdirection>EAST2WEST</wdirection>"+
						"<wspeed>30</wspeed>"+
						"<rain>5</rain>"+
						"<snow>0</snow>"+
						"</report>");
				bufferWritter.newLine();

			}
			bufferWritter.append("</weather>");

			bufferWritter.close();
		}


	}

}
