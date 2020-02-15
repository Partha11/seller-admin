package com.supernova.selleradmin.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.NonNull;

import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.model.Transaction;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.util.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Receiver", "Triggered");

        String smsSender = "";
        String smsBody;
        StringBuilder builder = new StringBuilder();

        for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

            smsSender = smsMessage.getDisplayOriginatingAddress();
            builder.append(smsMessage.getMessageBody());
        }

        smsBody = builder.toString();

        Log.d("Message:(Receiver)", smsSender);
        Log.d("Message:(Body)", smsBody);

        if (smsSender.equals("bKash") || smsSender.equals("Nagad")) {

            Transaction transaction = Utility.getTransaction(smsBody);

            if (transaction != null) {

                SharedPrefs prefs = new SharedPrefs(context);
                ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
/*                Call<ApiResponse> call = apiInterface.uploadTransaction(prefs.getUserEmail(), prefs.getToken(),
                        transaction.getTrxId(), Double.parseDouble(transaction.getTrxAmount()), transaction.getPhoneNumber());

                call.enqueue(new Callback<ApiResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                        ApiResponse data = response.body();

                        if (data != null) {

                            Log.d("Data", data.getFailReason());

                            if (!data.getStatus().equals(Constants.STATUS_SUCCESS)) {

*//*                                Pending pending = new Pending();

                                pending.setAmount(transaction.getTrxAmount());
                                pending.setPhoneNumber(transaction.getPhoneNumber());
                                pending.setReferenceCode(transaction.getPlayerId());
                                pending.setTrxId(transaction.getTrxId());
                                pending.setIsPending(1);

                                new InsertPending(context).execute(pending);*//*
                            }

                        } else {

*//*                            Pending pending = new Pending();

                            pending.setAmount(transaction.getTrxAmount());
                            pending.setPhoneNumber(transaction.getPhoneNumber());
                            pending.setReferenceCode(transaction.getPlayerId());
                            pending.setTrxId(transaction.getTrxId());
                            pending.setIsPending(1);

                            new InsertPending(context).execute(pending);*//*
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                        Log.d("Send:Failure", "" + t.getMessage());

*//*                        Pending pending = new Pending();

                        pending.setAmount(transaction.getTrxAmount());
                        pending.setPhoneNumber(transaction.getPhoneNumber());
                        pending.setReferenceCode(transaction.getPlayerId());
                        pending.setTrxId(transaction.getTrxId());
                        pending.setIsPending(1);

                        new InsertPending(context).execute(pending);*//*
                    }
                });*/
            }
        }
    }
}

/*class InsertPending extends AsyncTask<Pending, Void, Void> {

    private PendingDao dao;

    InsertPending(Context context) {

        PendingDatabase database = PendingDatabase.getInstance(context);
        dao = database.pendingDao();
    }

    @Override
    protected Void doInBackground(Pending... pendings) {

        dao.insertPending(pendings[0]);
        return null;
    }
}*/