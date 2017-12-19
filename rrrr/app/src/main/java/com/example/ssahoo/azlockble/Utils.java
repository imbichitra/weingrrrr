package com.example.ssahoo.azlockble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

public class Utils extends CommunicationError {

    public static boolean IS_ROUTER_CONFIGURED = false;
    public static BluetoothAdapter bluetoothAdapter = null;

    public static final char LOCKED = 'L';
    public static final char UNLOCKED = 'U';
    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;
    public static final int CANCELLED = 0;
    public static final int REFRESHED = 2;
    public static final int PARENT_ACTIVITY = 3;

    public static final int MASTER_REGISTRATION_CODE = 1;
    public static final int ALTERNATE_MASTER_REGISTRATION_CODE = 7;
    public static final int GUEST_EDIT_PROFILE_CODE = 2;
    public static final int GUEST_REGISTRATION_CODE = 3;
    public static final int MASTER_EDIT_PROFILE_CODE = 4;
    public static final int MASTER_CHANGE_PIN_CODE = 6;
    public static final int GUEST_DELETION_CODE = 5;
    public static final int DATABASE_ERROR_CODE = 101;
    public static final int DEVICE_ERROR_CODE = 102;

    public static final String EXTRA_CALLER_ACTIVITY_NAME = "callerActivity";
    public static final String EXTRA_DOWNLOAD_LOG = "downloadLog";

    public static ArrayList<String> accessTypeList = new ArrayList<String>(){
        {
            add("Limited Time");
            add("Full Time");
        }
    };

    public static HashMap<Integer, String> errors = new HashMap<Integer, String>(){
        {
            put(DATABASE_ERROR_CODE, "data cannot be inserted due to Database error");
            put(DEVICE_ERROR_CODE, "Operation Failed due to device error");
            put(INVALID_COMMAND, "Invalid Command");
            put(INVALID_ACCESS_MODE, "Invalid access mode");
            put(INVALID_CHECKSUM, "Invalid checksum");
            put(INVALID_LENGTH, "Invalid packet length");
            put(INVALID_OPTION, "Invalid option");
            put(INVALID_PHONE_TIME, "Invalid phone time");
            put(INVALID_ACCESS_TIME, "Invalid access time");
            put(GUEST_MAX_ATTEMPT, "Max Attempt");
            put(GUEST_KEY_NOT_FOUND, "Guest Key not found");
            put(GUEST_AUTH_FAILED, "Guest authentication failed");
            put(OUT_OF_SPACE, "Device is running out of space");
            put(DEVICE_NOT_CALIBRATED, "Device not calibrated");
            put(NOT_HANDSHAKED, "Device not handshaked");
        }
    };

    /* Group Categories */
    public static final char LIMITED_TIME_ACCESS = 'L';
    public static final char FULL_TIME_ACCESS = 'F';
    
    // length of guest and owner strings
    public static final int MASTER_NAME_LENGTH = 32;
    public static final int MASTER_PASSKEY_LENGTH = 32;
    public static final int MASTER_PHONE_LENGTH = 13;
    public static final int MASTER_EMAIL_LENGTH = 32;
    public static final int MASTER_ADDRESS_LENGTH = 96;
    public static final int MASTER_DOOR_LENGTH = 16;
    public static final int MASTER_NAME_START = 0;
    public static final int MASTER_PASSKEY_START = MASTER_NAME_START + MASTER_NAME_LENGTH;
    public static final int MASTER_PHONE_START = MASTER_PASSKEY_START + MASTER_PASSKEY_LENGTH;
    public static final int MASTER_EMAIL_START = MASTER_PHONE_START + MASTER_PHONE_LENGTH;
    public static final int MASTER_ADDRESS_START = MASTER_EMAIL_START + MASTER_EMAIL_LENGTH;
    public static final int MASTER_DOOR_START = MASTER_ADDRESS_START + MASTER_ADDRESS_LENGTH;
    public static final int MASTER_INFO_LENGTH = MASTER_DOOR_START + MASTER_DOOR_LENGTH;

