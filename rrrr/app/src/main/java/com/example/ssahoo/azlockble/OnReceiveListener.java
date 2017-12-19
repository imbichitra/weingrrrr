package com.example.ssahoo.azlockble;

/**
 * Created by Somnath on 6/22/2016.
 */
public interface OnReceiveListener {
    void onConnect();
    void onBackPressed();
    void onDisconnect();
    void onDataAvailable(byte responseData[]);
   // void onServicesDiscovered();
    void onError(int errorCode);
}
