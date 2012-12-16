package cz.cvut.localtrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.dao.ItemsDAO.GetByUserResponse;
import cz.cvut.localtrade.helper.LoginUtil;
import cz.cvut.localtrade.model.Item;

public class MyItemsActivity extends BaseActivity implements GetByUserResponse {

	ListView listView;

	private ItemsDAO itemDao;

	private List<Item> items = new ArrayList<Item>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_items_activity_layout);
		super.onCreate(savedInstanceState);

		itemDao = new ItemsDAO();
		itemDao.getAllByUser(this, LoginUtil.getUserId(this));
		showLoadingDialog();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.my_items_title));

		listView = (ListView) findViewById(R.id.my_items_listview);
		fillListView();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyItemsActivity.this,
						MyItemDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", items.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	@Override
	protected void onResume() {
		if (items == null) {
			itemDao.getAllByUser(this, LoginUtil.getUserId(this));
			showLoadingDialog();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		items = null;
		super.onPause();
	}

	public void fillListView() {
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

		for (Item item : items) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("tit", item.getTitle());
			hm.put("sta", getString(R.string.state) + ": " + item.getState());
			hm.put("pri", getString(R.string.price) + ": " + item.getPrice()
					+ " " + getString(R.string.currency));
			hm.put("image", Integer.toString(R.drawable.no_image));
			aList.add(hm);
		}

		String[] from = { "tit", "sta", "pri", "image" };
		int[] to = { R.id.item_title, R.id.item_state, R.id.item_price,
				R.id.item_image };
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.my_items_list_item_layout, from, to);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_items_activity_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ShowMapActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void addNewItem(MenuItem menuItem) {
		Intent intent = new Intent(this, AddNewItemActivity.class);
		startActivity(intent);
	}

	public void myMessages(MenuItem menuItem) {
		Intent intent = new Intent(this, AllConversationsActivity.class);
		startActivity(intent);
	}

	@Override
	public void onFound(List<Item> items) {
		this.items = items;
		hideLoadingDialog();
		fillListView();
	}

}
