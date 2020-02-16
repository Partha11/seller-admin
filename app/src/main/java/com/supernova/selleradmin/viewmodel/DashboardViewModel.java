package com.supernova.selleradmin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.repository.DashboardRepository;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;

    public DashboardViewModel(@NonNull Application application) {

        super(application);
        this.repository = new DashboardRepository();
    }

    public LiveData<ApiResponse> getTransactions(String email, String token) {

        return repository.getTransactions(email, token);
    }

    public LiveData<ApiResponse> searchTransaction(String email, String token, String key) {

        return repository.searchTransaction(email, token, key);
    }
}
