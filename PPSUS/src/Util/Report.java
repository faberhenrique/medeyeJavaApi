/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Casa
 */
public class Report {
    private static  Report instace;
    private static String result=
    "===================================================================================================================================================================================================================================================\n" +
    "																			RELATÓRIO DE TESTES UFMG PPSUS\n" +
    "===================================================================================================================================================================================================================================================\nData: #DATE#\n\n\n";        
    private boolean prepare = true;
    private Report(){
    }
    public static synchronized Report getInstance(){
        if(instace==null)
               instace = new Report();
        return instace;
    }
    public void addResult(String data){
        if(prepare)
            prepareResult();
          result = result+data+"\n";

    }
    private void prepareResult(){
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        result = result.replace("#DATE#", dateFormat.format(date));

    }
    public static void printResult(String name){
        try {
    BufferedWriter out = new BufferedWriter(new FileWriter(name+".txt"));
    out.write(result);  //Replace with the string 
                                             //you are trying to write  
    out.close();
}
catch (IOException e)
{
    System.out.println("Exception ");

}
    }
    
    
}
