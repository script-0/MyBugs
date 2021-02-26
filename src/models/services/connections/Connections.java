package models.services.connections;

/**
 *
 * @author Isaac
 */

import java.sql.*;

public class Connections {
    
    private static final String POSTGRES = "jdbc:postgresql://localhost:5432/scriptx_mylogs_db";
    
    private static final String MYSQL = "jdbc:mysql://localhost:3306/scriptx_mylogs_db";
    
    private static final String USER = "scriptx_mylogs";
            
    private static final String PASSWORD = "password";
    
    private static Connection conPostgres = null;
    
    private static Connection conMysql = null;
    
    public static Connection getPostgresConnection(){
        try{
            if(conPostgres == null){
                Class.forName("org.postgresql.Driver");
                conPostgres =DriverManager.getConnection(POSTGRES,USER,PASSWORD);
            }
            System.out.println("[Connected to Database]");
            return conPostgres;
	}catch(Exception e){
            System.out.println("[Can't connect to Database]\n"+e);
        }
        return null;
   }
    
    public static Connection getMySqlConnection(){
        try{
            if(conMysql  == null){
                Class.forName("com.mysql.cj.jdbc.Driver");
                conMysql  =DriverManager.getConnection(MYSQL,USER,PASSWORD);
            }
            return conMysql ;
	}catch(Exception e){
            System.out.println("Can't connect to Database");
        }
        return null;
    }
}
