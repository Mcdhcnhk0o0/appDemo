package com.example.appdemo.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appdemo.database.entity.ScreenStatusBean;

import java.util.List;

@Dao
public interface ScreenStatusDao {

    @Query("select * from screen_status_record")
    List<ScreenStatusBean> getAllRecords();

    @Insert
    void addRecord(ScreenStatusBean record);

}
