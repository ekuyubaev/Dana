package kz.foodmaster.filial.business;

import java.io.Serializable;
import java.sql.Timestamp;


public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int ID;
    private String title;
    private Timestamp time;
    private String text;
    private String author;
	
    public News() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}

