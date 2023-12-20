package com.example.myapplication.network;

/**
 * @Author lili
 * @Date 2023/12/13-16:43
 */
public enum FeelingUrl {

    DATA("http", "", "");

    public String mScheme;
    public String mHost;
    public String mPort;

    FeelingUrl(String scheme, String host, String port) {
        mScheme = scheme;
        mHost = host;
        mPort = port;
    }
}
