/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaClasses;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author felix
 */
public class SQLConnection {  
    static Connection conn = null;
    public static Connection GetConnection(){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=SBDProject2";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, "SBDUser", "felix.sbd2019");
            if(conn.isValid(1000)){
                System.out.println("conexión exitosa");
            }
        }catch(SQLException ex){
            System.out.println("can't connect" + ex.getMessage());
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
        }
         
        
        return conn;
    }
}
