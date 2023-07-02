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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loancollector.viewmodel.LoanViewModel;
import com.example.loancollector.R;
import com.example.loancollector.adapter.RemainingLoanListAdapter;
import com.example.loancollector.model.Loan;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RemainingLoanListFragment extends Fragment implements RemainingLoanListAdapter.OnLoanToBeDeletedFromDB {

    private static final String TAG = "RemainingLoanListFragment";
    EditText edtSearch;
    RecyclerView rvRemainingLoanList;
    LoanViewModel loanViewModel;
    RemainingLoanListAdapter remainingLoanListAdapter;
    ArrayList<Loan> remainingLoanList;
    ArrayList<Loan> userQueryLoanList;
    Gson gson;

    public RemainingLoanListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remaining_loan_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtSearch = view.findViewById(R.id.edtSearch);
        rvRemainingLoanList = view.findViewById(R.id.rvRemainingLoanList);

        remainingLoanList = new ArrayList<>();
        userQueryLoanList = new ArrayList<>();
        gson = new Gson();

        loanViewModel = ViewModelProviders.of(RemainingLoanListFragment.this).get(LoanViewModel.class);

        populateRemainingLoanListFromDB();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInput = charSequence.toString();

                if (!TextUtils.isEmpty(userInput)) {
                    userQueryLoanList.clear();

                    for (int j = 0; j < remainingLoanList.size(); j++) {
                        if (remainingLoanList.get(j).getName().contains(userInput)) {
                            userQueryLoanList.add(remainingLoanList.get(j));

                            Log.d(TAG, "User Query Loan List Size: " + userQueryLoanList.size());

                            remainingLoanListAdapter.refreshRemainingLoanList(userQueryLoanList);
                        }
                    }
                } else {
                    populateRemainingLoanListFromDB();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void populateRemainingLoanListFromDB() {
        loanViewModel.getLoanLiveDataList().observe(getViewLifecycleOwner(), new Observer<List<Loan>>() {
            @Override
            public void onChanged(List<Loan> loans) {
                remainingLoanList.clear();

                for (int i=0; i<loans.size(); i++) {
                    if (!loans.get(i).getStatus().equals("Completed")) {
                        remainingLoanList.add(loans.get(i));
                    }
                }
                remainingLoanListAdapter = new RemainingLoanListAdapter(requireContext(), remainingLoanList, gson,RemainingLoanListFragment.this);
                rvRemainingLoanList.setAdapter(remainingLoanListAdapter);
                rvRemainingLoanList.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });
    }

    @Override
    public void onLoanClicked(Loan loan) {
        loanViewModel.delete(loan);
        Toast.makeText(requireContext(), "Loan Deletion Complete", Toast.LENGTH_SHORT).show();
        populateRemainingLoanListFromDB();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateRemainingLoanListFromDB();
    }
}