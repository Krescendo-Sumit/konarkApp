package com.mytech.salesvisit.arch;

import android.location.Location;
import android.util.Log;

import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.util.AppConfig;

public class DistanceLocationCollect implements LocationCollect {
    public static final String TAG = "DistanceLocationCollect";
    User user;
    Location newLocation,oldLocation;

    public DistanceLocationCollect(User user, Location newLocation, Location oldLocation) {
        this.user = user;
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
    }

    @Override
    public Record getRecord() {
        if(user.getLogGEODistance()>0.0f){
            float distance = oldLocation.distanceTo(newLocation);
            if(AppConfig.TYPE.equalsIgnoreCase("debug")){
                Log.d(TAG,"Calculated distance:"+ distance);
            }
            if(distance >user.getLogGEODistance()){
                if(AppConfig.TYPE.equalsIgnoreCase("debug")){
                    Log.d(TAG,"Returning distance based record with distance:"+ distance);
                }
                return buildRecord(newLocation);
            }
        }
        return null;
    }
}
