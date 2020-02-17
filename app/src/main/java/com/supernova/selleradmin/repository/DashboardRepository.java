package com.supernova.selleradmin.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supernova.selleradmin.database.PendingDao;
import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.service.ApiClient;
import com.supernova.selleradmin.service.ApiInterface;
import com.supernova.selleradmin.task.InsertPending;
import com.supernova.selleradmin.util.Constants;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {

    private PendingDao dao;

    public DashboardRepository(PendingDao dao) {

        this.dao = dao;
    }

    public LiveData<ApiResponse> getTransactions(String email, String token) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.getTransactions(email, token);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse body = response.body();

                if (body != null) {

                    callback.setValue(body);

                } else {

                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.e("Error", Objects.requireNonNull(t.getMessage()));
                callback.setValue(new ApiResponse());
            }
        });

        return callback;
    }

    public LiveData<ApiResponse> searchTransaction(String email, String token, String key) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.searchTransaction(email, token, key);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse body = response.body();

                if (body != null) {

                    callback.setValue(body);

                } else {

                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.e("Error", Objects.requireNonNull(t.getMessage()));
                callback.setValue(new ApiResponse());
            }
        });

        return callback;
    }

    public LiveData<ApiResponse> getData(String email, String token) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.getData(email, token);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse body = response.body();

                if (body != null) {

                    callback.setValue(body);

                } else {

                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.e("Error", Objects.requireNonNull(t.getMessage()));
                callback.setValue(new ApiResponse());
            }
        });

        return callback;
    }

    public LiveData<ApiResponse> updateNumber(String email, String token, int position, String number) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        String field = "";

        if (position == 0) {

            field = "bkash";

        } else if (position == 1) {

            field = "nogod";

        } else if (position == 2) {

            field = "rocket";
        }

        String finalField = field;

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.updateNumber(email, token, finalField, number);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse body = response.body();

                if (body != null) {

                    callback.setValue(body);

                } else {

                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.d("Error", Objects.requireNonNull(t.getMessage()));
                callback.setValue(new ApiResponse());
            }
        });


        return callback;
    }

    public LiveData<ApiResponse> updatePassword(String userEmail, String userToken, String password) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiInterface.updatePassword(userEmail, userToken, password);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse body = response.body();

                if (body != null) {

                    callback.setValue(body);

                } else {

                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.e("Error", Objects.requireNonNull(t.getMessage()));
                callback.setValue(new ApiResponse());
            }
        });

        return callback;
    }

    public LiveData<Boolean> deletePending(Pending pending) {

        MutableLiveData<Boolean> callback = new MutableLiveData<>();

        try {

            callback.setValue(new DeletePending(dao).execute(pending).get());

        } catch (Exception e) {

            callback.setValue(false);
            e.printStackTrace();
        }

        return callback;
    }

    public LiveData<ApiResponse> addToServer(String email, String token, Pending pending) {

        MutableLiveData<ApiResponse> callback = new MutableLiveData<>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        InsertPending insertPending = new InsertPending(dao);
        Call<ApiResponse> call = apiInterface.uploadTransaction(email, token,
                pending.getTrxId(), pending.getAmount(), pending.getPhoneNumber(), pending.getTrxType());

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                ApiResponse data = response.body();

                if (data != null) {

                    if (!data.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        insertPending.execute(pending);
                        callback.setValue(data);

                    } else {

                        callback.setValue(data);
                    }

                } else {

                    insertPending.execute(pending);
                    callback.setValue(new ApiResponse());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {

                Log.d("Send:Failure", "" + t.getMessage());

                insertPending.execute(pending);
                callback.setValue(new ApiResponse());
            }
        });

        return callback;
    }

    static class DeletePending extends AsyncTask<Pending, Void, Boolean> {

        private PendingDao dao;

        DeletePending(PendingDao dao) {

            this.dao = dao;
        }

        @Override
        protected Boolean doInBackground(Pending... pending) {

            dao.deleteTransaction(pending[0]);
            return true;
        }
    }
}
