
package kh.com.kshrd.dbutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    ConfigureIO bConfig = new ConfigureIO();
    Connection cnn;
       
       public Connection getConnection()
       {
           bConfig.readConfigure(); 
           String conString = "jdbc:postgresql://localhost:"+bConfig.getConfigure().getPort_number()+"/"+bConfig.getConfigure().getDatabase_name();
           String userName = bConfig.getConfigure().getUsername();
           String Password = bConfig.getConfigure().getPassword();
           try {
               Class.forName("org.postgresql.Driver");
               cnn = DriverManager.getConnection(conString,userName,Password);
           } 
           catch (ClassNotFoundException ex) {
               Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
           } 
           catch (SQLException ex) {
               Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
           }
           return cnn;
       } 
}
