package edu.gonzaga.textsecretary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartOnBoot extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Intent pushIntent = new Intent(context, SMS_Service.class);
			context.startService(pushIntent);
		}
	}

}
