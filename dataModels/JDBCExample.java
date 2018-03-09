package dataModels;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCExample {
	public static Connection connection()
	{
		Connection connect=null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connect=DriverManager.getConnection("jdbc:mysql://www.papademas.net/510labs?"+"user=db510&password=510");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return connect;
	}		}