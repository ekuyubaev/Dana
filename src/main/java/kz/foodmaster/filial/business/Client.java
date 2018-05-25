package kz.foodmaster.filial.business;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int clientId;
    private String clientName;
    private Date clientBirthDate;
    private String clientMail;
    private String clientPhone;
    private String clientNotes;
    
    public Client() {
    	clientName = "";
    	clientBirthDate = null;
    	clientMail = "";
    	clientPhone = "";
    	clientNotes = "";
    }

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getClientBirthDate() {
		return clientBirthDate;
	}

	public void setClientBirthDate(Date clientBirthDate) {
		this.clientBirthDate = clientBirthDate;
	}

	public String getClientMail() {
		return clientMail;
	}

	public void setClientMail(String clientMail) {
		this.clientMail = clientMail;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getClientNotes() {
		return clientNotes;
	}

	public void setClientNotes(String clientNotes) {
		this.clientNotes = clientNotes;
	}
}
