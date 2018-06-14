package kz.foodmaster.filial.business;

public class Distance {

	private int fromID;
	private int toID;
	private float value;
	
	public Distance() {
	}

	public int getFromID() {
		return fromID;
	}

	public void setFromID(int fromID) {
		this.fromID = fromID;
	}

	public int getToID() {
		return toID;
	}

	public void setToID(int toID) {
		this.toID = toID;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
