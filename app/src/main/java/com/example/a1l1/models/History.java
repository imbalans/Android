package com.example.a1l1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mydatalist")
public class History {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cityName")
    private String cityName;

    @ColumnInfo(name = "degreesValue")
    public String degreesValue;

    public void setId(int id) {
        this.id = id;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDegreesValue(String degreesValue) {
        this.degreesValue = degreesValue;
    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDegreesValue() {
        return degreesValue;
    }
}
