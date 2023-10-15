package com.example.appdemo.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.appdemo.database.entity.ScreenStatusBean;

import java.util.List;

@Dao
public interface ScreenStatusDao {

    @Query("select * from screen_status_record order by time_stamp desc")
    List<ScreenStatusBean> getAllRecords();

    @Query("select * from screen_status_record order by time_stamp desc limit :limit offset :offset")
    List<ScreenStatusBean> getRecentRecords(int limit, int offset);

    @Insert
    void addRecord(ScreenStatusBean record);

}
