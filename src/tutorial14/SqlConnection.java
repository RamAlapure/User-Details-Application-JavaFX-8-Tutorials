/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ram
 */
public class SqlConnection {
    
    public static Connection DbConnector(){        
        try{  
            Connection conn = null;
            //https://bitbucket.org/xerial/sqlite-jdbc/downloads
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:UserDatabase.sqlite");
            return conn;
        }catch(ClassNotFoundException | SQLException e){
           System.out.println(e);     
                }
        return null;
    }
}
