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
import javax.imageio.ImageIO;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author faber.henrique
 */
public class Uniformity {

    private static BufferedImage matrizI;
    private int largura;
    private int altura;
    private PpsusImage image;
    double[][] data;
    double max=Double.MIN_VALUE;
    double min = Double.MAX_VALUE;
    double maxDiff = Integer.MIN_VALUE;
    double minDiff =0;
    String result = "===================================================================================================================================================================================================================================================\n"
                + "																			RESULTADO TESTE DE UNIFORMIDADE\n"
                + "===================================================================================================================================================================================================================================================\n";
    public Uniformity(BufferedImage imagem, int largura, int altura) {
        matrizI = imagem;
        this.largura = largura;
        this.altura = altura;

        Heatmap();
    }

    public Uniformity(PpsusImage ppImage) throws IOException {
        this.image = ppImage;
        smoothUFOV();
        getImageFromArray(data);
        diffUniformity();
        matrizI = ppImage.getImage().getBufferedImage();
//        Heatmap();
        printResult();
    }

    private void Heatmap() {
        double[][] data = image.getMatrix();
        int[] pixel;

        for (int linha = 0; linha < largura; linha++) {
            for (int coluna = 0; coluna < altura; coluna++) {
                pixel = matrizI.getRaster().getPixel(linha, coluna, new int[3]);
                data[linha][coluna] = pixel[0] + pixel[1] + pixel[2];

            }
        }

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
            map.saveToFile(new File(image.getInfo("103E")+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Uniformity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void smoothUFOV() throws IOException {
        data = image.getMatrix();

        int limit = (int) (data.length - (data.length * 0.75)) / 2;

        //STEP 1 SET 0 in values of range (75% of image)
        for (int lin = 0; lin < data.length; lin++) {
            for (int col = 0; col < data[lin].length; col++) {
                if ((lin < limit || col < limit) || (lin > data.length - limit || col > data.length - limit)) {
                    data[lin][col] = 0;
                }
            }
        }
        //STEP 2 Apply Mask
        for (int lin = 0; lin < data.length; lin++) {
            for (int col = 0; col < data[lin].length; col++) {
               if(data[lin][col]>0){
                   applyMask(lin, col);
               }
            }
        }
        
        addResult("Intergra Uniformity = "+100*((max-min)/(max+min)));
        System.out.println("fim");
        //getImageFromArray(data, "teste.jpg");

    }

    private void applyMask(int lin,int col){
        //POS 1 2 1
        double pos1 = data[lin-1][col-1];
        double pos2 = data[lin][col-1]*2;
        double pos3 = data[lin+1][col+1];
        //POS 2 4 2
        double pos4 = data[lin-1][col]*2;
        double pos5 = data[lin][col]*4;
        double pos6 = data[lin+1][col]*2;
        //POS 1 2 1
        double pos7 = data[lin+1][col-1]*1;
        double pos8 = data[lin+1][col]*2;
        double pos9 = data[lin+1][col+1]*1;
        double resultMatriz = (pos1+pos2+pos3+pos4+pos5+pos6+pos7+pos8+pos9);
        data[lin][col]=data[lin][col]/resultMatriz;
        if(data[lin][col]> max)
            max = data[lin][col];
        if(data[lin][col]< min)
            min = data[lin][col];
    }
    
    private void diffUniformity(){
        int limit = (int) (data.length - (data.length * 0.75)) / 2;

        for(int lin = 0;lin < data.length;lin+=5){
            for(int col = 0;col<data[0].length;col+=5){
              if (!((lin < limit || col < limit) || (lin > data.length - limit || col > data.length - limit))) 
        
                calcDiff(lin,col);
            }
                               addResult("Differential uniformity="+100 * ((maxDiff-minDiff)/(maxDiff+minDiff)));

            
        }

    }
    private void calcDiff(int x, int y) {
        double []lineDiff = new double [5];
        double []colDiff = new double [5];
        for(int lin=0;lin<5;lin++){
            for(int col = 0;col<5;col++){
                lineDiff[col]=data[x+lin][y+col];
                colDiff[col]=data[x+col][y+lin];             
            }
            getMaxMin(lineDiff);
            getMaxMin(colDiff);

        }
    }
    private void getMaxMin(double []values){
        double aux = Integer.MIN_VALUE;
            for(int diff = 1;diff<values.length;diff++){
             
                if(values[0]-values[diff]>aux){
                    aux = values[0]-values[diff];
                    
                }
               
            }
             if(aux > maxDiff)
                    maxDiff = aux;
                if(aux < minDiff)
                    minDiff = aux;
    }
    
    public  void getImageFromArray(double [][] yourmatrix) throws IOException {
       
            

        
        
        try {
            BufferedImage image = new BufferedImage(yourmatrix.length,yourmatrix[0].length,BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < yourmatrix.length; i++) {
                for (int j = 0; j < yourmatrix[i].length; j++) {
                    int a = (int) Math.round(255/(yourmatrix[i][j]+1));
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
