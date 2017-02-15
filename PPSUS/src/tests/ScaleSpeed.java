/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.PpsusImage;
import Util.Report;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casa
 */
public class ScaleSpeed {

    //material activity
    double[] activity;
    
    //table velocity
    double velocity;
    List<Double> counts = new ArrayList<>();
    PpsusImage image;
    String result = "===================================================================================================================================================================================================================================================\n"
            + "																			RESULTADO TESTE DE FLOODSPEED\n"
            + "===================================================================================================================================================================================================================================================\n";
    
    public ScaleSpeed(PpsusImage image, String activity, double velocity) {
        prepareAcivity(activity);
        this.velocity = velocity;
        this.image = image;
        executeTest();
        printResult();
    }
    private void prepareAcivity(String values){
      String []aux = (values.replace(",", ".")).split(";");
      activity = new double[aux.length];
      double max = Double.MIN_VALUE;
      //Preparando vetor
      for(int i=0;i<aux.length;i++){
          activity[i] = Double.parseDouble(aux[i]);
          if (activity[i]>max)
              max = activity[i];          
      }
      //Normalizando vetor
      for(int i=0;i<activity.length;i++){
          activity[i]=activity[i]/max;
      }
      
    }
    
    private void executeTest() {
        double point = 0;
        double max = Double.MIN_VALUE;
        double lineSum = 0;
        double[][] imageMatrix = image.getMatrix();
        for (int lin = 0; lin < imageMatrix[0].length; lin++) {
            for (int col = 0; col < imageMatrix.length; col++) {
                //ADD ALL LINE VALUES
                lineSum = lineSum + imageMatrix[col][lin];
                //  System.out.print(imageMatrix[lin][col]);
            }
            //System.out.println("Linha "+lin+" Total="+lineSum);
            //IF LINE VALUE >0 ADD INTO POINT TOTAL
            if (lineSum > 400) {
                point = point + lineSum;
            } else {
                //IF LINE VALUE = 0 AND POINT TOTAL >0 --> END OF POINT ADD TO LIST AND LOOK FOR A NEW POINT
                if (lineSum > 400 && point > 0) {
                    System.out.println("novoPonto");
                }
                
                if (point > 0) {
                    //SAVE MAX SUM VALUE
                    if (point > max) {
                        max = point;
                    }
                    counts.add(point);
                    point = 0;
                    System.out.println("novoPonto " + lin);
                }
            }
            lineSum = 0;
        }
        double diferencial=0;
        addResult("Foram encontrados " + counts.size() + " pontos");
        for (int i = 0; i < counts.size(); i++) {                   
            addResult("Contagem Normalizada=" + (counts.get(i) / max));
            diferencial = (activity[i]+counts.get(i) / max)/
                          (counts.get(i) / max);
            addResult("Diferencial = "+diferencial+"\n");
        }
        
    }

    private void addResult(String data) {
        
        result = result + data + "\n";
    }

    private void printResult() {
        Report r = Report.getInstance();
        r.addResult(result);
        
    }
}
