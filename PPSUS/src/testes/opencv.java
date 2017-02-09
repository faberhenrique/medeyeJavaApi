/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

/**
 *
 * @author Casa
 */
    
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import org.opencv.imgproc.*;
import static org.opencv.imgproc.Imgproc.HOUGH_GRADIENT;
import static org.opencv.imgproc.Imgproc.HoughCircles;
 
public class opencv {
 public static void main(String[] args){
    Mat source = Imgcodecs.imread("8circles.png", Imgcodecs.CV_LOAD_IMAGE_ANYCOLOR);
    Mat barrier = new Mat(source.rows(), source.cols(), source.type());
    Mat difference = new Mat();
    Mat circles = new Mat();

    Core.absdiff(source, barrier, difference);

    Mat grey = new Mat();
    Imgproc.cvtColor(difference, grey, Imgproc.COLOR_BGR2GRAY);
    Imgproc.GaussianBlur( grey, grey, new Size(9, 9), 2, 2 );

   HoughCircles( grey, circles, HOUGH_GRADIENT, 1, grey.rows()/8, 200, 100, 0, 0 );

     imwrite( "../../images/Gray_Image.jpg", circles );

 }
}