/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import Util.PpsusImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
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
    private PpsusImage ppImage;
    double[][] data;
    double max=Double.MIN_VALUE;
    double min = Double.MAX_VALUE;

    public Uniformity(BufferedImage imagem, int largura, int altura) {
        matrizI = imagem;
        this.largura = largura;
        this.altura = altura;

        Heatmap();
    }

    public Uniformity(PpsusImage ppImage) throws IOException {
        this.ppImage = ppImage;
        smoothUFOV();
    }

    private void Heatmap() {
        double[][] data = new double[largura][altura];
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
            map.saveToFile(new File("java-heat-chart.png"));
        } catch (IOException ex) {
            Logger.getLogger(Uniformity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void smoothUFOV() throws IOException {
        data = ppImage.getMatrix();

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
        
        System.out.println("Intergra Uniformity = "+100*((max-min)/(max+min)));
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

    public static void getImageFromArray(double[][] pxls, String path) throws IOException {

        File file = new File("modelo.png");
        BufferedImage img = null;

        img = ImageIO.read(file);

        int width = img.getWidth();
        int height = img.getHeight();

        WritableRaster raster1 = (WritableRaster) img.getRaster();
        double aux;
        for (int i = 0; i < pxls.length; i++) {
            for (int j = 0; j < pxls[0].length; j++) {
                aux = pxls[i][j];

                raster1.setSample(i, j, 0, aux);

            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        image.setData(raster1);

        try {
            File ouptut = new File("change.png");
            ImageIO.write(image, "png", ouptut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
