/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufmgimagens;

import Util.Math.FWHM;
import Util.PpsusImage;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import testes.uniformidade;

/**
 *
 * @author Casa
 */
public class inicio extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
      
        PpsusImage aux = new PpsusImage("UNIF_LEHR_E001_DS.dcm");
        uniformidade uni = new uniformidade(aux);
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
