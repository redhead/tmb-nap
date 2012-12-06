package cz.cvut.localtrade.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DAO {

	public static final String REMOTE_URL = "http://tmb-nap-serv.herokuapp.com";

	protected void send(SendAsyncTask task, String url) {
		send(task, url, null);
	}

	protected void send(SendAsyncTask task, String url,
			List<NameValuePair> nameValuePairs) {
		String destination = REMOTE_URL + url + "?format=json";

		HttpPost httppost = new HttpPost(destination);

		System.out.println("send: " + destination);

		if (nameValuePairs != null) {
			UrlEncodedFormEntity entity;
			try {
				entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
				httppost.setEntity(entity);
				entity.writeTo(System.out);
			} catch (Exception e) {
			}
		}

		task.execute(httppost);
	}

	abstract class SendAsyncTask extends AsyncTask<HttpPost, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(HttpPost... params) {
			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(params[0]);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				String result = sb.toString();
				System.out.println("response: " + result);
				return new JSONObject(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new JSONObject();
		}

		@Override
		protected void onPostExecute(JSONObject result) {

		}

	}

}
