/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufmgimagens;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.display.ConsumerFormatImageMaker;
import com.pixelmed.dicom.OtherWordAttribute;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import org.dcm4che3.imageio.plugins.dcm.DicomImageReadParam;
import testes.radiacaoFundo;
import testes.sensibilidade;
import testes.uniformidade;

//IMAGEJ FILES

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.util.DicomTools;
import ij.plugin.DICOM;
import ij.process.FloatProcessor;
/**
 *
 * @author Casa
 */


public class testeController extends ApplicationFrame implements Initializable {

    @FXML
    private TextArea textData;
    @FXML
    private ImageView imagemCarregada;
    @FXML
    private ScrollPane scrollTeste;
    
    @FXML
    private AnchorPane graficoA;
    private static AttributeList list = new AttributeList();
    private static SourceImage img = null;
    private static BufferedImage matrizI;
    XYChart.Series dadosGrafico = new XYChart.Series();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        textData.setText(carregarDados());

        imagemCarregada.setImage(geraImagem2());
      calculaVetorBarras();
     //calculaVetor();
     calculaVetorMedia();
    }

    private String carregarDados() {

        String dicomFile = "RES_LEHR_F_1_F001_DS.dcm";
        String metados = "";
        try {
            list.read(dicomFile);
            metados = metados + "Transfer Syntax:" + getTagInformation(TagFromName.TransferSyntaxUID) + "\n";
            metados = metados + "SOP Class:" + getTagInformation(TagFromName.SOPClassUID) + "\n";
            metados = metados + "Modality:" + getTagInformation(TagFromName.Modality) + "\n";
            metados = metados + "Samples Per Pixel:" + getTagInformation(TagFromName.SamplesPerPixel) + "\n";
            metados = metados + "Photometric Interpretation:" + getTagInformation(TagFromName.PhotometricInterpretation) + "\n";
            metados = metados + "Pixel Spacing:" + getTagInformation(TagFromName.PixelSpacing) + "\n";
            metados = metados + "Bits Allocated:" + getTagInformation(TagFromName.BitsAllocated) + "\n";
            metados = metados + "Bits Stored:" + getTagInformation(TagFromName.BitsStored) + "\n";
            metados = metados + "High Bit:" + getTagInformation(TagFromName.HighBit) + "\n";
            img = new com.pixelmed.display.SourceImage(list);

            metados = metados + "Number of frames " + img.getNumberOfFrames() + "\n";
            metados = metados + "Width " + img.getWidth() + "\n";//all frames will have same width
            metados = metados + "Height " + img.getHeight() + "\n";//all frames will have same height  
            metados = metados + "Is Grayscale? " + img.isGrayscale() + "\n";
            metados = metados + "Pixel Data present:" + (list.get(TagFromName.PixelData) != null) + "\n";

            OtherWordAttribute pixelAttribute = (OtherWordAttribute) (list.get(TagFromName.PixelData));
            //get the 16 bit pixel data values
            short[] data = pixelAttribute.getShortValues();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metados;
    }

    private static String getTagInformation(AttributeTag attrTag) {
        return Attribute.getDelimitedStringValuesOrEmptyString(list, attrTag);
    }

    private Image geraImagem() {
        String dicomFile = "imagem.dcm";
        String outputJpgFile = "imagemTeste.png";
        Image retorno = null;
        try {
            //DicomInputStream myimage = new DicomInputStream(new File(dicomFile));
            //  SourceImage teste = new SourceImage(myimage);                
            ConsumerFormatImageMaker.convertFileToEightBitImage(dicomFile, outputJpgFile, "png", 0);
            File file = new File(outputJpgFile);
            retorno = new Image(file.toURI().toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return retorno;
    }

    private Image geraImagem2() {
        BufferedImage myJpegImage = null;

        File file2 = new File("test.jpg");

        File file = new File("RES_LEHR_F_1_F001_DS.dcm");
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("DICOM");
        while (iterator.hasNext()) {
            ImageReader imageReader = (ImageReader) iterator.next();
            DicomImageReadParam dicomImageReadParam = (DicomImageReadParam) imageReader.getDefaultReadParam();
            try {
                try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
                    imageReader.setInput(iis, false);
                    myJpegImage = imageReader.read(0, dicomImageReadParam);
                }
                if (myJpegImage == null) {
                    System.out.println("Could not read image!!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                ImageIO.write(myJpegImage, "png", file2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Completed");

        }
        return new Image(file2.toURI().toString());

    }

    private void calculaVetor() {

        int[] pixel;
        int largura = img.getHeight();
        int altura = img.getHeight();
        int valores[] = new int[largura+altura];

        try {
            matrizI = ImageIO.read(new File("test.jpg"));
           // uniformidade teste = new uniformidade(matrizI, largura, altura);
            radiacaoFundo teste = new radiacaoFundo(matrizI, largura, altura);
            for (int cont : valores) {
                valores[cont] = 0;
            }
            for (int linha = 0; linha < largura; linha++) {
                for (int coluna = 0; coluna < altura; coluna++) {
                    pixel = matrizI.getRaster().getPixel(linha, coluna, new int[3]);

                    valores[pixel[0]] = valores[pixel[0]] + 1;
                }
            }

            dadosGrafico.getData().clear();
            for (int x = 1; x < 256; x++) {
                //   System.out.println(chamadas[x]);

                dadosGrafico.getData().add(new XYChart.Data(x, valores[x]));
                                    System.out.println(x + "----" + valores[x] );

            }
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setTickUnit(1);
            yAxis.setTickUnit(1);

            xAxis.setLabel("Valor do Pixel");
            yAxis.setLabel("Ocorrências");
            final LineChart<Number, Number> grafico;
//        final LineChart<Number,Number> lineChartIat;
//        final LineChart<Number,Number> lineChartDur;    
            grafico = new LineChart<>(xAxis, yAxis);
//        yAxis.setLabel("Intervalo Entre Chamadas");
            grafico.getData().add(dadosGrafico);
            grafico.getXAxis().autosize();
            grafico.getYAxis().autosize();
            grafico.setCreateSymbols(false);

            graficoA.getChildren().clear();
            graficoA.getChildren().add(grafico);
            graficoA.getChildren().get(0).autosize();

            System.out.println(grafico.getXAxis().isVisible());
        } catch (IOException ex) {
            Logger.getLogger(testeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculaVetorBarras() {

        XYChart.Series reta2 = new XYChart.Series();


		
        ImagePlus imp = IJ.openImage("RES_LEHR_F_1_F001_DS.dcm");
        String studyDescription = DicomTools.getTag(imp, "0008,1030"); 
        String imagePosition = DicomTools.getTag(imp, "0020,0032"); 
        String pixelSpacing = DicomTools.getTag(imp, "0028,0030");
        String CTnumbers = DicomTools.getTag(imp, "7FE0,0010");
        String slope = DicomTools.getTag(imp, "0028,1053");
        String intercept=DicomTools.getTag(imp, "0028,1052");

        int largura = imp.getWidth();
        int altura = imp.getHeight();
 

System.out.println("Study Description: "+ studyDescription); 
System.out.println("Image Position: "+imagePosition); 
System.out.println("Pixel Spacing: "+ pixelSpacing); 

     int us= imp.getBytesPerPixel();
     Calibration u= imp.getCalibration();
     int [] t;
     double aux;
        try {
            matrizI = ImageIO.read(new File("test.jpg"));

            
                        dadosGrafico.getData().clear();
                                        reta2.getData().clear();
                                        reta2.setName("Reta Quadrante 2");
                                        dadosGrafico.setName("Reta Quadrante 3");

int cont = 0;
int cont2 = 0;

           int linha = largura/4;
                for (int coluna = altura/2; coluna < altura; coluna++) {
                    //pixel = matrizI.getRaster().getPixel(linha, coluna, new int[3]);
                    //Linha 1
                     t = imp.getPixel(linha,coluna);
                     aux = u.getCValue(t[0]);
                     if(aux>0){
                           
                   matrizI.setRGB(linha, coluna, Color.RED.getRGB());
                  //  valores[pixel[0]] = valores[pixel[0]] + 1;
                 dadosGrafico.getData().add(new XYChart.Data(cont,aux));
                 cont++;
                     }
                    //Linha 2
                     t = imp.getPixel(coluna,linha);
                     aux = u.getCValue(t[0]);
                     if(aux>0){
                           
                   matrizI.setRGB(coluna,linha, Color.BLUE.getRGB());
                  //  valores[pixel[0]] = valores[pixel[0]] + 1;
                 reta2.getData().add(new XYChart.Data(cont,aux));
                 cont2++;
                     }
            }
File output = new File("imagemEditada.png");
              ImageIO.write(matrizI, "png", output);
//            for (int x = 1; x <altura; x++) {
//                //   System.out.println(chamadas[x]);
//
//                dadosGrafico.getData().add(new XYChart.Data(x, valores[x]));
//                                    System.out.println(x + "----" + valores[x] );
//
//            }
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setTickUnit(1);
            yAxis.setTickUnit(1);

            xAxis.setLabel("Posição");
            yAxis.setLabel("Valor do Pixel");
            final LineChart<Number, Number> grafico;
            //final LineChart<Number,Number> lineChartIat;
            //final LineChart<Number,Number> lineChartDur;    
            grafico = new LineChart<>(xAxis, yAxis);
//yAxis.setLabel("Intervalo Entre Chamadas");
            grafico.getData().add(dadosGrafico);
                        grafico.getData().add(reta2);

            grafico.getXAxis().autosize();
            grafico.getYAxis().autosize();
            grafico.setCreateSymbols(false);

            graficoA.getChildren().clear();
            graficoA.getChildren().add(grafico);
            graficoA.getChildren().get(0).autosize();

            System.out.println(grafico.getXAxis().isVisible());
        } catch (IOException ex) {
            Logger.getLogger(testeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void calculaVetorMedia() {

        int[] pixel;
        int largura = img.getHeight();
        int altura = img.getHeight();
        int valores[] = new int[largura+altura];
        ImagePlus imp = IJ.openImage("SENS_LEHR_F_F001_DS.dcm");
        Calibration u= imp.getCalibration();

        try {
            matrizI = ImageIO.read(new File("test.jpg"));
            sensibilidade teste = new sensibilidade(matrizI, largura, altura);
            teste.calcula(imp, u);
            //radiacaoFundo teste = new radiacaoFundo(matrizI, largura, altura);
            uniformidade aux31 = new uniformidade(matrizI, largura, altura);
            for (int cont : valores) {
                valores[cont] = 0;
            }
            int soma=0;
            for (int linha = 0; linha < largura; linha++) {
                for (int coluna = 0; coluna < altura; coluna++) {
                    pixel = matrizI.getRaster().getPixel(linha, coluna, new int[3]);
                    if(pixel[0]>=teste.getMedia()){
                    matrizI.setRGB(linha, coluna, Color.RED.getRed());
                    valores[pixel[0]] = valores[pixel[0]] + 1;
                    soma=soma+pixel[0];
                    }
                }
            }

            dadosGrafico.getData().clear();

            for (int x = 1; x < 256; x++) {
                //   System.out.println(chamadas[x]);

                dadosGrafico.getData().add(new XYChart.Data(x, valores[x]));
                                    System.out.println(x + "----" + valores[x] );

            }
            File output = new File("imagemEditada.png");
              ImageIO.write(matrizI, "png", output);
            System.out.println("Valor da area colorida="+soma);
            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setTickUnit(1);
            yAxis.setTickUnit(1);

            xAxis.setLabel("Valor do Pixel");
            yAxis.setLabel("Ocorrências");
            final LineChart<Number, Number> grafico;
//        final LineChart<Number,Number> lineChartIat;
//        final LineChart<Number,Number> lineChartDur;    
            grafico = new LineChart<>(xAxis, yAxis);
//        yAxis.setLabel("Intervalo Entre Chamadas");
            grafico.getData().add(dadosGrafico);
            grafico.getXAxis().autosize();
            grafico.getYAxis().autosize();
            grafico.setCreateSymbols(false);

            graficoA.getChildren().clear();
            graficoA.getChildren().add(grafico);
            graficoA.getChildren().get(0).autosize();

            System.out.println(grafico.getXAxis().isVisible());
        } catch (IOException ex) {
            Logger.getLogger(testeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


