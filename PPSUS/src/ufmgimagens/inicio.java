/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufmgimagens;

import Util.PpsusImage;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import tests.FloodSpeed;
import tests.ScaleSpeed;

/**
 *
 * @author Casa
 */
public class inicio extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException, Exception {
      
        //PpsusImage aux = new PpsusImage("UNIF_LEHR_E001_DS.dcm");
        //Uniformity uni = new Uniformity(aux);
        //Teste de velocidade regua        
        PpsusImage aux = new PpsusImage("VEL_REGUA_E001_DS.dcm");
        ScaleSpeed spe = new ScaleSpeed(aux, 0, 0);
        //Teste de velocidade Flood
        aux = new PpsusImage("VEL_FLOOD_E001_DS.dcm");
        FloodSpeed flo = new FloodSpeed(aux);
        
        //aux.SumImages("RES_LINF2_F001_DS.dcm");
       // aux.SumImage();
       // int inicio = (int) (aux.getWidth()*0.125);
       // int fim = (int) (aux.getWidth()*0.375);
       // FWHM estatistica = new FWHM(aux.profileHori(inicio,inicio,fim,fim));
       // System.out.println(estatistica.calcFWHM());
//        
//        Parent root = FXMLLoader.load(getClass().getResource("teste.fxml"));
//        
//        Scene scene = new Scene(root, 1150 , 500);
//        scene.setRoot(root);
//        Image icon = new Image(getClass().getResourceAsStream("medicina.jpg"));
//        primaryStage.getIcons().add(icon);
//
//      //  primaryStage.getIcons().add(new Image("medicina.jpg"));       
//        primaryStage.setTitle("PPSUS-Medicina Nuclear");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    Platform.exit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
