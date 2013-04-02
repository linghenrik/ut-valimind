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

import utvalimind.construct.Kandidaat;
import utvalimind.construct.KandidaatByParty;

/* This is servlet for finding our candidates by name fields
 *  it uses surename and givenname
 */
@SuppressWarnings("serial")
public class FindByName extends HttpServlet {
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
			
			String givenName;
			String sureName;
			Gson gson=new Gson();
			givenName=req.getParameter("eesnimi");
			sureName=req.getParameter("perenimi");
			boolean proceed=false;
			if(sureName != null || givenName!= null){
				if(givenName.length()>=1 && sureName.length()>=1){
					proceed=true;
				}
			}
			Connection con = null;
			String statement;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			ArrayList<Kandidaat> byName=new ArrayList<Kandidaat>();
			try{
				DriverManager.registerDriver(new AppEngineDriver());
				con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
				
				statement="select Kandidaat.Id, Regioon.Nimi,Partei.Nimi from Kandidaat, Regioon,Partei where Kandidaat.Isik=(select Id from Isik where Eesnimi=? and Perenimi=?) and Kandidaat.Regioon=Regioon.Id and Kandidaat.Partei=Partei.Id;";
				stmt=con.prepareStatement(statement);
				stmt.setString(1, givenName);
				stmt.setString(2, sureName);
				rs=stmt.executeQuery();
				while(rs.next()){
					byName.add(new Kandidaat(rs.getInt(1),givenName,sureName,rs.getString(3),rs.getString(2)));
				}
				String result=gson.toJson(byName);
				res.setContentType("application/json");
			    res.setCharacterEncoding("UTF-8");
			    PrintWriter out=res.getWriter();
			    out.print(result);
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
