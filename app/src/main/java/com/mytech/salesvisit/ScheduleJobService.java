package com.mytech.salesvisit;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.mytech.salesvisit.util.JobUtil;
import com.mytech.salesvisit.util.Util;

public class ScheduleJobService extends JobService {
    private static final String TAG = ScheduleJobService.class.getSimpleName();

    public ScheduleJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "Job executing");
        InternetService.startUploadRecord(getApplicationContext(),null,null);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
