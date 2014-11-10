package com.wsm.web.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLConverter
{
	private static final String outputFilePath = "ConvertedDataset";
	private String ouputFolder;	
	public void setOuputFolder(String ouputFolder) {
		this.ouputFolder = ouputFolder;
	}
	
	public String getOuputFolder() {
		return ouputFolder;
	}

	static DocumentBuilderFactory docFactory ;
	static DocumentBuilder docBuilder ;

	static int identifierCounter=-1;

	// root elements
	static Document doc ;
	static Element rootElement ;

	// EmpID","EmpName","DOB","DOJ","Salary","Department","Account","Project"
	static String XmlTags[]={"date","WMO-STATION-ID","RMAX","RMIN","RPRCP","EPRCP","VP","POTEV","VPD","SLP06","SLP12","SLP18","SLP00","APTMX","CHILLM","RAD","MAXFL","MINFL","IFPRQ","IFPRT","NOBS","NOBSP"};
	static String strRootElement="weather";//if u want to change root element, anything change here;
	static String strElement="report";//if u want to change Weather to anything change here;

	public void process(MultipartFile multipartFile)
	{
		try
		{
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			rootElement = doc.createElement(strRootElement);
			doc.appendChild(rootElement);


			File folder=new File(ouputFolder);
			folder.mkdirs();
			Scanner keyboard=new Scanner(System.in);
			int fileCounter=1;

			
			
			FileInputStream fis = null;
			BufferedReader reader = null;
                        
            reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), "UTF-8"));
            String line;
            StringTokenizer st;
            while((line = reader.readLine()) != null)
            {

            	//st=new StringTokenizer(line);
            	
            	rootElement.appendChild(getElement(line));
            	
            	if(identifierCounter!=0 && identifierCounter%300==0){            		
            		TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    //for pretty print
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource source = new DOMSource(doc);

                    //write to console or file
                    StreamResult file = new StreamResult(new File(ouputFolder+"/"+outputFilePath+"_"+fileCounter+".xml"));
                    fileCounter++;

                    //write data
                    transformer.transform(source, file);
                    doc = docBuilder.newDocument();

        			rootElement = doc.createElement(strRootElement);
        			doc.appendChild(rootElement);
            	}
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to console or file
            StreamResult file = new StreamResult(new File(ouputFolder+"/"+outputFilePath+"_"+fileCounter+".xml"));

            //write data
            transformer.transform(source, file);

            System.out.println(identifierCounter+" records saved in file!");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	static Element createChild(Document doc, Element element, String name, String value)
	{
		if(value!=null && value.trim().length()>0)
		{
			element.appendChild(getEmployeeElements(doc, element,name,value.trim()));
		}
		value=null;
		return element;
	}

	static Element createChild(Document doc, Element element, String name, String data, int startIndex, int endIndex)
	{
		String value = data.substring(startIndex,endIndex);

		if(value!=null && value.trim().length()>0)
		{
			element.appendChild(getEmployeeElements(doc, element,name,value.trim()));
		}
		value=null;
		return element;
	}

 	static Node getElement(String line)
 	{
		int i=0;
		String value=null;
 		Element xmlElement = doc.createElement(strElement);
 		identifierCounter++;
 		xmlElement.setAttribute("id",""+identifierCounter);

 		//==
		  String xmlDate = "";
		  xmlDate = xmlDate + line.substring(1,5);
		  xmlDate = xmlDate + "-";
		  xmlDate = xmlDate + line.substring(5,7);
		  xmlDate = xmlDate + "-";
		  xmlDate = xmlDate + line.substring(7,9);

 		//==


 		//date - GREENWICH MEAN DATE OF DATA
 		xmlElement = createChild(doc, xmlElement, XmlTags[i++],xmlDate);

 		//date - GREENWICH MEAN DATE OF DATA
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,1,9);

 		//BKIDN - WMO STATION ID
 		xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,10,14);

 		//RMAX - HOUR MAXIMUM TEMPERATURE
 		float maxTemp = Float.parseFloat(line.substring(15,19).trim());

 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,15,19);
 		i++;

 		//RMIN - HOUR MINIMUM TEMPERATURE
 		float minTemp = Float.parseFloat(line.substring(20,24).trim());

 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,20,24);
 		i++;

 		float avgTemp = (maxTemp+minTemp)/2;
 		xmlElement = createChild(doc, xmlElement,"temperature",""+avgTemp);

 		//RPRCP - TOTAL OF PRECIPITATION
 		float maxHumidity = Float.parseFloat(line.substring(25,30).trim());
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,25,30);
 		i++;

 		//EPRCP - TOTAL OF PRECIPITATION INCLUDING ESTIMATES
 		float minHumidity = Float.parseFloat(line.substring(31,36).trim());
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,31,36);
 		i++;

 		float avgHumidity = (maxHumidity+minHumidity)/2;
 		xmlElement = createChild(doc, xmlElement,"humidity",""+avgHumidity);

 		//VP - VAPOR PRESSURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,37,42);

 		//POTEV - POTENTIAL EVAPOTRANSPIRATION
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,43,48);

 		//VPD - VAPOR PRESSURE DEFICIT
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,49,54);

 		//SLP06 - 0600Z SEA LEVEL PRESSURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,55,60);

 		//SLP12 - 1200Z SEA LEVEL PRESSURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,61,66);

 		//SLP18 - 1800Z SEA LEVEL PRESSURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,67,72);

 		//SLP00 - 0000Z SEA LEVEL PRESSURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,73,78);

 		//APTMX - MAXIMUM APPARENT TEMPERATURE
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,79,84);

 		//CHILLM - MINIMUM WIND CHILL
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,85,89);

 		//RAD - NET RADIATION
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,90,94);
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,68,73);
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,74,79);
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,80,81);
 		//xmlElement = createChild(doc, xmlElement, XmlTags[i++],line,82,87);


/* 		int count=0;
 		while(count<=88){}
 		for(int i=0;i<count;i++)
 		{
 			//xmlElement.appendChild(getEmployeeElements(doc, xmlElement, XmlTags[i],st.nextToken()));
 		}*/
 		return xmlElement;
    }
 	static Node getEmployeeElements(Document doc, Element element, String name, String value)
 	{
 		Element node = doc.createElement(name);
 		node.appendChild(doc.createTextNode(value));
 		return node;
 	}

}