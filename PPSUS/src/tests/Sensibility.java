/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.PpsusImage;
import Util.Report;
import ij.ImagePlus;
import ij.measure.Calibration;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author faber.henrique
 */
public final class Sensibility {
    private final PpsusImage image;
    private double acitivity;
        String result = "===================================================================================================================================================================================================================================================\n"
                + "																			RESULTADO TESTE DE SENSIBILIDADE\n"
                + "===================================================================================================================================================================================================================================================\n";


    public Sensibility(PpsusImage image, double activity) {
        this.image = image;
        this.acitivity = activity;
        executeTest();
        printResult();
    }
    public void executeTest(){
        image.BinarizeImage();
        double[][] imageBin = image.getBin();
        double sum = 0;
        for (double[] imageBin1 : imageBin) {
            for (int j = 0; j < imageBin1.length; j++) {
                sum = sum + imageBin1[j];
            }
        }
        addResult("CONTAGEM TOTAL ="+sum);
        double taxa = (sum/100/1000);
        addResult("TAXA (Kctg/seg) = "+ taxa);
        addResult("TAXA (Kcpm/mCi) ="+(taxa*6000)/acitivity/1000);
        teste(imageBin);
    
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
            File output = new File("Sensibilidade.jpg");
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
