package utvalimind.servlet;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/** This servlet interacts with view LiveTulemused in DB and
 * returns JSON objects 
 * it uses doGet method
 * LiveTulemused(KandidaadiNr,Nimi,Perenimi,Partei, Regioon,Valimised)
 * @author Allar
 *
 */
public class StatisticServlet {
	Gson gson=new Gson();
	Connection conn=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException{
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
		DriverManager.registerDriver(new AppEngineDriver());
		conn=DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
		stmt=conn.createStatement();
		rs=stmt.executeQuery("select * from LiveTulemused");
		Collection collection= new ArrayList();
		collection.add(rs.getObject(1));
		collection.add(rs.getObject(2));
		collection.add(rs.getObject(3));
		collection.add(rs.getObject(4));
		collection.add(rs.getObject(5));
		collection.add(rs.getObject(6));
		gson.toJson(collection);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		}
		catch(SQLException e){
			e.printStackTrace();
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
