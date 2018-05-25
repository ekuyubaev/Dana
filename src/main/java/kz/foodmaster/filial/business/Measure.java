package kz.foodmaster.filial.business;

import java.io.Serializable;

public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int measureID;
	private String measureName;
	private String measureSymbol;
	
	public Measure() {
	}
	
	public int getMeasureID() {
		return measureID;
	}
	
	public void setMeasureID(int measureID) {
		this.measureID = measureID;
	}
	
	public String getMeasureName() {
		return measureName;
	}
	
	public void setMeasureName(String measureName) {
		this.measureName = measureName;
	}
	
	public String getMeasureSymbol() {
		return measureSymbol;
	}
	
	public void setMeasureSymbol(String measureSymbol) {
		this.measureSymbol = measureSymbol;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
