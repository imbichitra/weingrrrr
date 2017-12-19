package com.example.ssahoo.azlockble;


import android.util.Log;

/**
 * Created by user on 1/21/2016.
 */
public class CommunicationError {

    private static final String TAG = CommunicationError.class.getSimpleName();

    public static final int INVALID_COMMAND = 2;
    public static final int INVALID_ACCESS_MODE = 3;
    public static final int INVALID_CHECKSUM = 4;
    public static final int INVALID_LENGTH = 5;
    public static final int INVALID_OPTION = 6;
    public static final int INVALID_PHONE_TIME = 7;
    public static final int INVALID_ACCESS_TIME = 8;
    public static final int GUEST_MAX_ATTEMPT = 9;
    public static final int GUEST_KEY_NOT_FOUND = 10;
    public static final int GUEST_AUTH_FAILED = 11;
    public static final int OUT_OF_SPACE = 12;
    public static final int DEVICE_NOT_CALIBRATED = 13;
    public static final int NOT_HANDSHAKED = 14;
    public static final int BLE_NOT_FOUND = 15;
    public static final int WRONG_PASSWPRD = 16;
    public static final int AP_NOT_FOUND = 17;
    public static final int CONNECT_FAIL = 18;
    public static final int INVALID_DATA = 19;
    public static final int BLE_ALREADY_DISCONNECTED = 20;
    public static final int BLE_ALREADY_CONNECTED = 21;
    public static final int BLE_NOT_CONNECTED = 22;
    public static final int PREVIOUS_PACKET_NOT_ARRIVED = 23;
    public static final int TOO_MANY_GUESTS = 24;
    public static final int IMEI_ALREADY_EXISTS = 25;
    public static final int NAME_ALREADY_EXISTS = 26;

    private static String message = null;

    public static String getMessage(int errorCode)
    {
        switch (errorCode){
            case INVALID_COMMAND:
                message = "Invalid Command";
                Log.e(TAG, message);
                break;
            case INVALID_ACCESS_MODE:
                message = "Invalid Access Mode";
                Log.e(TAG, message);
                break;
            case INVALID_CHECKSUM:
                message = "Invalid Checksum";
                Log.e(TAG, message);
                break;
            case INVALID_LENGTH:
                message = "Invalid Length";
                Log.e(TAG, message);
                break;
            case INVALID_OPTION:
                message = "Invalid Option";
                Log.e(TAG, message);
                break;
            case INVALID_ACCESS_TIME:
                message = "Invalid Access Time";
                Log.e(TAG, message);
                break;
            case INVALID_PHONE_TIME:
                message = "Invalid Phone Time";
                Log.e(TAG, message);
                break;
            case GUEST_MAX_ATTEMPT:
                message = "Maximum Attempt";
                Log.e(TAG, message);
                break;
            case GUEST_KEY_NOT_FOUND:
                message = "Key not found";
                Log.e(TAG, message);
                break;
            case GUEST_AUTH_FAILED:
                message = "Authentication Failed";
                Log.e(TAG, message);
                break;
            case OUT_OF_SPACE:
                message = "Out of Space";
                Log.e(TAG, message);
                break;
            case DEVICE_NOT_CALIBRATED:
                message = "Device not Calibrated";
                Log.e(TAG, message);
                break;
            case NOT_HANDSHAKED:
                message = "Packet Length Mismatch";
                Log.e(TAG, message);
                break;
            case BLE_ALREADY_CONNECTED:
                message = "Device already connected";
                Log.e(TAG, message);
                break;
            case BLE_ALREADY_DISCONNECTED:
                message = "Device already disconnected";
                Log.e(TAG, message);
                break;
            case BLE_NOT_CONNECTED:
                message = "Device not connected";
                Log.e(TAG, message);
                break;
            case BLE_NOT_FOUND:
                message = "Device not found";
                Log.e(TAG, message);
                break;
            case CONNECT_FAIL:
                message = "Connection failed";
                Log.e(TAG, message);
                break;
            case INVALID_DATA:
                message = "Invalid data";
                Log.e(TAG, message);
                break;
            case WRONG_PASSWPRD:
                message = "Wrong password";
                Log.e(TAG, message);
                break;
            case PREVIOUS_PACKET_NOT_ARRIVED:
                message = "previous packet not arrived";
                Log.e(TAG, message);
                break;
            case AP_NOT_FOUND:
                message = "AP not found";
                Log.e(TAG, message);
                break;
            case TOO_MANY_GUESTS:
                message = "too many guests";
                Log.e(TAG, message);
                break;
            case IMEI_ALREADY_EXISTS:
                message = "User already registered";
                Log.e(TAG, message);
                break;
            case NAME_ALREADY_EXISTS:
                message = "Name already registered";
                Log.e(TAG, message);
                break;
        }
        return message;
    }
}
