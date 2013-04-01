package utvalimind.servlet;
import com.google.appengine.api.rdbms.AppEngineDriver;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*This servlet tries to create a new person, its called out when someone
 *	is logged in, then the someone needs to enter his/her personal information 
 * to be able to vote(Id, Name, Surname, DOB)
 */
	
	
@SuppressWarnings("serial")
public class CreateNewIsikServlet extends HttpServlet {
	
	
	@Override
	public void doPost (HttpServletRequest req, HttpServletResponse resp)
			  throws IOException{
		 long Id;
		 String nimi;
		 String perenimi;
		 DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
		 Date DOB;
		  Connection connection = null;
		  PrintWriter out=resp.getWriter();
		    try {
		      DriverManager.registerDriver(new AppEngineDriver());
		      connection = DriverManager.getConnection("jdbc:google:rdbms://valmindbyut:valimindbyut/evalimised");
		      Id=Long.parseLong(req.getParameter("ID")); 
		      nimi=req.getParameter("nimi");
		      perenimi=req.getParameter("perenimi"); //Insert those two into Isik
		      DOB=(Date) df.parse(req.getParameter("dob"));
		      
				if(nimi=="" || perenimi=="" || DOB==null ){
					out.println("<html><head></head><body>You are missing prameters!Try again!!...</body></html>");
				}
				else{
					Statement stmt = null;
					PreparedStatement ps = null;
					String sql;
					sql="insert into Isik(Id,Eesnimi,Perenimi,DOB) values("+Id+","+nimi+","+perenimi+","+DOB+")";
					ps= connection.prepareStatement(sql);
					stmt=connection.createStatement();
				}
	}
		    catch(SQLException e){
		    	out.println("<html><head></head><body>Ilmenes viga, see on nüüd küll piinlik.</body></html>");
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    finally{
				if(connection != null){
					try{
						connection.close();
					}
					catch(SQLException ignore){
						
					}
				}
			}
}
	
}