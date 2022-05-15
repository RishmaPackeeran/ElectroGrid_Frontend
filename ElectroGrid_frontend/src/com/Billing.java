package com;

import java.sql.*;



public class Billing {
	
	//Database Connection
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
 
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/paf", "root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	
	//Retrieve the bills
			public String readBills()
			{
				 String output = "";
				 
				 try
				 {
					 //DB connection
					 Connection con = connect();
					 
					 //Error message when connecting the DB
					 if (con == null)
					 {
						 return "Error while connecting to the database for reading."; 
					 }
					 
					 // Prepare the HTML table to be displayed
					 output = "<table border='1'><tr><th>Bill Code</th><th>Bill Month</th>" +
							 "<th>Current Reading</th>" +
							 "<th>Previous Reading</th>" +
							 "<th>Total Units</th>" +
							 "<th>Final Amount</th>"+
							 "<th>Update</th>"+
							 "<th>Remove</th>";
				
					 String query = "select * from bills";
					 
					 Statement stmt = con.createStatement();
					 
					 ResultSet rs = stmt.executeQuery(query);
					 
					 
					 // iterate through the rows in the result set
					 while (rs.next())
					 {
						 String BillID = Integer.toString(rs.getInt("BillID"));
						 String BillCode = rs.getString("BillCode");
						 String BillMonth = rs.getString("BillMonth");
						 String CurrentRead = Double.toString(rs.getDouble("CurrentRead"));
						 String PreviousRead = Double.toString(rs.getDouble("PreviousRead"));
						 String TotalUnits = Double.toString(rs.getDouble("TotalUnits"));
						 String FinalAmount = Double.toString(rs.getDouble("FinalAmount"));
						 
						 
						 // Add into the HTML table
						 output += "<tr><td>" + BillCode + "</td>";
						 output += "<td>" + BillMonth + "</td>";
						 output += "<td>" + CurrentRead + "</td>";
						 output += "<td>" + PreviousRead + "</td>";
						 output += "<td>" + TotalUnits + "</td>";
						 output += "<td>" + FinalAmount + "</td>";
						 
						 
						 
						// Update and delete buttons
						 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-billid='" + BillID + "'></td>"
								 + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='" + BillID + "'></td></tr>";
						 
						 
						
						 
					
					 }
					
					 
					 // Complete the HTML table
					 output += "</table>";
				 }
				 catch (Exception e)
				 {
					 output = "Error while reading the bill.";
					 System.err.println(e.getMessage());
				 }
			 return output;
			}
			
			
			
			//Insert the bills
			public String insertBills(String BillCode, String BillMonth, int CurrentRead, int PreviousRead)
			{
				 String output = "";
				 try
				 {
					 	//DB connection
					 	Connection con = connect();
					 	
					 	//Error message when connecting the DB
						 if (con == null)
						 {
							 return "Error while connecting to the database for inserting."; 
						 }
						 
					 
						 //Calculate the bill amount
						 double FinalAmount ;
						 int TotalUnits = CurrentRead-PreviousRead;
						 
						 
						 //Multiplying the total units from the Per unit charge and get the final amount
						 FinalAmount = 6 * TotalUnits;
					 
					 
				 
					// create a prepared statement
					 String query = " insert into bills (`BillID`,`BillCode`,`BillMonth`,`CurrentRead`,`PreviousRead`,`TotalUnits`,`FinalAmount`)"
					 + " values (?, ?, ?, ?, ?,?,?)";
					 
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, BillCode);
					 preparedStmt.setString(3, BillMonth);
					 preparedStmt.setInt(4, CurrentRead);
					 preparedStmt.setInt(5, PreviousRead);
					 preparedStmt.setInt(6, TotalUnits);
					 preparedStmt.setDouble(7, FinalAmount);
					 
					 
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 String newBills = readBills();
					output = "{\"status\":\"success\", \"data\": \"" +newBills + "\"}";
				 }
				 catch (Exception e)
				 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Bill.\"}";
					 System.err.println(e.getMessage());
				 }
				 return output;
			} 
			
			
			//Update the bills
			public String UpdateBills(String BillID,String BillCode, String BillMonth, String CurrentRead, String PreviousRead)
			{
				 String output = "";
				 
				 try
				 {
					 //DB connection
					 Connection con = connect();
					 
					//Error message when connecting the DB
					 if (con == null)
					 {
						 return "Error while connecting to the database for updating."; 
						 
					 }
					 String query = "UPDATE bills SET BillCode=?,BillMonth=?,CurrentRead=?,PreviousRead=? WHERE BillID=?";
					 
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 
					 // binding values
					 preparedStmt.setString(1, BillCode);
					 preparedStmt.setString(2, BillMonth);
					 preparedStmt.setDouble(3, Double.parseDouble(CurrentRead));
					 preparedStmt.setDouble(4, Double.parseDouble(PreviousRead));
					 preparedStmt.setInt(5, Integer.parseInt(BillID));
					 
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
						
					 String newBills = readBills();
					 output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
					 
				 }
				 
				 catch (Exception e)
				 {
					 output = "{\"status\":\"error\", \"data\": \"Error while updating the Bills.\"}";
					 System.err.println(e.getMessage());
				 }
				 return output;
			}
			
			
			//Delete the bills
			public String deleteBills(String BillID)
			{
				 String output = "";
				 
				 try
				 {
					 //DB connection
					 Connection con = connect();
					 
					 //Error message when connecting the DB
					 if (con == null)
					 {
						 return "Error while connecting to the database for deleting."; 
						 
					 }
					 
					 // create a prepared statement
					 String query = "delete from bills where BillID=?";
					 PreparedStatement preparedStmt = con.prepareStatement(query);
					 
					 // binding values
					 preparedStmt.setInt(1, Integer.parseInt(BillID));
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 String newBills = readBills();
					 output = "{\"status\":\"success\", \"data\": \"" +
							 newBills + "\"}";
				 }
				 catch (Exception e)
				 {
					 output = "{\"status\":\"error\", \"data\": \"Error while deleting the bill.\"}";
					 System.err.println(e.getMessage());
				 }
				 return output;
			} 	
			
	

}
