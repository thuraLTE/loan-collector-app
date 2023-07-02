package com.example.loancollector.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.loancollector.model.Loan;
import com.example.loancollector.ui.LoanFormActivity;

@Database(entities = Loan.class, version = 1, exportSchema = false)
public abstract class LoanDatabase extends RoomDatabase {

    public static LoanDatabase loanDatabase;
    public abstract LoanDao loanDao();

    public static synchronized LoanDatabase getLoanDatabaseInstance(Context context) {
        if (loanDatabase == null) {
            loanDatabase = Room.databaseBuilder(
                    context,
                    LoanDatabase.class,
                    "loan.db"
            )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return loanDatabase;
    }
}
