package cz.cvut.localtrade.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import cz.cvut.localtrade.R;
import cz.cvut.localtrade.model.Conversation;
import cz.cvut.localtrade.model.Message;

public class ConversationArrayAdapter extends ArrayAdapter<Message> {

	TextView commentText;
	TextView commentUsername;
	Conversation conversation;
	LinearLayout wrapper;
	List<Message> messages = new ArrayList<Message>();

	public ConversationArrayAdapter(Context context, int textViewResourceId,
			Conversation conversation) {
		super(context, textViewResourceId);
		this.conversation = conversation;
	}

	@Override
	public void add(Message message) {
		this.messages.add(message);
		super.add(message);
	}

	public int getCount() {
		return this.messages.size();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.conversation_item_layout, parent,
					false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		Message message = getItem(position);

		commentText = (TextView) row.findViewById(R.id.commentText);
		commentUsername = (TextView) row.findViewById(R.id.commentUsername);

		commentText.setText(message.getText());
		commentUsername.setText(message.getLeft() ? conversation.getOther()
				.getUsername() : conversation.getOwner().getUsername());
		commentUsername.setTextColor(message.getLeft() ? Color.GREEN
				: Color.LTGRAY);

		wrapper.setBackgroundColor(message.getLeft() ? Color.rgb(227, 227, 227)
				: Color.WHITE);

		return row;
	}
}
