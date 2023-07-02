package com.example.loancollector.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.loancollector.model.Loan;
import com.example.loancollector.repository.LoanRepository;

import java.util.List;

public class LoanViewModel extends AndroidViewModel {

    private LoanRepository loanRepository;
    private LiveData<List<Loan>> loanLiveDataList;

    public LoanViewModel(@NonNull Application application) {
        super(application);
        loanRepository = new LoanRepository(application);
        loanLiveDataList = loanRepository.getLoanList();
    }

    public void insert(Loan loan) {
        loanRepository.insertNewLoan(loan);
    }

    public void delete(Loan loan) {
        loanRepository.deleteCurrentLoan(loan);
    }

    public void update(Loan loan) {
        loanRepository.updateCurrentLoan(loan);
    }

    public LiveData<List<Loan>> getLoanLiveDataList() {
        return loanLiveDataList;
    }
}
