package utvalimind.construct;

import java.sql.Date;

public class Kandidaat {
	
	 int id;
	 String firstName;
	 String lastName;
	 String party;
	 String area;
	 Date dob;
	 
	public Kandidaat(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Kandidaat(int id,String eesnimi, String perenimi,String party,  String area)
	{	
		this.firstName = eesnimi;
		this.lastName = perenimi;
		this.id = id;
		this.area = area;
		this.party = party;
	}
	public Kandidaat(int id,String eesnimi, String perenimi,
			Date dob,String party,  String area)
	{	
		this.firstName = eesnimi;
		this.lastName = perenimi;
		this.id = id;
		this.area = area;
		this.party = party;
		this.dob=dob;
	}
}
