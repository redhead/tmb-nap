package cz.cvut.localtrade;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.SearchView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.dao.ItemsDAO.FindAllResponse;
import cz.cvut.localtrade.helper.MapUtils;
import cz.cvut.localtrade.model.Item;

public class ShowMapActivity extends MapActivity implements
		android.widget.SearchView.OnQueryTextListener, FindAllResponse {

	private static final int LOADING_DIALOG = 1;

	MapView mapView;

	private ItemsDAO itemDao;

	private List<Item> items;
	private List<Overlay> overlays;

	private SearchView searchView;

	private String query = "";

	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_map_activity_layout);
		mapView = (MapView) findViewById(R.id.mapview);

		itemDao = new ItemsDAO();
		itemDao.findAll(this, query);
		showLoadingDialog();

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
					overlays = mapView.getOverlays();
					// "this" refers to your activity
					MyLocationOverlay myLocationOverlay = new MyLocationOverlay(
							getApplicationContext(), mapView);
					myLocationOverlay.enableCompass();
					myLocationOverlay.enableMyLocation();
					GeoPoint gp = MapUtils.actualLocation;
					// overlays.add(new MyPositionMarkerOverlay(gp));
					MapController mapController = mapView.getController();
					mapController.animateTo(gp);
					mapController.setZoom(16);
					mapView.invalidate();
				}
				return false;
			}
		});
	}

	private void showMarkers() {
		overlays = mapView.getOverlays();
		overlays.clear();
		for (Item item : items) {
			overlays.add(new MarkerOverlay(item.getLocation()));
		}
		// pridani i vlastni polohy
		GeoPoint gp = MapUtils.actualLocation;
		overlays.add(new MyPositionMarkerOverlay(gp));
		mapView.invalidate();
	}

	@Override
	protected void onResume() {
		itemDao.findAll(this, query);
		showLoadingDialog();
		super.onResume();
	}

	@Override
	protected void onPause() {
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.show_map_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.search);
		searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(this);
		return true;
	}

	public void iconClick(MenuItem item) {
	}

	public void myItemsClick(MenuItem item) {
		Intent intent = new Intent(ShowMapActivity.this, MyItemsActivity.class);
		startActivity(intent);
	}

	public void searchedItemsClick(MenuItem item) {
		Intent intent = new Intent(ShowMapActivity.this,
				SearchedItemsActivity.class);
		intent.putExtra("items", (ArrayList<Item>) items);
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

	@Override
	public boolean onQueryTextChange(String newText) {
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		searchView.clearFocus();
		searchView.onActionViewCollapsed();
		this.query = query;
		itemDao.findAll(this, query);
		return true;
	}

	@Override
	public void onFound(List<Item> items) {
		this.items = items;
		showMarkers();
		hideLoadingDialog();
	}

	protected void hideLoadingDialog() {
		if (loadingDialog != null) {
			loadingDialog.dismiss();
		}
	}

	protected void showLoadingDialog() {
		showDialog(LOADING_DIALOG);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (loadingDialog == null) {
			loadingDialog = new ProgressDialog(this);
		}
		return loadingDialog;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (id == LOADING_DIALOG) {
			loadingDialog.setTitle("Loading");
			loadingDialog.setMessage("Loading");
			loadingDialog.setCancelable(false);
		}
	}
}
