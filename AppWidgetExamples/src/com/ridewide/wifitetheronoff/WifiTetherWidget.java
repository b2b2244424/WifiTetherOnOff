package com.ridewide.wifitetheronoff;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.net.wifi.WifiConfiguration;

public class WifiTetherWidget extends AppWidgetProvider {
	public static final String TAG = "WifiTetherWidget";
	public static final Boolean DEBUG = false;
	private static final String ACTION_MY_CLICK = "com.ridewide.wifitetheronoff.ACTION_MY_CLICK";
	public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	if (DEBUG) Log.d(TAG, "onUpdate()");
        Intent serviceIntent = new Intent(context, MyService.class);
        context.startService(serviceIntent);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	super.onReceive(context, intent);
    	if (DEBUG) Log.d(TAG, "onReceive()");
    	String action = intent.getAction();
    	if (WifiApManager.WIFI_AP_STATE_CHANGED_ACTION.equals(action)) {
    		int newApState = intent.getIntExtra(WifiApManager.EXTRA_WIFI_AP_STATE, 
    				WifiApStatus.getWifiApStateDisabled());

        	boolean flg = false;
	        ComponentName thisWidget = new ComponentName(context, WifiTetherWidget.class);
	        AppWidgetManager manager = AppWidgetManager.getInstance(context);
	        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.slide_show);
        	
	    	if (DEBUG) Log.d(TAG, "onReceive() newApState="+newApState);
	    	if (newApState == WifiApStatus.getWifiApStateEnabling()) {
            	flg = true;
    	        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.enabling);
	    	} if (newApState == WifiApStatus.getWifiApStateEnabled()) {
            	flg = true;
    	        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.enabled);
	    	} if (newApState == WifiApStatus.getWifiApStateDisabling()) {
            	flg = true;
    	        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.disabling);
	    	} if (newApState == WifiApStatus.getWifiApStateDisabled()) {
            	flg = true;
    	        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.disabled);
        	}
        	if (flg) {
    	        Intent clickIntent = new Intent();
    	        clickIntent.setAction(ACTION_MY_CLICK);
    	        PendingIntent pendingIntent = PendingIntent.getService(context, 0, clickIntent, 0);
    	        remoteViews.setOnClickPendingIntent(R.id.ImageView01, pendingIntent);
    	        manager.updateAppWidget(thisWidget, remoteViews);
        	}
         }
    }

	public static class MyService extends Service {
	    @Override
	    public void onStart(Intent intent, int startId) {
	    	if (DEBUG) Log.d(TAG, "MyService#onStart()");
	    	WifiApManager wifiApManager = new WifiApManager(this);
	        WifiConfiguration wifiConfig = wifiApManager.getWifiApConfiguration();
	        ComponentName thisWidget = new ComponentName(this, WifiTetherWidget.class);
	        AppWidgetManager manager = AppWidgetManager.getInstance(this);
	        
	        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.slide_show);
	        boolean apState = wifiApManager.getWifiApState();
	        
	        if (ACTION_MY_CLICK.equals(intent.getAction())) {
	        	// click event
		        if (apState) {
			    	if (DEBUG) Log.d(TAG, "MyService#onStart() click event. start disable");
			        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.disabling);
		            wifiApManager.setWifiApEnabled(wifiConfig, false);
		        } else {
			    	if (DEBUG) Log.d(TAG, "MyService#onStart() click event. start enable");
			        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.enabling);
		            wifiApManager.setWifiApEnabled(wifiConfig, true);
		        }
	        } else {
		        if (apState) {
			    	if (DEBUG) Log.d(TAG, "MyService#onStart() no click event. update view -> enabled");
			        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.enabled);
		        } else {
			    	if (DEBUG) Log.d(TAG, "MyService#onStart() no click event. update view -> disabled");
			        remoteViews.setImageViewResource(R.id.ImageView01, R.drawable.disabled);
		        }
	        }
	        Intent clickIntent = new Intent();
	        clickIntent.setAction(ACTION_MY_CLICK);
	        PendingIntent pendingIntent = PendingIntent.getService(this, 0, clickIntent, 0);
	        
	        remoteViews.setOnClickPendingIntent(R.id.ImageView01, pendingIntent);
	        
	        manager.updateAppWidget(thisWidget, remoteViews);
	    }

	    @Override
	    public IBinder onBind(Intent intent) {
	        return null;
	    }
	}
}
