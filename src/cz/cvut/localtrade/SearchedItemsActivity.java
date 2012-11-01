package cz.cvut.localtrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchedItemsActivity extends Activity {

	String[] titles = new String[] { "Red chair", "Blue chair", "Chair red",
			"Chair blue", "Chair", "New red chair", "New blue chair",
			"Wooden chair", "Broken chair", "Brand new red chair" };
	String[] states = new String[] { "used", "used", "used", "used", "new",
			"new", "new", "used", "broken", "new" };
	int[] distances = new int[] { 1, 2, 1, 3, 10, 15, 8, 7, 6, 22 };
	int[] prices = new int[] { 1000, 2340, 2342, 399, 1010, 1550, 899, 1700,
			200, 2200 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.searched_items_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		fillListView();
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

		for (int i = 0; i < 10; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("tit", titles[i]);
			hm.put("sta", "State: " + states[i]);
			hm.put("dis", distances[i] + " km");
			hm.put("pri", "Price: " + prices[i] + " Kč");
			hm.put("image", Integer.toString(R.drawable.no_image));
			aList.add(hm);
		}

		String[] from = { "tit", "sta", "dis", "pri", "image" };
		int[] to = { R.id.item_title, R.id.item_state, R.id.item_distance,
				R.id.item_price, R.id.item_image };
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.searched_items_list_item_layout, from, to);
		ListView listView = (ListView) findViewById(R.id.searched_items_listview);
		listView.setAdapter(adapter);
	}

	public void showPopupFilter(MenuItem item) {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searched_items_activity_menu, menu);
		return true;
	}

}
