package com.example.loancollector.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.loancollector.utils.GsonHelper;
import com.example.loancollector.utils.TimeHelper;
import com.example.loancollector.viewmodel.LoanViewModel;
import com.example.loancollector.R;
import com.example.loancollector.model.Loan;
import com.google.gson.Gson;

public class SettleLoanActivity extends AppCompatActivity {

    TextView tvName, tvRemainingPrice, tvPhNum, tvNrc, tvStatus;
    EditText editAmount;
    AppCompatButton btnConfirm;
    Loan currentLoan;
    LoanViewModel loanViewModel;
    Gson gson;
    String settledDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_loan);

        tvName = findViewById(R.id.tvName);
        tvRemainingPrice = findViewById(R.id.tvRemainingPrice);
        tvPhNum = findViewById(R.id.tvPhNum);
        tvNrc = findViewById(R.id.tvNrc);
        tvStatus = findViewById(R.id.tvStatus);

        editAmount = findViewById(R.id.editAmount);

        btnConfirm = findViewById(R.id.btnConfirm);
        gson = new Gson();

        loanViewModel = ViewModelProviders.of(this).get(LoanViewModel.class);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));

        if (getIntent().getStringExtra("Current Loan To Be Settled") != null) {

            String currentLoanAsString = getIntent().getStringExtra("Current Loan To Be Settled");
            currentLoan = GsonHelper.fromJsonString(gson, currentLoanAsString);

            tvName.setText(getString(R.string.name_placeholder, currentLoan.getName()));
            tvRemainingPrice.setText(getString(R.string.remaining_price_placeholder, TimeHelper.formatCurrency(currentLoan.getRemainingPrice())));
            tvPhNum.setText(getString(R.string.phone_number_placeholder, currentLoan.getPhNum()));
            tvNrc.setText(getString(R.string.nrc_placeholder, currentLoan.getNrc()));
            tvStatus.setText(getString(R.string.status_placeholder, currentLoan.getStatus()));
        }

        initEvents();
    }

    private void initEvents() {
        btnConfirm.setOnClickListener(view -> {

            // Do minus calculation & save the result to DB

            String amount = editAmount.getText().toString().trim();
            double paidAmount = Double.parseDouble(amount);

            String remainingAmountString = currentLoan.getRemainingPrice();
            double remainingAmount = Double.parseDouble(remainingAmountString);

            double calculatedAmount = remainingAmount - paidAmount;
            String calculatedAmountString = String.valueOf(calculatedAmount);

            if (calculatedAmount <= 0) {
                settledDate = TimeHelper.calculateSettledDate();

                currentLoan.setStatus("Completed");
                currentLoan.setRemainingPrice(calculatedAmountString);
                currentLoan.setSettledDate(settledDate);

                loanViewModel.update(currentLoan);

            } else {
                currentLoan.setRemainingPrice(calculatedAmountString);

                loanViewModel.update(currentLoan);
            }

            Intent intent = new Intent(this, LoanListActivity.class);
            startActivity(intent);
            finish();
        });
    }
}