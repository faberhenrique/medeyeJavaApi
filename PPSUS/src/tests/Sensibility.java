    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import ij.ImagePlus;
import ij.measure.Calibration;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author faber.henrique
 */
public class Sensibility {

    private static BufferedImage matrixI;
    private  int width;
    private int height;
    private double media;

    public Sensibility(BufferedImage image, int width,int height) {
        matrixI = image;
        this.width=width;
        this.height = height; 
        binarizarImagem();
        Heatmap();
    }
    
    private void Heatmap(){
        double[][] data = new double[width][height];
        int[] pixel;
        int soma=0;

         for (int line = 0; line < width; line++) {
                for (int column = 0; column < height; column++) {
                    pixel = matrixI.getRaster().getPixel(line, column, new int[3]);
                   soma=soma+pixel[0] ;
                    
                }
            }
         System.out.println("Valor de toda a image ="+soma);
         // Step 1: Create our heat map chart using our data.
        HeatChart map = new HeatChart(data);
        map.setHighValueColour(Color.RED);
        map.setLowValueColour(Color.BLUE);
        // Step 2: Customise the chart.
        //map.setTitle("This is my heat chart title");
        //map.setXAxisLabel("X Axis");
        //map.setYAxisLabel("Y Axis");

        try {
            // Step 3: Output the chart to a file.
            map.saveToFile(new File("java-heat-chart.png"));
        } catch (IOException ex) {
            Logger.getLogger(Uniformity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void binarizarImagem() {
        int[] pixel;
        int soma=0;
        int cont = 0;

         for (int line = 0; line < width; line++) {
                for (int column = 0; column < height; column++) {
                    pixel = matrixI.getRaster().getPixel(line, column, new int[3]);
                    if(pixel[0]>0){
                    soma=soma+pixel[0] ;
                    cont++;
                    }
                }
            }
         media = soma/cont; 
    }
    public double getMedia(){
        return media;
    }
    public void calcula(ImagePlus img,Calibration cal){
         int[] t;
         double aux;
double soma = 0;
          for (int line = 0; line < width; line++) {
                for (int column = 0; column < height; column++) {
                    
                     t = img.getPixel(column,line);
                     aux = cal.getCValue(t[0]);
                     if(aux>0)
                     soma = soma + aux;
                    
                    
                }
          }
          System.out.println("VALOR TOTAL IMAGEJ="+soma);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
