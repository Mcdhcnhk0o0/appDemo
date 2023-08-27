package com.example.appdemo.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "screen_status_record")
public class ScreenStatusBean {

    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "time_stamp")
    public Long timeStamp;

    @ColumnInfo(name = "state")
    public ScreenState state;

}
