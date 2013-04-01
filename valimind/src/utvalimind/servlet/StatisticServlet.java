package utvalimind.servlet;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utvalimind.construct.KandidaatByParty;
import utvalimind.construct.Tulemused;


/** This servlet interacts with view LiveTulemused in DB and
 * returns JSON objects 
 * it uses doGet method
 * LiveTulemused(KandidaadiNr,Nimi,Perenimi,Partei, Regioon,Valimised)
 * @author Allar
 *
 */
@SuppressWarnings("serial")
public class StatisticServlet extends HttpServlet {
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException{
		Gson gson=new Gson();
		Connection conn=null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<Tulemused> someList=new ArrayList<Tulemused>();
		try{
		DriverManager.registerDriver(new AppEngineDriver());
		conn=DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
		stmt=conn.createStatement();
		rs=stmt.executeQuery("select * from LiveTulemused");
		while(rs.next()){
			someList.add(new Tulemused(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
		}
		String result=gson.toJson(someList);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out=response.getWriter();
	    out.print(result);
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
