package com.example.loancollector.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.loancollector.R;
import com.example.loancollector.model.Loan;
import com.example.loancollector.utils.GsonHelper;
import com.google.gson.Gson;

public class LoanFormActivity extends AppCompatActivity {

    public static final String LOAN_NAME = "loan_name";
    public static final String LOAN_PH_NUM = "loan_ph_num";
    public static final String LOAN_NRC = "loan_nrc";
    public static final String LOAN_AMOUNT = "loan_amount";
    public static final String LOAN_STATUS = "loan_status";

    EditText editName, editPhNum, editNrc, editAmount;
    RadioButton radioBtnDaily, radioBtnWeekly, radioBtnMonthly;
    AppCompatButton btnConfirm;
    boolean isUpdate = false;
    Loan currentLoan;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_form);

        editName = findViewById(R.id.editName);
        editPhNum = findViewById(R.id.editPhNum);
        editNrc = findViewById(R.id.editNrc);
        editAmount = findViewById(R.id.editAmount);

        radioBtnDaily = findViewById(R.id.radioBtnDaily);
        radioBtnWeekly= findViewById(R.id.radioBtnWeekly);
        radioBtnMonthly = findViewById(R.id.radioBtnMonthly);

        btnConfirm = findViewById(R.id.btnConfirm);

        gson = new Gson();

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));

        if (getIntent().getStringExtra("Current Loan To Be Updated") != null) {

            getSupportActionBar().setTitle("Update Loan");
            String currentLoanAsString = getIntent().getStringExtra("Current Loan To Be Updated");

            currentLoan = GsonHelper.fromJsonString(gson, currentLoanAsString);

            editName.setText(currentLoan.getName());
            editPhNum.setText(currentLoan.getPhNum());
            editNrc.setText(currentLoan.getNrc());
            editAmount.setText(currentLoan.getRemainingPrice());

            if (currentLoan.getStatus().equals("Daily")) {
                radioBtnDaily.setChecked(true);
            } else if (currentLoan.getStatus().equals("Weekly")) {
                radioBtnWeekly.setChecked(true);
            } else if (currentLoan.getStatus().equals("Monthly")) {
                radioBtnMonthly.setChecked(true);
            }

            isUpdate = true;

        } else {

            // Change Action Bar Title to Add New Loan
            getSupportActionBar().setTitle("Add New Loan");

            isUpdate = false;
        }

        initEvents();
    }

    private void initEvents() {
        btnConfirm.setOnClickListener(view -> {

            String name = editName.getText().toString().trim();
            String phNum = editPhNum.getText().toString().trim();
            String nrc = editNrc.getText().toString().trim();
            String amount = editAmount.getText().toString().trim();
            String status = "";

            if (radioBtnDaily.isChecked()) {
                status = radioBtnDaily.getText().toString();
            } else if (radioBtnWeekly.isChecked()) {
                status = radioBtnWeekly.getText().toString();
            } else if (radioBtnMonthly.isChecked()) {
                status = radioBtnMonthly.getText().toString();
            }

            if (isUpdate) {
                Intent intent = new Intent(this, LoanListActivity.class);
                intent.putExtra(LOAN_NAME, name);
                intent.putExtra(LOAN_PH_NUM, phNum);
                intent.putExtra(LOAN_NRC, nrc);
                intent.putExtra(LOAN_AMOUNT, amount);
                intent.putExtra(LOAN_STATUS, status);

                String currentLoanAsString = GsonHelper.toJsonString(gson, currentLoan);
                intent.putExtra("Current Loan To Be Updated", currentLoanAsString);

                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent();
                intent.putExtra(LOAN_NAME, name);
                intent.putExtra(LOAN_PH_NUM, phNum);
                intent.putExtra(LOAN_NRC, nrc);
                intent.putExtra(LOAN_AMOUNT, amount);
                intent.putExtra(LOAN_STATUS, status);

                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }
}