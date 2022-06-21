package com.mytech.salesvisit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecordDao {

    @Query("SELECT * FROM record")
    List<Record> getAll();

    @Query("SELECT * FROM record LIMIT 50")
    Record getRecords();

    @Query("SELECT * FROM record WHERE recordType =0 order by createdAt LIMIT 50")
    List<Record> geo();
    @Query("SELECT * FROM record WHERE recordType =1 order by createdAt LIMIT 1")
    Record checkin();
    @Query("SELECT * FROM record WHERE recordType =1 order by createdAt LIMIT 50")
    List<Record> checkins();
    @Query("SELECT * FROM record WHERE recordType =2 order by createdAt LIMIT 50")
    List<Record> checkout();
    @Query("SELECT * FROM record WHERE recordType =3 order by createdAt LIMIT 50")
    List<Record> logout();
    @Insert
    void insertAll(Record... users);
    @Delete
    void delete(Record... record);
    @Update
    void updateRecords(Record... records);

    @Query("SELECT * FROM record WHERE recordType =0 order by createdAt desc LIMIT 1")
    Record lastGeo();
}
