package cz.cvut.localtrade;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import cz.cvut.localtrade.helper.MapUtils;
import cz.cvut.localtrade.model.Item;

public class SearchedItemDetailActivity extends BaseActivity {

	private Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.searched_item_detail_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.found_items_detail));

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		item = (Item) bundle.getSerializable("item");
		fillContent();
	}

	public void fillContent() {
		TextView title = (TextView) findViewById(R.id.item_title);
		TextView price = (TextView) findViewById(R.id.item_price);
		TextView state = (TextView) findViewById(R.id.item_state);
		TextView distance = (TextView) findViewById(R.id.item_distance);
		TextView description = (TextView) findViewById(R.id.item_description);

		title.setText(item.getTitle());
		price.setText(item.getPrice() + " " + getString(R.string.currency));
		state.setText(item.getState().toString());
		distance.setText(MapUtils.distanceBetween(MapUtils.actualLocation, item.getLocation()) + " "
				+ getString(R.string.distance_unit));
		description.setText(item.getDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searched_item_detail_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, SearchedItemsActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showOnMap(MenuItem item) {

	}

	public void sendMessage(MenuItem item) {

	}

}
