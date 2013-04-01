package utvalimind.construct;

public class Tulemused {
	 int KandidaatId;
	 String nimi;
	 String perenimi;
	 String partei;
	 String regioon;
	 String valimised;
	 
	 public Tulemused(int id, String nimi, String perenimi, String partei, String regioon, String valimised)
	 {
		this.KandidaatId=id;
		this.nimi=nimi;
		this.perenimi=perenimi;
		this.partei=partei;
		this.regioon=regioon;
		this.valimised=valimised;
	 }
}
