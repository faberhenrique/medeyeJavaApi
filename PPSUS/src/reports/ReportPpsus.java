/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Casa
 */
public class ReportPpsus {
    public ReportPpsus(){
        GeradorDeRelatorios aux = new GeradorDeRelatorios(null);
OutputStream saida =null;
        try {
            saida = new FileOutputStream("alunos.pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPpsus.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Object> parametros = null;
aux.geraPdf("UFMG.jrxml", parametros, saida);
    }
}
