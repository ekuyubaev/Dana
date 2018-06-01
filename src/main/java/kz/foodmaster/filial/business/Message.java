package kz.foodmaster.filial.business;

import java.io.Serializable;
import java.sql.Date;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private int ID;
	private String userLogin;
	private Date datetime;
	private String text;
	
	public Message() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
