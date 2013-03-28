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

@SuppressWarnings("serial")
public class FindByRegion extends HttpServlet {
	private String regionName;
	//private List<T> list=new ArrayList<T>();
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
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
			try{
				DriverManager.registerDriver(new AppEngineDriver());
				con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
				stmt=con.createStatement();
				rs=stmt.executeQuery("select Kandidaat.Id, Eesnimi, Perenimi from Kandidaat, Isik where Kandidaat.Regioon=(select Id from Regioon where Nimi='"+regionName+ "') and Kandidaat.Isik=Isik.Id");
				Collection collection= new ArrayList();
				collection.add(rs.getObject(1));
				collection.add(rs.getObject(2));
				collection.add(rs.getObject(3));
				collection.add(regionName);
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
