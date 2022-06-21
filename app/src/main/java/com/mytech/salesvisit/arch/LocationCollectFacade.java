package com.mytech.salesvisit.arch;

import android.location.Location;
import android.util.Log;

import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.User;

public class LocationCollectFacade implements LocationCollect {
    User user;
    Location newLocation,oldLocation;

    public LocationCollectFacade(User user, Location newLocation, Location oldLocation) {
        this.user = user;
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
    }

    @Override
    public Record getRecord() {
       Record record=null;
        if(isLocationEnabled(user)){
            LocationCollect locationCollect=null;
            locationCollect =new DistanceLocationCollect(user,newLocation,oldLocation);
            record=locationCollect.getRecord();
            if(record!=null){
               return record;
            }
            locationCollect =new TimeLocationCollect(user,newLocation,oldLocation);
            record=locationCollect.getRecord();
        }

        return record;
    }
}
