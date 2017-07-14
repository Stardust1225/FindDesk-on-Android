import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {

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
	
	
	 static int insert(Person student) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into person(account,password,id,email,tele,name,statue) values(?,?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, student.getAccount());
	        pstmt.setString(2, student.getPassword());
	        pstmt.setString(3, student.getId());
	        pstmt.setString(4, student.getEmail());
	        pstmt.setString(5, student.getTele());
	        pstmt.setString(6, student.getName());
	        pstmt.setString(7, "0");
	       
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	 
	static int changeIdStatue(String id,String account) {
		    Connection conn = getConn();
		    int i = 0;
		    String sql = "update person set id='" + id + "' where account='" + account + "'";
		    String sql1 = "update person set statue='" + "1" + "' where account='" + account + "'";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        i = pstmt.executeUpdate();
		        
		        pstmt = (PreparedStatement) conn.prepareStatement(sql1);
		        i = pstmt.executeUpdate();
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return i;
		}
	
	
	static String found(String account) {
	    Connection conn = getConn();
	    String sql = "select * from person where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("statue")+rs.getString("password");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static String changefound(String account) {
	    Connection conn = getConn();
	    String sql = "select * from person where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("id")+"&"+rs.getString("password");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return s;
	}
	
	static void delete(String account) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from person where account='" + account + "'";
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
