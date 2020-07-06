package com.example.a1l1.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.a1l1.models.History;

import java.util.List;

@androidx.room.Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addData(History history);

    @Query("SELECT * FROM mydatahistory")
    List<History> getMyData();

    @Query("DELETE FROM mydatahistory")
    void delete();
}
