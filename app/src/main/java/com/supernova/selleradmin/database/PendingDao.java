package com.supernova.selleradmin.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.util.Constants;

import java.util.List;

@Dao
public interface PendingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPendingList(Pending... pending);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPending(Pending pending);

    @Query("SELECT * FROM " + Constants.TABLE_PENDING)
    LiveData<List<Pending>> getPendingList();

    @Delete
    void deleteTransaction(Pending pending);

    @Query("DELETE FROM " + Constants.TABLE_PENDING)
    void deleteAllTransaction();
}
