package kz.foodmaster.filial.business;

import java.io.Serializable;

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	private int categoryID;
	private String categoryName;
	private String categoryNote;
	
	public Category() {
		categoryName = null;
		categoryNote = null;
	}
	
	public Category(int ID, String name, String note) {
		categoryID = ID;
		categoryName = name;
		categoryNote = note;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryNote() {
		return categoryNote;
	}

	public void setCategoryNote(String categoryNote) {
		this.categoryNote = categoryNote;
	}
}
