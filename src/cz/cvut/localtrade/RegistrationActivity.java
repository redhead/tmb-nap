package cz.cvut.localtrade;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class RegistrationActivity extends Activity {
	boolean answer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_activity_layout);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Registration");
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

	}

	public void cancelRegistration(MenuItem item) {
		// if (confirm(getApplicationContext())) {
		Intent intent = new Intent(RegistrationActivity.this,
				LoginActivity.class);
		startActivity(intent);
		// }
	}

	private boolean confirm(Context context) {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage(R.string.cancel_dialog_message).setTitle(
				R.string.cancel_dialog_title);

		// 3. Get the AlertDialog from create()

		builder.setPositiveButton(R.string.confirm,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						answer = true;
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						answer = false;
					}
				});
		AlertDialog dialog = builder.create();
		return answer;
	}
}
