package cz.cvut.localtrade.model;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	String text;
	Boolean left;

	public Message(String text, Boolean left) {
		super();
		this.text = text;
		this.left = left;
	}

	public Message() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeft() {
		return left;
	}

	public void setLeft(Boolean left) {
		this.left = left;
	}

	@Override
	public String toString() {
		return ("Text: " + getText());
	}
}
