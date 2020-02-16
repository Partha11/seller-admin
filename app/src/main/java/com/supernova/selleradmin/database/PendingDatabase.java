package com.supernova.selleradmin.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.util.Constants;

@Database(entities = {Pending.class}, version = Constants.DATABASE_VERSION, exportSchema = false)
public abstract class PendingDatabase extends RoomDatabase {

    private static PendingDatabase instance;

    public abstract PendingDao pendingDao();

    public static synchronized PendingDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context, PendingDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
