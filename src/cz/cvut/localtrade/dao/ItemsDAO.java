package cz.cvut.localtrade.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.localtrade.helper.Filter;
import cz.cvut.localtrade.model.Item;
import cz.cvut.localtrade.model.Item.State;

public class ItemsDAO extends DAO {

	private final static String CREATE_URL = "/items/add";
	private final static String EDIT_URL = "/items/edit";
	private final static String DELETE_URL = "/items/remove";
	private final static String FIND_URL = "/items/get";
	private final static String GET_ALL_BY_USER_URL = "/items/get-all-by-user";

	private final static String USER_ID = "1";

	public void open() {
	}

	public void close() {
	}

	public void createItem(CreateResponse resp, String title, State state,
			String description, double price, int lat, int lon) {
		Item item = new Item(title, state, description, price, lat, lon);

		List<NameValuePair> params = item.toParams();
		params.add(new BasicNameValuePair("user_id", USER_ID));

		send(new CreateAsyncTask(resp), CREATE_URL, params);
	}

	public void editItem(EditResponse resp, Item item) {
		List<NameValuePair> params = item.toParams();
		params.add(new BasicNameValuePair("id", item.getId() + ""));
		params.add(new BasicNameValuePair("user_id", USER_ID));

		send(new EditAsyncTask(resp), EDIT_URL, params);
	}

	public void deleteItem(DeleteResponse resp, int itemId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", itemId + ""));
		params.add(new BasicNameValuePair("user_id", USER_ID));

		send(new DeleteAsyncTask(resp), DELETE_URL, params);
	}

	public List<Item> getAllItems() {
		return new ArrayList<Item>();
	}

	public void find(FindResponse resp, int itemId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", itemId + ""));
		send(new FindAsyncTask(resp), FIND_URL, params);
	}

	public List<Item> getFilteredItems(Filter filter) {
		return new ArrayList<Item>();
	}

	public void getAllByUser(GetByUserResponse resp) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", USER_ID));
		send(new GetByUserAsyncTask(resp), GET_ALL_BY_USER_URL, params);
	}

	public interface CreateResponse {
		public void onItemCreate(Item item);
	}

	class CreateAsyncTask extends SendAsyncTask {

		private CreateResponse response;

		public CreateAsyncTask(CreateResponse response) {
			this.response = response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result.getString("status").equals("OK")) {
					JSONObject object = result.getJSONObject("item");
					Item item = Item.fromJSON(object);
					response.onItemCreate(item);
					return;
				}
			} catch (JSONException e) {
			}
		}
	}

	public interface EditResponse {
		public void onItemEdit(Item item);

		public void onItemEditFail();
	}

	class EditAsyncTask extends SendAsyncTask {

		private EditResponse response;

		public EditAsyncTask(EditResponse response) {
			this.response = response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result.getString("status").equals("OK")) {
					JSONObject obj = result.getJSONObject("item");
					Item item = Item.fromJSON(obj);
					response.onItemEdit(item);
					return;
				}
			} catch (JSONException e) {
			}
			response.onItemEditFail();
		}
	}

	public interface DeleteResponse {
		public void onDeleted();

		public void onDeleteFail();
	}

	class DeleteAsyncTask extends SendAsyncTask {

		private DeleteResponse response;

		public DeleteAsyncTask(DeleteResponse response) {
			this.response = response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result.getString("status").equals("OK")) {
					response.onDeleted();
					return;
				}
			} catch (JSONException e) {
			}
			response.onDeleteFail();
		}
	}

	public interface FindResponse {
		public void onFound(Item item);

		public void onFindFail();
	}

	class FindAsyncTask extends SendAsyncTask {

		private FindResponse response;

		public FindAsyncTask(FindResponse response) {
			this.response = response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result.getString("status").equals("OK")) {
					JSONObject obj = result.getJSONObject("item");
					Item item = Item.fromJSON(obj);
					response.onFound(item);
					return;
				}
			} catch (JSONException e) {
			}
			response.onFindFail();
		}
	}

	public interface GetByUserResponse {
		public void onFound(List<Item> items);
	}

	class GetByUserAsyncTask extends SendAsyncTask {

		private GetByUserResponse response;

		public GetByUserAsyncTask(GetByUserResponse response) {
			this.response = response;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			List<Item> items = new ArrayList<Item>();
			try {
				if (result.has("items")) {
					JSONArray arr = result.getJSONArray("items");
					for (int i = 0; i < arr.length(); i++) {
						JSONObject obj = arr.getJSONObject(i);
						Item item = Item.fromJSON(obj);
						items.add(item);
					}
				}
			} catch (JSONException e) {
			}
			response.onFound(items);
		}
	}

}
