/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.Graph.Graph;
import Util.PpsusImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author faber.henrique
 */
public class Resolution {
    PpsusImage image;
    public Resolution(PpsusImage image){
        this.image = image;
        executeTest();
    }

    private void executeTest() {
        Graph chart = new Graph();
        try {
            chart.lineChart(calcVector(1, true), "First Square Horizont", "", "Values");
        } catch (IOException ex) {
            Logger.getLogger(Resolution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private double [] calcVector(int fourSquare, boolean horizont){
       int width = image.getWidth();
       int height = image.getHeight();
       int hInit,vInit,hEnd,vEnd;
       switch(fourSquare){
           case 1:
               hInit = (int) (width * 0.25);
               hEnd =  (int) (width * 0.5);
               vInit = (int) (height * 0.25);
               vEnd =  (int) (height * 0.5);
               break;
           case 2:
               hInit = (int) (width * 0.5);
               hEnd =  (int) (width * 0.75);
               vInit = (int) (height * 0.25);
               vEnd =  (int) (height * 0.5); 
               break;
              
           case 3:
               hInit = (int) (width * 0.25);
               hEnd =  (int) (width * 0.5);
               vInit = (int) (height * 0.5);
               vEnd =  (int) (height * 0.75); 
               break;

           case 4:
               hInit = (int) (width * 0.5);
               hEnd =  (int) (width * 0.75);
               vInit = (int) (height * 0.5);
               vEnd =  (int) (height * 0.75);
               break;
           
           default:
               hInit = 0;
               hEnd = width;
               vInit = 0;
               vEnd = height;
                break;

       }
       if(horizont)
           return image.profileHori(hInit,vInit,hEnd,vEnd);
       else
           return image.profileVet(hInit,vInit,hEnd,vEnd);
    }
}
