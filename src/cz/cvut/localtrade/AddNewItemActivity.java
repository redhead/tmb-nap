package cz.cvut.localtrade;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import cz.cvut.localtrade.dao.ItemsDAO;
import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class AddNewItemActivity extends BaseActivity implements
		OnItemSelectedListener {

	Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.add_new_item_activity_layout);
		super.onCreate(savedInstanceState);

		item = new Item();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.add_new_item));

		final EditText titleText = (EditText) findViewById(R.id.item_title);
		final EditText descriptionText = (EditText) findViewById(R.id.item_description);
		final EditText priceText = (EditText) findViewById(R.id.price_of_item);

		final Spinner spinner = (Spinner) findViewById(R.id.state_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.states_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		Button done = (Button) findViewById(R.id.doneButton);
		done.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					ItemsDAO dao = new ItemsDAO(AddNewItemActivity.this);
					dao.open();
					String title = titleText.getText().toString();
					String description = descriptionText.getText().toString();
					double price = Double.parseDouble(priceText.getText()
							.toString());

					// FIXME: remove fixed location
					double off1 = Math.random() - 0.5;
					double off2 = Math.random() - 0.5;
					int lat = (int) ((50 + off1) * 1E6);
					int lon = (int) ((14 + off2) * 1E6);
					dao.createItem(title, item.getState(), description, price,
							lat, lon);

					dao.close();
					AddNewItemActivity.this.finish();
				}
				return false;
			}
		});
	}

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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		switch (pos) {
		case 0:
			item.setState(State.NEW);
			break;
		case 1:
			item.setState(State.USED);
			break;
		case 2:
			item.setState(State.DYSFUNCTIONAL);
			break;
		case 3:
			item.setState(State.BROKEN);
			break;
		default:
			item.setState(State.NEW);
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
