package cz.cvut.localtrade.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LoginUtil {

	private static SharedPreferences getPrefs(Context context) {
		return context.getApplicationContext().getSharedPreferences("USER_ID",
				Context.MODE_PRIVATE);
	}

	public static int getUserId(Context context) {
		return getPrefs(context).getInt("user_id", -1);
	}

	public static void setUserId(Context context, int userId) {
		Editor editor = getPrefs(context).edit();
		editor.putInt("user_id", userId);
		editor.commit();
	}

}
