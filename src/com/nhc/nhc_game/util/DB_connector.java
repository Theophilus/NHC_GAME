package com.nhc.nhc_game.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_connector {

	public DB_connector() {
		// TODO Auto-generated constructor stub
	}

	
	 
	 public class DataController {
	 	private Connection connect = null;
	 	private Statement statement = null;
	 	private PreparedStatement preparedStatement = null;
	 	private ResultSet resultSet = null;
	 	
	 	public DataController() throws Exception{
	 		Class.forName("com.mysql.jdbc.Driver").newInstance();
	 		connect = DriverManager
	 		          .getConnection("jdbc:mysql://localhost/projtest?"
	 		              + "user=sqluser&password=sqluserpw");
	 		
	 		//getUser(new User());
	 	}
	 	
	 	public boolean writeUser(User user) throws Exception{
	 		try {
	 			preparedStatement = connect.prepareStatement(
	 					"insert into projtest.account values ()");
	 			
	 			resultSet = preparedStatement.executeQuery();
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} finally{
	 			close();
	 		}
	 		
	 		return true;
	 	}
	 	
	 	public boolean getUser(User user) throws Exception{
	 		try {
	 			statement = connect.createStatement();
	 			resultSet = statement.executeQuery("SELECT * FROM account");
	 			
	 			writeMetaData(resultSet);
	 			
	 			resultSet = preparedStatement.executeQuery();
	 		} catch (Exception e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 		return true;
	 	}
	 	
	 	private void writeMetaData(ResultSet resultSet) throws SQLException {
	 	    // now get some metadata from the database
	 	    System.out.println("The columns in the table are: ");
	 	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	 	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	 	      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	 	    }
	 	  }
	 	
	 	private void close(){
	 		close((Closeable) resultSet);
	 		close((Closeable) statement);
	 		close((Closeable) connect);
	 	}
	 	private void close(Closeable c){
	 		try{
	 			if(c != null){
	 				c.close();
	 			}
	 		} catch(Exception e){
	 			
	 		}
	 	}
	 }
}
