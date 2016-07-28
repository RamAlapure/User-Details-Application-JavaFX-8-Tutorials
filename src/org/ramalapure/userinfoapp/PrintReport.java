
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ramalapure.userinfoapp;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

/**
 *
 * @author Ram
 */
public class PrintReport  extends JFrame{    
    
    Connection conn = SqlConnection.DbConnector();// Database Connection
    PreparedStatement pst = null;
    ResultSet rs = null;   

    public PrintReport() throws HeadlessException {
    }
   
    public void showReport(){
         try {
             
             JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\RAM ALAPURE\\Documents\\NetBeansProjects\\User Info App\\src\\org\\ramalapure\\userinfoapp\\newReport.jrxml");
             String query = "select * from UserDatabase order by FirstName";
             JRDesignQuery jrquery = new JRDesignQuery();
             jrquery.setText(query);
             jasperDesign.setQuery(jrquery);
             
             JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
             JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
             JRViewer viewer = new JRViewer(JasperPrint);
             
             /*JasperReport jasperReport = JasperCompileManager.compileReport("C:\\Users\\RAM ALAPURE\\Documents\\NetBeansProjects\\User Info App\\src\\org\\ramalapure\\userinfoapp\\newReport.jrxml");
             JasperPrint JasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
             JRViewer viewer = new JRViewer(JasperPrint);*/
             viewer.setOpaque(true);
             viewer.setVisible(true);
             
             this.add(viewer);
             this.setSize(900,500); // JFrame size
             this.setVisible(true);
            
         } catch (Exception e) {
             JOptionPane.showMessageDialog(rootPane, e.getMessage());
         }
   
    }
}
