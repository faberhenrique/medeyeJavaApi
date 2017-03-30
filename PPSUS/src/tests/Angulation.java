/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

/**
 *
 * @author Casa
 */
import Util.Graph.Graph;
import Util.Hough_Circles;
import Util.PpsusImage;

import Util.PpsusImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Angulation {
    private final PpsusImage image;
    public Angulation(PpsusImage image){
        this.image = image;
    }
    public void executeTest(){
        verifyAng();
    }

    private void verifyAng() {
       double [][]data = image.getMatrix();
       double []dataVector = new double[image.getWidth()];
       Graph chart = new Graph();

       //Inicilize vector
       for(int aux = 0; aux<dataVector.length;aux++)
           dataVector[aux]=0;
       
       
        for (int lin = 0; lin < data.length; lin++) {
            for (int col = 0; col < data[lin].length; col++) {
                dataVector[lin] = dataVector[lin]+data[lin][col];
            }
        }
        
          try {
              
                chart.lineChart(dataVector, "Teste", "", "Values");
            } catch (IOException ex) {
                Logger.getLogger(Resolution.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
