package cz.cvut.localtrade;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.helper.MapUtils;
import cz.cvut.localtrade.model.Item;

public class ShowItemOnMapActivity extends MapActivity {

	MapView mapView;
	Item item;
	private ItemsDAO itemDao;
	long itemId;

	private List<Overlay> overlays;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_item_on_map_activity_layout);
		mapView = (MapView) findViewById(R.id.mapview);

		itemDao = new ItemsDAO(this);
		itemDao.open();

		itemId = getIntent().getExtras().getLong("itemId");
		item = itemDao.find(itemId);

		showMarkers();

		// LocationManager locMgr = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(item.getTitle());

	}

	private void showMarkers() {
		overlays = mapView.getOverlays();
		overlays.clear();
		overlays.add(new MarkerOverlay(item.getLocation()));

		// pridani i vlastni polohy
		GeoPoint gp = MapUtils.actualLocation;
		overlays.add(new MyPositionMarkerOverlay(gp));
		MapController mapController = mapView.getController();
		mapController.animateTo(gp);
		mapController.setZoom(16);

		mapView.invalidate();
	}

	@Override
	protected void onResume() {
		itemDao.open();
		item = itemDao.find(itemId);
		showMarkers();
		super.onResume();
	}

	@Override
	protected void onPause() {
		itemDao.close();
		super.onPause();
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, SearchedItemDetailActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			long itemId = this.item.getId();
			Bundle bundle = new Bundle();
			bundle.putLong("itemId", itemId);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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

	class MyPositionMarkerOverlay extends Overlay {

		GeoPoint location;

		public MyPositionMarkerOverlay(GeoPoint location) {
			this.location = location;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			if (!shadow) {
				Point point = new Point();
				mapView.getProjection().toPixels(location, point);

				Bitmap bmp = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_map_marker);

				int x = point.x - bmp.getWidth() / 2;
				int y = point.y - bmp.getHeight();

				canvas.drawBitmap(bmp, x, y, null);
			}
		}
	}

}
