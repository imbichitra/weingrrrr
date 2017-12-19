package com.example.ssahoo.azlockble;

import java.util.Date;

/**
 * Class to global information about this application environment. It
 * allows access to application-specific resources and classes, as well as
 * up-calls for application-level operations such as connection information,
 * connected door, application mode (owner/guest) and lock status, etc.
 */
public class AppContext {

    private static AppContext ourInstance = new AppContext();

    private final String TAG=AppContext.class.getSimpleName();
    private boolean isConnected;
    private ConnectionMode connectionMode;
//    private Door door;
//    private User user;
    private AppMode appMode;
    private String imei;
    private DeviceStatus deviceStatus;
//    private RouterInfo routerInfo;
//    private LockStatus lockStatus;
    private OnDataSendListener onDataSendListener;
    private boolean isTamperNotificationEnabled;
    private boolean shouldAskPin;
    private boolean shouldConfigPin;
    private String pin;
    private final int ASK_PIN_ENABLE=1;
    private final int ASK_PIN_DISABLE=0;
    private Date savedDateTime;
    private boolean playSound;

    /**
     * Get the application context
     * @return application context
     */
    public static AppContext getContext() {
        return ourInstance;
    }

    /**
     * cannot accessible to other classes
     */
    public AppContext() {
    }

    /**
     * Return the details of connected door.
     *
     * @return The details of connected door.
     *
     * @see #//setDoor(Door)
     */
  /*  public Door getDoor() {
        return door;
    }*/

    /**
     * Modify the details of connected door.
     * The default door is null.
     */
    /*public void setDoor(Door door) {
        this.door = door;
    }
*/
    /**
     * Return the details of connected user.
     *
     * @return The details of connected user.
     *
     * @see #//setUser(User)
     */
   /* public User getUser() {
        return user;
    }*/

    /**
     * Modify the details of connected door.
     * The default door is null.
     *
     * @param user
     *
     * For more details check {@Link User}
     */
    /*public void setUser(User user) {
        this.user = user;
    }
*/
    /**
     * Return current application mode. It defines the
     * connected user is {@Link AppMode.GUEST} or {@Link AppMode.OWNER}
     * For more details check {@Link AppMode}
     *
     * @return The application mode.
     *
     * @see #setAppMode(AppMode)
     */
    public AppMode getAppMode() {
        return appMode;
    }

    /**
     * Modify current application mode.  The default priority is {@Link AppMode.GUEST}.
     * Check {@link AppMode} for different values can be set.
     *
     * @param appMode current application mode.
     *
     * @see #getAppMode()
     */
    public void setAppMode(AppMode appMode) {
        this.appMode = appMode;
    }

    /**
     * Get connection status.
     *
     * @return True if app is connected with the lock, False otherwise.
     *
     * @see #setConnectionStatus(boolean)
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Modify current application mode.
     *
     * @param connected current connection status.
     *
     * @see #isConnected()
     */
    public void setConnectionStatus(boolean connected) {
        isConnected = connected;
    }

    /**
     * Return current connection mode. It defines the device
     * connected over BLE or Remote.
     * For more details check {@Link ConnectionMode}
     *
     * @return The connection mode.
     *
     * @see #setConnectionMode(ConnectionMode)
     */
    public ConnectionMode getConnectionMode() {
        return connectionMode;
    }

    /**
     * Modify connection mode to {@Link ConnectionMode.CONNECTION_MODE_REMOTE}
     * or {@Link ConnectionMode.CONNECTION_MODE_BLE}
     * Check {@link ConnectionMode}.
     *
     * @param connectionMode
     *
     * @see #getConnectionMode()
     */
    public void setConnectionMode(ConnectionMode connectionMode) {
        this.connectionMode = connectionMode;
    }

    /**
     * Return IMEI of the phone. IMEI (International Mobile Equipment Identity)
     * is a unique 15-digit serial number given to every mobile phone which can
     * then be used to check information such as the phone's Country of Origin,
     * the Manufacturer and it's Model Number.
     *
     * @return String IMEI number.
     *
     * @see #setImei(String)
     */
    public String getImei() {
        return imei;
    }

