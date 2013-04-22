package utvalimind.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

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
			PreparedStatement stmt = null;
			String statement = null;
			
			statement="insert into Kandidaat(Partei,Regioon,Isik) select Partei.Id,Regioon.Id,Isik.Id from Partei,Regioon,Isik where Partei.Id=(select Id from Partei where Nimi=?) and Regioon.Id=(select Id from Regioon where Nimi=?) and Isik.Id=(select Id from Isik where Eesnimi=? and Perenimi=?);";
			stmt=conn.prepareStatement(statement);
			stmt.setString(1, erakond);
			stmt.setString(2,piirkond);
			stmt.setString(3, nimi);
			stmt.setString(4, perenimi);
			stmt.executeUpdate();
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
		String lausend = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Gson gson=new Gson();
		ArrayList<Kandidaat> valitavad=new ArrayList<Kandidaat>();
		try{
			DriverManager.registerDriver(new AppEngineDriver());
			con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			lausend="select * from Valitavad;";
			ps=con.prepareStatement(lausend);
			rs=ps.executeQuery();
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
			throw new RuntimeException(e.getMessage(),e);
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