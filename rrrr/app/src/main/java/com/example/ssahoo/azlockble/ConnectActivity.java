package com.example.ssahoo.azlockble;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;


public class ConnectActivity extends AppCompatActivity implements OnDataSendListener, BLeDisconnect {
    private Context mContext;
    // private AppContext appContext;
    private TextView networkStatus;
    LinearLayout internetLinearLayout;
    LinearLayout bleLinearLayout;
    private int result;
    Button connect;
    AlertDialog dialog;
    // ImageView connectInternet, connectWifi;
    private final String TAG = ConnectActivity.class.getSimpleName();
    public static final String CONNECTION_MODE_ARGUMENT = "data";
    private NetworkChangeReceiver receiver;
    IntentFilter filter;
    public static final int GATT_PROFILE_READY = 10;
    public static final int GATT_PROFILE_CONNECTED = 20;
    public static final int GATT_PROFILE_DISCONNECTED = 21;
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_DANGEROUS_PERMISSION = 11;
    private static final int REQUEST_OWNER_REGISTRATION = 1010;
    private static final int REQUEST_REMOTE_CONNECT_ACTIVITY = 75;
    public static final String BATTERY_STATUS_EXTRA = "batteryStatus";
    public static final String TAMPER_STATUS_EXTRA = "tamperStatus";
    boolean locationPermission, isAboveVersion6;
    boolean readPhoneStatePermission, smsPermission;
    boolean shouldShowLocationPermissionRationale;
    boolean shouldPhoneStatePermissionRationale;
    boolean shouldShowSmsPermissionRationale;
    private TelephonyManager telephonyManager;
    private int mState = GATT_PROFILE_DISCONNECTED;
    private BleMessagingService mBleService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private boolean isGuestRegistered, isOwnerRegistered;

    private IntentFilter smsSentIntentFilter, smsReportIntentFilter;
    private boolean isSmsDeliveryReportBroadcastReceiverSet, isSmsSentBroadcastReceiverSet;
    private OnDataAvailableListener mOnDataAvailableListener;
    private int batteryStatus;
    private boolean isTimeOk;
    private ProgressDialog pDialog;
    private IntentFilter intentFilter;
    private AppContext appContext;
    private SessionManager sessionManager;
    private LocationManager locationManager;
    private LocationListener listener;

    String data = "000000kg";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mContext = this;

        intentFilter = new IntentFilter();

        appContext =appContext.getContext();
        sessionManager = new SessionManager(this);
        appContext.setOnDataSendListener(this);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Intent homeIntent = new Intent(ConnectActivity.this, WeightMsg.class);
        homeIntent.putExtra(BATTERY_STATUS_EXTRA, batteryStatus);
        Utils.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBtAdapter = Utils.bluetoothAdapter;
        if (mBtAdapter == null) {
            Log.e(TAG, "BT adapter null");
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        isAboveVersion6 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;


        setContentView(R.layout.welcome);
        connect = (Button) findViewById(R.id.continue_button);
        /*networkStatus = (TextView) findViewById(R.id.messageTextView);
        internetLinearLayout = (LinearLayout) findViewById(R.id.internet_linearLayout);
        bleLinearLayout = (LinearLayout) findViewById(R.id.ble_linearLayout);
        connectInternet = (ImageView) findViewById(R.id.connect_internet_imageView);
        //((TextView) findViewById(R.id.imei_textView)).setText("IMEI - "+appContext.getImei());
        int color = Color.parseColor("#FFFFFF");
        connectInternet.setColorFilter(color);
        connectWifi = (ImageView) findViewById(R.id.connect_wifi_imageView);
        connectWifi.setColorFilter(color);

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();*/
        Log.d(TAG, "Started");

       /* smsSender=new SmsSender(this);
        smsSentIntentFilter = new IntentFilter(SmsSender.SENT);
        smsReportIntentFilter = new IntentFilter(SmsSender.DELIVERED);
        smsSentBroadcastReceiver = new SmsSentBroadcastReceiver(this, new SmsSender.SmsStatusListener()
        {
            @Override
            public void onSmsStatusChange(int resultCode, String message)
            {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
        });
        smsDeliveryReportBroadcastReceiver = new SmsDeliveryReportBroadcastReceiver(this, new SmsSender.SmsStatusListener()
        {
            @Override
            public void onSmsStatusChange(int resultCode, String message)
            {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
        });*/

        pDialog = new ProgressDialog(mContext);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);


