package utvalimind.construct;

public class VotesPerCandidate {
	String eesnimi;
	String perenimi;
	String erakond;
	String piirkond;
	int haaled;
	public VotesPerCandidate(String eesnimi, String perenimi, String erakond, String piirkond,
			int haaled) {
		super();
		this.eesnimi = eesnimi;
		this.perenimi = perenimi;
		this.erakond = erakond;
		this.piirkond = piirkond;
		this.haaled = haaled;
	}
}
