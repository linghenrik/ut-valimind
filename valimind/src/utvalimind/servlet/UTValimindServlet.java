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

@SuppressWarnings("serial")
public class UTValimindServlet extends HttpServlet{
/**This servlet does insert into evalmised db into table Kandidaat
	and also returns full list of Candidates
	**/
	@Override
public void doPost(HttpServletRequest request, HttpServletResponse response)
		  throws IOException{
	PrintWriter out=response.getWriter();
	Connection conn=null;
	
	try {
		DriverManager.registerDriver(new AppEngineDriver());
		conn=DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
		String nimi=request.getParameter("nimi");
		String perenimi=request.getParameter("perenimi"); //Insert those two into Isik
		String dob=request.getParameter("Bday");
		String piirkond=request.getParameter("piirkond");
		String erakond=request.getParameter("erakond");
		if(nimi=="" || perenimi=="" || dob=="" || piirkond=="" || erakond==""){
			out.println("<html><head></head><body>You are missing prameters!Try again!!...</body></html>");
		}
		else{
			Statement stmt = null;
			PreparedStatement ps = null;
			String sql;
			sql="insert into Kandidaat(Partei,Regioon,Isik) select Partei.Id,Regioon.Id,Isik.Id from Partei,Regioon,Isik where Partei.Id=(select Id from Partei where Nimi="+erakond+") and Regioon.Id=(select Id from Regioon where Nimi="+piirkond+") and Isik.Id=(select Id from Isik where Eesnimi="+nimi+" and Perenimi="+perenimi+")";
			ps= conn.prepareStatement(sql);
			stmt=conn.createStatement();
		}
	}
	catch (SQLException e) {
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
	//This method selects human-readble list of candidates for view Valitavad
	//Valitavad(KandidaadiNr,Eesnimi,Perenimi,DOB,Partei,Piirkond)
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		res.setContentType("text/html");
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Gson gson=new Gson();
		ArrayList<Kandidaat> valitavad=new ArrayList<Kandidaat>();
		try{
			DriverManager.registerDriver(new AppEngineDriver());
			con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from Valitavad");
			while(rs.next()){
				valitavad.add(new Kandidaat(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6)) );
			}
			String result=gson.toJson(valitavad);
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