package com.example.ssahoo.azlockble;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WeightMsg extends AppCompatActivity implements Data {

    private String filename = "SampleFile.txt";
    private String filepath = "MyFileStorage";
    File myExternalFile;
    SimpleDateFormat dateFormat;
    OutputStreamWriter outStreamWriter = null;
    String myData = "";
    String currentDateTime;
    private static final String TAG = " ";
    Button Button2,Button3,Button4;
    TextView tv;
    private BleMessagingService bleMessagingService;
    public static BLeDisconnect disconnect;
    Boolean isGetWeight = false;
    private IntentFilter intentFilter;
    public String cal;
    private ImageView batteryIcon;
    private TextView doorNameTextView,lockAccessTextView,batteryPercentageTextView;
    private AppContext appContext;
    private SessionManager sessionManager;
    private static OnDataSendListener mOnDataSendListener;
    private OnReceiveListener mOnDataAvailableListener;
    private LogoutBroadcastReceiver logoutBroadcastReceiver;
    private  String data="";
    private String messag=null;
    private Context mContext;
    private int batteryStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_msg);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("azWeigh");
        tv = (TextView) findViewById(R.id.textView1);
        mContext = this;
        appContext = AppContext.getContext();
        sessionManager = new SessionManager(this);
        logoutBroadcastReceiver = new LogoutBroadcastReceiver(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(SessionManager.ACTION_DISCONNECTED);
        intentFilter.addAction(SessionManager.ACTION_EXIT);

        batteryIcon = (ImageView) findViewById(R.id.battery_imageView);
        batteryPercentageTextView = (TextView) findViewById(R.id.battery_percent_textView);
        mOnDataSendListener = appContext.getOnDataSendListener();


        appContext.setOnDataSendListener(mOnDataSendListener);
        //mOnDataSendListener = appContext.getOnDataSendListener();

        batteryStatus = getIntent().getIntExtra(ConnectActivity.BATTERY_STATUS_EXTRA, Integer.MIN_VALUE);
        // tv.setText(getIntent().getExtras().getString("data"));
        dateFormat = new SimpleDateFormat("  yyyy-MM-dd  HH:mm:ss");

        ConnectActivity.setData(this);


        // button1 = (Button) findViewById(R.id.Button1);
        Button2 = (Button) findViewById(R.id.button2);
        Button3 = (Button) findViewById(R.id.button3);
        Button4=(Button)findViewById(R.id.button4);

        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "T";
                byte[] data = s.getBytes(Charset.forName("UTF-8"));
                mOnDataSendListener.onSend(data, new OnDataAvailableListener() {
                    public static final String TAG =" " ;

                    @Override
                    public void onDataAvailable(String data) {
                        // setUiProgressStatus(View.INVISIBLE);
                        // processRegisterPacket(data);


                        Log.d(TAG,"Data Send ");
                    }
                }, "Connecting...");

            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDateTime = dateFormat.format(Calendar.getInstance().getTime());

                try {
                    FileOutputStream fos = new FileOutputStream(myExternalFile,true);
                    outStreamWriter = new OutputStreamWriter(fos);
                    // outStreamWriter.append(tv.getText().toString().getBytes())
                    fos.write(tv.getText().toString().getBytes());
                    fos.write(currentDateTime.getBytes(Charset.forName("UTF-8")));

                    fos.write("\n".getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // inputText.setText("");
                Toast.makeText(getApplicationContext(), "weight stored", Toast.LENGTH_SHORT).show();
            }
        });
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Button2.setEnabled(false);
        }
        else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }


        Button4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayBatteryStatus(batteryStatus);
            mOnDataSendListener.onSend("W");
            String s = "B";
            byte[] data = s.getBytes(Charset.forName("UTF-8"));
            Log.d(TAG,"DTTTT " +data);
            mOnDataSendListener.onSend(data, new OnDataAvailableListener() {
                public static final String TAG =" " ;

                @Override
                public void onDataAvailable(String data) {
                    // setUiProgressStatus(View.INVISIBLE);
                    // processRegisterPacket(data);
                    batteryStatus = Utils.parseInt(data,0);

                }
            }, "Connecting...");

        }
    });}
    @Override
    protected void onResume()
    {
        if(!appContext.isConnected()){
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        registerReceiver(logoutBroadcastReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(logoutBroadcastReceiver);
        super.onDestroy();
    }

    public static void setBleDisconnect(BLeDisconnect dis){
        disconnect=dis;
    }



    @Override
    public void onData(String arg) {
        tv.setText(arg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.Calibration:

                // get prompts.xml view
                //mOnDataSendListener.onSend("CA");
                String s="CA";
                byte[] data = s.getBytes(Charset.forName("UTF-8"));
                mOnDataSendListener.onSend(data, new OnDataAvailableListener() {
                    @Override
                    public void onDataAvailable(String data) {
                        Log.d("WeightMsg", "data : " + data);
                        Log.d("WeightMsg","Data Send");
                        if(data.equals("AOK")){
                            showInputDialog();
                        }
                    }
                }, "Connecting...");

                //showInputDialog();
                return true;

            case R.id.History:
                Intent intent=new Intent(WeightMsg.this,history.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_disconnect) {
            mBTLE_Service.disconnect();
            return true;
        }*/

    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(WeightMsg.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(WeightMsg.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String cal = "CB" + editText.getText().toString();
                        mOnDataSendListener.onSend(cal);
                        Toast.makeText(WeightMsg.this, cal, Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        android.app.AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    public void displayBatteryStatus(int percentage)
    {
        Log.d(TAG, "Battery Percentage:"+percentage);
        batteryIcon.setVisibility(View.VISIBLE);
        batteryPercentageTextView.setVisibility(View.VISIBLE);
        batteryPercentageTextView.setText(String.valueOf(percentage) + "%");
        if (percentage < 10)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_alert_black_36dp);
            int color = Color.parseColor("#ffcc0000");
            batteryIcon.setColorFilter(color);
        }
        if (percentage >= 10 && percentage < 25)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_20_black_36dp);
        }
        else if (percentage >= 25 && percentage < 40)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_30_black_36dp);
        }
        else if (percentage >= 40 && percentage < 55)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_50_black_36dp);
        }
        else if (percentage >= 55 && percentage < 70)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_60_black_36dp);
        }
        else if (percentage >= 70 && percentage < 85)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_80_black_36dp);
        }
        else if (percentage >= 85 && percentage < 95)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_90_black_36dp);
        }
        else if (percentage >= 95 && percentage <= 100)
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_full_black_36dp);
        }
        else
        {
            batteryIcon.setImageResource(R.mipmap.ic_battery_unknown_black_36dp);
        }

        if (percentage <= 20)
        {
            new AlertDialog.Builder(mContext).setTitle("Warning")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(Html.fromHtml("<font color='#FF0000' line-height='40px'>Battery is critically low. Please replace the battery for further use.</font>"))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).create().show();
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }



}

