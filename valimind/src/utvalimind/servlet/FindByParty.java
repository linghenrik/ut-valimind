package utvalimind.servlet;

import com.google.appengine.api.rdbms.AppEngineDriver;

import java.io.IOException;
import java.io.PrintWriter;
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

import utvalimind.construct.KandidaatByParty;

import com.google.gson.Gson;

/* This is servlet implementation for finding Candidates by Party
 * It connects with our db and selects suitable candidates
 * 
 */
@SuppressWarnings("serial")
public class FindByParty extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		String partyName;
		Gson gson=new Gson();
		partyName=req.getParameter("partei");
		boolean proceed= false;
		if(partyName != null){
			if(partyName.length()>0){
				proceed=true;
			}
		}
		Connection con = null;
		String statement=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<KandidaatByParty> someList=new ArrayList<KandidaatByParty>();
		try{
			DriverManager.registerDriver(new AppEngineDriver());
			con = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
			statement="select Kandidaat.Id, Eesnimi, Perenimi from Kandidaat, Isik where Kandidaat.Partei=(select Id from Partei where Nimi=?) and Kandidaat.Isik=Isik.Id;";
			stmt=con.prepareStatement(statement);
			stmt.setString(1, partyName);
			rs=stmt.executeQuery();
			while(rs.next()){
				someList.add(new KandidaatByParty(rs.getInt(1),rs.getString(2),rs.getString(3),partyName));
			}
			String result=gson.toJson(someList);
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
