package com.supernova.selleradmin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.model.Transaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {

        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String number = "Phone No: " + transactions.get(position).getPhoneNumber();
        String trxId = "Trx Id: " + transactions.get(position).getTrxId().toUpperCase();
        String amount = "Amount: " + transactions.get(position).getTrxAmount() + " BDT";

        holder.playerIdText.setText(transactions.get(position).getPlayerId().toUpperCase());
        holder.phoneNumberText.setText(number);
        holder.transactionIdText.setText(trxId);
        holder.transactionAmountText.setText(amount);
        holder.transactionTimeText.setText(transactions.get(position).getTrxTime());
    }

    @Override
    public int getItemCount() {

        return transactions == null ? 0 : transactions.size();
    }

    public List<Transaction> getTransactions() {

        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {

        this.transactions = transactions;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_id_text)
        AppCompatTextView playerIdText;
        @BindView(R.id.successful_icon)
        AppCompatImageView successfulIcon;
        @BindView(R.id.phone_number_text)
        AppCompatTextView phoneNumberText;
        @BindView(R.id.transaction_id_text)
        AppCompatTextView transactionIdText;
        @BindView(R.id.transaction_amount_text)
        AppCompatTextView transactionAmountText;
        @BindView(R.id.transaction_time_text)
        AppCompatTextView transactionTimeText;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
