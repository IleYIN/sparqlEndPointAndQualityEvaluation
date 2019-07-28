package fr.ensma.lias.sparqlendpointjavaclient.completudeprecision.precisiontime;

public class OpeningHours {
	private int number;
	private String time;
	private int distance;
	
	public OpeningHours(int number, String time, int distance){
		super();
		this.number = number;
		this.time = time;
		this.setDistance(distance);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
}
