package cz.cvut.localtrade;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class BaseActivity extends Activity {

	protected void showToast(int id) {
		showToast(id, Toast.LENGTH_LONG);
	}

	protected void showToast(int id, int type) {
		Context ctx = getApplicationContext();
		Toast toast = Toast.makeText(ctx, getString(id), type);
		toast.show();
	}

}
