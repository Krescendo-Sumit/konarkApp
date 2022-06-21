package com.mytech.salesvisit.util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.mytech.salesvisit.ScheduleJobService;

import java.util.List;

public class JobUtil {

    private static final String TAG = JobUtil.class.getSimpleName();
    public static final int JOB_UPLOAD_ID = 101;
    public static final int JOB_RECORD_ID=102;


    public static void scheduleJob(Context context) {
            if(isScheduled(context))return;
        ComponentName serviceComponent = new ComponentName(context, ScheduleJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_RECORD_ID, serviceComponent)
                .setPeriodic(5 * 60 * 1000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // require unmetered network
                .setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = getScheduler(context);
        int resultCode = jobScheduler.schedule(builder.build());
        if(JobScheduler.RESULT_SUCCESS==resultCode)
            Log.i(TAG, "Job schedule Success:");
        else
            Log.i(TAG, "Job schedule failed:");

    }

    public static boolean isScheduled(Context context) {
        JobScheduler js = getScheduler(context);
        List<JobInfo> jobs = js.getAllPendingJobs();
        if (jobs == null) {
            return false;
        }
        for (int i=0; i<jobs.size(); i++) {
            if (jobs.get(i).getId() == JOB_RECORD_ID) {
                return true;
            }
        }
        return false;
    }

    private static JobScheduler getScheduler(Context context) {
    return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    public static void cancelJob(Context context) {
        JobScheduler js =  getScheduler(context);
        js.cancel(JOB_RECORD_ID);
    }
}
