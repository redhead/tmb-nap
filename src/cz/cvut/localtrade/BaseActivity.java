package cz.cvut.localtrade;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class BaseActivity extends Activity {
	protected static final int LOADING_DIALOG = 1;
	protected ProgressDialog loadingDialog;
	
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
	
	@Override
	protected Dialog onCreateDialog(int id) {
		if(loadingDialog == null) {
			loadingDialog = new ProgressDialog(this);
		}
		return loadingDialog;
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if(id == LOADING_DIALOG) {
			loadingDialog.setTitle("Loading");
			loadingDialog.setMessage("Loading");
			loadingDialog.setCancelable(false);
		}
	}

}
