package com.mytech.salesvisit.arch;

import android.location.Location;
import android.text.TextUtils;

import com.mytech.salesvisit.db.GeoLocation;
import com.mytech.salesvisit.db.GeoType;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.RecordType;
import com.mytech.salesvisit.db.User;

public interface LocationCollect {
    default Record buildRecord(Location newLocation){
        Record record = new Record();
        record.setRecordType(RecordType.GEO);
        GeoLocation location = new GeoLocation();
        location.setAccuracy(String.valueOf(newLocation.getAccuracy()));
        location.setLatlng(newLocation.getLatitude()+","+newLocation.getLongitude());
        location.setType(GeoType.LOC);
        record.setLocation(location);
        return record;
    }

     default boolean isLocationEnabled(User user){
        return  user!=null && !TextUtils.isEmpty(user.getApiAuthToken()) && user.isGEOTrackEnabled();
    }
    Record getRecord();
}
