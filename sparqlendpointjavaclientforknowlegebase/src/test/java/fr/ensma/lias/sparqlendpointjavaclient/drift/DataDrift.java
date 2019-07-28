package fr.ensma.lias.sparqlendpointjavaclient.drift;

public class DataDrift {
	
	private int index;
	private int number;
	private String data;
	
	public DataDrift(int index, int number, String data) {
		super();
		this.index = index;
		this.number = number;
		this.data = data;
	}
	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
