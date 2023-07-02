package com.example.loancollector.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.loancollector.model.Loan;

import java.util.List;

@Dao
public interface LoanDao {

    String getAllLoansQuery = "SELECT * FROM loan_table";

    @Insert
    void insertLoan(Loan loan);

    @Update
    void updateLoan(Loan loan);

    @Delete
    void deleteLoan(Loan loan);

    @Query(getAllLoansQuery)
    LiveData<List<Loan>> getAllLoans();
}
