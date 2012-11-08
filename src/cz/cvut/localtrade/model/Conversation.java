package cz.cvut.localtrade.model;

import java.io.Serializable;
import java.util.List;

public class Conversation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Message> listOfMessages;
	User owner; // zakladatel konverzace
	User other; // nevim jak ho mam pojmenovat jinak

	public Conversation() {
	}

	public Conversation(List<Message> listOfMessages, User owner, User other) {
		super();
		this.listOfMessages = listOfMessages;
		this.owner = owner;
		this.other = other;
	}

	public List<Message> getListOfMessages() {
		return listOfMessages;
	}

	public void setListOfMessages(List<Message> listOfMessages) {
		this.listOfMessages = listOfMessages;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getOther() {
		return other;
	}

	public void setOther(User other) {
		this.other = other;
	}

}
