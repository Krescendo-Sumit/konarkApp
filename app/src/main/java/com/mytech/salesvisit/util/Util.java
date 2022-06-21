package com.mytech.salesvisit.util;

import android.location.Location;
import android.text.TextUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytech.salesvisit.ForegroundLocationService;
import com.mytech.salesvisit.db.GeoType;
import com.mytech.salesvisit.db.Record;
import com.mytech.salesvisit.db.User;
import com.mytech.salesvisit.net.GeoLocation;
import com.mytech.salesvisit.net.GeoRequest;
import com.mytech.salesvisit.net.UserResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Util {
    private static final String PACKAGE_NAME =
            "com.mytech.salesvisit";
    public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";
    public static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    public static final String EXTRA_RECORDTYPE = PACKAGE_NAME + ".rtype";

    public static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";
    public static final int GPSNOTIFYI=2459863;
    public static final String CHANNEL_ID = "channel_01";
    private static ObjectMapper mapper;
    private static SimpleDateFormat parseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    protected static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    protected static String tr(String s){
        return s.length()>20?s.substring(0,18):s;
    }

    public  static User convertUser(UserResponse userResponse){
        User user =new User();
        user.setUserID(userResponse.getUserID());
        user.setFullName(userResponse.getFullName());
        user.setApiAuthToken(userResponse.getApiAuthToken());
        user.setAppAccessAllowed(userResponse.isAppAccessAllowed());
        user.setGEOTrackEnabled(userResponse.isGEOTrackEnabled());
        user.setDesignation(userResponse.getDesignation());
        user.setCheckOutDistance(userResponse.getCheckOutDistance());
        user.setLogGEODistance(userResponse.getLogGEODistance());
        user.setLogGEOTime(userResponse.getLogGEOTime());
        return user;
    }


    public static ObjectMapper getNetworkMapper() {
            if(mapper==null){
                mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            }
            return mapper;
    }

    public  static long getTime(String datestring){
        try {
            Date parse = parseDateFormat.parse(datestring);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public  static String getTimeString(String datestring){
        return timeFormat.format(new Date(getTime(datestring)));
    }

    public static boolean requestingLocationUpdates(ForegroundLocationService foregroundLocationService) {
        return true;
    }

    public static GeoRequest convergeoRecord(Record record) {
        GeoRequest request = new GeoRequest();
        request.setGEOLogTypeId(GeoType.getTypeInt(record.getLocation().getType()));
        request.setGEOLocation(convertLocation(record.getLocation()));
        request.setLogDateTime(parseDateFormat.format(new Date(record.getCreatedAt())));
        return request;
    }

    private static GeoLocation convertLocation(com.mytech.salesvisit.db.GeoLocation location) {
         GeoLocation loc = new GeoLocation();
         loc.setAccuracy(location.getAccuracy());
         loc.setLatLong(location.getLatlng());
        return  loc;
    }

    public static GeoLocation convertlatlong(Location mLocation) {
        GeoLocation loc = new GeoLocation();
        if(mLocation==null)return loc;
        loc.setLatLong(mLocation.getLatitude()+","+mLocation.getLongitude());
        loc.setAccuracy(String.valueOf(mLocation.getAccuracy()));
        return loc;
    }

    public static List<GeoRequest> convergeoRecords(List<Record> geo, Integer userid) {
        List<GeoRequest> requests = new ArrayList<>();
        for(Record record:geo){
            GeoRequest request = convergeoRecord(record);
            request.setUserId(userid);
            requests.add(request);
        }
        return  requests;
    }


    public static float getdistanceBetween(com.mytech.salesvisit.db.GeoLocation location1, com.mytech.salesvisit.db.GeoLocation location2){
        float loc1[]=getDistance(location1.getLatlng());
        float loc2[]=getDistance(location2.getLatlng());
        if(loc1!=null && loc2!=null) {
            float result[]=new float[1];
            Location.distanceBetween(loc1[0],loc1[1],loc2[0],loc2[1],result);
            return result[0];
        }
        return 0;
    }

    private static float[] getDistance(String latlng) {
        if(!TextUtils.isEmpty(latlng)){
            String[] split = latlng.split(",");
            if(split.length==2){
                float result[]=new float[]{Float.valueOf(split[0]),Float.valueOf(split[1])};
                return result;
            }
        }
        return null;
    }

    public static GeoLocation fromDblocation(com.mytech.salesvisit.db.GeoLocation location) {
            GeoLocation loc= new GeoLocation();
            loc.setAccuracy(location.getAccuracy());
            loc.setLatLong(location.getLatlng());
            return loc;
    }

    public static com.mytech.salesvisit.db.GeoLocation toDblocation(GeoLocation location) {
        com.mytech.salesvisit.db.GeoLocation loc = new com.mytech.salesvisit.db.GeoLocation();
        loc.setAccuracy(location.getAccuracy());
        loc.setLatlng(location.getLatLong());
        return loc;
    }
}
