/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.PpsusImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casa
 */
public class ScaleSpeed {
    //material activity
    double activity;
    //table velocity
    double velocity;
    List<Double> counts = new ArrayList<>();
    PpsusImage image;
    public ScaleSpeed(PpsusImage image, double material, double velocity){
        activity = material;
        this.velocity = velocity;
        this.image = image;
        executeTest();
    }

    private void executeTest() {
        double point=0;
        double max = Double.MIN_VALUE;
        double lineSum=0;
        double[][] imageMatrix = image.getMatrix();
      for(int lin = 0; lin< imageMatrix[0].length;lin++){
        for(int col = 0; col< imageMatrix.length;col++){
                //ADD ALL LINE VALUES
                lineSum = lineSum + imageMatrix[col][lin];       
              //  System.out.print(imageMatrix[lin][col]);
            }
        //System.out.println("Linha "+lin+" Total="+lineSum);
            //IF LINE VALUE >0 ADD INTO POINT TOTAL
            if(lineSum >400){
                point = point+lineSum;
            } else{
                //IF LINE VALUE = 0 AND POINT TOTAL >0 --> END OF POINT ADD TO LIST AND LOOK FOR A NEW POINT
                if(lineSum >400 && point >0){
                    System.out.println("novoPonto");
                }
                    
                if(point >0){
                    //SAVE MAX SUM VALUE
                    if(point > max)
                         max = point;
                    counts.add(point);
                    point=0;
                    System.out.println("novoPonto "+lin);
                }
            }
            lineSum = 0;
        }
        for(Double value: counts){
            System.out.println("Diferencial="+(value/max));
        }
      
    }
}
