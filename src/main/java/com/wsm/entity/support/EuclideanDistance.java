package com.wsm.entity.support;

public class EuclideanDistance implements DistanceMeasure{
    
    public float calculate (float[] vect1, float[] vect2){
            if(vect1.length != vect2.length){
                    throw new NumberFormatException();
            }
            double result = 0;
            double d;
            for (int i = 0; i < vect1.length; i++) {
                    d = (vect1[i] - vect2[i]);
                    result += ( d * d ); //power of 2
            }
            return (float) Math.sqrt(result);
    }
    
    public String toString(){
            return "Euclidean Distance";
    }

}