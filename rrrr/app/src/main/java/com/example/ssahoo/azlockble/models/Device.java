package com.example.ssahoo.azlockble.models;

import java.util.HashMap;

/*
 * Created by user on 8/13/2015.
 */
public class Device {

    private String id, name, deviceIp;
  //  RouterInfo routerInfo;
    public int status;

    public static int KEY_SHARED = 0;
    public static int KEY_EXPIRED = 1;
    public static int KEY_DELETED = 2;
    public static int KEY_UNKNOWN = -1;

    public static HashMap<Integer, String> statusString = new HashMap<Integer, String>(){{
        put(KEY_DELETED,"Key Deleted");
        put(KEY_EXPIRED,"Key Expired");
        put(KEY_UNKNOWN,"Key Unknown");
        put(KEY_SHARED,"Key Shared");
    }};

    public Device(){
        id = null;
        name = null;
       // routerInfo = null;
        deviceIp = null;
        status = KEY_UNKNOWN;
    }
    public Device(String id, String name)
    {
        this.id = id;
        this.name = name;
       // routerInfo = null;
        deviceIp = null;
        status = KEY_UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }



   /* public RouterInfo getRouterInfo() {
        return routerInfo;
    }

    public void setRouterInfo(RouterInfo routerInfo) {
        this.routerInfo = routerInfo;
    }*/

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    /*@Override
    public String toString() {
        return "Door ID:"+getId()+"\nDoor Name:"+getName()+"\nRouter Info:"+getRouterInfo();
    }*/
}
