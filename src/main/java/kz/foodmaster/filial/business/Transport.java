package kz.foodmaster.filial.business;

import java.io.Serializable;

public class Transport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String model;
    private String number;
    private int capacity;
    private boolean fridge;
    private String notes;

    public Transport() {
    	model = null;
    	number = null;
    	notes = null;
    }

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isFridge() {
		return fridge;
	}

	public void setFridge(boolean fridge) {
		this.fridge = fridge;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
