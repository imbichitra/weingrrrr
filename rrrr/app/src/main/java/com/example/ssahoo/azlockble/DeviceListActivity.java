/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ssahoo.azlockble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeviceListActivity extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;

   // private BluetoothAdapter mBtAdapter;
    private TextView mEmptyList;
    private ImageView refreshImageView;
    public static final String TAG = "DeviceListActivity";
    
    List<BluetoothDevice> deviceList;
    private DeviceAdapter deviceAdapter;
    private ArrayAdapter arrayAdapter;
    private ServiceConnection onService = null;
    Map<String, Integer> devRssiValues;
    private static final long SCAN_PERIOD = 15000; // 15 seconds
    private Handler mHandler;
    private boolean mScanning;
    private IntentFilter intentFilter;
  //  private LogoutBroadcastReceiver logoutBroadcastReceiver;
    //private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.hide();
        }
        Log.d(TAG, "onCreate");
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        setContentView(R.layout.ble_device_list);
        //android.view.WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        //layoutParams.gravity=Gravity.CENTER;
        //layoutParams.y = 200;
        mHandler = new Handler();
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = Utils.bluetoothAdapter; //bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null)
        {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        populateList();
        //setProgressBarIndeterminate(true);
        mEmptyList = (TextView) findViewById(R.id.empty);
        //refreshImageView=(ImageView) findViewById(R.id.refresh);
        /*Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	if (mScanning==false) scanLeDevice(true); 
            	else {
                    onBackPressed();
                }
            }
        });*/

 //       sessionManager = new SessionManager(this);
   //     logoutBroadcastReceiver = new LogoutBroadcastReceiver(DeviceListActivity.this);
        intentFilter=new IntentFilter();
     //   intentFilter.addAction(SessionManager.ACTION_LOGOUT);
       // intentFilter.addAction(SessionManager.ACTION_EXIT);

    }

    public void onClickRefreshImageView(View v)
    {
        if (mScanning==false) scanLeDevice(true);
        else
        {
            onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MainActivity", "onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        super.onCreateOptionsMenu(menu);
        MenuItem progressItem = menu.findItem(R.id.miActionProgress);
        MenuItem refreshItem = menu.findItem(R.id.Refresh);
        refreshItem.setVisible(false);
        ProgressBar progressBar =
                (ProgressBar) MenuItemCompat.getActionView(progressItem);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.Refresh:
                if (mScanning==false) scanLeDevice(true);
                else {
                    mOnCancelListener.onCancel(TAG, OnCancelListener.ACTION_EXIT);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        Log.d(TAG, "onPrepareOptionsMenu/mScanning:"+mScanning);
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        MenuItem progressItem = menu.findItem(R.id.miActionProgress);
        MenuItem refreshItem = menu.findItem(R.id.Refresh);
        ProgressBar progressBar =
                (ProgressBar) MenuItemCompat.getActionView(progressItem);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        if(mScanning)
        {
            setSupportProgressBarIndeterminateVisibility(true);
            refreshItem.setVisible(false);
        }
        else
        {
            setSupportProgressBarIndeterminateVisibility(false);
            refreshItem.setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }*/

    private void populateList()
    {
        /* Initialize device list container */
        Log.d(TAG, "populateList:"+deviceList);
        deviceList = new ArrayList<BluetoothDevice>();
        deviceAdapter = new DeviceAdapter(this, deviceList);
        //arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList);
        devRssiValues = new HashMap<String, Integer>();

        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(deviceAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        scanLeDevice(true);

    }
    
    private void scanLeDevice(final boolean enable)
    {
        //final Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        refreshImageView=(ImageView) findViewById(R.id.refresh);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbProgressBar);
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
					mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mEmptyList.setText("No device Found");
                    //cancelButton.setText(R.string.scan);
                    progressBar.setVisibility(View.INVISIBLE);
                    refreshImageView.setVisibility(View.VISIBLE);
                    //invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            //invalidateOptionsMenu();
            if(mEmptyList!=null)
                mEmptyList.setText("Scanning...");
            //cancelButton.setText(R.string.cancel);
            progressBar.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);

            //cancelButton.setText(R.string.scan);
            progressBar.setVisibility(View.INVISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
        }

    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	
                	  runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              addDevice(device,rssi);
                          }
                      });
                   
                }
            });
        }
    };
    
    private void addDevice(BluetoothDevice device, int rssi) {
        boolean deviceFound = false;

        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                break;
            }
        }
        
        
        devRssiValues.put(device.getAddress(), rssi);
        if (!deviceFound) {
        	deviceList.add(device);
            mEmptyList.setVisibility(View.GONE);

            deviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        super.onStart();
    }

    @Override
    public void onStop() {
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy()");
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG,"onBackPressed()");
        //sessionManager.exit();
        super.onBackPressed();
    }

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            BluetoothDevice device = deviceList.get(position);
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
  
            Bundle b = new Bundle();
            b.putString(BluetoothDevice.EXTRA_DEVICE, deviceList.get(position).getAddress());
            b.putString(BluetoothDevice.EXTRA_NAME, deviceList.get(position).getName());

            //MainActivity.activityProgressBar.setVisibility(View.VISIBLE);
            //MainActivity.lockAccessTextView.setVisibility(View.VISIBLE);
            //MainActivity.lockAccessTextView.setText("Connecting...");

            Intent result = new Intent();
            result.putExtras(b);
            setResult(Activity.RESULT_OK, result);
            finish();
        	
        }
    };


    
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
    }
    
    class DeviceAdapter extends BaseAdapter {
        Context context;
        List<BluetoothDevice> devices;
        LayoutInflater inflater;

        public DeviceAdapter(Context context, List<BluetoothDevice> devices) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.devices = devices;
        }

        @Override
        public int getCount() {
            return devices.size();
        }

        @Override
        public Object getItem(int position) {
            return devices.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup vg;

            if (convertView != null) {
                vg = (ViewGroup) convertView;
            } else {
                vg = (ViewGroup) inflater.inflate(R.layout.device_element, null);
            }

            BluetoothDevice device = devices.get(position);
            final TextView tvadd = ((TextView) vg.findViewById(R.id.address));
            final TextView tvname = ((TextView) vg.findViewById(R.id.name));
            final TextView tvpaired = (TextView) vg.findViewById(R.id.paired);
            final TextView tvrssi = (TextView) vg.findViewById(R.id.rssi);

            tvrssi.setVisibility(View.VISIBLE);
            byte rssival = (byte) devRssiValues.get(device.getAddress()).intValue();
            if (rssival != 0) {
                tvrssi.setText("Rssi = " + String.valueOf(rssival));
            }

            tvname.setText(device.getName());
            tvadd.setText(device.getAddress());
            if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                Log.i(TAG, "device::"+device.getName());
                //tvname.setTextColor(Color.WHITE);
                //tvadd.setTextColor(Color.WHITE);
                //tvpaired.setTextColor(Color.GRAY);
                tvpaired.setVisibility(View.VISIBLE);
                tvpaired.setText(R.string.paired);
                tvrssi.setVisibility(View.VISIBLE);
                //tvrssi.setTextColor(Color.WHITE);
            } else {
                //tvname.setTextColor(Color.WHITE);
                //tvadd.setTextColor(Color.WHITE);
                tvpaired.setVisibility(View.GONE);
                tvrssi.setVisibility(View.VISIBLE);
                //tvrssi.setTextColor(Color.WHITE);
            }
            return vg;
        }
    }
    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
