package com.supernova.selleradmin.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.supernova.selleradmin.model.ApiResponse;
import com.supernova.selleradmin.repository.LoginRepository;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;

    public LoginViewModel(@NonNull Application application) {

        super(application);
        repository = new LoginRepository();
    }

    public LiveData<ApiResponse> signInUser(String email, String password) {

        return repository.loginUser(email, password);
    }
}
