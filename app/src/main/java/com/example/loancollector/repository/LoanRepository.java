package com.example.loancollector.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.loancollector.database.LoanDao;
import com.example.loancollector.database.LoanDatabase;
import com.example.loancollector.model.Loan;

import java.util.List;

public class LoanRepository {

    private LoanDao loanDao;
    private LiveData<List<Loan>> loanList;

    public LoanRepository(Application application) {
        LoanDatabase loanDatabase = LoanDatabase.getLoanDatabaseInstance(application);
        loanDao = loanDatabase.loanDao();
        loanList = loanDao.getAllLoans();
    }

    public LiveData<List<Loan>> getLoanList() {
        return loanList;
    }

    public void insertNewLoan(Loan loan) {
        new InsertNewLoanAsyncTask(loanDao).execute(loan);
    }

    public static class InsertNewLoanAsyncTask extends AsyncTask<Loan, Void, Void> {

        private LoanDao loanDao;

        public InsertNewLoanAsyncTask(LoanDao loanDao) {
            this.loanDao = loanDao;
        }

        @Override
        protected Void doInBackground(Loan... loans) {
            loanDao.insertLoan(loans[0]);
            return null;
        }
    }

    public void deleteCurrentLoan(Loan loan) {
        new DeleteCurrentLoanAsyncTask(loanDao).execute(loan);
    }

    public static class DeleteCurrentLoanAsyncTask extends AsyncTask<Loan, Void, Void> {

        private LoanDao loanDao;

        public DeleteCurrentLoanAsyncTask(LoanDao loanDao) {
            this.loanDao = loanDao;
        }

        @Override
        protected Void doInBackground(Loan... loans) {
            loanDao.deleteLoan(loans[0]);
            return null;
        }
    }

    public void updateCurrentLoan(Loan loan) {
        new UpdateCurrentLoanAsyncTask(loanDao).execute(loan);
    }

    public static class UpdateCurrentLoanAsyncTask extends AsyncTask<Loan, Void, Void> {

        private LoanDao loanDao;

        public UpdateCurrentLoanAsyncTask(LoanDao loanDao) {
            this.loanDao = loanDao;
        }

        @Override
        protected Void doInBackground(Loan... loans) {
            loanDao.updateLoan(loans[0]);
            return null;
        }
    }
}
