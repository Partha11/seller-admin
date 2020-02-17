package com.supernova.selleradmin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.listener.PendingActionListener;
import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {

    private List<Pending> transactions;
    private PendingActionListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_pending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String number = "Phone No: " + transactions.get(position).getPhoneNumber();
        String trxId = "Trx Id: " + transactions.get(position).getTrxId().toUpperCase();
        String amount = "Amount: " + transactions.get(position).getAmount() + " BDT";

        holder.pendingPhoneNumber.setText(number);
        holder.pendingTransactionId.setText(trxId);
        holder.transactionAmountText.setText(amount);
    }

    @Override
    public int getItemCount() {

        return transactions == null ? 0 : transactions.size();
    }

    public void setListener(PendingActionListener listener) {

        this.listener = listener;
    }

    public void setTransactions(List<Pending> transactions) {

        this.transactions = transactions;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pending_phone_number)
        AppCompatTextView pendingPhoneNumber;
        @BindView(R.id.pending_transaction_id)
        AppCompatTextView pendingTransactionId;
        @BindView(R.id.transaction_amount_text)
        AppCompatTextView transactionAmountText;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.delete_button, R.id.resend_button})
        public void onViewClicked(View view) {

            switch (view.getId()) {

                case R.id.delete_button:
                    listener.delete(transactions.get(getAdapterPosition()));
                    break;
                case R.id.resend_button:
                    listener.resend(transactions.get(getAdapterPosition()), Constants.FROM_ADAPTER);
                    break;
            }
        }
    }
}
