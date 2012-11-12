package cz.cvut.localtrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import cz.cvut.localtrade.model.Conversation;
import cz.cvut.localtrade.model.Message;
import cz.cvut.localtrade.model.User;

public class AllConversationsActivity extends BaseActivity {
	User[] users = {
			new User("Jarin", "Medků", "jarin@medku.com", "medy75", "a"),
			new User("Radek", "Ježdík", "radek@jezdik.com", "redhead", "a"),
			new User("Záda", "Bolavý", "bolavy@zada.com", "bolavyZada", "a") };

	Message[] messages = { new Message("Chci všchno co máš...", true),
			new Message("Už to nechci!!", false),
			new Message("A kosmodisk nemáte?? Bolí mě totiž záda...", true),
			new Message("A máte i židle??", true) };

	List<Message> list = new ArrayList<Message>();
	List<Message> list2 = new ArrayList<Message>();
	Conversation[] conversations = new Conversation[2];

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.all_conversations_activity_layout);
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.conversations));

		listView = (ListView) findViewById(R.id.converstaionsList);

		fillConversationList();
		fillListView();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(AllConversationsActivity.this,
						ConversationActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("conversation", conversations[position]);
				intent.putExtras(bundle);
				startActivity(intent);
			}

		});
	}

	public void fillConversationList() {
		list.add(messages[0]);
		list.add(messages[1]);
		list2.add(messages[2]);
		list2.add(messages[3]);
		conversations[0] = new Conversation(list, users[0], users[1]);
		conversations[1] = new Conversation(list2, users[0], users[2]);
	}

	public void fillListView() {
		List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

		for (int i = 0; i < conversations.length; i++) {
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("title", conversations[i].getOther().getUsername());
			hm.put("text", conversations[i].getListOfMessages().get(0)
					.getText());
			aList.add(hm);
		}

		String[] from = { "title", "text" };
		int[] to = { R.id.conversationTitle, R.id.conversationText };
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList,
				R.layout.all_conversations_list_item_layout, from, to);
		listView.setAdapter(adapter);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.my_items_activity_menu, menu);
	// return true;
	// }

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

	// public void addNewItem(MenuItem menuItem) {
	// Intent intent = new Intent(this, AddNewItemActivity.class);
	// startActivity(intent);
	// }
	//
	// public void myMessages(MenuItem menuItem) {
	//
	// }

}
