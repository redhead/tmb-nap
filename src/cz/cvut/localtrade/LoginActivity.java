package cz.cvut.localtrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(LoginActivity.this,
							ShowMapActivity.class);
					startActivity(intent);
				}
				return false;
			}
		});
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

}
