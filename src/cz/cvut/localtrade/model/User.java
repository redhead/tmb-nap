package cz.cvut.localtrade.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private long id;
	String firstName;
	String lastName;
	String email;
	String username;
	String password;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String firstName, String lastName, String email,
			String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return username;
	}
	
	public List<NameValuePair> toParams() {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user[username]", username));
		pairs.add(new BasicNameValuePair("user[firstname]", firstName));
		pairs.add(new BasicNameValuePair("user[email]", email));
		pairs.add(new BasicNameValuePair("user[password]", password));
		pairs.add(new BasicNameValuePair("user[firstname]", firstName));
		return pairs;
	}

}