        WeightMsg.setBleDisconnect(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              //  connect.append("\n " + location.getLongitude() + " " + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);


            }
        };




    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }*/


  /*  public void onClickInternetLayout(View v)
    {
        Log.d(TAG, "CONNECTION_MODE_REMOTE");
        if(isRouterConfigured())
        {
            appContext.setConnectionMode(ConnectionMode.CONNECTION_MODE_REMOTE);
            connect();
        }
        else
        {
            Toast.makeText(mContext, "Router not configured", Toast.LENGTH_LONG).show();
        }
    }*/

    public void onClickLayout(View v) {
        Log.d(TAG, "CONNECTION_MODE_BLE");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        connect();

    }


    public void connect()
    {
       Log.d(TAG, "onResume/else");
        if (isAboveVersion6 && !(locationPermission && readPhoneStatePermission)) {
            Log.d(TAG, "requesting Dangerous Permission");
            requestDangerousPermission();
         if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        else {

            if ((isAboveVersion6 && locationPermission && readPhoneStatePermission) || !isAboveVersion6) {
                //doBindBleMessagingService();
                //appContext.setImei(telephonyManager.getDeviceId());
                // Log.d(TAG, "DeviceId (IMEI):"+appContext.getImei());

                Intent newIntent = new Intent(this, DeviceListActivity.class);
                doBindBleMessagingService();
                startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
            }
        }

    }}


    public void requestDangerousPermission()
    {
        locationPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        readPhoneStatePermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED);
        smsPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED);

        if (!locationPermission || !readPhoneStatePermission || !smsPermission) {
            shouldShowLocationPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            shouldPhoneStatePermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE) ;
            shouldShowSmsPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS) ;
            Log.d("MainActivity", "Permission Rationale:" + shouldShowLocationPermissionRationale);
            // Should we show an explanation?
            if (shouldShowLocationPermissionRationale || shouldPhoneStatePermissionRationale || shouldShowSmsPermissionRationale) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this).setTitle("Permission Denied")
                        .setCancelable(false)
                        .setMessage("Without these permissions the app is unable to use Wi-Fi and can not store any data on this device. Are you sure you want to deny these permissions?")
                        .setPositiveButton("I'M SURE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Process.killProcess(Process.myPid());
                                System.exit(0);
            }
        })
                        .setNegativeButton("RETRY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(ConnectActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                                        REQUEST_DANGEROUS_PERMISSION);
                            }
                        }).create().show();

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE},
                        REQUEST_DANGEROUS_PERMISSION);


                // REQUEST_DANGEROUS_PERMISSION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
       /* else
        {
           connect();
        }
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_DANGEROUS_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                boolean isGranted = true;
                for(int i=0;i<grantResults.length;i++){
                    isGranted = isGranted && grantResults[i] == PackageManager.PERMISSION_GRANTED;
                }
                if (isGranted) {
                    locationPermission =true;
                    readPhoneStatePermission=true;
                    smsPermission=true;
                    //connect();
                    // doBindBleMessagingService();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                }
                else {
                    Log.e(TAG,"Permission not Granted");
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

   @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(mContext).setMessage("Do you want to exit?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.exit();
                         finish();
                        // sessionManager.exit();
                    }
                })
                .setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

   @Override
    protected void onStart()
    {
        super.onStart();
       // registerReceiver(receiver, filter);
       // registerReceiver(logoutBroadcastReceiver, intentFilter);
        Log.d(TAG, "onStart() Called");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
       // unregisterReceiver(receiver);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause() Called");
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG, "onDestroy() Called");
        if(mBleService !=null) {
            try {
              //  LocalBroadcastManager.getInstance(this).unregisterReceiver(BleStatusChangeReceiver);
            } catch (Exception ignore) {
                Log.e(TAG, ignore.toString());
            }
            unbindService(mBleServiceConnection);
            mBleService.stopSelf();
            mBleService = null;
        }
      /*  if(smsSentBroadcastReceiver!=null && isSmsSentBroadcastReceiverSet) {
            unregisterReceiver(smsSentBroadcastReceiver);
        }
        if(smsDeliveryReportBroadcastReceiver!=null && isSmsDeliveryReportBroadcastReceiverSet) {
            unregisterReceiver(smsDeliveryReportBroadcastReceiver);
        }
        if(logoutBroadcastReceiver!=null){
            unregisterReceiver(logoutBroadcastReceiver);
        }*/
       /* ((BitmapDrawable)connectInternet.getDrawable()).getBitmap().recycle();
        connectInternet.setImageDrawable(null);
        connectInternet.setImageBitmap(null);
        ((BitmapDrawable)connectWifi.getDrawable()).getBitmap().recycle();
        connectWifi.setImageDrawable(null);
        connectWifi.setImageBitmap(null);
        Runtime.getRuntime().gc();*/
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

                    pDialog.setMessage("Connecting...");
                    pDialog.show();
                    Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "\nmserviceValue" + mBleService);
                    //((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - connecting");
                    mBleService.connect(deviceAddress);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();
                    connect();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                    //onExit();
                }
                break;
           /* case REQUEST_OWNER_REGISTRATION:
                int registrationSuccessExtra=Integer.MIN_VALUE, renameDoorExtra=Integer.MIN_VALUE;
                if(data!=null){
                    registrationSuccessExtra=data.getIntExtra(RegisterOwnerActivity.REGISTRATION_SUCCESS_EXTRA, Integer.MIN_VALUE);
                    renameDoorExtra=data.getIntExtra(RegisterOwnerActivity.RENAME_SUCCESS_EXTRA, Integer.MIN_VALUE);
                }
                if(resultCode == RESULT_OK) {
                    if(registrationSuccessExtra== Utils.SUCCESS) {
                        Log.d(TAG, "Registration Successful");
                        Intent homeIntent = new Intent(this, HomeActivity.class);
                        homeIntent.putExtra(BATTERY_STATUS_EXTRA, batteryStatus);
                        startActivity(homeIntent);
                    }
                }
                else {
                    Log.d(TAG, "RESULT_CANCELLED");
                }
                break;
            case REQUEST_REMOTE_CONNECT_ACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    String handshakePacket = data.getStringExtra("HANDSHAKE_PACKET");
                    doorID = data.getStringExtra("DOOR_ID");
                    doorName = data.getStringExtra("DOOR_NAME");
                    authenticate(handshakePacket, doorID, doorName);
                }
                break;*/
        }
    }

    @Override
    public void onSend(String data) {


        byte[] ar=data.getBytes(Charset.forName("UTF-8"));
        //byte[] ar=new byte[Byte.parseByte("Hi")];
        //ar[]= Byte.parseByte("hi");
        Log.d(TAG, "onSend/Sent Packet");
            mBleService.writeRXCharacteristic(ar);
       // mBleService.writeRXCharacteristic(a);

      //  mBleService.writeRXCharacteristic(ar.length);

           // Utils.printByteArray(data);

    }










   /* public void onSend(byte[] data, OnDataAvailableListener onDataAvailableListener, final String progressMessage) {

        mOnDataAvailableListener = onDataAvailableListener;

       /* if(appContext.getConnectionMode()==ConnectionMode.CONNECTION_MODE_BLE) {
            try {
                mBleService.writeRXCharacteristic(data);
                String s = new String(data, "ISO-8859-1");
                Log.d(TAG, "Sent Packet:" + s);
                Utils.printByteArray(data);
            } catch (java.io.UnsupportedEncodingException e) {
                Log.d(TAG, "Unsupported String Encoding Exception");
            }
        }*/
        /*else if(appContext.getConnectionMode()==ConnectionMode.CONNECTION_MODE_REMOTE){
            Log.e(TAG, "Connecting [Address="+appContext.getRouterInfo().getAddress()+" Port="
                    +appContext.getRouterInfo().getPort()+"]");
            new NetClientAsyncTask(ConnectActivity.this, appContext.getRouterInfo().getAddress(),
                    appContext.getRouterInfo().getPort(), data, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(int resultCode, String value) {
                    Log.d(TAG, "onTaskCompleted:"+value);
                    try {
                        Utils.printByteArray(value.getBytes("ISO-8859-1"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(resultCode== Activity.RESULT_OK) {
                        mOnDataAvailableListener.onDataAvailable(value);
                    }
                    else
                    {
                        if(NetClientAsyncTask.ERROR_CODE==NetClientAsyncTask.SOCKET_NOT_CONNECTED)
                        {
                            Log.e(TAG, "onTaskCompleted: SOCKET_NOT_CONNECTED"+value);
                            //Snackbar.make(findViewById(android.R.id.content), "Not connected", Snackbar.LENGTH_LONG).show();
                        }
                        else if(NetClientAsyncTask.ERROR_CODE==NetClientAsyncTask.MESSAGE_NOT_RECEIVED)
                        {
                            Log.e(TAG, "onTaskCompleted: SOCKET_NOT_CONNECTED"+value);
                            //Snackbar.make(findViewById(android.R.id.content), "Timeout occurred", Snackbar.LENGTH_LONG).show();
                        }
                        mOnDataAvailableListener.onDataAvailable(null);
                    }
                }
            }, null).execute();
        }
    }*/


    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if(isNetworkAvailable(context)){
                internetLinearLayout.setClickable(true);
                networkStatus.setText("Internet Connected");
                networkStatus.setTextColor(Color.WHITE);
            } else {
                internetLinearLayout.setClickable(false);
                networkStatus.setTextColor(Color.RED);
                networkStatus.setText("No Internet Connection");
            }
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();

        }
    }

   /* public boolean isRouterConfigured(){
        DatabaseHandler databaseHandler = new DatabaseHandler(mContext);
        int c = databaseHandler.getRouterConfiguredDoors().size();
        databaseHandler.close();
        return  c > 0;
    }*/

    private void doBindBleMessagingService() {
        Intent bindIntent = new Intent(this, BleMessagingService.class);
        bindService(bindIntent, mBleServiceConnection, Context.BIND_AUTO_CREATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(BleStatusChangeReceiver, makeGattUpdateIntentFilter());
    }
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BleMessagingService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BleMessagingService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BleMessagingService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BleMessagingService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BleMessagingService.DEVICE_DOES_NOT_SUPPORT_BLE);
        return intentFilter;
    }

    private ServiceConnection mBleServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mBleService = ((BleMessagingService.LocalBinder) rawBinder).getService();
            Log.d(TAG, "onServiceConnected mBleService= " + mBleService);
            if (!mBleService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName classname) {
            ////     mBleService.disconnect(mDevice);
            Log.e(TAG, "BleMessagingService disconnected");
            mBleService = null;
        }
    };

    private Handler mHandler = new Handler() {
        @Override

        //Handler events that received from BLE service
        public void handleMessage(Message msg) {
            Log.d(TAG, "mHandler/Message:"+msg);
        }
    };

    //private String doorID, doorName;
    private BleBroadcastReceiver BleStatusChangeReceiver = new BleBroadcastReceiver(this, new OnReceiveListener() {
        @Override
        public void onConnect() {

            Log.d(TAG, "Ble connected");
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            mBleService.enableTXNotification();
            //Log.d(TAG,"Ble Discovered");
            appContext.setConnectionStatus(true);
            onSend("W");
            Intent k = new Intent(ConnectActivity.this, WeightMsg.class);
            k.putExtra("data", data);
            startActivity(k);

           /*Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {


                }
            }, 2000);*/


        }

        @Override
        public void onBackPressed() {
            moveTaskToBack(true);
            finish();
        }


        @Override
        public void onDisconnect() {
            Log.e(TAG, "BLE_DISCONNECT_MSG");
        }




        @Override
        public void onDataAvailable(byte responseData[]) {
            //byte data[] = new byte[4];
            int weight = 0;
            float dat=0;
            if (((char) responseData[0]) == 'W') {
                weight = (((byte) responseData[1] & 0xFF) << 24) | (((byte) responseData[2] & 0xFF) << 16) | (((byte) responseData[3] & 0xFF) << 8) | ((byte) responseData[4] & 0xFF);
                dat= (float) weight/1000;
                //Log.d("aa", "weight = " + weight);

                data = String.format("%.3f",dat);
                //data += " Kg";
                dataListener.onData(data);


            }
            else {
                String str = new String(responseData);
                mOnDataAvailableListener.onDataAvailable(str);
            }
           // Toast.makeText(ConnectActivity.this, data, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onDataAvailable: " +data);
           // Intent k = new Intent(.this, WeightMsg.class);
           // k.putExtra("data", data);
          //  startActivity(k);
            }




        /* @Override
         public void onServicesDiscovered() {
             mBleService.enableTXNotification();
             Log.d(TAG,"Ble Discovered");
              onSend();
            Intent k = new Intent(ConnectActivity.this, WeightMsg.class);
             k.putExtra("data", data);
             startActivity(k);
         }*/
        @Override
        public void onError(int errorCode) {
            //showMessage("Device doesn't support BLE. Disconnecting");
            Log.e(TAG, "BLE error occurred");
            mBleService.disconnect();
        }
    });


        @Override
    public void onResume() {
        Log.d(TAG, "onResume()");
        /*if(sessionManager.verify()){
            finish();
        }*/
        super.onResume();
    }

    public void disconnect(){
        mBleService.disconnect();
        appContext.setConnectionStatus(false);
    }

    static Data dataListener;
    public static void setData(Data d){
        dataListener=d;
    }


    public void onSend(byte[] data, OnDataAvailableListener onDataAvailableListener,
                       final String progressMessage) {

        mOnDataAvailableListener = onDataAvailableListener;

      //  if(appContext.getConnectionMode()==ConnectionMode.CONNECTION_MODE_BLE) {
            try {
                mBleService.writeRXCharacteristic(data);
                String s = new String(data, "ISO-8859-1");
                Log.d(TAG, "Sent Packet:" + s);
               // Utils.printByteArray(data);
            } catch (java.io.UnsupportedEncodingException e) {
                Log.d(TAG, "Unsupported String Encoding Exception");
         //   }
        }
        /*else if(appContext.getConnectionMode()==ConnectionMode.CONNECTION_MODE_REMOTE){
            Log.w(TAG, "Connecting [Address="+appContext.getRouterInfo().getAddress()+" Port="
                    +appContext.getRouterInfo().getPort()+"]");
            NetClientContext clientContext=NetClientContext.getContext();
            NetClientAsyncTask clientAsyncTask=new NetClientAsyncTask(ConnectActivity.this, appContext.getRouterInfo().getAddress(),
                    appContext.getRouterInfo().getPort(), data, new OnTaskCompleted<String>() {
                @Override
                public void onTaskCompleted(int resultCode, String value) {
                    Log.d(TAG, "onTaskCompleted:"+value);
                    try {
                        Utils.printByteArray(value.getBytes("ISO-8859-1"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if(resultCode== Activity.RESULT_OK) {
                        Log.d(TAG,"Sending data to listener");
                        mOnDataAvailableListener.onDataAvailable(value);
                    }
                    else
                    {
                        if(NetClientAsyncTask.ERROR_CODE==NetClientAsyncTask.SOCKET_NOT_CONNECTED)
                        {
                            Log.e(TAG, "onTaskCompleted: SOCKET_NOT_CONNECTED"+value);
                            Toast.makeText(mContext,"Unable to connect", Toast.LENGTH_LONG).show();
                            //Snackbar.make(findViewById(android.R.id.content), "Not connected", Snackbar.LENGTH_LONG).show();
                        }
                        else if(NetClientAsyncTask.ERROR_CODE==NetClientAsyncTask.MESSAGE_NOT_RECEIVED)
                        {
                            Log.e(TAG, "onTaskCompleted: SOCKET_NOT_CONNECTED"+value);
                            Toast.makeText(mContext,"Timeout occurred", Toast.LENGTH_LONG).show();
                            //Snackbar.make(findViewById(android.R.id.content), "Timeout occurred", Snackbar.LENGTH_LONG).show();
                        }
                        mOnDataAvailableListener.onDataAvailable(null);
                    }
                }
            });
            if(progressMessage!=null){
                clientAsyncTask.showProgressDialog(true, progressMessage);
            }
            clientContext.setNetClient(clientAsyncTask);
            clientAsyncTask.execute();

        }*/
    }


}
