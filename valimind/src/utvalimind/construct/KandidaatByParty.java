package utvalimind.construct;

public class KandidaatByParty {
	int id;
	String eesnimi;
	String perenimi;
	String erakond;
	
	public KandidaatByParty(String erakond){
		this.erakond=erakond;
	}
	public KandidaatByParty(int id, String eesnimi,String perenimi){
		this.id=id;
		this.eesnimi=eesnimi;
		this.perenimi=perenimi;
	}
	public KandidaatByParty(int id, String eesnimi,String perenimi,
			String erakond){
		this.id=id;
		this.eesnimi=eesnimi;
		this.perenimi=perenimi;
		this.erakond=erakond;
	}
}
