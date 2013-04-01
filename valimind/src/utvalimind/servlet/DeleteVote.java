package utvalimind.servlet;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet lets people to delete their vote
 * Testing with hardcoded person
 * @author Allar
 *
 */
@SuppressWarnings("serial")
public class DeleteVote extends HttpServlet {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws IOException{
		PrintWriter out=response.getWriter();
		Connection conn=null;
		
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			conn=DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
				Statement stmt = null;
				PreparedStatement ps = null;
				String sql;
				
				sql="delete from Voter where Isik=38505214624";
				ps= conn.prepareStatement(sql);
				stmt=conn.createStatement();
				
				
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			out.println("Mingi viga");
		}
		finally{
			if(conn != null){
				try{
					conn.close();
				}
				catch(SQLException ignore){
					
				}
			}
		}
		
		
		
		}
	
}
