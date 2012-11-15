package cz.cvut.localtrade;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cz.cvut.localtrade.dao.UsersDAO;
import cz.cvut.localtrade.model.User;

public class LoginActivity extends BaseActivity {

	Button loginButton;
	UsersDAO userDao;
	List<User> users;
	EditText usernameText;
	EditText passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);

		userDao = new UsersDAO(this);
		userDao.open();
		// users = userDao.getAllUsers();

		usernameText = (EditText) findViewById(R.id.usernameText);
		passwordText = (EditText) findViewById(R.id.passwordText);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					loginProcess();
				}
				return false;

			}
		});

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
		inflater.inflate(R.menu.login_activity_menu, menu);
		return true;
	}

	public void doRegistration(MenuItem item) {
		Intent intent = new Intent(LoginActivity.this,
				RegistrationActivity.class);
		startActivity(intent);
	}

	private void loginProcess() {
		users = userDao.getAllUsers();
<<<<<<< HEAD
=======

		showToast("Wanna login " + usernameText.getText() + " and "
				+ passwordText.getText());
		
>>>>>>> 8629db675131dae50a23b254cb80f2b0a6260d6f
		for (int i = 0; i < users.size(); i++) {
			if (usernameText.getText().toString()
					.equals(users.get(i).getUsername())) {
				if (passwordText.getText().toString()
						.equals(users.get(i).getPassword())) {
					Intent intent = new Intent(LoginActivity.this,
							ShowMapActivity.class);
					startActivity(intent);
					return;
				}
			}
		}
		TextView areYouRegistered = (TextView) findViewById(R.id.are_you_registered);
		areYouRegistered.setVisibility(View.VISIBLE);
		showToast(R.string.wrong_username_or_password);
	}
}
