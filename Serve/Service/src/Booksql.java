
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Booksql {

	static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/person?useUnicode=true&characterEncoding=utf-8";
	    String username = "root";
	    String password = "123";
	    Connection conn = null;
	    try {
	        Class.forName(driver); 
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {}
	    return conn;
	}
	
	
	 static int insert(String lib,String part,String floor,String column,String seat,String id) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into book(library,part,floor,column,seat,id) values(?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, lib);
	        pstmt.setString(2, part);
	        pstmt.setString(3, floor);
	        pstmt.setString(4, column);
	        pstmt.setString(5, seat);
	        pstmt.setString(6,id);
	       
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	static String found(String lib) {
	    Connection conn = getConn();
	    String sql = "select * from book where library='"+lib+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("part")+"&"+rs.getString("floor")+"|"+rs.getString("column")+"/"+rs.getString("seat");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	
	static void delete(String id) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from book where id='" + id + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}
