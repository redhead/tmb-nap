package cz.cvut.localtrade;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class ShowMapActivity extends MapActivity {

	MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_map_activity_layout);
		mapView = (MapView) findViewById(R.id.mapview);
		//
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onDoubleTap(MotionEvent e) {
		int x = (int) e.getX(), y = (int) e.getY();
		Projection p = mapView.getProjection();
		mapView.getController().animateTo(p.fromPixels(x, y));
		mapView.getController().zoomIn();
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.show_map_menu, menu);
		return true;
	}

	public void iconClick(MenuItem item) {
		Toast.makeText(getApplicationContext(), "List icon clicked",
				Toast.LENGTH_LONG).show();
	}

	public void myItemsClick(MenuItem item) {
		// Toast.makeText(getApplicationContext(), "My items clicked",
		// Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ShowMapActivity.this, MyItemsActivity.class);
		startActivity(intent);
	}

	public void searchedItemsClick(MenuItem item) {
		// Toast.makeText(getApplicationContext(), "Searched items clicked",
		// Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ShowMapActivity.this,
				SearchedItemsActivity.class);
		startActivity(intent);
	}

}
