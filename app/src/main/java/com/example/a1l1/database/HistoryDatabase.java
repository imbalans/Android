package com.example.a1l1.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.a1l1.dao.HistoryDao;
import com.example.a1l1.models.History;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
