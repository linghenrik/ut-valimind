package utvalimind.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/* This is servlet implementation for finding Candidates by Party
 * It connects with our db and selects suitable candidates
 * 
 */
public class FindByParty extends HttpServlet {
	private String partyName;
	private String candidate;
	Gson gson= new Gson();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
		partyName=req.getParameter("partei").trim();
		boolean proceed= false;
		if(partyName != null){
			if(partyName.length()>0){
				proceed=true;
			}
		}
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			DriverManager.registerDriver(new AppEngineDriver());
			con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select Kandidaat.Id, Eesnimi, Perenimi from Kandidaat, Isik where Kandidaat.Partei=(select Id from Partei where Nimi='"+partyName+ "') and Kandidaat.Isik=Isik.Id" );
			Collection collection= new ArrayList();
			collection.add(rs.getObject(1));
			collection.add(rs.getObject(2));
			collection.add(rs.getObject(3));
			collection.add(partyName);
			gson.toJson(collection);
			res.setContentType("application/json");
		    res.setCharacterEncoding("UTF-8");
		}
		catch(Exception e){
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
