package Hardware;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Markan on 14.02.2017.
 */
public class WiFiConnector {
    WifiManager wifi;
    SupplicantState supplicantState;
    NetworkInfo.DetailedState state;

    public WiFiConnector(Context context) {
        wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        supplicantState = wifi.getConnectionInfo().getSupplicantState();
        state = WifiInfo.getDetailedStateOf(supplicantState);
    }

    public boolean isConnected() {
        return (state == NetworkInfo.DetailedState.CONNECTED
                || state == NetworkInfo.DetailedState.OBTAINING_IPADDR);
    }
}
