package cz.cvut.localtrade;

import model.Item;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ItemDetail extends Activity {

	private Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.item_detail_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		item = (Item) bundle.getSerializable("item");
		fillContent();
	}

	public void fillContent() {
		TextView title = (TextView) findViewById(R.id.item_title);
		TextView price = (TextView) findViewById(R.id.item_price);
		TextView state = (TextView) findViewById(R.id.item_state);
		TextView description = (TextView) findViewById(R.id.item_description);

		title.setText(item.getTitle());
		price.setText(item.getPrice() + " Kč");
		state.setText(item.getState());
		description.setText(item.getDescription());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.item_detail_activity_menu, menu);
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

	public void deleteItem(MenuItem item) {

	}

	public void editItem(MenuItem item) {

	}

}
