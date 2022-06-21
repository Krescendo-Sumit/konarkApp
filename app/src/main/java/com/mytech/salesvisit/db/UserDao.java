package com.mytech.salesvisit.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user LIMIT 1")
    User getUser();

    @Query("SELECT * FROM user WHERE userID IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE username LIKE :uname LIMIT 1")
    User findByUsername(String uname);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void updateUsers(User... users);
}