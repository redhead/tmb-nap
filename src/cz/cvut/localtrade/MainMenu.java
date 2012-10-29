package cz.cvut.localtrade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu_activity_layout);

		Button showMapButton = (Button) findViewById(R.id.showMapButton);
		Button buyEverythingButton = (Button) findViewById(R.id.buyEverythingButton);
		Button sellButton = (Button) findViewById(R.id.sellButton);

		showMapButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(MainMenu.this,
							ShowMapActivity.class);
					startActivity(intent);
				}
				return false;
			}
		});

		buyEverythingButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(MainMenu.this, BuyActivity.class);
					startActivity(intent);
				}
				return false;
			}
		});

		sellButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(MainMenu.this,
							TestActivity.class);
					startActivity(intent);
				}
				return false;
			}
		});
	}
}
