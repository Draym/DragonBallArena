package com.andres_k.gameToolsLib.utils.configs;

/**
 * Created by com.andres_k on 11/03/2015.
 */
public class NetworkServerConfig {
    private static int udpPort = 55555;
    private static int tcpPort = 55556;
    private static String address = "";

    public static int getUdpPort(){
        return udpPort;
    }
    public static int getTcpPort(){
        return tcpPort;
    }
    public static String getAddress(){
        return address;
    }

    public static void setAddress(String value) {
        address = value;
    }
}
