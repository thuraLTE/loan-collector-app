package com.example.loancollector.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loancollector.R;
import com.example.loancollector.model.Loan;
import com.example.loancollector.ui.LoanFormActivity;
import com.example.loancollector.ui.SettleLoanActivity;
import com.example.loancollector.utils.GsonHelper;
import com.example.loancollector.utils.TimeHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RemainingLoanListAdapter extends RecyclerView.Adapter<RemainingLoanListAdapter.RemainingLoanViewHolder> {

    Context context;
    ArrayList<Loan> remainingLoanList;
    OnLoanToBeDeletedFromDB listener;
    Gson gson;

    public RemainingLoanListAdapter(Context context, ArrayList<Loan> remainingLoanList, Gson gson, OnLoanToBeDeletedFromDB listener) {
        this.context = context;
        this.remainingLoanList = remainingLoanList;
        this.listener = listener;
        this.gson = gson;
    }

    public void refreshRemainingLoanList(List<Loan> newRemainingLoanList) {
        remainingLoanList.clear();
        remainingLoanList.addAll(newRemainingLoanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RemainingLoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RemainingLoanViewHolder(LayoutInflater.from(context).inflate(R.layout.single_remaining_loan_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RemainingLoanViewHolder holder, int position) {
        Loan currentLoan = remainingLoanList.get(position);

        holder.tvName.setText(context.getString(R.string.name_placeholder, currentLoan.getName()));
        holder.tvRemainingPrice.setText(context.getString(R.string.remaining_price_placeholder, TimeHelper.formatCurrency(currentLoan.getRemainingPrice())));
        holder.tvPhNum.setText(context.getString(R.string.phone_number_placeholder, currentLoan.getPhNum()));
        holder.tvNrc.setText(context.getString(R.string.nrc_placeholder, currentLoan.getNrc()));
        holder.tvStatus.setText(context.getString(R.string.status_placeholder, currentLoan.getStatus()));

        if (currentLoan.getStatus().equals("Daily")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else if (currentLoan.getStatus().equals("Weekly")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.purple));
        } else if (currentLoan.getStatus().equals("Monthly")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.maroon));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    final String currentLoanAsString = GsonHelper.toJsonString(gson, currentLoan);
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.editLoan) {
                            Intent intent = new Intent(context, LoanFormActivity.class);
                            intent.putExtra("Current Loan To Be Updated", currentLoanAsString);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                            return true;
                        } else if (menuItem.getItemId() == R.id.deleteLoan) {
                            listener.onLoanClicked(currentLoan);
                            return true;
                        } else if (menuItem.getItemId() == R.id.settleLoan) {
                            Intent intent = new Intent(context, SettleLoanActivity.class);
                            intent.putExtra("Current Loan To Be Settled", currentLoanAsString);
                            context.startActivity(intent);
                            ((Activity)context).finish();
                            return true;
                        } else
                            return false;
                    }
                });

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return remainingLoanList.size();
    }

    static class RemainingLoanViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRemainingPrice, tvPhNum, tvNrc, tvStatus;
        public RemainingLoanViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvRemainingPrice = itemView.findViewById(R.id.tvRemainingPrice);
            tvPhNum = itemView.findViewById(R.id.tvPhNum);
            tvNrc = itemView.findViewById(R.id.tvNrc);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    public interface OnLoanToBeDeletedFromDB {
        default void onLoanClicked(Loan loan) {}
    }
}
