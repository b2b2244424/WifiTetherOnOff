package com.ridewide.wifitetheronoff;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiApManager {
	private final WifiManager mWifiManager;
	public static final String TAG = "WifiTetherWidget";
	public static final Boolean DEBUG = false;
	public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
	public static final String EXTRA_WIFI_AP_STATE = "wifi_state";
	
	public WifiApManager(Context context) {
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}
	
	/*
	 * テザリング状態取得
     * State: (0)WIFI_STATE_DISABLING (1)WIFI_STATE_DISABLED (2)WIFI_STATE_ENABLING (3)WIFI_STATE_ENABLED (4)WIFI_STATE_UNKNOWN
     */
	public boolean getWifiApState() {
        try {
            Method method = mWifiManager.getClass().getMethod("getWifiApState");
            int state = (Integer)method.invoke(mWifiManager);
            if (DEBUG) Log.d(TAG, "WifiApManager#getWifiApState() state="+state);
            if (state == WifiApStatus.getWifiApStateEnabled()) {
            	return true;
            } else {
            	return false;
            }
        } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
        }
    }
	//テザリング設定値の取得
	public WifiConfiguration getWifiApConfiguration() {
		try {
            Method method;
			method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
			return (WifiConfiguration)method.invoke(mWifiManager);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
    /*
     * テザリングON/OFF
     */
    public void setWifiApEnabled(WifiConfiguration wifiConfig, boolean enabled) {
        try {
            Method method = mWifiManager.getClass().getMethod(
            		"setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, wifiConfig, enabled);
        } catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
        }
	}
}
