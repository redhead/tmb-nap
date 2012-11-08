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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class MyItemsActivity extends Activity {

	Item[] items = {
			new Item("Red chair", State.NEW, "Description of red chair", 982, 1),
			new Item("Blue chair", State.USED, "Description of blue chair",
					1100, 3),
			new Item("Chair red", State.NEW, "Description of red chair", 890, 4),
			new Item("Chair blue", State.USED, "Description of blue chair",
					1000, 2),
			new Item("Wooden chair", State.USED, "Description of wooden chair",
					1550, 9),
			new Item("Broken chair", State.BROKEN,
					"Description of broken chair", 400, 12),
			new Item("Chair", State.USED, "Description of chair", 999, 21),
			new Item("Brand new chair", State.USED,
					"Description of brand new chair", 2999, 1),
			new Item("New chair", State.NEW, "Description of new chair chair",
					1099, 3),
			new Item("Office chair", State.USED, "Description of office chair",
					7000, 1),
			new Item("New chair", State.USED, "Description of new chair", 749,
					0.5) };

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.my_items_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("My items");

		listView = (ListView) findViewById(R.id.my_items_listview);

		fillListView();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyItemsActivity.this,
						MyItemDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("item", items[position]);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	public void fillListView() {
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < items.length / 2; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("tit", items[i].getTitle());
			hm.put("sta", "State: " + items[i].getState());
			hm.put("pri", "Price: " + items[i].getPrice() + " Kč");
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

}
