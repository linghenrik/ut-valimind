package utvalimind.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * THis should do autocomplete
 * @author Allar
 *
 */

@SuppressWarnings("serial")
public class AutoCompleteServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String perenimi=null;
		Gson gson=new Gson();
		perenimi=request.getParameter("lastName");
		boolean proceed=false;
		if(perenimi != null ){
			if(perenimi.length()>=2){
				proceed=true;
			}
		}
		Connection con = null;
		String statement;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> vasted=new ArrayList<String>();
		try{
			DriverManager.registerDriver(new AppEngineDriver());
			con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			statement="SELECT Perenimi,Eesnimi from Isik, Kandidaat where Isik.Id=Kandidaat.Id and Perenimi COLLATE UTF8_UNICODE_CI like ?%;";
			stmt=con.prepareStatement(statement);
			stmt.setString(1, perenimi);
			rs=stmt.executeQuery();
			while(rs.next()){
				vasted.add(rs.getString(1));
				vasted.add(rs.getString(2));
				
			}
			String json=gson.toJson(vasted);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    PrintWriter out=response.getWriter();
		    out.print(json);
		}
		catch(SQLException e) {
    		e.printStackTrace();
    	}
		finally{
			if(con != null){
				try{
					con.close();
				}
				catch(SQLException ignore){
					
				}
			}
		}
	}
}
