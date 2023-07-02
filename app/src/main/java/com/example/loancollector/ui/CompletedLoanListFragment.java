package com.example.loancollector.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.loancollector.viewmodel.LoanViewModel;
import com.example.loancollector.R;
import com.example.loancollector.adapter.CompletedLoanListAdapter;
import com.example.loancollector.model.Loan;

import java.util.ArrayList;
import java.util.List;

public class CompletedLoanListFragment extends Fragment {

    EditText edtSearch;
    RecyclerView rvCompletedLoanList;
    LoanViewModel loanViewModel;
    ArrayList<Loan> completedLoanList;
    ArrayList<Loan> userQueryLoanList;
    CompletedLoanListAdapter completedLoanListAdapter;

    public CompletedLoanListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_loan_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearch = view.findViewById(R.id.edtSearch);
        rvCompletedLoanList = view.findViewById(R.id.rvCompletedLoanList);

        completedLoanList = new ArrayList<>();
        userQueryLoanList = new ArrayList<>();

        loanViewModel = ViewModelProviders.of(CompletedLoanListFragment.this).get(LoanViewModel.class);

        populateCompletedLoanListFromDB();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInput = charSequence.toString();

                if (!TextUtils.isEmpty(userInput)) {
                    userQueryLoanList.clear();

                    for (int j = 0; j < completedLoanList.size(); j++) {
                        if (completedLoanList.get(j).getName().contains(userInput)) {
                            userQueryLoanList.add(completedLoanList.get(j));
                            completedLoanListAdapter.refreshCompletedLoanList(userQueryLoanList);
                        }
                    }
                } else {
                    populateCompletedLoanListFromDB();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void populateCompletedLoanListFromDB() {
        loanViewModel.getLoanLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<Loan>>() {
            @Override
            public void onChanged(List<Loan> loans) {
                completedLoanList.clear();

                for (int i=0; i<loans.size(); i++) {
                    if (loans.get(i).getStatus().equals("Completed")) {
                        completedLoanList.add(loans.get(i));
                    }
                }
                completedLoanListAdapter = new CompletedLoanListAdapter(requireContext(), completedLoanList);
                rvCompletedLoanList.setAdapter(completedLoanListAdapter);
                rvCompletedLoanList.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        populateCompletedLoanListFromDB();
    }
}