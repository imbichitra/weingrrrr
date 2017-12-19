package com.example.ssahoo.azlockble;

/**
 * Created by Somnath on 7/5/2016.
 */
public interface OnDataSendListener {
    void onSend(String data);

    void onSend(byte[] data, OnDataAvailableListener onDataAvailableListener, String progressMessage);
}
