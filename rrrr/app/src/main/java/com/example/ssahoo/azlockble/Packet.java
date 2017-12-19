package com.example.ssahoo.azlockble;

/*
 * Created by user on 8/18/2015.
 */
public interface Packet {

    int REQUEST_PACKET_TYPE_POS = 0;
    int REQUEST_ACCESS_MODE_POS = 1;
    int REQUEST_PACKET_LENGTH_POS = 2;

    int RESPONSE_PACKET_TYPE_POS = 0;
    int RESPONSE_PACKET_LENGTH_POS = 3;
    int RESPONSE_ACTION_STATUS_POS = 2;
    int RESPONSE_COMMAND_STATUS_POS = 1;
    //int RESPONSE_ACCESS_MODE_POS = 2;
    //int RESPONSE_DEVICE_STATUS_POS = 3;

    char SUCCESS = 'S';
    char FAILURE = 'F';

    interface HandshakePacket
    {
        /* Handshake Packet Details */
        int RECEIVED_PACKET_LENGTH = 18;
        int SENT_PACKET_LENGTH = 10;

        /* SEND PACKET PARAMETERS */
        int PHONE_MAC_START = 3;
        int TEST_NUM_POS = 3;
        int PHONE_IP_ADDRESS_START = 9;
        int CHECKSUM_SENT = 9;

        /* RECEIVED PACKET PARAMETERS */
        int REGISTRATION_STATUS_POS = 2;
        int PACKET_LENGTH_POS = 3;
        int DOOR_STATUS_POS = 4;
        int BATTERY_STATUS_POS = 5;
        int OWNER_NAME_START = 6;
        int OWNER_PHONE_START = 6;
        int TAMPER_STATUS_POS = 16;
        //int DOOR_NAME_START = 68;
        //int OWNER_PIN_START = 84;
        //int DOOR_MAC_ID_START = 84;
        int CHECKSUM_RECV = 17;

        /* TAMPER STATUS */
        char TAMPERED = '1';
        char NOT_TAMPERED = '0';
    }

    interface OwnerRegistrationPacket
    {
        /* Owner Packet Details */
        int SENT_PACKET_LENGTH = 20;
        int RECEIVED_PACKET_LENGTH = 5;

        char REGISTER = 'R';
        char EDIT_OWNER_DETAILS = 'E';
        char CHANGE_PIN = 'P';
        char DELETE_ALL_GUESTS='2';
        char DELETE_ALL_LOGS='1';
        char DELETE_ALL_LOGS_AND_GUESTS='3';
        char DELETE_NOTHING='0';

        /* Options */
        char PACKET_NEW_OWNER_ID = '8';
        char PACKET_ALT_OWNER_ID = '9';

        /* SEND PACKET PARAMETERS */
        int OWNER_MAC_ID_START = 3;
        int OWNER_NAME_START = 9;
        int DELETE_FLAG = 18;
        int CHECKSUM_SENT = 19;

        /* RECEIVED PACKET PARAMETERS */
        int PACKET_ID_POS = 4;
    }

    interface TamperPacket
    {
        /* Lock Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 15;

        /* Options */
        char ENABLE = 'E';
        char DISABLE = 'D';

        /* SEND PACKET PARAMETERS */
        int NOTIFICATION_POSITION = 3;
        int OWNER_PHONE_START = 4;
        int CHECKSUM_SENT = 14;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;
    }

    interface RemoteConnectionModePacket
    {
        /* Lock Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 11;

        /* Options */
        char CONNECT = 'C';
        char DISCONNECT = 'D';

        /* SEND PACKET PARAMETERS */
        int CONNECTION_MODE_POSITION = 3;
        int DOOR_MAC_ID_START = 4;
        int CHECKSUM_SENT = 10;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;
    }

    interface LockPacket
    {
        /* Lock Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 17;

        /* SEND PACKET PARAMETERS */
        int DOOR_STATUS_REQUEST_POSITION = 3;
        int PHONE_MAC_START = 4;
        int CURRENT_DATE_TIME_START = 10;
        int CHECKSUM_SENT = 16;

        /* RECEIVED PACKET PARAMETERS */
        int LOCK_STATUS_POS = 2;
        //int BATTERY_STATUS = 4;
        int CHECKSUM_RECV = 4;

    }

