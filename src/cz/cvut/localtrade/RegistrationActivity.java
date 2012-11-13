package cz.cvut.localtrade;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import cz.cvut.localtrade.dao.UsersDAO;
import cz.cvut.localtrade.model.User;

public class RegistrationActivity extends BaseActivity {
	boolean answer;
	UsersDAO userDao;
	List<User> users;
	EditText username;
	EditText firstname;
	EditText lastname;
	EditText email;
	EditText password;
	EditText passwordCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_activity_layout);

		userDao = new UsersDAO(this);
		userDao.open();

		users = userDao.getAllUsers();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.registration_title));

		username = (EditText) findViewById(R.id.usernameInput);
		firstname = (EditText) findViewById(R.id.firstnameInput);
		lastname = (EditText) findViewById(R.id.lastnameInput);
		email = (EditText) findViewById(R.id.emailInput);
		password = (EditText) findViewById(R.id.passwordInput);
		passwordCheck = (EditText) findViewById(R.id.checkPasswordInput);
	}

	@Override
	protected void onResume() {
		userDao.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		userDao.close();
		super.onPause();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.registration_activity_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, LoginActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void submitForm(MenuItem item) {
		if (!isValid()) {
			return;
		}

		User user = userDao.createUser(username.getText().toString(), firstname
				.getText().toString(), lastname.getText().toString(), email
				.getText().toString(), password.getText().toString());
		users.add(user);

		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private boolean isValid() {
		return passwordCheck() && usernameCheck();
	}

	private boolean usernameCheck() {
		String usernameStr = username.getText().toString();

		if (usernameStr.isEmpty()) {
			showToast(R.string.empty_username);
			return false;
		}

		for (User user : users) {
			if (usernameStr.equals(user.getUsername())) {
				showToast(R.string.taken_username);
				return false;
			}
		}
		return true;
	}

	private boolean passwordCheck() {
		String pswd = password.getText().toString();

		if (pswd.isEmpty()) {
			showToast(R.string.empty_password);
			return false;
		}

		if (!pswd.equals(passwordCheck.getText().toString())) {
			showToast(R.string.different_password);
			return false;
		}

		return true;
	}

	public void cancelRegistration(MenuItem item) {
		// if (confirm(getApplicationContext())) {
		Intent intent = new Intent(RegistrationActivity.this,
				LoginActivity.class);
		startActivity(intent);
		// }
	}

	// private boolean confirm(Context context) {
	// // 1. Instantiate an AlertDialog.Builder with its constructor
	// AlertDialog.Builder builder = new AlertDialog.Builder(context);
	//
	// // 2. Chain together various setter methods to set the dialog
	// // characteristics
	// builder.setMessage(R.string.cancel_dialog_message).setTitle(
	// R.string.cancel_dialog_title);
	//
	// // 3. Get the AlertDialog from create()
	//
	// builder.setPositiveButton(R.string.confirm,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// answer = true;
	// }
	// });
	// builder.setNegativeButton(R.string.cancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// answer = false;
	// }
	// });
	// AlertDialog dialog = builder.create();
	// return answer;
	// }

}
