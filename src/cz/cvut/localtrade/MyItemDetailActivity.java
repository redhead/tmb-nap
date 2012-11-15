package cz.cvut.localtrade;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.model.Item;

public class MyItemDetailActivity extends BaseActivity {

	private Item item;

	private ItemsDAO itemDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_item_detail_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.my_item_detail));

		long itemId = getIntent().getExtras().getLong("itemId");

		itemDao = new ItemsDAO(this);
		itemDao.open();

		item = itemDao.find(itemId);
		if (item == null)
			finish();

		fillContent();
	}

	public void fillContent() {
		TextView title = (TextView) findViewById(R.id.item_title);
		TextView price = (TextView) findViewById(R.id.item_price);
		TextView state = (TextView) findViewById(R.id.item_state);
		TextView description = (TextView) findViewById(R.id.item_description);

		title.setText(item.getTitle());
		price.setText(item.getPrice() + " " + getString(R.string.currency));
		state.setText(item.getState().toString());
		// distance.setText(item.getDistance() + " Km");
		description.setText(item.getDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_item_detail_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MyItemsActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void deleteItem(MenuItem menuItem) {
		itemDao.deleteItem(item);
		finish();
	}

	public void editItem(MenuItem item) {

	}

	@Override
	protected void onResume() {
		itemDao.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		itemDao.close();
		super.onPause();
	}

}