    /**
     * Store IMEI number for current device.
     *
     * @param imei
     *
     * @see #getImei()
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * Return device status after connection established. It defines the
     * connected device has successfully authenticated or not.
     * For more details check {@Link DeviceStatus}
     *
     * @return The device status.
     *
     * @see #setDeviceStatus(DeviceStatus)
     */
    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Modify device status to {@Link DeviceStatus.DEVICE_HANDSHAKED} if
     * authentication successful, {@Link DeviceStatus.NO_DEVICE}
     * Default value is {@Link DeviceStatus.NO_DEVICE}
     * Check {@link DeviceStatus} for more details.
     *
     * @param deviceStatus
     *
     * @see #getDeviceStatus()
     */
    public void setDeviceStatus(DeviceStatus deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

   /* public RouterInfo getRouterInfo() {
        return routerInfo;
    }

    public void setRouterInfo(RouterInfo routerInfo) {
        this.routerInfo = routerInfo;
    }

    public LockStatus getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(LockStatus lockStatus) {
        this.lockStatus = lockStatus;
    }*/

    public OnDataSendListener getOnDataSendListener() {
        return onDataSendListener;
    }

    public void setOnDataSendListener(OnDataSendListener onDataSendListener) {
        this.onDataSendListener = onDataSendListener;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

   /* public void savePin(Context context, String pin, boolean isChecked){
        FileAccess fileAccess=new FileAccess(context, Utils.PIN_FILE);
        String askFlag=isChecked ? "1:" : "0:";
        fileAccess.write(askFlag+pin);
        checkPinStatus(context);
    }

    public void updateAskPinStatus(Context context, boolean shouldAsk){
        checkPinStatus(context);
        Log.d(TAG, "updateAskPinStatus:"+shouldAsk+":"+getPin());
        savePin(context, getPin(), shouldAsk);
    }

    public void setPlaySoundOnLockUnlock(Context context, boolean playSound){
        FileAccess fileAccess=new FileAccess(context, Utils.PLAY_SOUND_FILE);
        String playFlag=playSound ? "1" : "0";
        fileAccess.write(playFlag);
        checkPlaySoundOnLockUnlock(context);
    }

    public void checkPlaySoundOnLockUnlock(Context context){
        FileAccess fileAccess=new FileAccess(context, Utils.PLAY_SOUND_FILE);
        String play=fileAccess.read();
        if(fileAccess.FILE_NOT_FOUND){
            setPlaySoundOnLockUnlock(context, true);
            playSound=true;
        }
        else {
            playSound = play != null && play.equals("1");
        }
        Log.d(TAG, "checkPlaySoundOnLockUnlock/playSound:"+playSound);
    }*/

    public boolean shouldPlaySound(){
        return playSound;
    }

    public void checkSelfPermission(String accessFineLocation) {
    }

   /* public void setNotificationStatus(Notification notification, boolean status){
        switch(notification){
            case TAMPER:
                isTamperNotificationEnabled=status;
                break;
        }
    }

    public void updateNotificationStatus(Notification notification, Context context){
        switch(notification){
            case TAMPER:
                FileAccess fileAccess = new FileAccess(context, Utils.TAMPER_NOTIFICATION_CONFIG_FILE);
                String tamperNotificationFlag = fileAccess.read();
                if(tamperNotificationFlag == null || fileAccess.FILE_NOT_FOUND){
                    isTamperNotificationEnabled = false;
                }
                else if(!tamperNotificationFlag.isEmpty()) {
                    isTamperNotificationEnabled = Integer.parseInt(tamperNotificationFlag) == Utils.ENABLE_TAMPER_NOTIFICATION;
                }
                break;
        }
    }*/

    /*public boolean getNotificationStatus(Notification notification){
        switch(notification){
            case TAMPER:
                return isTamperNotificationEnabled;
        }
        return false;
    }

    public boolean shouldAskPin(){
        return shouldAskPin;
    }

    public boolean shouldConfigPin(){
        return shouldConfigPin;
    }

    public void checkPinStatus(Context context){
        FileAccess fileAccess=new FileAccess(context, Utils.PIN_FILE);
        String askPinFlag = fileAccess.read();
        if(askPinFlag == null || fileAccess.FILE_NOT_FOUND || askPinFlag.isEmpty())
        {
            shouldConfigPin=true;
        }
        else if(!askPinFlag.isEmpty())
        {
            Log.d(TAG, "checkPinStatus/Pin:"+askPinFlag);
            String val[]=askPinFlag.split(":");
            shouldConfigPin=false;
            shouldAskPin = (Integer.parseInt(val[0]) == ASK_PIN_ENABLE);
            setPin(val[1]);
        }
        Log.d(TAG, "shouldAskPin:"+shouldAskPin+"\nshouldConfigPin:"+shouldConfigPin);
    }
*/
   /* public Date getSavedDateTime(Context context)
    {
        FileAccess fileAccess=new FileAccess(context, Utils.SAVE_DATETIME_FILE);
        String savedTime=fileAccess.read();
        if(savedTime==null || fileAccess.FILE_NOT_FOUND || savedTime.isEmpty())
        {
            return null;
        }
        return DateTimeFormat.toDate(savedTime, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH));
    }

    public void saveDateTime(Context context)
    {
        FileAccess fileAccess=new FileAccess(context, Utils.SAVE_DATETIME_FILE);
        fileAccess.write(DateTimeFormat.toString(new Date(), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)));
    }

    public void clearSavedDateTime(Context context){
        FileAccess fileAccess=new FileAccess(context, Utils.SAVE_DATETIME_FILE);
        fileAccess.write("");
    }
*/}
