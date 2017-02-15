/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufmgimagens;

import Util.PpsusImage;
import Util.Report;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import reports.ReportPpsus;
import tests.Background;
import tests.FloodSpeed;
import tests.Resolution;
import tests.ScaleSpeed;
import tests.Sensibility;
import tests.Uniformity;

/**
 *
 * @author Casa
 */
public class inicio extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException, Exception {
      
        PpsusImage aux = new PpsusImage("UNIF_LEHR_E001_DS.dcm");
        Uniformity uni = new Uniformity(aux);
        //aux.toPng("uniformidadeTeste");
       // Teste de velocidade regua        
        aux = new PpsusImage("VEL_REGUA_E001_DS.dcm");
        ScaleSpeed spe = new ScaleSpeed(aux, "0,487;0,488;0,491;0,491;0,489;0,488;0,494;0,492;0,486;0,493", 0);
        aux = new PpsusImage("Bg_E001_DS.dcm");
        Background bg = new Background(aux);
        aux = new PpsusImage("SENS_LEHR_F_F001_DS.dcm");
        Sensibility sen = new Sensibility(aux, 0.99);
        aux.toPng("SensibilidadeOriginal");
        //Teste de velocidade Flood
        //aux = new PpsusImage("VEL_FLOOD_E001_DS.dcm");
        //FloodSpeed flo = new FloodSpeed(aux);
       aux = new PpsusImage("RES_LINF2_F001_DS.dcm");
     //  aux.toPng("resolucao.png");
        Resolution reso = new Resolution(aux);
        Report.printResult(aux.getInfo("103E")+"_RESULT");
      //  ReportPpsus x= new ReportPpsus();
        //aux.SumImages("RES_LINF2_F001_DS.dcm");
       // aux.SumImage();
       // int inicio = (int) (aux.getWidth()*0.125);
       // int fim = (int) (aux.getWidth()*0.375);
       // FWHM estatistica = new FWHM(aux.profileHori(inicio,inicio,fim,fim));
       // System.out.println(estatistica.calcFWHM());

    Platform.exit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
