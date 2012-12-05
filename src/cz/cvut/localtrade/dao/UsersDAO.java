package cz.cvut.localtrade.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.database.SQLException;
import cz.cvut.localtrade.model.User;

public class UsersDAO extends DAO {
	
	static final String REGISTER_URL = "/users/register";
	static final String AUTHENTICATE_URL = "/users/authenticate";

	public UsersDAO(String baseUrl) {
		super(baseUrl);
	}
	
	public void open() throws SQLException {
	}

	public void close() {
	}

	public void createUser(RegistrationResponse resp, String username, String firstname, String lastname,
			String email, String password) {
		
		User user = new User(firstname, lastname, email, username, password);
		send(new RegistrationAsyncTask(resp), REGISTER_URL, user.toParams());
	}

	public void authenticateUser(AuthenticateResponse resp, String username, String password) {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("user[username]", username));
		pairs.add(new BasicNameValuePair("user[password]", password));
		send(new AuthenticateAsyncTask(resp), AUTHENTICATE_URL, pairs);
	}

	private User cursorToUser(Cursor cursor) {
		User user = new User();
		user.setId(cursor.getLong(0));
		user.setUsername(cursor.getString(1));
		user.setFirstName(cursor.getString(2));
		user.setLastName(cursor.getString(3));
		user.setEmail(cursor.getString(4));
		user.setPassword(cursor.getString(5));
		return user;
	}
	
	public interface RegistrationResponse {
		public void onResponse(boolean registered);
	}
	
	class RegistrationAsyncTask extends SendAsyncTask {
		
		private RegistrationResponse response;
		
		public RegistrationAsyncTask(RegistrationResponse response) {
			this.response = response;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				boolean authenticated = result.getString("status").equals("OK");
				response.onResponse(authenticated);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	public interface AuthenticateResponse {
		public void onResponse(boolean authenticated);
	}
	
	class AuthenticateAsyncTask extends SendAsyncTask {
		
		private AuthenticateResponse response;
		
		public AuthenticateAsyncTask(AuthenticateResponse response) {
			this.response = response;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if(result.getString("status").equals("OK")) {
					response.onResponse(true);
					return;
				}
			} catch (JSONException e) {
			}
			response.onResponse(false);
		}
	}

}