    public static final int GUEST_NAME_LENGTH = 32;
    public static final int GUEST_PHONE_LENGTH = 13;
    public static final int GUEST_PASSKEY_LENGTH = 32;
    public static final int GUEST_EMAIL_LENGTH = 64;
    public static final int GUEST_ADDRESS_LENGTH = 128;
    public static final int GUEST_GROUP_LENGTH = 6;
    public static final int GUEST_DATE_LENGTH = 10;
    public static final int GUEST_TIME_LENGTH = 7;
    public static final int GUEST_DURATION_LENGTH = 3;
    public static final int GUEST_KEY_LENGTH = 32;
    public static final int GUEST_KEY_STS_LENGTH = 16;
    public static final int GUEST_NAME_START = 0;
    public static final int GUEST_PHONE_START = GUEST_NAME_START + GUEST_NAME_LENGTH;
    public static final int GUEST_PASSKEY_START = GUEST_PHONE_START + GUEST_PHONE_LENGTH;
    public static final int GUEST_EMAIL_START = GUEST_PASSKEY_START + GUEST_PASSKEY_LENGTH;
    public static final int GUEST_ADDRESS_START = GUEST_EMAIL_START + GUEST_EMAIL_LENGTH;
    public static final int GUEST_GROUP_START = GUEST_ADDRESS_START + GUEST_ADDRESS_LENGTH;
    public static final int GUEST_DATE_START = GUEST_GROUP_START + GUEST_GROUP_LENGTH;
    public static final int GUEST_TIME_START = GUEST_DATE_START + GUEST_DATE_LENGTH;
    public static final int GUEST_DURATION_START = GUEST_TIME_START + GUEST_TIME_LENGTH;
    public static final int GUEST_KEY_START = GUEST_DURATION_START + GUEST_DURATION_LENGTH;
    public static final int GUEST_KEY_STS_START = GUEST_KEY_START + GUEST_KEY_LENGTH;
    public static final int GUEST_INFO_LENGTH = GUEST_KEY_STS_START + GUEST_KEY_STS_LENGTH;

    public static final int GUEST_LOG_NAME_START  = 6;
    public static final int GUEST_LOG_ADDRESS_START  = 6;
    public static final int GUEST_LOG_ADDRESS_LENGTH  = 128;
    public static final int GUEST_LOG_PHONE_START  = GUEST_LOG_ADDRESS_START + GUEST_LOG_ADDRESS_LENGTH;
    public static final int GUEST_LOG_PHONE_LENGTH = 13;
    public static final int GUEST_LOG_GNAME_START = GUEST_LOG_PHONE_START + GUEST_LOG_PHONE_LENGTH;
    public static final int GUEST_LOG_GNAME_LENGTH = 32;
    public static final int GUEST_LOG_START  = GUEST_LOG_GNAME_START + GUEST_LOG_GNAME_LENGTH;
    public static final int GUEST_LOG_END = GUEST_LOG_START + GUEST_INFO_LENGTH;

    public static final int APP_MODE_KEY_START = 6;
    public static final int APP_MODE_KEY_LENGTH = 17;
    public static final int APP_MODE_NAME_LENGTH = 32;
    public static final int APP_MODE_PHONE_LENGTH = 13;
    public static final int APP_MODE_EMAIL_LENGTH = 32;
    public static final int APP_MODE_DOOR_LENGTH = 16;
    public static final int APP_MODE_NAME_START = APP_MODE_KEY_START + APP_MODE_KEY_LENGTH;
    public static final int APP_MODE_PHONE_START = APP_MODE_NAME_START + APP_MODE_NAME_LENGTH;
    public static final int APP_MODE_EMAIL_START = APP_MODE_PHONE_START + APP_MODE_PHONE_LENGTH;
    public static final int APP_MODE_DOOR_START = APP_MODE_EMAIL_START + APP_MODE_EMAIL_LENGTH;
    public static final int APP_MODE_INFO_LENGTH = APP_MODE_DOOR_START + APP_MODE_DOOR_LENGTH;
    public static final String APP_MODE_DUMMY_KEY = "00:00:00:00:00:00";
    public static final String KEY_STATUS_SHARED = "MAC ID SHARED TO GENERATE KEY";

    public static final int APP_MODE_POS = 3;
    public static final int LOCK_STS_POS = 2;

    public static final int CMD_TYPE_POS = 0;
    public static final int CMD_MODE_POS = 1;
    public static final int CMD_MIN_LENGTH = 4;
    public static final int CMD_LB_POS = 2;
    public static final int CMD_HB_POS = 3;
    public static final int CMD_STS_POS = 1;
    public static final int DEV_STS_POS = 2;
    public static final int ACT_STS_POS = 3;

    public static final int GUEST_PASSWD_SUB_START = 0;
    public static final int GUEST_PASSWD_SUB_LEN = 17;
    public static final int GUEST_PASSWD_SUB_END = GUEST_PASSWD_SUB_START + GUEST_PASSWD_SUB_LEN;

    // commands supported by the device
    public static final char HANDSHAKE_REQ = '0'; // automatically send after every connection
    public static final char CONNECTION_MODE_REQ = '9'; // automatically send after every connection
    public static final char CONFIG_TIME_REQ = '6';
    public static final char CALIBRATION_REQ = '6'; // installation setup
    public static final char KEY_REQ = '4';
    public static final char RENAME_DOOR_REQUEST = '2';
    public static final char CHANGE_PASSWORD_REQUEST = '2';
    public static final char LOCK_ACCESS_REQUEST = '3';
    public static final char LOG_REQUEST = '5'; // history file, communicate the guest info
    public static final char DIAGNOSTIC_REQ = 'D';
    public static final char OWNER_REQUEST = '1'; // time of master config, send master info to device and receive updated info of master
    public static final char TAMPER_REQUEST = '6'; // time of master config, send master info to device and receive updated info of master
    public static final char ROUTER_CONFIG_REQUEST = '7'; // for remote access, store wifi router details
    public static final char EMAIL_CONFIG_REQUEST = 'E'; // for remote access, store wifi router details
    public static final char UNDEFINED_REQ = 'U';
    public static final char NEW_OWNER_REQ = 'N';
    public static final char FACTORY_RESET_REQ = 'A';

