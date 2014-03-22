package com.wsm.util;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	

	public JSONObject parseXML(Document document){
		JSONObject outerJson=new JSONObject();

		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("report");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			String xmlString="<report id=\"";
			JSONObject innerJsonObject=new JSONObject();
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				xmlString=xmlString+eElement.getAttribute("id")+"\">";
				NodeList innerList=eElement.getChildNodes();
				for(int i=0;i<innerList.getLength();i++){
					Node currentNode=innerList.item(i);
					if(currentNode.getNodeType()==Node.ELEMENT_NODE){
						String nodeNameString=currentNode.getNodeName();
						String nodeValueString=currentNode.getTextContent();
						innerJsonObject.put(nodeNameString, nodeValueString);
						xmlString=xmlString+"<"+nodeNameString+">"+nodeValueString+"</"+nodeNameString+">";
					}
				}
				xmlString=xmlString+"</report>";
				innerJsonObject.put("xml", xmlString);
				outerJson.put(eElement.getAttribute("id"),innerJsonObject);
			}
		}	
		return outerJson;
	}
}
