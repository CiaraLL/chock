package com.example.myapplication.util;

import com.example.myapplication.network.FeelingUrl;

/**
 * @Author lili
 * @Date 2023/12/13-16:46
 */
public class FeelingUrlUtil {
    public static String convertUrl(FeelingUrl feelingUrl) {
        return feelingUrl.mScheme + "://" + feelingUrl.mHost + ":" + feelingUrl.mPort;
    }
}
