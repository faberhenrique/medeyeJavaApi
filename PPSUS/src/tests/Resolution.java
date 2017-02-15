/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.Graph.Graph;
import Util.PpsusImage;
import Util.Report;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author faber.henrique
 */
public class Resolution {

    PpsusImage image;
    String result = "===================================================================================================================================================================================================================================================\n"
                + "																			RESULTADO TESTE DE RESOLUCAO\n"
                + "===================================================================================================================================================================================================================================================\n";
    public Resolution(PpsusImage image) {
        this.image = image;
        executeTest(true);
        executeTest(false);
       // teste(image.getMatrix());
       printResult();

    }

    private void executeTest(boolean orientation) {
        Graph chart = new Graph();
        double tax = Integer.parseInt(image.getInfo("1243").trim()) / 1000;
        double[] result;
        int pos;
        int fwhmPos;
        double max;
        int cont = 1;
        do {

            result = calcVector(cont, orientation);
            pos = -1;
            fwhmPos = -1;
            max = Double.MIN_VALUE;
            for (int i = 0; i < result.length; i++) {
                if (result[i] > max) {
                    max = result[i];
                    pos = i;
                } else if (result[i] / (result[i] / 2) > 0.75) {
                    fwhmPos = i;
                }

            }
            double fwhm = (pos * tax) / (fwhmPos * 2 * tax) * 100;
            addResult("Square Number " + cont + " Horizont= "+orientation+" FWHM= " + fwhm);
            try {
                chart.lineChart(result, "Square Number " + cont + " Horizont="+orientation, "", "Values");
            } catch (IOException ex) {
                Logger.getLogger(Resolution.class.getName()).log(Level.SEVERE, null, ex);
            }
            cont++;
        } while (cont <= 4);
        
    }

    private double[] calcVector(int fourSquare, boolean horizont) {
        int width = image.getWidth();
        int height = image.getHeight();
        int hInit, vInit, hEnd, vEnd;
        switch (fourSquare) {
            case 1:
                vInit = (int) (width * 0.125);
                vEnd = (int) (width * 0.25);
                hInit = (int) (height * 0.375);
                hEnd= (int) (height * 0.5);
                break;
            case 2:
                vInit = (int) (width * 0.125);
                vEnd = (int) (width * 0.25);
                hInit = (int) (height * 0.5);
                hEnd= (int) (height * 0.625);
                break;

            case 3:
                vInit = (int) (width * 0.625);
                vEnd = (int) (width * 0.75);
                hInit = (int) (height * 0.375);
                hEnd= (int) (height * 0.5);
                break;

            case 4:
                vInit = (int) (width * 0.625);
                vEnd = (int) (width * 0.75);
                hInit = (int) (height * 0.5);
                hEnd= (int) (height * 0.625);
                break;

            default:
                hInit = 0;
                hEnd = width;
                vInit = 0;
                vEnd = height;
                break;

        }
        if (horizont) {
            return image.profileHori(hInit, vInit, hEnd, vEnd);
        } else {
            return image.profileVert(hInit, vInit, hEnd, vEnd);
        }
    }
    
    private void teste(double [][] yourmatrix){
         for (int line = (int)(image.getWidth() * 0.125); line < image.getWidth() * 0.25; line++) 
         for ( int col = (int)(image.getHeight() * 0.5); col < image.getHeight() * 0.625; col++) 
                    yourmatrix[line][col]=0;
         
            

        
        
        try {
            BufferedImage image = new BufferedImage(yourmatrix.length,yourmatrix[0].length,BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < yourmatrix.length; i++) {
                for (int j = 0; j < yourmatrix[i].length; j++) {
                    int a = (int) yourmatrix[i][j];
                    Color newColor = new Color(a, a, a);
                    image.setRGB(j, i, newColor.getRGB());
                }
            }
            File output = new File("GrayScale3.jpg");
            ImageIO.write(image, "jpg", output);
        } catch (Exception e) {
                    System.out.println(e);

        }
    }
    private void addResult(String data){
        
        result = result+data+"\n";
    }
    private void printResult(){
        Report r = Report.getInstance();
        r.addResult(result);
        
    }
}
