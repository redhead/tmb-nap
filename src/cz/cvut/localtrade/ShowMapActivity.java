package cz.cvut.localtrade;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import cz.cvut.localtrade.helper.ItemListHelper;
import cz.cvut.localtrade.model.Item;

public class ShowMapActivity extends MapActivity {

	MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_map_activity_layout);
		mapView = (MapView) findViewById(R.id.mapview);

		List<Overlay> overlays = mapView.getOverlays();
		overlays.clear();
		for(Item item : ItemListHelper.items) {
			overlays.add(new MarkerOverlay(item.getLocation()));
		}
		
		mapView.invalidate();
		
		// LocationManager locMgr = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);

		//
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);
		ImageButton locationButton = (ImageButton) findViewById(R.id.locationButton);
		locationButton.getBackground().setAlpha(20);
		locationButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// Zobrazení moji pozice na mape (mapView)
					Toast toast = Toast
							.makeText(getApplicationContext(),
									"Your position is not available",
									Toast.LENGTH_LONG);
					toast.show();
				}
				return false;
			}
		});
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

	class MarkerOverlay extends Overlay {
		
		GeoPoint location;
		
		public MarkerOverlay(GeoPoint location) {
			this.location = location;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			if (!shadow) {
				Point point = new Point();
				mapView.getProjection().toPixels(location, point);

				Bitmap bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_location_place);

				int x = point.x - bmp.getWidth() / 2;
				int y = point.y - bmp.getHeight();

				canvas.drawBitmap(bmp, x, y, null);
			}
		}
	}

}
