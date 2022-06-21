package com.mytech.salesvisit.db;

import androidx.room.TypeConverter;

public enum RecordType {
    GEO(0),
    CHECKIN(1),
    CHECKOUT(2),
    LOGOUT(3),
    ;
    private final Integer code;

    RecordType(Integer code) {
        this.code = code;
    }

    @TypeConverter
    public static RecordType getRecordType(Integer numeral){
        for(RecordType ds : values()){
            if(ds.code == numeral){
                return ds;
            }
        }
        return null;
    }

    @TypeConverter
    public static Integer getRecordTypeInt(RecordType type){

        if(type != null)
            return type.code;

        return  null;
    }
}
