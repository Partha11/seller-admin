package com.supernova.selleradmin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.supernova.selleradmin.database.PendingDao;
import com.supernova.selleradmin.database.PendingDatabase;
import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.repository.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;
    private LiveData<List<Pending>> pendingList;

    public DashboardViewModel(@NonNull Application application) {

        super(application);

        PendingDao dao = PendingDatabase.getInstance(application).pendingDao();

        this.repository = new DashboardRepository(dao);
        this.pendingList = dao.getPendingList();
    }

    public LiveData<ApiResponse> getTransactions(String email, String token) {

        return repository.getTransactions(email, token);
    }

    public LiveData<ApiResponse> searchTransaction(String email, String token, String key) {

        return repository.searchTransaction(email, token, key);
    }

    public LiveData<ApiResponse> getData(String email, String token) {

        return repository.getData(email, token);
    }

    public LiveData<ApiResponse> updateNumber(String email, String token, int position, String text) {

        return repository.updateNumber(email, token, position, text);
    }

    public LiveData<ApiResponse> updatePassword(String userEmail, String userToken, String password) {

        return repository.updatePassword(userEmail, userToken, password);
    }

    public LiveData<List<Pending>> getPendingList() {

        return pendingList;
    }

    public LiveData<Boolean> deletePending(Pending pending) {

        return repository.deletePending(pending);
    }
}
