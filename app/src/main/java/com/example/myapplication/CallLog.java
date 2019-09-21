package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "call_log")
public class CallLog {
    @PrimaryKey(autoGenerate = true)

    private int id;

    private String phone_no;

    private String customer_no;

    private String duration;

    private String time;

    public CallLog(int id, String phone_no, String customer_no, String duration, String time) {
        this.id = id;
        this.phone_no = phone_no;
        this.customer_no = customer_no;
        this.duration = duration;
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public String getDuration() {
        return duration;
    }

    public String getTime() {
        return time;
    }
}
