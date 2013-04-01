package utvalimind.construct;

public class KandidaatByRegion {
	int id;
	String eesnimi;
	String perenimi;
	String erakond;
	String regioon;
	
	public KandidaatByRegion(String regioon){
		this.regioon=regioon;
	}
	
	public KandidaatByRegion(int id, String eesnimi, String perenimi) {
		super();
		this.id = id;
		this.eesnimi = eesnimi;
		this.perenimi = perenimi;
	}
}
