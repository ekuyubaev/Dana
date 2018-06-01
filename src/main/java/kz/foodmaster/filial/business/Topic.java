package kz.foodmaster.filial.business;

import java.io.Serializable;
import java.util.List;

public class Topic  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int ID;
	private String name;
	private List<Message> messages;
	
	public Topic() {
		// TODO Auto-generated constructor stub
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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
