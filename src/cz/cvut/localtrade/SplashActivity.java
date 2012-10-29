package cz.cvut.localtrade;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	protected long splashDelay = 0;
	boolean clickOnLogo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity_layout);
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				finish();
				Intent mainIntent = new Intent().setClass(SplashActivity.this,
						LoginActivity.class);
				startActivity(mainIntent);
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, splashDelay);

	}
}
