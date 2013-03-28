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

/**
 * This servlet lets people to vote we preserv their vote into our good 
 * database, it also performs control, if someone has voted already
 * The control is also implemented in DB, with unique keyword
 * @author Allar
 *
 */
public class VotingServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			  throws IOException{
		PrintWriter out=response.getWriter();
		Connection conn=null;
		
		try {
			DriverManager.registerDriver(new AppEngineDriver());
			conn=DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			String nimi =request.getParameter("nimi");
			String[] nimed=nimi.split(" ");
			String eesnimi=nimed[0];
			String perenimi=nimed[1];
			String piirkond=request.getParameter("piirkond");
			String erakond=request.getParameter("erakond");
			long voterId=(Long) null;
			voterId=Long.parseLong(request.getParameter("ID"));
			if(eesnimi=="" || perenimi=="" || piirkond=="" || erakond=="" || voterId==(Long)null){
				out.println("<html><head></head><body>You are missing prameters!Try again!!...</body></html>");
			}
			else{
				Statement stmt = null;
				PreparedStatement ps = null;
				String sql;
				String sql1;
				
				sql1="insert into Voter(Login,Isik) values(1,"+voterId+ ")";
				ps= conn.prepareStatement(sql1);
				stmt=conn.createStatement();
				
				sql="insert into Tulemused(Kandidaat,Valimine,Regioon,Voter) select Kandidaat.Id, Valimine.Id,Regioon.Id,Voter.Id from Kandidaat,Valimine,Regioon,Voter,Isik where Kandidaat.Id=(select Id from Kandidaat where Isik=(select Id from Isik where Eesnimi="+eesnimi+" and Perenimi="+perenimi+" )) and Voter.Id=(select Id from Voter where Isik="+voterId+" )  and Regioon.Id=(select Id from Regioon where Nimi="+piirkond+" ) and Valimine.Id=1 ";
				ps= conn.prepareStatement(sql);
				stmt=conn.createStatement();
				
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			out.println("AIAIAIAIAI, te olete juba hääletanud, tühistage oma hääl");
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
