package com.wsm.entity.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphElement extends Element{

	/**
	 * Creates a new FeatureVector by reading the contents of the passed array
	 * @param fileLine
	 */
	public GraphElement(int id, String[] fileLine, boolean isResult) {
		super(id);

		this.label = (Integer.parseInt(fileLine[0]));

		if (isResult)   {
			//the line contains a result that was already calculated in
			//addition to the label (*.result usually)
			this.calculatedClusternumber = (Integer.parseInt(fileLine[1]));

		} else {
			//the line is not the result of a clustering process (*test,*.valid)
			this.calculatedClusternumber = Element.UNCLASSIFIED;

		}

	}

	public GraphElement(int id ,String[] fileLine) {
		//in the default case we read in FeatureVectors that do not
		//have a calculated result yet
		this(id,fileLine, false);
	}


	/**
	 *Creates a FV with the specified Features and the specified CalculatedClusterNumber
	 *Used for the KMEans algorithm. Do not use to create real FV

	 * @param clusterID the calculatedClusternumber
	 */
	public GraphElement(int elementid ,Integer clusterID) {
		super(elementid);
		this.calculatedClusternumber = clusterID;
	}




	/**
	 * Determines if the point vect is calculated to be in the same cluster as
	 * this point.
	 * @param vect
	 * @return false if not in same cluster or clusters are not yet calculated
	 */
	public boolean isSameCluster(Element vect) {
		if (this.calculatedClusternumber == Element.UNCLASSIFIED
				&& vect.getCalculatedClusternumber() == Element.UNCLASSIFIED) {
			//clusternumbers have not been calculated
			return false;
		} else if (vect.getCalculatedClusternumber() == this.calculatedClusternumber) {
			//clusternumber has been calculated to be the same
			return true;
		} else {
			//clusternumbers have been calculated but are different
			return false;
		}
	}



	/**
	 * Calculates the dist to another Element using the distance matrix
	 */
	@Override
	public float calculateDistance(Element other) {
		if (other instanceof GraphElement){
			float tempDitance=0.00f;
			float humidityDitance=0.00f;
			float finalDistance=0.00f;
			if(this.getTemp() >other.getTemp()){
				tempDitance=this.getTemp()-other.getTemp();
			}else{
				tempDitance=other.getTemp()-this.getTemp();
			}

			/*if(this.getHumidity() >other.getHumidity()){
                			humidityDitance=this.getHumidity()-other.getHumidity();
                		}else{
                			humidityDitance=other.getHumidity()-this.getHumidity();
                		}*/


			finalDistance=humidityDitance+tempDitance;               			

			return finalDistance;

		} else{
			throw new RuntimeException("could not calculate distance between FeatureVector and other element");
		}

	}

	/**
	 * calculates the average distance to all other elements by using the imported
	 * Distance Matrix which is a square matrix
	 */
	public float calculateAverageDistanceToOthers() {
		float[] distances = null;

		Map<String, Float> disMap=Dataset.getDistanceMatrix();

		Set<String> keys=disMap.keySet();
		List<String> selectedKeys=new ArrayList<String>();

		for (String string : keys) {
			if(string.indexOf(" "+this.getId()+",")>-1){
				selectedKeys.add(string);
			}
		}
		distances=new float[selectedKeys.size()];

		for (int i=0;i<selectedKeys.size();i++) {			
			distances[i]=disMap.get(selectedKeys.get(i));
		}


		float sum = 0.0f;
		for (int i = 0; i < distances.length; i++) {
			sum += distances[i];
		}
		return (sum /distances.length); 

	}

	/**
	 * @return
	 */
	public float getDimension() {
		// TODO Auto-generated method stub
		return Float.NaN;
	}





}