package edu.gonzaga.textsecretary;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NotificationUtility {
	private static final String TAG = "NOTIFICATION";
	private Context mContext;

	public NotificationUtility(Context context) {
		mContext = context;
	}

	public void displayNotification(String number) {
		Log.d(TAG, "notification");

		String id = getId(number);

		/* Invoking the default notification service */
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);

		notificationBuilder.setContentTitle("Auto Replied");
		notificationBuilder.setContentText("Text Secretary auto replied to: " + id);
		notificationBuilder.setTicker("Text Secretary Auto Reply");
		notificationBuilder.setSmallIcon(R.drawable.ic_action_notification_holo_light);
		notificationBuilder.setAutoCancel(true);

		/* Creates an explicit intent for an Activity in your app */
		Intent resultIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		//stackBuilder.addParentStack(NotificationView.class);

		/* Adds the Intent that starts the Activity to the top of the stack */
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
				);

		notificationBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotificationManager =
				(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

		int randomInt = (int) (1000.0 * Math.random());
		//int notificationID = 100;
		/* notificationID allows you to update the notification later on. */
		mNotificationManager.notify(randomInt, notificationBuilder.build());
	}

	private String getId(String number) {
		String id;
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
		String[] projection = new String[]{
				PhoneLookup.DISPLAY_NAME
		};
		Cursor contactCursor = mContext.getContentResolver().query(uri, projection, null, null, null);
		Log.d(TAG, "query completed");
		if (contactCursor.moveToFirst()) {
			id = contactCursor.getString(0);
		} else {
			id = number;
		}
		contactCursor.close();
		return id;
	}

}
