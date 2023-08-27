package com.example.appdemo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.appdemo.database.dao.ScreenStatusDao;
import com.example.appdemo.database.entity.ScreenStatusBean;

@Database(entities = {ScreenStatusBean.class}, version = 2)
public abstract class ScreenStatusRecordDatabase extends RoomDatabase {

    public abstract ScreenStatusDao screenStatusDao();

}
