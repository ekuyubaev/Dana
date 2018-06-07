package kz.foodmaster.filial.business;

import java.io.Serializable;

public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ID;
	private String name;
	
	public Position() {
		name = null;
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
