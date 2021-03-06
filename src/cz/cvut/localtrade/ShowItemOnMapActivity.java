package cz.cvut.localtrade;

import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
import cz.cvut.localtrade.dao.ItemsDAO.FindResponse;
import cz.cvut.localtrade.helper.MapUtils;
import cz.cvut.localtrade.model.Item;

public class ShowItemOnMapActivity extends MapActivity implements FindResponse {

	MapView mapView;
	Item item;
	private ItemsDAO itemDao;
	int itemId;

	private List<Overlay> overlays;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_item_on_map_activity_layout);
		mapView = (MapView) findViewById(R.id.mapview);

		itemDao = new ItemsDAO();
		itemDao.open();

		itemId = getIntent().getExtras().getInt("itemId");
		itemDao.find(this, itemId);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// setupMapView();
		// displayRoute();
		// LocationManager locMgr = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
	}

	// set your map and enable default zoom controls
	private void setupMapView() {
		this.mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	}

	// // create a route and display on the map
	// private void displayRoute() {
	// RouteManager routeManager = new RouteManager(this);
	// routeManager.setMapView(mapView);
	// routeManager.createRoute("San Francisco, CA", "Fremont, CA");
	// }

	@Override
	public boolean isRouteDisplayed() {
		return false;
	}

	private void showMarkers() {
		overlays = mapView.getOverlays();
		overlays.clear();
		overlays.add(new MarkerOverlay(item.getLocation()));

		// pridani i vlastni polohy
		GeoPoint gp = MapUtils.getUserGeoPoint();
		overlays.add(new MyPositionMarkerOverlay(gp));
		overlays.add(new LineOverlay(gp, item.getLocation()));
		MapController mapController = mapView.getController();
		mapController.animateTo(item.getLocation());
		mapController.setZoom(16);

		mapView.invalidate();
	}

	@Override
	protected void onResume() {
		itemDao.open();
		itemDao.find(this, itemId);
		super.onResume();
	}

	@Override
	protected void onPause() {
		itemDao.close();
		super.onPause();
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
			int itemId = this.item.getId();
			Bundle bundle = new Bundle();
			bundle.putInt("itemId", itemId);
			bundle.putSerializable("items",
					getIntent().getSerializableExtra("items"));
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

	class LineOverlay extends Overlay {
		GeoPoint start;
		GeoPoint end;

		public LineOverlay(GeoPoint start, GeoPoint end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			if (!shadow) {

				Paint mPaint = new Paint();
				mPaint.setDither(true);
				mPaint.setColor(Color.RED);
				mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
				mPaint.setStrokeJoin(Paint.Join.ROUND);
				mPaint.setStrokeCap(Paint.Cap.ROUND);
				mPaint.setStrokeWidth(2);

		        Point p1 = new Point();
		        Point p2 = new Point();
		        Path path = new Path();
		        
				Projection projection = mapView.getProjection();
				projection.toPixels(start, p1);
				projection.toPixels(end, p2);
				
				path.moveTo(p1.x, p1.y);
				path.lineTo(p2.x, p2.y);

				canvas.drawPath(path, mPaint);
			}
		}
	}

	@Override
	public void onFound(Item item) {
		getActionBar().setTitle(item.getTitle());
		this.item = item;
		showMarkers();
	}

	@Override
	public void onFindFail() {
	}

}
