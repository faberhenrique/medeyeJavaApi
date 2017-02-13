/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.ImageCalculator;
import ij.plugin.RGBStackMerge;
import ij.process.Blitter;
import ij.process.ImageProcessor;
import ij.util.DicomTools;
import java.io.IOException;

/**
 * Class responsable by load an image and create his methods
 * @version 0.1
 * @author Faber Henrique
 */
public class PpsusImage {

    private ImagePlus image = null;
    private double result_bin[][];
    private double result_sum[][];

    private Calibration cali;

    /**
     *sum of all values of image
     */
    public double sum;

    /**
     *value average of image
     */
    public double avg;
    /** 
    * Class constructor.
     * @param path
    */
    public PpsusImage(String path) {
        if (image == null) {
            image = IJ.openImage(path);
            cali = image.getCalibration();
            sum = 0;
        } //reseting internal variables         
        else {
            image = IJ.openImage(path);
            cali = image.getCalibration();
            result_bin = null;
            result_sum = null;
            sum = 0;
        }

    }
    public int getWidth(){
        return image.getWidth();
    }
    public int getHeight(){
        return image.getHeight();
    }
    public ImagePlus getImage(){
        return image;
    }
/**
 * Method responsable by return the info of DICOM Image using a Key
 * @param value Key to retrieve the information of DICOM File
 * @return Information retrieved
 * @version 0.1
 * @author Faber Henrique
 */
    public String getInfo(String value) {
        return DicomTools.getTag(image, value);
    }

    /**
     *Method that performs the sum of two DICOM images and generate a new image
     * @param path
     * @throws IOException
     */
    public void SumImages(String path) throws IOException {
        ImagePlus nova = IJ.openImage(path);
        RGBStackMerge merge = new RGBStackMerge();
        ImageProcessor proc = nova.getProcessor();
        proc.copyBits(image.getProcessor(), 0, 0, Blitter.ADD);
        // result_sum = new ImagePlus("result_sum_"+path,proc);
        //   result_sum.draw();
        //   IJ.saveAs(result_sum,"png","SOMADA");

        ImagePlus[] ch = new ImagePlus[7];
        ch[3] = image;
        ch[1] = nova;

        //   ch[2]=result_sum;
        ImagePlus aux = merge.mergeHyperstacks(ch, false);
        IJ.saveAs(aux, "png", "test1");

        ch[2] = image;
        ch[5] = nova;
        //   ch[2]=result_sum;
        aux = merge.mergeHyperstacks(ch, false);
        IJ.save(aux, "location");
        IJ.saveAs(aux, "png", "test2");
        ImageCalculator ic = new ImageCalculator();
        IJ.saveAs( ic.run("Add", nova, nova),"png","SERA");

    }

    /**
     *Method that performs the sum of two DICOM images into a double vector
     * @param path
     * @throws IOException
     */
    public void SumImages2(String path) throws IOException {
        ImagePlus imageSum = IJ.openImage(path);
        Calibration sumCal = imageSum.getCalibration();
        if (imageSum.getHeight() == image.getHeight() && imageSum.getWidth() == image.getWidth()) {
            //Initialize Reulst_sum
            result_sum = new double[imageSum.getHeight()][imageSum.getWidth()];
            //Executing the sum
            for (int line = 0; line < image.getWidth(); line++) {
                for (int col = 0; col < image.getHeight(); col++) {
                    //Geting the values of IMAGE1 and IMAGE2
                    result_sum[line][col] = sumCal.getCValue((imageSum.getPixel(line, col))[0]) + cali.getCValue((image.getPixel(line, col))[0]);
                    sum = +result_sum[line][col];
                }
            }
            avg = sum / (image.getHeight() * image.getWidth());
        }

    }

    public void SumImage() {
        for (int line = 0; line < image.getWidth(); line++) {
            for (int col = 0; col < image.getHeight(); col++) {
                sum = sum + cali.getCValue((image.getPixel(line, col))[0]);
            }
            avg = sum / (image.getHeight() * image.getWidth());

        }
    }
    //Method that traces profile to check horizontal bars
    public double [] profileVet(int lineI,int colI,int lineF, int colF) {
        double sum_vet[]=new double[colF-colI];
        int aux = 0;
        for (int line = lineI; line < lineF; line++) {
            for ( int col = colI; col < colF; col++) {
                sum_vet[aux] = sum_vet[aux] + cali.getCValue((image.getPixel(line, col))[0]);
            }
            aux++;

        }
        return sum_vet;
    }
        //Method that traces profile to check vertical bars
    public double[] profileHori(int lineI,int colI,int lineF, int colF){
        double sum_vet[]=new double[colF-colI];
        int aux = 0;
        for ( int col = colI; col < colF; col++) {
        for (int line = lineI; line < lineF; line++) {
                sum_vet[aux] = sum_vet[aux] + cali.getCValue((image.getPixel(line, col))[0]);
            }
            aux++;

        }
        return sum_vet;
    }
    /**
     * Method that identifies values lower than average value and switch to 0
     */
    public void BinarizeImage() {
        if (sum == 0) {
            SumImage();
        }
        result_bin = new double[image.getWidth()][image.getHeight()];

        for (int col = 0; col < image.getWidth(); col++) {
            for (int lin = 0; lin < image.getHeight(); lin++) {
                //Testing if value is lower than average
                if (cali.getCValue((image.getPixel(col,lin))[0]) > avg) {
                    result_bin[col][lin] = cali.getCValue((image.getPixel(col,lin))[0]);
                } else {
                    result_bin[col][lin] = 0;
                }

            }
        }

    }
    public Hough_Circles circles(){
        Hough_Circles circ = new Hough_Circles();
        circ.run(image.getProcessor());              
        return circ;
    }
    public double[][] getMatrix(){
                System.out.println(image.getHeight());
        System.out.println(image.getWidth());

        double[][] values = new double [image.getWidth()][image.getHeight()];

        for (int col = 0; col < image.getWidth(); col++) {
            for (int line = 0; line < image.getHeight(); line++) {
                //Retriving the values               
                    values[col][line] = cali.getCValue((image.getPixel(col, line))[0]);                
            }
        }

        
        return values;
        
    }
    public double getValue(int x, int y){
        return cali.getCValue((image.getPixel(x,y))[0]);
    }
}
