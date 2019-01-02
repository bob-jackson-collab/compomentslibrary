package com.example.momo.myapplication.util;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/29
 *   desc: MyApplication
 * </pre>
 */
public class NetUtils {

    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_MOBILE = "mobile";
    public static final String NETWORK_CLASS_2G = "2g";
    public static final String NETWORK_CLASS_3G = "3g";
    public static final String NETWORK_CLASS_4G = "4g";
    public static final String NETWORK_CLASS_UNKNOWN = "unknown";

    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final String WIFI = "uniwap";

    public static final int TYPE_NET_WORK_DISABLED = 0;
    public static final int WAP_TYPE_CM = 1;
    public static final int WAP_TYPE_3G = 2;
    public static final int WAP_TYPE_UNI = 3;
    public static final int WAP_TYPE_CT = 5;
    public static final int TYPE_OTHER_NET = 6;

    public static final int NETWORK_STATUS_UNKNOWN = -1;
    public static final int NETWORK_STATUS_WIFI = 1;
    public static final int NETWORK_STATUS_2G = 2;
    public static final int NETWORK_STATUS_3G = 3;
    public static final int NETWORK_STATUS_4G = 4;

    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    public NetUtils() {

    }

    public static int getNetWorkStatus() {
        if (AppContext.sContext != null) {
            ConnectivityManager connManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connManager == null) {
                return -1;
            }
            NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                if (activeNetInfo.getType() == 1) {
                    return 1;
                }
                if (activeNetInfo.getType() == 0) {
                    return getNetWorkStatus(activeNetInfo.getSubtype());
                }
            }
        }
        return -1;
    }

    private static int getNetWorkStatus(int networkType) {
        switch (networkType) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 2;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 4;
            default:
                return -1;
        }
    }

    public static String getNetWorkType() {
        if (AppContext.sContext != null) {
            ConnectivityManager connManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = null;
            if (connManager != null) {
                activeNetInfo = connManager.getActiveNetworkInfo();
            }
            if (activeNetInfo != null) {
                if (activeNetInfo.getType() == 1) {
                    return "wifi";
                }

                if (activeNetInfo.getType() == 0) {
                    return "mobile";
                }
            }
        }

        return null;
    }


    public static String getNetWorkClass() {
        try {
            if (AppContext.sContext != null) {
                ConnectivityManager connManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetInfo = connManager != null ? connManager.getActiveNetworkInfo() : null;
                if (activeNetInfo != null) {
                    if (activeNetInfo.getType() == 1) {
                        return "wifi";
                    }
                    if (activeNetInfo.getType() == 0) {
                        return getNetworkClass(activeNetInfo.getSubtype());
                    }
                }
            }
        } catch (Throwable var2) {
        }

        return null;
    }

    private static String getNetworkClass(int networkType) {
        switch (networkType) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2g";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3g";
            case 13:
                return "4g";
            default:
                return "unknown";
        }
    }

    public static int getNetType() {
        if (AppContext.sContext != null) {
            try {
                ConnectivityManager connManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetInfo = null;
                if (connManager != null) {
                    activeNetInfo = connManager.getActiveNetworkInfo();
                }
                if (activeNetInfo != null) {
                    return activeNetInfo.getType();
                }
            } catch (Exception var2) {

            }
        }

        return -1;
    }

    public static int getMobileNetType() {
        if (AppContext.sContext != null) {
            try {
                TelephonyManager manager = (TelephonyManager) AppContext.sContext.getSystemService(Context.TELEPHONY_SERVICE);
                return manager != null ? manager.getNetworkType() : -1;
            } catch (Exception var1) {
            }
        }

        return -1;
    }

    public static boolean isWifi() {
        return "wifi".equals(getNetWorkType());
    }

    public static boolean isMobile() {
        return "mobile".equals(getNetWorkType());
    }

    public static boolean is4G() {
        return getNetWorkStatus() == 4;
    }

    public static int getAPNPointType() {
        byte type = -1;

        try {
            ConnectivityManager connManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfoActivity = null;
            if (connManager != null) {
                mobNetInfoActivity = connManager.getActiveNetworkInfo();
            }
            if (mobNetInfoActivity != null && mobNetInfoActivity.isAvailable()) {
                int netType = mobNetInfoActivity.getType();
                if (netType == 1) {
                    type = 6;
                } else if (netType == 0) {
                    Cursor c = AppContext.sContext.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
                    String netMode;
                    if (c != null) {
                        c.moveToFirst();
                        netMode = c.getString(c.getColumnIndex("user"));
                        if (!TextUtils.isEmpty(netMode)) {
                            if (netMode.startsWith("ctwap")) {
                                type = 5;
                            }
                        }

                        c.close();
                    }

                    netMode = mobNetInfoActivity.getExtraInfo();
                    if (netMode != null) {
                        netMode = netMode.toLowerCase();
                        if (netMode.equals("cmwap")) {
                            type = 1;
                        } else if (netMode.equals("3gwap")) {
                            type = 2;
                        } else if (netMode.equals("uniwap")) {
                            type = 3;
                        }
                    }
                }
            } else {
                type = 0;
            }
        } catch (Exception var6) {
            type = 6;
        }

        return type;
    }

    public static boolean isWap() {
        int apnType = getAPNPointType();
        return apnType == 2 || apnType == 1 || apnType == 5 || apnType == 3;
    }

    public static boolean isCMWAP() {
        String currentAPN = "";
        if (AppContext.sContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getNetworkInfo(0);
            currentAPN = info.getExtraInfo();
            if (currentAPN != null && currentAPN != "") {
                return currentAPN.toLowerCase().equals("cmwap");
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isWifiOpen() {
        if (AppContext.sContext == null) {
            return false;
        } else {
            WifiManager mWifiManager = (WifiManager) AppContext.sContext.getSystemService(Context.WIFI_SERVICE);
            return mWifiManager != null && mWifiManager.isWifiEnabled();
        }
    }

    public static boolean isNetworkAvailable() {
        if (AppContext.sContext == null) {
            return false;
        } else {
            ConnectivityManager connectivityManager = (ConnectivityManager) AppContext.sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            } else if (Build.VERSION.SDK_INT < 21) {
                return isNetworkAvailableApiBelow21(connectivityManager);
            } else {
                try {
                    Network[] networks = connectivityManager.getAllNetworks();
                    NetworkInfo networkInfo = null;
                    if (networks != null) {
                        Network[] var3 = networks;
                        int var4 = networks.length;

                        for (int var5 = 0; var5 < var4; ++var5) {
                            Network mNetwork = var3[var5];

                            try {
                                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                            } catch (Exception var8) {
                                ;
                            }

                            if (networkInfo != null && NetworkInfo.State.CONNECTED.equals(networkInfo.getState())) {
                                return true;
                            }
                        }
                    }

                    return false;
                } catch (NoSuchMethodError var9) {
                    return isNetworkAvailableApiBelow21(connectivityManager);
                }
            }
        }
    }

    private static boolean isNetworkAvailableApiBelow21(ConnectivityManager connectivityManager) {
        NetworkInfo[] info = null;

        try {
            info = connectivityManager.getAllNetworkInfo();
        } catch (Exception var3) {
            return false;
        }

        if (info != null) {
            for (int i = 0; i < info.length; ++i) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getIpAddress() {
        try {
            Enumeration en = NetworkInterface.getNetworkInterfaces();

            label40:
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration enumIpAddr = intf.getInetAddresses();

                while (true) {
                    InetAddress inetAddress;
                    String ip;
                    String[] splits;
                    do {
                        do {
                            if (!enumIpAddr.hasMoreElements()) {
                                continue label40;
                            }

                            inetAddress = (InetAddress) enumIpAddr.nextElement();
                            ip = inetAddress.getHostAddress();
                            splits = ip.split("\\.");
                        } while (inetAddress.isLoopbackAddress());
                    } while (inetAddress.isLinkLocalAddress());

                    try {
                        Integer.parseInt(splits[0]);
                        return ip;
                    } catch (Exception var7) {
                        ;
                    }
                }
            }
        } catch (Exception var8) {
            ;
        }

        return "";
    }

    public static boolean isIPHost(String host) {
        String regex = "(?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
        Pattern mPattern = null;
        mPattern = Pattern.compile(regex);
        Matcher matcher = mPattern.matcher(host);
        return matcher.matches();
    }
}
