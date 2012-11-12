package cz.cvut.localtrade;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import cz.cvut.localtrade.helper.ConversationArrayAdapter;
import cz.cvut.localtrade.model.Conversation;
import cz.cvut.localtrade.model.Message;

public class ConversationActivity extends BaseActivity {

	Conversation conversation;
	ConversationArrayAdapter adapter;
	ListView listView;
	EditText input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.conversation_activity_layout);
		super.onCreate(savedInstanceState);

		listView = (ListView) findViewById(R.id.commentsList);

		// listView.setAdapter(adapter);
		input = (EditText) findViewById(R.id.commentInput);

		Bundle bundle = new Bundle();
		bundle = getIntent().getExtras();
		conversation = (Conversation) bundle.getSerializable("conversation");

		adapter = new ConversationArrayAdapter(getApplicationContext(),
				R.layout.conversation_item_layout, conversation);

		input.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					sendMessage();
					return true;
				}
				return false;
			}
		});

		ImageButton sendButton = (ImageButton) findViewById(R.id.sendButton);
		sendButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					sendMessage();
				}
				return false;
			}
		});

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(conversation.getOther().getUsername());

		addItems();
		listView.setAdapter(adapter);
	}

	private void addItems() {
		// adapter.add(new Message("Hello bubbles!", true));

		for (int i = 0; i < conversation.getListOfMessages().size(); i++) {
			Message mes = conversation.getListOfMessages().get(i);
			adapter.add(new Message(mes.getText(), mes.getLeft()));
		}
	}

	private void sendMessage() {
		adapter.add(new Message(input.getText().toString(), false));
		input.setText("");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, AllConversationsActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
