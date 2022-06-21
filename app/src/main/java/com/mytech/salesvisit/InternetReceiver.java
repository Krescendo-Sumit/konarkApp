package com.mytech.salesvisit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mytech.salesvisit.util.JobUtil;

public class InternetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        InternetService.startUploadRecord(context,null,null);
        JobUtil.scheduleJob(context);
    }


}
