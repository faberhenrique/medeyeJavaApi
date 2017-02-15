/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.PpsusImage;
import Util.Report;
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
public class Background {

    private final  PpsusImage image;
        String result = "===================================================================================================================================================================================================================================================\n"
                + "																			RESULTADO TESTE DE BG\n"
                + "===================================================================================================================================================================================================================================================\n";

    public Background(PpsusImage image) {
        
        this.image = image;                
        Heatmap();
        printResult();
    }
    
    private void Heatmap(){
        double[][] data = image.getMatrix();
        double soma=0;

         for (int l = 0; l < image.getHeight(); l++) {
                for (int c = 0; c < image.getWidth(); c++) {
                   soma=soma+image.getValue(l, c) ;
                    
                }
            }
         addResult("Valor de toda a imagem ="+soma);
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
