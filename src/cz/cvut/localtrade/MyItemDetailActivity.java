package cz.cvut.localtrade;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.dao.ItemsDAO.DeleteResponse;
import cz.cvut.localtrade.dao.ItemsDAO.FindResponse;
import cz.cvut.localtrade.helper.LoginUtil;
import cz.cvut.localtrade.model.Item;

public class MyItemDetailActivity extends BaseActivity implements
		DeleteResponse, FindResponse {

	private ItemsDAO itemDao;
	private Item item;
	private int itemId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_item_detail_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.my_item_detail));

		item = (Item) getIntent().getExtras().get("item");
		itemId = item.getId();
		itemDao = new ItemsDAO();

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
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void deleteItem(MenuItem menuItem) {
		itemDao.deleteItem(this, itemId, LoginUtil.getUserId(this));
	}

	public void editItem(MenuItem menuItem) {
		Intent intent = new Intent(this, EditItemActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("item", item);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		if (item == null) {
			itemDao.find(this, itemId);
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		item = null;
		super.onPause();
	}

	@Override
	public void onDeleted() {
		finish();
	}

	@Override
	public void onDeleteFail() {

	}

	@Override
	public void onFound(Item item) {
		this.item = item;
		fillContent();
	}

	@Override
	public void onFindFail() {
		finish();
	}

}
