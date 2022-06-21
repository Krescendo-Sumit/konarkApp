package com.mytech.salesvisit.arch;


import android.location.Location;
import android.os.SystemClock;
import android.util.Log;

import com.mytech.salesvisit.db.GeoType;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.RecordType;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.util.AppConfig;

import java.util.concurrent.TimeUnit;

public class TimeLocationCollect implements LocationCollect {
    public static final String TAG = "TimeLocationCollect";
    User user;
    Location newLocation,oldLocation;

    public TimeLocationCollect(User user, Location newLocation, Location oldLocation) {
        this.user = user;
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
    }

    @Override
    public Record getRecord() {
        if(user.getLogGEOTime()>0){
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()-oldLocation.getElapsedRealtimeNanos();
             long elapsedSeconds =TimeUnit.SECONDS.convert(elapsedRealtimeNanos,TimeUnit.NANOSECONDS);
           if(AppConfig.TYPE.equalsIgnoreCase("debug")){
               Log.d(TAG,"elapsedRealtimeNanosOld="+oldLocation.getElapsedRealtimeNanos()+", elapsedRealtimeNanosNew="+ SystemClock.elapsedRealtimeNanos() +", ");
               Log.d(TAG,"elapsedRealtimeNanos="+elapsedRealtimeNanos+", elapsedSeconds="+elapsedSeconds+", ");
           }

            if (elapsedSeconds > user.getLogGEOTime()) {
                if(AppConfig.TYPE.equalsIgnoreCase("debug")){
                    Log.d(TAG,"Returning time based record with time:"+ elapsedSeconds);
                }
                Record record = buildRecord(newLocation);
                record.getLocation().setType(GeoType.TIMEBASED);
                return record;
            }

        }
        return null;
    }
}
