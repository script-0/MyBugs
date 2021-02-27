package models.services;

import models.services.connections.Connections;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import models.entities.BugEntity;
/**
 *
 * @author Isaac
 */
public class BugServices {
    
    public int save(String label, String solution ,Boolean isResolved){
        Connection con = Connections.getPostgresConnection();
        try{
            String insertSql = "INSERT INTO bug(label, solution, resolved,creationdate,lastupdatedate)"
              + " VALUES(?, ?, ?, ?, ?)";
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
            return -1;
        }
    }
    
    public int update (BugEntity bug){
        Connection con = Connections.getPostgresConnection();
        try{
            String insertSql = "UPDATE bug SET label=? , solution = ?, resolved= ? ,lastupdatedate=NOW() WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1,bug.getLabel());
            pstmt.setString(2, bug.getSolution());
            pstmt.setBoolean(3, bug.isResolved());
            pstmt.setLong(4,bug.getId());
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("[PostgreSql : UPDATE -> bug]: "+rowsAffected+" rows affected");
            return rowsAffected;
	}catch(SQLException e){
            return -1;
        }
    }
    
    public boolean testDBConnection(){
        return Connections.getPostgresConnection() != null;
    }
    
    public Set<BugEntity> search(String label){
        Connection con = Connections.getPostgresConnection();
        try{
            Statement stmt = con.createStatement();
            //Check label format : SQL Injection
            String insertSql = "SELECT * FROM bug WHERE UPPER(label) LIKE ? ORDER BY lastupdatedate ASC";
            PreparedStatement pstmt = con.prepareStatement(insertSql);
            pstmt.setString(1,"%"+label.toUpperCase()+"%");
            Set<BugEntity> bugs = new HashSet<>();
            pstmt.execute();
            while(pstmt.getResultSet().next()){
                bugs.add(new BugEntity(pstmt.getResultSet().getLong("id"),
                                        pstmt.getResultSet().getString("label"),
                                        pstmt.getResultSet().getString("solution"),
                                        pstmt.getResultSet().getDate("creationdate"),
                                        pstmt.getResultSet().getDate("lastupdatedate"),
                                        pstmt.getResultSet().getBoolean("resolved"))
                );
            }
            System.out.println("[PostgreSql : SELECT -> bug]: "+bugs.size()+" rows affected");
            return bugs;
	}catch(SQLException e){
           // throw e;
           System.out.println("[Error : BugServices]");
           return new HashSet<BugEntity>();
        }
    }
}
