package com.mytech.salesvisit.db;

import androidx.room.TypeConverter;

public enum GeoType {
    LOC(0),
    LOGIN(1),
    LOGOUT(2),
    CHECKIN(3),
    CHCKOUT(4),
    TIMEBASED(-1);
    private final Integer code;

    GeoType(Integer c) {
        code=c;
    }

    @TypeConverter
    public static GeoType getType(Integer numeral){
        for(GeoType ds : values()){
            if(ds.code == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getTypeInt(GeoType type){

        if(type != null)
            return type.code;

        return  null;
    }
}
