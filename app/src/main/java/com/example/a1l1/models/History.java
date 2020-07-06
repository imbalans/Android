package com.example.a1l1.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mydatahistory")
public class History {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cityName")
    private String cityName;

    @ColumnInfo(name = "degreesValue")
    public String degreesValue;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDegreesValue(String degreesValue) {
        this.degreesValue = degreesValue;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDegreesValue() {
        return degreesValue;
    }
}
