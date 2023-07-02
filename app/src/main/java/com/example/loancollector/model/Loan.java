package com.example.loancollector.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "loan_table")
public class Loan {

    @PrimaryKey (autoGenerate = true)
    public int id;
    public String name;
    String remainingPrice;
    public String phNum;
    public String nrc;
    public String settledDate;
    String status;

    public Loan(int id, String name, String remainingPrice, String phNum, String nrc, String status) {
        this.id = id;
        this.name = name;
        this.remainingPrice = remainingPrice;
        this.phNum = phNum;
        this.nrc = nrc;
        this.status = status;
    }

    public Loan(String name, @Nullable String remainingPrice, String phNum, String nrc, String status) {
        this.name = name;
        this.remainingPrice = remainingPrice;
        this.phNum = phNum;
        this.nrc = nrc;
        this.status = status;
    }

    public Loan() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRemainingPrice() {
        return remainingPrice;
    }

    public String getPhNum() {
        return phNum;
    }

    public String getNrc() {
        return nrc;
    }

    public String getStatus() {
        return status;
    }

    public void setRemainingPrice(String remainingPrice) {
        this.remainingPrice = remainingPrice;
    }

    public String getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(String settledDate) {
        this.settledDate = settledDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
