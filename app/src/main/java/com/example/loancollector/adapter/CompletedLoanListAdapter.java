package com.example.loancollector.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loancollector.R;
import com.example.loancollector.model.Loan;

import java.util.ArrayList;
import java.util.List;

public class CompletedLoanListAdapter extends RecyclerView.Adapter<CompletedLoanListAdapter.CompletedLoanViewHolder> {

    Context context;
    ArrayList<Loan> completedLoanList;

    public CompletedLoanListAdapter(Context context, ArrayList<Loan> completedLoanList) {
        this.context = context;
        this.completedLoanList = completedLoanList;
    }

    public void refreshCompletedLoanList(List<Loan> newCompletedLoanList) {
        completedLoanList.clear();
        completedLoanList.addAll(newCompletedLoanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompletedLoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompletedLoanViewHolder(LayoutInflater.from(context).inflate(R.layout.single_completed_loan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedLoanViewHolder holder, int position) {
        Loan currentLoan = completedLoanList.get(position);

        holder.tvName.setText(context.getString(R.string.name_placeholder, currentLoan.getName()));
        holder.tvSettledDate.setText(context.getString(R.string.settled_date_placeholder, currentLoan.getSettledDate()));
        holder.tvPhNum.setText(context.getString(R.string.phone_number_placeholder, currentLoan.getPhNum()));
        holder.tvNrc.setText(context.getString(R.string.nrc_placeholder, currentLoan.getNrc()));
        holder.tvStatus.setText(context.getString(R.string.status_placeholder, currentLoan.getStatus()));
    }

    @Override
    public int getItemCount() {
        return completedLoanList.size();
    }

    static class CompletedLoanViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvSettledDate, tvPhNum, tvNrc, tvStatus;
        public CompletedLoanViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvSettledDate = itemView.findViewById(R.id.tvSettledDate);
            tvPhNum = itemView.findViewById(R.id.tvPhNum);
            tvNrc = itemView.findViewById(R.id.tvNrc);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