    public static final int COMMAND_LENGTH_IN_CHAR  = 200;
    public static final int RESPONSE_LENGTH_IN_CHAR  = 200;
    public static final int MIN_RES_LEN = 6;
    public static final int PACKET_LENGTH_POS = 2;
    public static final int DEVICE_MAC_ID_START = 3;

    public static final char OWNER_NOT_REGISTERED = '0';
    public static final char OWNER_REGISTERED = '1';
    public static final char GUEST_NOT_REGISTERED = '2';
    public static final char GUEST_REGISTERED = '3';

    // Device Info options
    public static final char SET_TIME = '0';
    public static final char READ_DEVICE_INFO = '1';
    public static final char SET_ALARM = '2';
    public static final char ERASE_MASTER_INFO = '3';
    public static final char SET_ALTERNATE_PHONE_AS_MASTER = '4';

    // Received Packet parameters
    public static final char OWNER_STS = 2;
    public static final char MAC_ID_STS = 4;
    public static final int DEV_STS = 3;
    public static final int ACTION_STS_POS = 4;


    public static final char HANDSHAKE_OK = 'K';
    public static final char DEVICE_INFO_OK = 'O';
    public static final char NEW_OWNER_OK = 'D';

    public static final int PHONE_MAC_ID_LEN_IN_HEX = 6;
    public static final int PHONE_MAC_ID_LEN = 17;

    // command mode
    public static final char APP_MODE_OWNER = 'O';
    public static final char APP_MODE_GUEST = 'G';
    public static final char APP_MODE_VISITOR = 'V';
    public static final char APP_ALTERNATE_OWNER = 'A';

    // common status for all type of commands
    public static final char DEV_NOT_RESPONDING = 'D';
    public static final char DEV_NOT_PRESENT = 'N';
    public static final char CMD_TIMEOUT = 'T';
    public static final char CMD_NOT_SUPPORTED = 'S';
    public static final char CMD_LEN_MISMATCH =	'M';
    public static final char CMD_INVALID_CHKSUM = 'K';
    public static final int CMD_OK = 1;
    public static final char RES_NOT_OK = 'R';

    // guest registration status
    public static final char GUEST_CANT_READ = 'R';
    public static final char GUEST_FIELD_EMPTY = 'E';
    public static final char GUEST_CANT_WRITE =	'W';
    public static final char GUEST_NO_SPACE = 'F';
    public static final char GUEST_KEY_VALID = 'V';
    public static final char GUEST_REG_OK = 'G';
    public static final char GUEST_ALREADY_DELETED = 'K';

    // owner status
    public static final char MASTER_CANT_READ = 'R';
    public static final char MASTER_FIELD_EMPTY = 'E';
    public static final char MASTER_CANT_WRITE =	'W';
    public static final char MASTER_OK = 'M';

    //Device Status Error
    public static final char FLASH_CANT_READ = 'F';
    public static final char FLASH_CANT_WRITE = 'W';
    public static final char FLASH_FIELD_EMPTY = 'E';
    public static final char FLASH_NO_SPACE = 'S';
    public static final char TIME_NOT_SET = 'T';
    public static final char ALARM_NOT_SET = 'A';
    public static final char FLASH_READ_ERROR = 'F';
    public static final char FLASH_WRITE_ERROR = 'W';
    public static final char TIME_NOT_READ = 'R';

    // device calibration errors
    public static final char CAL_NOTHING_TO_DO = 'D';
    public static final char CAL_OK = 'C';

    // device lock/unlock status
    public static final char STS_AUTH_FAILED = 'A';
    public static final char STS_AUTH_FAILED_BEFORE_ACCESS_TIME = 'B';
    public static final char STS_KEY_NOT_READ = 'K';
    public static final char STS_OK = 'S';
    public static final char STS_LOCK = 'L';
    public static final char STS_UNLOCK = 'U';
    public static final char STS_AUTH_KEY_MISMATCH = 'M';
    public static final char STS_AUTH_KEY_EXPIRED = 'E';
    public static final char STS_NO_ACCESS = 'X';
    public static final char STS_NO_DEVICE = 'D';
    public static final char STS_MAX_ATTEMPT = 'T';
    public static final char STS_TIME_AUTH_FAILED = 'F';
    public static final char INVALID_KEY = 'I';