    interface RegisterGuestPacket
    {
        /* RegsterGuest Packet Details */
        int RECEIVED_PACKET_LENGTH_ADD_GUEST = 6;
        int SENT_PACKET_LENGTH_ADD_GUEST_1 = 19;
        int SENT_PACKET_LENGTH_ADD_GUEST_2 = 17;

        /* Action Commands */
        char ADD_GUEST = 'A';

        /* Options */
        char PACKET_1_ID = '0';
        char PACKET_2_ID = '1';

        /* SEND PACKET PARAMETERS */
        int GUEST_NAME_START = 9;
        int GUEST_PHONE_START = 26;
        int ACCESS_TYPE_POSITION = 3;
        int START_DATE_OF_ACCESS_START = 4;
        int START_TIME_OF_ACCESS_START = 8;
        int END_DATE_OF_ACCESS_START = 10;
        int END_TIME_OF_ACCESS_START = 14;
        int PHONE_MAC_ID_START = 3;
        int CHECKSUM_SENT_1 = 18;
        int CHECKSUM_SENT_2 = 16;

        /* RECEIVED PACKET PARAMETERS */
        int PACKET_ID = 4;
        int CHECKSUM_RECV = 5;

    }

    interface DeleteGuestPacket
    {
        /* Delete Packet Details */
        int RECEIVED_PACKET_LENGTH_DELETE_GUEST = 5;
        int SENT_PACKET_LENGTH_DELETE_SELECTED_GUEST = 10;
        int SENT_PACKET_LENGTH_DELETE_ALL_GUEST = 4;

        /* Action Commands */
        char DELETE_GUEST = 'D';
        char DELETE_ALL = '1';
        char DELETE_SELECTED = '0';

        /* SEND PACKET PARAMETERS */
        int GUEST_MAC_START = 3;
        int CHECKSUM_DELETE_SELECTED_SENT = 9;
        int CHECKSUM_DELETE_ALL_SENT = 3;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;

    }

    interface LogRequestPacket
    {
        /* LogRequest Packet Details */
        int RECEIVED_PACKET_LENGTH = 20;
        int SENT_PACKET_LENGTH = 5;

        /* SEND PACKET PARAMETERS */
        int LOG_READ_FLAG = 3;
        int CHECKSUM_SENT = 4;
        char PACKET_ID = '6';

        /* RECEIVED PACKET PARAMETERS */
        int PACKET_ID_POS = 4;
        int GUEST_MAC_START = 5;
        int ACCESS_DATE_START = 11;
        int ACCESS_TIME_START = 15;
        int ACCESS_STATUS_POS = 17;
        int ACCESS_FAILURE_REASON_CODE_POS = 18;
        int CHECKSUM_RECV = 19;

    }

    interface LogDeletePacket
    {
        /* LogRequest Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 18;

        /* SEND PACKET PARAMETERS */
        int DELETE_ALL_FLAG_POS = 3;
        int GUEST_MAC_START = 4;
        int ACCESS_DATE_START = 10;
        int ACCESS_TIME_START = 14;
        int ACCESS_STATUS_POS = 16;
        int ACCESS_FAILURE_REASON_POS = 17;
        int CHECKSUM_SENT = 17;

        char PACKET_ID = '7';

        /* RECEIVED PACKET PARAMETERS */
        int PACKET_ID_POS = 4;
        int CHECKSUM_RECV = 4;

    }

    interface ConfigPacket {
        /* Handshake Packet Details */
        int RECEIVED_PACKET_LENGTH = 4;
        int SENT_CONFIG_TIME_PACKET_LENGTH = 10;
        int SENT_CALIBRATION_PACKET_LENGTH = 5;

        /* SEND PACKET PARAMETERS */
        int CURRENT_DATE_TIME_POSITION = 3;
        int CHECKSUM_SENT = 9;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;
    }

    interface NewOwnerPacket
    {
        /* Packet Details */
        int RECEIVED_PACKET_LENGTH = 7;
        int SENT_PACKET_LENGTH = 12;

        /* SEND PACKET PARAMETERS */
        int DEVICE_MAC_ID_START = 3;
        int PIN_START = 9;
        int CHECKSUM_SENT = 11;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 6;
        int ACTION_STATUS_POS = 4;
        int PACKET_LENGTH_POS = 5;
    }

