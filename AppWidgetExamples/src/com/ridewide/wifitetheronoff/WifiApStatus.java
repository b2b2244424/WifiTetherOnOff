package com.ridewide.wifitetheronoff;

import android.os.Build;

public class WifiApStatus {
	// 2.2 - 2.3
	private static final int WIFI_STATE_DISABLING = 0;
	private static final int WIFI_STATE_DISABLED = 1;
	private static final int WIFI_STATE_ENABLING = 2;
	private static final int WIFI_STATE_ENABLED = 3;
	private static final int WIFI_STATE_FAILED = 4;
    // 4.0.3 - 
	private static final int WIFI_AP_STATE_DISABLING = 10;
	private static final int WIFI_AP_STATE_DISABLED = 11;
	private static final int WIFI_AP_STATE_ENABLING = 12;
	private static final int WIFI_AP_STATE_ENABLED = 13;
	private static final int WIFI_AP_STATE_FAILED = 14;
	
	public static int getWifiApStateDisabling() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return WIFI_AP_STATE_DISABLING;
	    } else {
			return WIFI_STATE_DISABLING;
	    }
	}
	public static int getWifiApStateDisabled() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return WIFI_AP_STATE_DISABLED;
	    } else {
			return WIFI_STATE_DISABLED;
	    }
	}
	public static int getWifiApStateEnabling() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return WIFI_AP_STATE_ENABLING;
	    } else {
			return WIFI_STATE_ENABLING;
	    }
	}
	public static int getWifiApStateEnabled() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return WIFI_AP_STATE_ENABLED;
	    } else {
			return WIFI_STATE_ENABLED;
	    }
	}
	public static int getWifiApStateFailed() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return WIFI_AP_STATE_FAILED;
	    } else {
			return WIFI_STATE_FAILED;
	    }
	}


	
}