    // diagnosis status
    public static final char DIAG_KEY_INFO_NOT_DEL = 'K';
    public static final char DIAG_CAL_INFO_NOT_DEL = 'C';
    public static final char DIAG_STS_INFO_NOT_DEL = 'S';
    public static final char DIAG_MODE_INFO_NOT_DEL = 'U';
    public static final char DIAG_RESET_ALL_NOT_DEL = 'A';
    public static final char DIAG_LOCKED = 'L';
    public static final char DIAG_UNLOCKED = 'U';
    public static final char DIAG_OK = 'D';

    public static final char STS_LOG_OK = 'L';
    public static final char STS_FETCH = 'A';
    public static final char STS_END = 'E';
    public static final char STS_LOG_ERROR = 'M';
    public static final char STS_LOG_INVALID = 'I';

    public static final char ROUTER_CONFIG_OK = 'K';
    public static final char ROUTER_CANT_READ = 'R';
    public static final char ROUTER_CANT_WRITE = 'W';
    public static final char ROUTER_FIELD_EMPTY = 'E';
    public static final char EMAIL_CONFIG_OK = 'E';

    // diagnosis commands
    public static final char ACCESS_TEST = 'A';
    public static final char KEY_INFO_RESET = 'K';
    public static final char CAL_INFO_RESET = 'C';
    public static final char STS_INFO_RESET = 'S';
    public static final char MODE_INFO_RESET = 'M';
    public static final char RESET_ALL_INFO = 'R';

    public static final int UNUSED_MACID_CHAR_NUM = 5;

    public static final String ENCRYPT_PASS_KEY= "AE12X7YY7A9RBBCCDD772663001V38U81";