    interface UpdateGuestPacket
    {
        /* Packet Details */
        int RECEIVED_PACKET_1_LENGTH = 20;
        int RECEIVED_PACKET_2_LENGTH = 20;
        int SENT_PACKET_LENGTH = 5;

        /* OPTION */
        char REFRESH_GUEST_LIST = 'R';
        char PACKET_1 = '1';
        char PACKET_2 = '2';
        char PACKET_1_ID = '4';
        char PACKET_2_ID = '5';

        /* SEND PACKET PARAMETERS */
        int REFRESH_FLAG = 3;
        int CHECKSUM_SENT = 4;

        /* RECEIVED PACKET PARAMETERS */
        int SEQUENCE_NUMBER_POS = 5;
        int PACKET_TYPE_POSITION = 4;
        int GUEST_NAME_START = 12;
        int ACCESS_TYPE_POSITION = 8;
        int START_ACCESS_DATE_POS = 9;
        int START_ACCESS_TIME_POS = 11;
        int END_ACCESS_DATE_POS = 13;
        int END_ACCESS_TIME_POS = 17;
        int PHONE_MAC_ID_START = 6;
        int CHECKSUM_RECV_1 = 19;
        int CHECKSUM_RECV_2 = 19;
        int PACKET_ID = 4;

    }

    interface SetupPacket
    {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 4;
        int SENT_PACKET_LENGTH = 5;

        /* SEND PACKET PARAMETERS */
        int DISTANCE_POS = 3;
        int CHECKSUM_SENT = 4;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;
    }

    interface SelftestPacket
    {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 7;
        int SENT_PACKET_LENGTH = 11;

        /* SEND PACKET PARAMETERS */
        int DEVICE_MAC_START = 3;
        //int TEST_NUM_POS = 3;
        int SELFTEST_CMD_POS = 9;
        int CHECKSUM_SENT = 10;

        /* RECEIVED PACKET PARAMETERS */
        //int OWNER_STATUS_POS = 2;
        int DEVICE_STATUS_POS = 3;
        int ACTION_STATUS_POS = 4;
        int PACKET_LENGTH_POS = 5;
        //int CHECKSUM_RECV = 6;
    }

    interface RouterConfigPacket
    {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 83;

        /* SEND PACKET PARAMETERS */
        int ROUTER_SSID_START = 3;
        int ROUTER_SEC_TYPE_POS = 35;
        int ROUTER_PASSOWRD_START = 36;
        int DEVICE_LOCAL_IP_ADDR_START = 68;
        int DEFAULT_GATEWAY_START = 72;
        int SUBNET_MASK_START = 76;
        int ROUTER_PORT_START = 80;
        int CHECKSUM_SENT = 82;

        /* RECEIVED PACKET PARAMETERS */
        int CHECKSUM_RECV = 4;
    }

    interface EmailConfigPacket
    {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 7;
        int SENT_PACKET_LENGTH = 92;

        /* SEND PACKET PARAMETERS */
        int DOOR_MAC_ID_START = 3;
        int EMAIL_ID_START = 9;
        int PASSWORD_START = 41;
        int SERVER_IP_START = 73;
        int SERVER_PORT_START = 89;
        int CHECKSUM_SENT = 91;

        /* RECEIVED PACKET PARAMETERS */
        int ACTION_STATUS_POS = 4;
        int RECV_PACKET_LENGTH_POS = 5;
        int CHECKSUM_RECV = 6;
    }

    interface DoorSettingsPacket
    {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 20;

        /* SETTINGS OPTIONS */
        char RENAME_DOOR = 'R';
        char CHANGE_PASSWORD = 'P';

        /* SEND PACKET PARAMETERS */
        int SETTINGS_OPTION_POS = 3;
        int DOOR_NAME_START = 3;
        int DOOR_PASSWORD_START = 4;
        int CHECKSUM_SENT = 19;
    }

    interface FactoryResetPacket {
        /* Setup Packet Details */
        int RECEIVED_PACKET_LENGTH = 5;
        int SENT_PACKET_LENGTH = 5;

        int RESET_POS = 3;
        int CHECKSUM_SENT = 4;
    }
}
