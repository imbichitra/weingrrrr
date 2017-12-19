package com.example.ssahoo.azlockble;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by user on 11/30/2015.
 */
public class SessionManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private Activity activity;
    private AppContext appContext;
    private OnSessionBroadcastListener onSessionBroadcastListener;
    private RemoteDisconnect remoteDisconnect;
    private String TAG=this.getClass().getSimpleName();

    private final String PREFERENCE_NAME = "com.asiczen.azlock.content.SessionDetails";
    private final String IS_LOGGED_IN = "com.asiczen.azlock.content.isLoggedIn";
    public static final String USER_ID = "com.asiczen.azlock.content.userId";
    public static final String USER_NAME = "com.asiczen.azlock.content.userName";
    public static final String USER_PASSWORD = "com.asiczen.azlock.content.userPassword";
    public static final String CAMP_ID = "com.asiczen.azlock.content.campId";
    public static final String APP_MODE = "mAppMode";

    public static final String ACTION_LOGOUT = "com.asiczen.azlock.content.ACTION_LOGOUT";
    public static final String ACTION_DISCONNECTED = "com.asiczen.azlock.content.ACTION_DISCONNECTED";
    public static final String ACTION_EXIT = "om.asiczen.azlock.content.ACTION_EXIT";

    public SessionManager(Activity activity){
        this.mContext = activity;
        this.activity=activity;
        appContext=AppContext.getContext();
        preferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        onSessionBroadcastListener=null;
    }

    public SessionManager(Activity activity, OnSessionBroadcastListener onSessionBroadcastListener){
        this.mContext = activity;
        this.activity=activity;
        this.onSessionBroadcastListener=onSessionBroadcastListener;
        appContext=AppContext.getContext();
        preferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public SessionManager(Activity activity, RemoteDisconnect remoteDisconnect){
        this.mContext = activity;
        this.activity=activity;
        this.remoteDisconnect=remoteDisconnect;
        appContext=AppContext.getContext();
        preferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void create(String userId, String userName, String userPassword, int role, String campId){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(CAMP_ID, campId);
        editor.putString(USER_ID, userId);
        editor.putString(USER_NAME, userName);
        editor.putString(USER_PASSWORD, userPassword);
        editor.putInt(APP_MODE, role);

        editor.commit();
    }

    public boolean verify()
    {
        if(!appContext.isConnected()){
            Intent i = new Intent(mContext, ConnectActivity.class);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
            return true;
        }
        return false;
    }

    public void update(String userId, String userName, String userPassword, int role, String campId){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(CAMP_ID, campId);
        editor.putString(USER_ID, userId);
        editor.putString(USER_NAME, userName);
        editor.putString(USER_PASSWORD, userPassword);
        editor.putInt(APP_MODE, role);

        editor.commit();
    }

    public SessionDetails getSessionDetails(){
        return new SessionDetails();
    }

    public class SessionDetails {

        public String getCampId()
        {
            return preferences.getString(CAMP_ID, null);
        }
        public String getUserId()
        {
            return preferences.getString(USER_ID, null);
        }
        public String getPassword()
        {
            return preferences.getString(USER_PASSWORD, null);
        }
        public String getUserName()
        {
            return preferences.getString(USER_NAME, null);
        }
        public int getRole(){
            return preferences.getInt(APP_MODE, Integer.MIN_VALUE);
        }
    }

    /**
     * Clear session details
     * */
    public void logout(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        appContext.setConnectionStatus(false);
        if(onSessionBroadcastListener!=null){
            onSessionBroadcastListener.onLogout();
        }

        if(remoteDisconnect != null){
            Log.e(TAG, "Disconnecting...");
            remoteDisconnect.disconnect();
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_LOGOUT);
        mContext.sendBroadcast(broadcastIntent);

        // After logout redirect user to Login Activity
        Intent i = new Intent(mContext, ConnectActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }

    public void exit(){

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        Log.e(TAG, "Exiting...");
        /*if(appContext.getConnectionMode()== ConnectionMode.CONNECTION_MODE_REMOTE && appContext.isConnected()){
            Log.e(TAG, "Disconnecting...");
            disconnectRemoteDevice();
        }*/
        appContext.setConnectionStatus(false);
        if(onSessionBroadcastListener!=null){
            Log.e(TAG, "Initialize callback..");
            onSessionBroadcastListener.onExit();
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_EXIT);
        mContext.sendBroadcast(broadcastIntent);

    }

  /*  public void disconnectRemoteDevice(){
        new RemoteDisconnectAsyncTask(activity, new OnTaskCompleted<Boolean>() {
            @Override
            public void onTaskCompleted(int resultCode, Boolean data) {
                exit();
            }
        }, "Disconnecting...").execute(appContext.getDoor().getId());
    }*/

    private boolean isLoggedIn()
    {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public interface OnSessionBroadcastListener{
        void onLogout();
        void onExit();
    }

    public interface RemoteDisconnect {
        void disconnect();
    }
}
