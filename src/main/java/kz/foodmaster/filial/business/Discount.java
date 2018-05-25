package kz.foodmaster.filial.business;

import java.io.Serializable;
import java.sql.Date;


public class Discount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int discountID;
    private String discountName;
    private float discountAmount;
    private Date discountStart;
    private Date discountEnd;
	
    public Discount() {
	}

	public int getDiscountID() {
		return discountID;
	}

	public void setDiscountID(int discountID) {
		this.discountID = discountID;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Date getDiscountStart() {
		return discountStart;
	}

	public void setDiscountStart(Date discountStart) {
		this.discountStart = discountStart;
	}

	public Date getDiscountEnd() {
		return discountEnd;
	}

	public void setDiscountEnd(Date discountEnd) {
		this.discountEnd = discountEnd;
	}
}