    public static final byte [] cryptoSalt = {
            (byte)0xDE, (byte)0xAD, (byte)0xBE, (byte)0xEF,
            (byte)0xAA, (byte)0xAA, (byte)0xAA, (byte)0xAA,
            (byte)0x55, (byte)0x55, (byte)0x55, (byte)0x55,
            (byte)0xA5, (byte)0xA5, (byte)0xA5, (byte)0xA5,
            (byte)0x5A, (byte)0x5A, (byte)0x5A, (byte)0x5A,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
            (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78,
            (byte)0x87, (byte)0x65, (byte)0x43, (byte)0x21,
    };

    public static int selectedGuestsSize;
    public static final String PIN_FILE ="AANCMKFLPENRIJD";
    public static final String SAVE_DATETIME_FILE ="SANCMDDLPEXVIJD";
    public static final String PLAY_SOUND_FILE ="PZLCMAPDFOEPEXVIJD";
    public static final int NEW_PIN_FLAG =101;
    public static final int CHANGE_PIN_FLAG =102;

    //command and response details
    public char requestType = UNDEFINED_REQ;
    public int requestStatus = 0;
    public int requestDirection = 0;
    public String commandDetails = null;
    public String responseDetails = null;

    //public static Utils LockDemoUtils = new Utils();

    public static final int TCP_PACKET_UNDEFINED = 10;
    public static final int TCP_PACKET_SENT = 11;
    public static final int TCP_PACKET_SENT_FAILED = 12;
    public static final int TCP_PACKET_RECEIVED = 13;
    public static final int TCP_PACKET_RECEIVED_FAILED = 14;

    public static final int TCP_RECEIVE_PACKET = 17;
    public static final int TCP_SEND_PACKET = 18;
    public static final int TCP_DIRECTION_UNDEFINED = 0;

    private final int SEND_PACKET_LENGTH_POS = 2;
    private final int RECV_PACKET_LENGTH_POS = 3;

    public static final String GUEST_LIST_TYPE_OPTIONS_FILE = "GLTOFABFHDZCSOFH.config";
    public static final String TAMPER_NOTIFICATION_CONFIG_FILE = "SISCFABFH138OFH.config";
    public static final int GUEST_LIST_TYPE_INDEX = 0;
    public static final int SHOW_ACTIVE_GUESTS_ONLY = 1;
    public static final int SHOW_EXPIRED_GUESTS_ONLY = 2;
    public static final int SHOW_ALL_GUESTS_EXCEPT_KEY_DELETED = 0;
    public static final int SHOW_ALL_GUESTS = -1;
    public static final int ENABLE_TAMPER_NOTIFICATION = 1;
    public static final int DISABLE_TAMPER_NOTIFICATION = 0;

    private final static String p2pInt = "p2p-p2p0";

    private int pktCheckSum = 0;

    public static byte [] utilsTcpPacket;

    public static Utils LockDemoUtils = new Utils();

    public void InitUtilsInfo()
    {
        LockDemoUtils.requestStatus = TCP_PACKET_UNDEFINED;
        LockDemoUtils.commandDetails = null;
        LockDemoUtils.responseDetails = null;
        LockDemoUtils.requestDirection = TCP_DIRECTION_UNDEFINED;
        LockDemoUtils.requestType = UNDEFINED_REQ; // not used
    }

    public void setUtilsInfo(Utils u)
    {
        if (u.commandDetails != null)
        {
            LockDemoUtils.commandDetails = u.commandDetails;
        }
        else
        {
            Log.i("Utils", "Lacerta:::Null Command");
        }
        LockDemoUtils.requestType = u.requestType;
        if(u.responseDetails != null)
        {
            LockDemoUtils.responseDetails = u.responseDetails;
        }
        else
        {
            Log.i("Utils", "Lacerta:::Null Response");
        }
        LockDemoUtils.requestStatus = u.requestStatus;
        LockDemoUtils.requestDirection = u.requestDirection;
    }

    public Utils getUtilsInfo()
    {
        if (LockDemoUtils.commandDetails == null)
        {
            Log.i("Utils", "Lacerta:::Null Command");
        }
        if(LockDemoUtils.responseDetails == null)
        {
            Log.i("Utils", "Lacerta:::Null Response");
        }
        return LockDemoUtils;
    }

    public void setTcpPacket(byte [] packet)
    {
        utilsTcpPacket = new byte[packet.length];
        for(int i = 0; i < packet.length; ++ i) {
            utilsTcpPacket[i] = packet[i];
        }
    }

    public byte [] getTcpPacket()
    {
        return utilsTcpPacket;
    }

    private void getChecksum(byte chksum)
    {
        pktCheckSum = (byte)~chksum;
        Log.d("Utils", "Checksum "+(byte)pktCheckSum);
    }

    public byte calculateChecksum(byte [] packet, boolean isCmd)
    {
        int cs = 0;
        int pktLen;
        if(isCmd) {
            pktLen = packet[SEND_PACKET_LENGTH_POS] - 1;
        } else {
            pktLen = packet[RECV_PACKET_LENGTH_POS] - 1;
        }
        //Log.d("Utils", "calculateChecksum Packet length "+pktLen);
        for(int i = 0; i < pktLen; ++ i) {
            cs += (int)packet[i] & 0xFF;
        }
        // if cs > 0xFFFF
        if(cs > 0xFF) {
            int tempcs1 = cs;
            cs = 0;
            for (; tempcs1 != 0; tempcs1 = (tempcs1 >> 8)) {
                cs += (tempcs1 & 0xFF);
            }
        }
        if(cs > 0xFF) {
            int tempcs1 = cs;
            cs = 0;
            for (; tempcs1 != 0; tempcs1 = (tempcs1 >> 8)) {
                cs += (tempcs1 & 0xFF);
            }
        }
        /*pktCheckSum = cs;
        final int WAIT_TIME = 100;
        new CountDownTimer(WAIT_TIME, 10){
            private int tempcs = pktCheckSum;
            public void onTick(long milliSecond) {
                int tempcs1 = tempcs;
                tempcs = 0;
                for(;tempcs1 != 0; tempcs1 = (tempcs1 >> 8))
                {
                    tempcs += (tempcs1 & 0xFF);
                }
                if(tempcs < 0x100) {
                    Log.d("Utils", ":::processChecksum onTick");
                    getChecksum((byte) tempcs);
                    cancel();
                }
            }

            public void onFinish() {
                Log.d("Utils", ":::processChecksum onFinish");
            }
        }.start();
        return (byte)pktCheckSum;*/
        Log.d("Utils", "Checksum "+(byte)(~cs));
        return (byte)~cs;
    }

    public boolean isChecksumValid(byte [] packet, boolean isCmd)
    {
        int pktLen;
        if(isCmd) {
            pktLen = packet[Packet.REQUEST_PACKET_LENGTH_POS] - 1;
        } else {
            pktLen = packet[Packet.RESPONSE_PACKET_LENGTH_POS] - 1;
        }
        Log.d("Utils", "isChecksumValid Packet length "+pktLen);
        if(packet[pktLen] == calculateChecksum(packet, isCmd)) {
            return true;
        } else {
            return false;
        }
    }

    public byte ch2Byte(char c)
    {
        byte b = 0;
        char[] ref = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for(int i = 0; i < ref.length; ++ i) {
            if(c == ref[i]) {
                b = (byte)i;
            }
        }
        return b;
    }

    public byte [] getMacIdInHex(String macid)
    {
        byte [] hexBytes = new byte [Utils.PHONE_MAC_ID_LEN_IN_HEX];
        String mac = macid.replaceAll(":", "").toUpperCase();
        for(int i = 0; i < hexBytes.length; ++ i) {
            hexBytes[i] = (byte) (ch2Byte(mac.charAt((i << 1) + 1)) |
                    (ch2Byte(mac.charAt(i << 1)) << 4));
        }
        return hexBytes;
    }

    public static String getStringFromHex(String in)
    {
        String out = null;
        char [] ref = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte [] strBytes;
        try {
            strBytes = in.getBytes("ISO-8859-1");
            char [] outCh = new char[strBytes.length << 1];
            for(int i = 0; i < strBytes.length; i ++) {
                outCh[(i << 1)] = ref[((strBytes[i] >> 4) & 0x0F)];
                outCh[(i << 1) + 1] = ref[(strBytes[i] & 0x0F)];
            }
            out = new String(outCh);
        } catch(java.io.UnsupportedEncodingException e) {
            Log.d("Utils", "Unsupported String Decoding Exception");
        }
        return out;
    }

    public String int2Str(int value)
    {
        int temp1 = value;
        int temp3 = 0;
        char[] reference = {'0', '1', '2', '4', '5', '6', '7', '8', '9'};
        char[] output = new char[16];
        char[] output_int = new char[16];

        for (int i = 0; i < 16; ++ i)
        {
            output [i] = '\0';
            output_int [i] = '\0';
        }
        do {
            if (temp1 == 0)
            {
                break;
            }
            output[temp3] = reference[temp1 % 10];
            temp1 /= 10;
            temp3 ++;
        }while(true);

        for (temp3 = 0; (temp3 < 16) && (output [temp3] != '\0'); ++temp3);

        for (int i = 0; i < temp3; ++ i)
        {
            output_int [i] = output [temp3 - i - 1];
        }

        return new String(output_int);
    }

    public void delay(int ms_count)
    {
        for(int i = 0; i < ms_count; ++ i)
        {
            for (int j = 0; j < 10000; ++ j)
            {
                ;
            }
        }
    }


	public static String getIPFromMac(String MAC) {
		/*
		 * method modified from:
		 * 
		 * http://www.flattermann.net/2011/02/android-howto-find-the-hardware-mac-address-of-a-remote-host/
		 * 
		 * */
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/proc/net/arp"));
			String line;
			while ((line = br.readLine()) != null) {

				String[] splitted = line.split(" +");
				if (splitted.length >= 4) {
					// Basic sanity check
					String device = splitted[5];
					if (device.matches(".*" +p2pInt+ ".*")){
						String mac = splitted[3];
						if (mac.matches(MAC)) {
							return splitted[0];
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
                if(br !=  null) {
                    br.close();
                }
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
                e.getCause();
                e.printStackTrace();
            }
		}
		return null;
	}


	public static String getLocalIPAddress() {
		/*
		 * modified from:
		 * 
		 * http://thinkandroid.wordpress.com/2010/03/27/incorporating-socket-programming-into-your-applications/
		 * 
		 * */
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();

					String iface = intf.getName();
					if(iface.matches(".*" +p2pInt+ ".*")){
						if (inetAddress instanceof Inet4Address) { // fix for Galaxy Nexus. IPv4 is easy to use :-)
							return getDottedDecimalIP(inetAddress.getAddress());
						}
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("NetworkAddressFactory", "SocketException " + getLocalIPAddress(), ex);
		} catch (NullPointerException ex) {
			Log.e("NetworkAddressFactory", "NullPointerException " + getLocalIPAddress(), ex);
		}
		return null;
	}

	private static String getDottedDecimalIP(byte[] ipAddr) {
		/*
		 * ripped from:
		 * 
		 * http://stackoverflow.com/questions/10053385/how-to-get-each-devices-ip-address-in-wifi-direct-scenario
		 * 
		 * */
		String ipAddrStr = "";
		for (int i=0; i<ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i]&0xFF;
		}
		return ipAddrStr;
	}
    /*
    private class UtilsCommand
    {
        private String commandString;
        public UtilsCommand(byte b1, byte b2, byte [] ba3, String sInfo)
        {
            byte [] ba1 = new byte [1];
            byte [] ba2 = new byte [2];
            ba1 [0] = b1;
            String bs1 = new String(ba1);
            if(bs1.length() < COMMAND_TYPE_LENGTH)
            {
                for (int i = bs1.length(); i < COMMAND_TYPE_LENGTH; ++ i)
                {
                    bs1.concat(" ");
                }
            }
            ba2 [0] = b2;
            String s = new String(ba1) + new String(ba2) + new String(ba3) + sInfo;
            byte [] len = new byte [1];
            len [0] = (byte)s.length();
        }
    }

    private class UtilsResponse
    {
        private byte cmdStatus;
        private byte cmdType;
        private byte devStatus;
        private byte len;
        private byte [] resData;
        private String resInfo;
        public UtilsResponse(String res)
        {

        }
    }
    */


    public static class CommunicationError
    {
        public static String commandStatusError(char errorCode)
        {
            String errorMessage = "";
            switch (errorCode)
            {
                case CMD_NOT_SUPPORTED:
                    errorMessage = "Command not Supported";
                    break;
                case CMD_LEN_MISMATCH:
                    errorMessage = "Command Lenth Mismatch";
                    break;
                case CMD_TIMEOUT:
                    errorMessage = "Communication Time out";
                    break;
                case Utils.CMD_INVALID_CHKSUM:
                    errorMessage = "Invalid Checksum";
                    break;
            }
            return errorMessage;
        }
        public static String deviceStatusError(char errorCode)
        {
            String errorMessage = "";
            switch (errorCode)
            {
                case DEV_NOT_RESPONDING:
                    errorMessage = "Device Not Responding";
                    break;
                case DEV_NOT_PRESENT:
                    errorMessage = "Device not present";
                    break;
                case MASTER_CANT_READ:
                    errorMessage = "Cannot read data";
                    break;
                case MASTER_FIELD_EMPTY:
                    errorMessage = "Data field empty";
                    break;
                case MASTER_CANT_WRITE:
                    errorMessage = "Cannot write data";
                    break;
                case STS_AUTH_KEY_MISMATCH:
                    errorMessage = "[Authentication Failed] Key Mismatch";
                    break;
                case INVALID_KEY:
                    errorMessage = "Invalid Key";
                    break;
            }
            return errorMessage;
        }
        public static String keyPacketError(char errorCode)
        {
            String errorMessage = "";
            switch (errorCode)
            {
                case DEV_NOT_RESPONDING:
                    errorMessage = "Device Not Responding";
                    break;

                case DEV_NOT_PRESENT:
                    errorMessage = "Device not present";
                    break;

                case Utils.GUEST_CANT_READ:
                    errorMessage = "Can't Read Database FileAccess";
                    break;

                case Utils.GUEST_CANT_WRITE:
                    errorMessage = "Can't Write Database FileAccess";
                    break;

                case Utils.GUEST_FIELD_EMPTY:
                    errorMessage = "Some Fields Are Empty";
                    break;

                case Utils.GUEST_NO_SPACE:
                    // todo - open dialog to delete a key first
                    errorMessage = "Not Enough Storage";
                    break;

                case Utils.GUEST_KEY_VALID:
                    errorMessage = "Valid Existing Key";
                    break;

                case Utils.GUEST_ALREADY_DELETED:
                    errorMessage = "Key already deleted.";
                    break;
            }
            return errorMessage;
        }
        public static String packetStatusError()
        {
            String errorMessage = "";
            Utils u = new Utils();
            u = u.getUtilsInfo();
            switch (u.requestStatus)
            {
                case TCP_PACKET_SENT_FAILED:
                    errorMessage = "TCP Packet not sent";
                    break;
                case TCP_PACKET_RECEIVED_FAILED:
                    errorMessage = "TCP Packet not received";
                    break;
            }
            return errorMessage;
        }
        public static String lockStatusError(char errorCode)
        {
            String errorMessage = "";
            switch (errorCode)
            {
                case DEV_NOT_RESPONDING:
                    errorMessage = "Device Motor damaged or Low Battery";
                    break;
                case DEV_NOT_PRESENT:
                    errorMessage = "Device not Found";
                    break;
                case STS_AUTH_FAILED:
                    errorMessage = "Key not found";
                    break;
                case STS_AUTH_FAILED_BEFORE_ACCESS_TIME:
                    errorMessage = "Key Accessed Before Time";
                    break;
                case STS_AUTH_KEY_EXPIRED:
                    errorMessage = "Key expired";
                    break;
                case STS_TIME_AUTH_FAILED:
                    errorMessage = "Current time is not matched with device time";
                    break;
                case STS_AUTH_KEY_MISMATCH:
                    errorMessage = "[Authentication Failed] Key Mismatch";
                    break;
                case STS_KEY_NOT_READ:
                    errorMessage = "[Authentication Failed] Key cannot read";
                    break;
                case STS_NO_ACCESS:
                    errorMessage = "Device is not calibrated";
                    break;
                case STS_MAX_ATTEMPT:
                    errorMessage = "Maximum Attempt";
                    break;
            }
            return errorMessage;
        }
        public static String deviceInfoPacketError(char errorCode)
        {
            String errorMessage = "";
            switch (errorCode)
            {
                case DEV_NOT_RESPONDING:
                    errorMessage = "Device Motor damaged or Low Battery";
                    break;
                case DEV_NOT_PRESENT:
                    errorMessage = "Device not Found";
                    break;
                case TIME_NOT_SET:
                    errorMessage = "Time cannot be set";
                    break;
                case ALARM_NOT_SET:
                    errorMessage = "Alarm cannot be set";
                    break;
                case FLASH_READ_ERROR:
                    errorMessage = "Cannot read from flash";
                    break;
                case FLASH_WRITE_ERROR:
                    errorMessage = "Cannot write into flash";
                    break;
                case TIME_NOT_READ:
                    errorMessage = "[Authentication Failed] Key expired";
                    break;
            }
            return errorMessage;
        }
    }

    public static String getModifiedMac(String mac)
    {
        String temp="";
        String s[] = mac.split(":");
        for(String str : s)
            temp += str;
        return temp;
    }
    public static String generateMac(String mac)
    {
        String x="";
        int j=0;

        for(int i=0;i<mac.length();i++)
        {
            x += mac.charAt(i);
            j++;
            if(j==2 && i != mac.length()-1)
            {
                x += ":";
                j=0;
            }
        }
        return x;
    }
    public static int parseInt(String bytes, int data)
    {
        return (bytes.charAt(data) & 0xFF);
    }

    private static byte toUnsignedByte(byte x)
    {
        return (byte)(((int) x) & 0xFF);
    }

    public static byte[] toUnsignedBytes(String bytes)
    {
        byte[] b=new byte[bytes.length()];
        for(int i=0;i<bytes.length();i++)
        {   Log.e("Utils", "byte to int:"+parseInt(bytes, i));
            b[i]=(byte) parseInt(bytes, i);
        }
        return b;
    }

    public static boolean isValidEmailAddress(String email) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public static boolean isValidDoorName(String doorName){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(
                "^[A-Za-z0-9-]+$");
        java.util.regex.Matcher m = p.matcher(doorName);
        return m.matches();
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static double getScreenSize(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x + y);
        return screenInches;
    }

    /*public static boolean forget(Activity activity)
    {
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        boolean isForget = false;
        boolean isSaved = false;
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int networkId = wifiInfo.getNetworkId();
                isForget = wifiManager.removeNetwork(networkId);
                isSaved = wifiManager.saveConfiguration();
            }
        }
        return isForget && isSaved;
    }*/


    public static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)){
                    continue;
                }

