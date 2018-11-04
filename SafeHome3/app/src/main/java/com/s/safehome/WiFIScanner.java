package com.s.safehome;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WiFIScanner extends AppCompatActivity {

    //TextView tvWifiState;
    //TextView tvScanning, tvResult;

    //ArrayList<InetAddress> inetAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fiscanner);

        final ArrayList<InetAddress> inetAddresses;
        final TextView tvWifiState = (TextView)findViewById(R.id.WifiState);
        final TextView tvScanning = (TextView)findViewById(R.id.Scanning);
        final TextView tvResult = (TextView)findViewById(R.id.Result);

        //To prevent memory leaks on devices prior to Android N,
        //retrieve WifiManager with
        //getApplicationContext().getSystemService(Context.WIFI_SERVICE),
        //instead of getSystemService(Context.WIFI_SERVICE)
        WifiManager wifiManager =
                (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
       // tvWifiState.setText(readtvWifiState(wifiManager));

        //new ScanTask(tvScanning, tvResult).execute();
    }

    // "android.permission.ACCESS_WIFI_STATE" is needed
    /*private String readtvWifiState(WifiManager wm){
        String result = "";
        switch (wm.getWifiState()){
            case WifiManager.WIFI_STATE_DISABLED:
                result = "WIFI_STATE_DISABLED";
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                result = "WIFI_STATE_DISABLING";
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                result = "WIFI_STATE_ENABLED";
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                result = "WIFI_STATE_ENABLING";
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                result = "WIFI_STATE_UNKNOWN";
                break;
            default:
        }
        return result;
    }

    private class ScanTask extends AsyncTask<Void, String, Void> {

        TextView tvCurrentScanning, tvScanResullt;
        ArrayList<String> canonicalHostNames;

        public ScanTask(TextView tvCurrentScanning, TextView tvScanResullt) {
            this.tvCurrentScanning = tvCurrentScanning;
            this.tvScanResullt = tvScanResullt;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            tvCurrentScanning.setText("Finished.");
            tvScanResullt.setText("");
            for(int i = 0; i < inetAddresses.size(); i++){
                tvScanResullt.append(canonicalHostNames.get(i) + "\n");
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            scanInetAddresses();
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvCurrentScanning.setText(values[0]);
        }

        private void scanInetAddresses(){
            //May be you have to adjust the timeout
            final int timeout = 500;

            if(inetAddresses == null){
                inetAddresses = new ArrayList<>();
            }
            inetAddresses.clear();

            if(canonicalHostNames == null){
                canonicalHostNames = new ArrayList<>();
            }
            canonicalHostNames.clear();

            //For demonstration, scan 192.168.1.xxx only
            byte[] ip = {(byte) 192, (byte) 168, (byte) 1, 0};
            for (int j = 0; j < 255; j++) {
                ip[3] = (byte) j;
                try {
                    InetAddress checkAddress = InetAddress.getByAddress(ip);
                    publishProgress(checkAddress.getCanonicalHostName());
                    if (checkAddress.isReachable(timeout)) {
                        inetAddresses.add(checkAddress);
                        canonicalHostNames.add(checkAddress.getCanonicalHostName());
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    publishProgress(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    publishProgress(e.getMessage());
                }
            }
        }
    }*/

    }


