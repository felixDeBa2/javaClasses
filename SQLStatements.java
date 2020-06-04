/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaClasses;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author felix
 */
public class SQLStatements {
    private static Connection conn = SQLConnection.GetConnection();
    public static boolean InsertUser(User u){
        
        boolean userAdded = false;
        String query = "Insert into users (userName, emailAddress, password) values("+ u.getUserName() +","+u.getEmailAddress() +","+ u.getPassword()+")" ;
        try{
            Statement consultaInsert =  conn.createStatement();
            consultaInsert.executeUpdate(query);
            userAdded = true;
           
        }catch(Exception e){
            System.out.println("No se pudo insertar el usuario:\n" +e.getMessage());
        }
        return userAdded;
    }
    
    public static boolean LoginUser(User u){
        boolean loginStatus = false;
        String query = "SELECT * FROM users WHERE emailAddress = "+ u.getEmailAddress();
        try{
            Statement consultaSelect = conn.createStatement();
            ResultSet rs = consultaSelect.executeQuery(query);
            while(rs.next()){
               
                String selectedPassword = rs.getString("password");
                String selectedEmailAddress = rs.getString("emailAddress");
                if(u.getPassword().equals(selectedPassword) && u.getEmailAddress().equals(selectedEmailAddress)){
                   loginStatus = true;
                }
            }
        }catch(SQLException ex){
            System.out.println("No se pudo compa :( " + ex.getMessage());
        }catch(Exception e){
            System.out.println("No se pudo compa :( " + e.getMessage());
        }
        return loginStatus;
    }
    
    public static boolean DeleteUser(String emailAdd){
        boolean usuarioEliminado = false;
        String query = "DELETE FROM users WHERE emailAddress = "+emailAdd;
        try{
            Statement consultaDelete = conn.createStatement();
            consultaDelete.executeUpdate(query);
            usuarioEliminado = true;
           
        }catch(Exception e){
            System.out.println("No se pudo eliminar el usuario:\n" +e.getMessage());
        }
        return usuarioEliminado;
    }
    
    public static User SelectUser(User u){
        User loggedUser = new User();
        String query = "SELECT userName, password, emailAddress, description FROM users WHERE emailAddress = "+u.getEmailAddress();
        try{
            Statement consultaSelect = conn.createStatement();
            ResultSet rs = consultaSelect.executeQuery(query);
            while(rs.next()){
                String selectedUserName = rs.getString("userName");
                String selectedPassword = rs.getString("password");
                String selectedEmailAddress = rs.getString("emailAddress");
                String selectedDescription = rs.getString("description");
                loggedUser.setUserName(selectedUserName);
                loggedUser.setEmailAddress(selectedEmailAddress);
                loggedUser.setPassword(selectedPassword);
                loggedUser.setDescription(selectedDescription);
            }
        }catch(SQLException ex){
            System.out.println("No se pudo compa :( " + ex.getMessage());
        }catch(Exception e){
            System.out.println("No se pudo compa :( " + e.getMessage());
        }
        return loggedUser;
    }
    
    public static boolean UpdateUser(User u){
        boolean updated = false;
        String query = "UPDATE users SET password = " + u.getPassword() +", description="+u.getDescription()+" WHERE emailAddress="+ u.getEmailAddress();
        try{
            PreparedStatement consultaUpdate = conn.prepareStatement(query);
            consultaUpdate.executeUpdate();
            updated = true;
        }catch(SQLException ex){
            System.out.println("No se pudo compa :( " + ex.getMessage());
        }catch(Exception e){
            System.out.println("No se pudo compa :( " + e.getMessage());
        }
        return updated;
    }
}
