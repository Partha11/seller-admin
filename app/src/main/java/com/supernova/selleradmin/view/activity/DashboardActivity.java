package com.supernova.selleradmin.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.gson.Gson;
import com.supernova.selleradmin.R;
import com.supernova.selleradmin.service.SmsService;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.util.Utility;
import com.supernova.selleradmin.view.fragment.PendingFragment;
import com.supernova.selleradmin.view.fragment.SettingsFragment;
import com.supernova.selleradmin.view.fragment.TransactionFragment;
import com.supernova.selleradmin.viewmodel.DashboardViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    private static boolean accessibilityDialog = false;
    private static boolean permissionDialog = false;
    private static boolean serviceLaunched = false;
    private boolean doubleBackPressed = false;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.transaction, R.drawable.ic_home, R.color.login_bg);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.pending, R.drawable.ic_home, R.color.login_bg);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.settings, R.drawable.ic_gear, R.color.login_bg);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.setOnTabSelectedListener(this);

        Log.d("Object", new Gson().toJson(Utility.getTransaction("Tk70.00 received from A/C:017147768720 Fee:Tk0, Your A/C Balance: Tk7,063.80 TxnId:1623929982 Date:11-FEB-20 12:59:18 am. Download https://bit.ly/nexuspay")));

        loadFragment(Constants.TRANSACTION_FRAGMENT);
        checkSmsPermission();
        updateData();
    }

    private void updateData() {

        DashboardViewModel viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        SharedPrefs prefs = new SharedPrefs(this);

        viewModel.getData(prefs.getUserEmail(),
                prefs.getUserToken()).observe(this, response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        Log.d("Data", new Gson().toJson(response));

                        prefs.setBkash(response.getBkash());
                        prefs.setNogod(response.getNogod());
                        prefs.setRocket(response.getRocket());
                        prefs.setChipsPrice(response.getChipsPrice());

                    } else {

                        Log.d("Error", response.getFailReason());
                    }

                } else {

                    Log.d("Error", "Response Null");
                }
            }
        });
    }

    private void loadFragment(int value) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;

        if (value == Constants.TRANSACTION_FRAGMENT) {

            fragment = new TransactionFragment();

        } else if (value == Constants.PENDING_FRAGMENT) {

            fragment = new PendingFragment();

        } else {

            fragment = new SettingsFragment();
        }

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void checkSmsPermission() {

        if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DashboardActivity.this, new String[]{Manifest.permission.RECEIVE_SMS},
                    Constants.SMS_REQUEST_CODE);

        } else {

            if (!serviceLaunched) {

                serviceLaunched = true;
                serviceIntent = new Intent(this, SmsService.class);

                serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(serviceIntent);
                ignoreBatteryOptimizations();
            }

            if (!Utility.isServiceRunning(this) && !permissionDialog) {

                permissionDialog = true;

                startService(serviceIntent);
                ignoreBatteryOptimizations();
                Utility.isServiceRunning(this);
            }

            if (!Utility.checkAccessibilitySettings(this) && !accessibilityDialog) {

                accessibilityDialog = true;

                ignoreBatteryOptimizations();
                Utility.launchAccessibility(this);
            }
        }
    }

    @SuppressLint("BatteryLife")
    public void ignoreBatteryOptimizations() {

        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        String packageName = getPackageName();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Intent intent = new Intent();

            if (!Objects.requireNonNull(powerManager).isIgnoringBatteryOptimizations(packageName)) {

                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (doubleBackPressed) {

            super.onBackPressed();
            return;
        }

        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

        this.doubleBackPressed = true;
        new Handler().postDelayed(() -> doubleBackPressed = false, 2000);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {

        loadFragment(position + 1);
        return true;
    }
}
