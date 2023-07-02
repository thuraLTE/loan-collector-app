package com.example.loancollector.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.loancollector.utils.GsonHelper;
import com.example.loancollector.viewmodel.LoanViewModel;
import com.example.loancollector.R;
import com.example.loancollector.model.Loan;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class LoanListActivity extends AppCompatActivity {

    public static final int LOAN_FORM_REQUEST_CODE = 100;
    LoanViewModel loanViewModel;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_list);

        loanViewModel = ViewModelProviders.of(this).get(LoanViewModel.class);
        gson = new Gson();

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, RemainingLoanListFragment.class, null).commit();

        setUpBottomNavigationView();
        prepareDataFromLoanUpdate();
    }

    private void setUpBottomNavigationView() {
        BottomNavigationView btmNavView = findViewById(R.id.btmNavView);

        btmNavView.setSelectedItemId(R.id.fragment_remaining_loan_list);
        btmNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.fragment_remaining_loan_list) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, RemainingLoanListFragment.class, null).commit();
                return true;
            } else if (item.getItemId() == R.id.fragment_completed_loan_list) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, CompletedLoanListFragment.class, null).commit();
                return true;
            } else
                return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addNewLoan) {
            Intent intent = new Intent(this, LoanFormActivity.class);
            startActivityForResult(intent, LOAN_FORM_REQUEST_CODE);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAN_FORM_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {

                String name = data.getStringExtra(LoanFormActivity.LOAN_NAME);
                String phNum = data.getStringExtra(LoanFormActivity.LOAN_PH_NUM);
                String nrc = data.getStringExtra(LoanFormActivity.LOAN_NRC);
                String amount = data.getStringExtra(LoanFormActivity.LOAN_AMOUNT);
                String status = data.getStringExtra(LoanFormActivity.LOAN_STATUS);

                loanViewModel.insert(new Loan(name, amount, phNum, nrc, status));
                Toast.makeText(this, "New Loan Addition Complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void prepareDataFromLoanUpdate() {
        if (getIntent().getStringExtra("Current Loan To Be Updated") != null) {

            String currentLoanAsString = getIntent().getStringExtra("Current Loan To Be Updated");
            Loan currentLoan = GsonHelper.fromJsonString(gson, currentLoanAsString);

            String name = getIntent().getStringExtra(LoanFormActivity.LOAN_NAME);
            String phNum = getIntent().getStringExtra(LoanFormActivity.LOAN_PH_NUM);
            String nrc = getIntent().getStringExtra(LoanFormActivity.LOAN_NRC);
            String amount = getIntent().getStringExtra(LoanFormActivity.LOAN_AMOUNT);
            String status = getIntent().getStringExtra(LoanFormActivity.LOAN_STATUS);

            loanViewModel.update(new Loan(currentLoan.getId(), name, amount, phNum, nrc, status));
            Toast.makeText(this, "Current Loan Update Complete", Toast.LENGTH_SHORT).show();
        }
    }
}