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

/* This is servlet for finding our candidates by name fields
 *  it uses surename and givenname
 */
@SuppressWarnings("serial")
public class FindByName extends HttpServlet {
	private String givenName;
	private String sureName;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
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
			Statement stmt = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			try{
				DriverManager.registerDriver(new AppEngineDriver());
				con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
				stmt=con.createStatement();
				rs=stmt.executeQuery("select Kandidaat.Id, Regioon.Nimi,Partei.Nimi from Kandidaat, Regioon,Partei where Kandidaat.Isik=(select Id from Isik where Eesnimi="+givenName+"and Perenimi="+sureName+") and Kandidaat.Regioon=Regioon.Id and Kandidaat.Partei=Partei.Id");
				Collection collection= new ArrayList();
				collection.add(rs.getObject(1));
				collection.add(rs.getObject(2));
				collection.add(rs.getObject(3));
				collection.add(sureName);
				collection.add(givenName);
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
