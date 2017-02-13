/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.Graph.graph;
import Util.PpsusImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casa
 */
public class FloodSpeed {
    
    PpsusImage image;
    public FloodSpeed(PpsusImage image) throws IOException{
        this.image = image;
        executeTest();
    }

    private void executeTest() throws IOException {
        
       double[] values = new double[image.getHeight()];
        for(int lin = 0; lin< image.getHeight();lin++){
                //ADD ALL LINE VALUES               
                values[lin]= image.getValue(image.getWidth()/2,lin);
             //   System.out.println(values[lin]+","+lin);
              //  System.out.print(imageMatrix[lin][col]);
            }
        saveImage(values);
    }

    private void saveImage(double[] values) throws IOException {
        graph chart = new graph();
        chart.lineChart(values, "Flood Profile", "Point", "Total Count");
    }
}
