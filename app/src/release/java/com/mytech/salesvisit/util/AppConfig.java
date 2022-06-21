package com.mytech.salesvisit.util;

import android.os.Build;

import java.util.UUID;

import static com.mytech.salesvisit.util.Util.tr;

public class AppConfig {
    public final static String BASEURL="http://crm.konarkgroup.com:8101";
    public final static String TYPE="";

    public static String getDeviceUid() {
        return tr(UUID.randomUUID().toString());
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return tr(Util.capitalize(model));
        } else {
            return tr(Util.capitalize(manufacturer) + " " + model);
        }
    }
}
