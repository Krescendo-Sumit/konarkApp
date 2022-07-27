package com.mytech.salesvisit.util;

import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.mytech.salesvisit.arch.LocationCollect;
import com.mytech.salesvisit.arch.LocationCollectFacade;
import com.mytech.salesvisit.db.AppDatabase;
import com.mytech.salesvisit.db.GeoLocation;
import com.mytech.salesvisit.db.GeoType;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.RecordType;
import com.mytech.salesvisit.db.User;

import java.util.concurrent.TimeUnit;

public class DbUtil {

    private static final String TAG = DbUtil.class.getSimpleName();

    public static boolean saveGeoLog(AppDatabase db, Location oldLocation, Location newLocation){
        User user = db.userDao().getUser();
        LocationCollect locationCollect = new LocationCollectFacade(user,newLocation,oldLocation);
        Record record = locationCollect.getRecord();
        if(record!=null){
            saveRecord(db,record);
            return true;
        }
        return false;

    }

    private static void saveRecord(AppDatabase db, Record record) {
        record.setCreatedAt(System.currentTimeMillis());
        db.recordDao().insertAll(record);
    }

    public static boolean isLocationEnabled(User user){
        return  user!=null && !TextUtils.isEmpty(user.getApiAuthToken()) && user.isGEOTrackEnabled();
    }

    public static boolean saveCheckin(AppDatabase db, GeoLocation location, int custmerid){
        if( location!=null){
            Record record = new Record();
            record.setRecordType(RecordType.CHECKIN);
            location.setType(GeoType.CHECKIN);
            record.setCustomerId(custmerid);
            record.setLocation(location);
            saveRecord(db,record);
            return true;
        }
        return false;
    }

    public static void clearCheckin(AppDatabase db) {
        Record checkin = db.recordDao().checkin();
        if(checkin!=null){
            db.recordDao().delete(checkin);
        }
    }

}
