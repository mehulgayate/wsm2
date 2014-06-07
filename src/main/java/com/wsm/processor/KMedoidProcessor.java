/**
 * 
 */
package com.wsm.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wsm.entity.support.Cluster;
import com.wsm.entity.support.Dataset;
import com.wsm.entity.support.GraphElement;
import com.wsm.entity.support.Element;
import com.wsm.util.CalculationUtil;

/**
 * @author Markus
 * 
 */
public class KMedoidProcessor {

        protected Integer numOfClusters;
        protected GraphElement[] centers;

        //Constructors -------------------------------
        public KMedoidProcessor(){
        }
        public KMedoidProcessor (int numOfClusters){

                this.numOfClusters = numOfClusters;
        }
        

        //Getters and Setters -----------------------


        public int getNumOfClusters() {
                return numOfClusters;
        }

        public void setNumOfClusters(Integer numOfClusters) {
                this.numOfClusters = numOfClusters;
        }
        
        //Methods ------------------------------------------------------------

        /*
         * (non-Javadoc)
         * 
         * @see algorithms.ClusteringAlgorithm#doClustering(input.Dataset)
         */        
        public void doClustering(Dataset dataset) {
                dataset.reset();
                
                boolean foundUnique=false;
                GraphElement[] randomCentroids = this.chooseRandomElementsAsCenters(dataset);
                
                int iteration=0;
                while (!foundUnique) {
                	randomCentroids = this.chooseRandomElementsAsCenters(dataset);
                	
                	for (GraphElement graphElement : randomCentroids) {
                		for (GraphElement graphElement2 : randomCentroids) {                			
        					if(graphElement.getId()!=graphElement2.getId() && graphElement.getTemp()!=graphElement2.getTemp()){
        						foundUnique=true;        						
        					}else if(graphElement.getId()!=graphElement2.getId() && graphElement.getTemp()==graphElement2.getTemp()){
        						foundUnique=false;
        						break;
        					}
        				}
                		if(foundUnique==false){
                			break;
                		}
    				}                	
					iteration++;
					if(iteration>20){
						throw new RuntimeException("Unable to find unique centers >>>>");
					}
				}
                for (GraphElement graphElement : randomCentroids) {
					System.out.println("Center selected : id : "+graphElement.getId() +" Temp : "+graphElement.getTemp());
				}
                
                this.runKMedoids(dataset,randomCentroids );
        

        }

        /**
         * @param centers
         * @param recalcCenters
         * @return true if the Element on position x of the centers array is equal to
         *         the the Elements at position x in the racalcCenters array.
         *         Also returns true if one or both parameters are <code> null</code>
         */
        private boolean centersChanged(GraphElement[] centers,
                        GraphElement[] recalcCenters) {
                if (centers == null || recalcCenters == null){
                        return true;
                }
                for (int i = 0; i < centers.length; i++) {
                        boolean isEqualCenter;
                        
                        isEqualCenter = centers[i].equals(recalcCenters[i]);
                        if (!isEqualCenter) {
                                return true; // center has changed
                        }
                }
                return false; // all centers are the same as before
        }

        protected GraphElement[] chooseRandomElementsAsCenters(Dataset dataset) {
                List<GraphElement> candidatPoints = new ArrayList<GraphElement>();
                candidatPoints.addAll(dataset.getAllPoints());
                GraphElement[] centers = new GraphElement[this.numOfClusters];
                for (int i = 0; i < this.numOfClusters; i++) {
                        int randElement = (int) Math.floor(Math.random()
                                        * candidatPoints.size());
                        Element center = candidatPoints.get(randElement);
                        center.setCalculatedClusternumber(i);
                        centers[i] = candidatPoints.get(randElement);
                        candidatPoints.remove(randElement);                        
                }
                
                
                
                return centers;
        }



        private void assingPointsToClosestCenter(Dataset dataset,
                        GraphElement[] centers) {
        	
                assert (centers[centers.length-1] != null);
                for (GraphElement currFeatureVector : dataset) {
                        Float[] distances = new Float[centers.length];
                        
                        for (int i = 0; i < distances.length; i++) {
                                GraphElement currCenter = centers[i];
                                distances[i] = currCenter.calculateDistance(currFeatureVector);                               
                        }
                        int closestId = CalculationUtil.getIndexOfMinElement(distances);                        
                        currFeatureVector.setCalculatedClusternumber(closestId);
                        assert (centers[closestId].getCalculatedClusternumber() == closestId);
                }
                assert (dataset.getClustermap().keySet().size() == this.numOfClusters);
        }
        
        /**
         * For every cluster in the dataset calculate its Medoid
         * @param dataset
         * @return
         */
        private  GraphElement[] calculateCenters(Dataset dataset){
                GraphElement[] recalcCenters = new GraphElement[this.numOfClusters];
                Map<Integer, Cluster> clusters = dataset.getClustermap();
                
                for (Integer clusterID : clusters.keySet()) {                
                        GraphElement newMedoid = (clusters
                                        .get(clusterID).getMedoid());
                        assert (newMedoid.getCalculatedClusternumber() == clusterID);
                        recalcCenters[clusterID] = newMedoid;
                }
                return recalcCenters;                
        }
        
        /**
         * Starts a run of the KMeans algortihm. It runs as long as there is 
         * a change in the positioning of the centers. The initial centers 
         * are stored in the field centers and are recalculated every iteration.
         * @param dataset
         * @param initialMedoid
         */
        public void runKMedoids (Dataset dataset, GraphElement[] initialMedoid){
       // dataset.reset();
        GraphElement[] oldCenters = null;
        this.centers = initialMedoid;
                while (centersChanged(oldCenters, this.centers)) {
                        oldCenters = this.centers;
                        this.assingPointsToClosestCenter(dataset, oldCenters);
                        this.centers = this.calculateCenters(dataset);
                }
        }
        


}
