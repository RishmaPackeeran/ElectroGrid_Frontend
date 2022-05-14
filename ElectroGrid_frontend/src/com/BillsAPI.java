 package com;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.Billing;















public class BillsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Billing billObj = new Billing(); 
       
    
    public BillsAPI() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,NumberFormatException {
		//NOTUSED
	}
	
	
	
	
	
	
	
	
	

	//Insert
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException
	{
		String output = billObj.insertBills(request.getParameter("BillCode"),
							request.getParameter("BillMonth"),
							Integer.parseInt(request.getParameter("CurrentRead")),
							Integer.parseInt(request.getParameter("PreviousRead")));
		
		response.getWriter().write(output);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
	    {
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ?
						scanner.useDelimiter("\\A").next() : "";
				scanner.close();
		 
				String[] params = queryString.split("&");
				for (String param : params)
				{ 
					String[] p = param.split("=");
					map.put(p[0], p[1]);
			    }
			 }
					
			 catch (Exception e)
		     {
			 }
			 
			return map;
		}
		
		
		
		
		
		
		
		
		
		

		
		//Update
		protected void doPut(HttpServletRequest request, HttpServletResponse response)
				 throws ServletException, IOException,NumberFormatException
		{
			Map paras = getParasMap(request);
			String output = billObj.UpdateBills(paras.get("hidBillIDSave").toString(),
											   paras.get("BillCode").toString(),
											   paras.get("BillMonth").toString(),
											   paras.get("CurrentRead").toString(),
											   paras.get("PreviousRead").toString());
				
			response.getWriter().write(output);
		}
		
		
		
		
		
		
		
		
		
		

		//Delete
		protected void doDelete(HttpServletRequest request, HttpServletResponse response)
				 throws ServletException, IOException
		{
			Map paras = getParasMap(request);
			String output = billObj.deleteBills(paras.get("BillID").toString());
			response.getWriter().write(output);
		}

}
