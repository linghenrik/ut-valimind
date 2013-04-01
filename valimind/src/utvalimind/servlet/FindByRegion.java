package utvalimind.servlet;
import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utvalimind.construct.KandidaatByRegion;

@SuppressWarnings("serial")
public class FindByRegion extends HttpServlet {
	
	//private List<T> list=new ArrayList<T>();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		
			String regionName;
			Gson gson=new Gson();
			regionName=req.getParameter("regioon");
			boolean proceed=false;
			if(regionName != null){
				if(regionName.length()>0){
					proceed=true;
				}
			}
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
			PreparedStatement ps = null;
			ArrayList<KandidaatByRegion> byRegion=new ArrayList<KandidaatByRegion>();
			try{
				DriverManager.registerDriver(new AppEngineDriver());
				con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
				stmt=con.createStatement();
				rs=stmt.executeQuery("select Kandidaat.Id, Eesnimi, Perenimi from Kandidaat, Isik where Kandidaat.Regioon=(select Id from Regioon where Nimi='"+regionName+ "') and Kandidaat.Isik=Isik.Id");
				byRegion.add(new KandidaatByRegion(regionName));
				while(rs.next()){
					byRegion.add(new KandidaatByRegion(rs.getInt(1),rs.getString(2),rs.getString(3)));
				}
				String result=gson.toJson(byRegion);
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
