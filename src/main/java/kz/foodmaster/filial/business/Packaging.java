package kz.foodmaster.filial.business;

public class Packaging {
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String name;
	
	public Packaging() {

	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

