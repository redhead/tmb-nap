package cz.cvut.localtrade;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class BaseActivity extends Activity {
	protected void showToast(int id) {
		showToast(id, Toast.LENGTH_LONG);
	}

	protected void showToast(int id, int type) {
		showToast(getString(id), type);
	}

	protected void showToast(String str) {
		showToast(str, Toast.LENGTH_LONG);
	}

	protected void showToast(String str, int type) {
		Context ctx = getApplicationContext();
		Toast toast = Toast.makeText(ctx, str, type);
		toast.show();
	}

}
