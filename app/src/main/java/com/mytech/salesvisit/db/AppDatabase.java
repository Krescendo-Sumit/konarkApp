package com.mytech.salesvisit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class,Record.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {


    public abstract UserDao userDao();
    public abstract RecordDao recordDao();
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User "
                    + " ADD COLUMN logGEOTime INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static AppDatabase getInstance(Context context) {

        return Room.databaseBuilder(context,
                AppDatabase.class, "konark-db")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries().build();
    }
}