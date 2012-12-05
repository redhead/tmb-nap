package cz.cvut.localtrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.helper.Filter;
import cz.cvut.localtrade.helper.MapUtils;
import cz.cvut.localtrade.model.Item;

public class SearchedItemsActivity extends FragmentActivity {

	ListView listView;

	private ItemsDAO itemDao;

	private List<Item> items;

	private Filter filter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.searched_items_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.found_items));

		itemDao = new ItemsDAO(this);
		itemDao.open();
		items = itemDao.getAllItems();

		fillListView();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SearchedItemsActivity.this,
						SearchedItemDetailActivity.class);
				long itemId = items.get(position).getId();
				Bundle bundle = new Bundle();
				bundle.putLong("itemId", itemId);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	@Override
	protected void onResume() {
		itemDao.open();
		items = itemDao.getFilteredItems(filter);
		fillListView();
		super.onResume();
	}

	@Override
	protected void onPause() {
		itemDao.close();
		super.onPause();
	}

	@Override
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

	public void fillListView() {
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

		double minItemPrice = Double.MAX_VALUE;
		double maxItemPrice = Double.MIN_VALUE;

		for (Item item : items) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("tit", item.getTitle());
			hm.put("sta", getString(R.string.state) + ": " + item.getState());
			hm.put("dis",
					String.format("%.2f", MapUtils.distanceBetween(
							MapUtils.actualLocation, item.getLocation()))
							+ " " + getString(R.string.distance_unit));
			hm.put("pri", getString(R.string.price) + ": " + item.getPrice()
					+ " " + getString(R.string.currency));
			hm.put("image", Integer.toString(R.drawable.no_image));
			aList.add(hm);
		}

		String[] from = { "tit", "sta", "dis", "pri", "image" };
		int[] to = { R.id.item_title, R.id.item_state, R.id.item_distance,
				R.id.item_price, R.id.item_image };
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.searched_items_list_item_layout, from, to);
		listView = (ListView) findViewById(R.id.searched_items_listview);
		listView.setAdapter(adapter);
	}

	public void showPopupFilter(MenuItem item) {
		FilterDialogFragment f = new FilterDialogFragment();
		f.show(getFragmentManager(), "FilterDialogFragment");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searched_items_activity_menu, menu);
		return true;
	}

}