                byte[] mac = intf.getHardwareAddress();
                if (mac==null){
                    return "";
                }

                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length()>0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) { }
        return "";
    }

   /* public static boolean isWifiNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
        if(netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            ArrayList<WifiNetwork> wifiNetworks = databaseHandler.getNetworks();
            for(WifiNetwork wifiNetwork : wifiNetworks) {
                if(wifiInfo.getSSID().equals("\""+wifiNetwork.getSSID()+"\"")) {
                    return true;
                }
            }
        }
        return false;
    }
*/
  /*  public static String getConnectedSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
*/
   /* public static String getUserId()
    {
        String IMEI = AppContext.getInstance().getImei();
        int a[]=new int[IMEI.length()];
        int p=0;
        String key="";
        byte[] k=new byte[6];
        for(int i=0;i<a.length;i++){
            a[i]=Character.getNumericValue(IMEI.charAt(i));
            p+=a[i];
        }
        int j=0;
        for(int i=0;i<a.length;i=i+3)
        {
            int sum=a[i]*a[i+1]+a[i+2];
            k[j]=(byte)(sum+p);
            key+=Integer.toHexString(sum+p);
        }
        key+=Integer.toHexString(p);
        //byte k2[]=new Utils().getMacIdInHex(key);
        //printByteArray(k2);
        //System.out.println(getStringFromHex(new String(k2)));
        return key.toUpperCase();
    }
*/
    public static String getUserId(String imei)
    {
        int a[]=new int[imei.length()];
        int p=0;
        String key="";
        byte[] k=new byte[6];
        for(int i=0;i<a.length;i++){
            a[i]=Character.getNumericValue(imei.charAt(i));
            p+=a[i];
        }
        int j=0;
        for(int i=0;i<a.length;i=i+3)
        {
            int sum=a[i]*a[i+1]+a[i+2];
            k[j]=(byte)(sum+p);
            key+=Integer.toHexString(sum+p);
        }
        key+=Integer.toHexString(p);
        return key.toUpperCase();
    }

    public static byte[] toByteArray(String byteArray)
    {
        return new Utils().getMacIdInHex(byteArray);
    }

   /* public static void printByteArray(byte[] txValue)
    {
        try {
            //String text = new String(txValue, "UTF-8");
            int i = 0;
            StringBuilder sb = new StringBuilder();
            sb.append(txValue);
            String text=sb.toString();
            System.out.println("Byte Array:"+text);
           // text = new String(txValue, "ISO-8859-1");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }*/

  /*  public static boolean forget(Activity activity)
    {
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        boolean isForget = false;
        boolean isSaved = false;
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                int networkId = wifiInfo.getNetworkId();
                isForget = wifiManager.removeNetwork(networkId);
                isSaved = wifiManager.saveConfiguration();
            }
        }
        return isForget && isSaved;
    }*/

    public static byte[] toPrimitive(Byte[] byteObjects)
    {
        byte[] bytes = new byte[byteObjects.length];
        int j=0;
        // Unboxing byte values. (Byte[] to byte[])
        for(Byte b: byteObjects)
            bytes[j++] = b.byteValue();
        return bytes;
    }

    public static Byte[] toObject(byte[] bytes)
    {
        Byte[] byteObjects = new Byte[bytes.length];

        int i=0;
        // Associating Byte array values with bytes. (byte[] to Byte[])
        for(byte b: bytes)
            byteObjects[i++] = b;  // Autoboxing
        return byteObjects;
    }
}
