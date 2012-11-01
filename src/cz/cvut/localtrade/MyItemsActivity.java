package cz.cvut.localtrade;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class MyItemsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_items_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

}
