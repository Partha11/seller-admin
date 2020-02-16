package com.supernova.selleradmin.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.service.ApiClient;
import com.supernova.selleradmin.service.ApiInterface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {

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
}
