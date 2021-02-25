package models.services;

import models.services.connections.Connections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Date;
/**
 *
 * @author Isaac
 */
public class BugServices {
    
    public int save(String label, String solution ,Boolean isResolved) throws SQLException{
        Connection con = Connections.getPostgresConnection();
        try{
            Statement stmt = con.createStatement();
            String insertSql = "INSERT INTO bug(label, solution, resolved,creationdate,lastupdatedate)"
              + " VALUES(?, ?, ?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1,label);
            pstmt.setString(2, solution);
            pstmt.setBoolean(3,isResolved);
            pstmt.setDate(4,new Date(System.currentTimeMillis()));
            pstmt.setDate(5,new Date(System.currentTimeMillis()));
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("[PostgreSql : INSERT -> bug]: "+rowsAffected+" rows affected");
            return rowsAffected;
	}catch(SQLException e){
            throw e;
        }
    }
}
