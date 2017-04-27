package io.github.projektmedinf.wifisdcryptolocker.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import io.github.projektmedinf.wifisdcryptolocker.activities.SetupActivity;

import java.util.ArrayList;
import java.util.List;

public class WifiScanReceiver extends BroadcastReceiver {

    private CrossfadePageTransformer crossfadePageTransformer;
    private WifiManager wifiManager;

    public WifiScanReceiver(CrossfadePageTransformer crossfadePageTransformer, SetupActivity activity) {
        this.crossfadePageTransformer = crossfadePageTransformer;
        wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        crossfadePageTransformer.setWifiNetworks(getWifiNetworks());
        wifiManager.startScan();
    }

    @SuppressLint("UseValueOf")
    public void onReceive(Context c, Intent intent) {
        ArrayAdapter arrayAdapter = crossfadePageTransformer.getAdapter();
        if (arrayAdapter != null) {
            arrayAdapter.clear();
            arrayAdapter.addAll(getWifiNetworks());
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public List<String> getWifiNetworks() {
        ArrayList<String> wifiNetworks = new ArrayList<>();
        for (ScanResult scanResult : wifiManager.getScanResults()) {
            if (scanResult.SSID.length() != 0) {
                wifiNetworks.add(scanResult.SSID);
            }
        }
        return wifiNetworks;
    }

    public void finallyConnect(String networkPass, String networkSSID) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        // remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        wifiManager.addNetwork(conf);
    }
}