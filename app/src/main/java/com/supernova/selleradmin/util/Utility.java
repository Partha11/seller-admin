package com.supernova.selleradmin.util;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.model.Transaction;
import com.supernova.selleradmin.service.AccessibilityService;
import com.supernova.selleradmin.service.SmsService;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static boolean isServiceRunning(Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : Objects.requireNonNull(manager).getRunningServices(Integer.MAX_VALUE)) {

            if (SmsService.class.getName().equals(service.service.getClassName())) {

                Log.d("ServiceRunning", "Yes");
                return true;
            }
        }

        Log.d("ServiceRunning", "No");
        return false;
    }

    public static boolean checkAccessibilitySettings(Context context) {

        int accessibilityEnabled = 0;
        String service = context.getPackageName() + "/" + AccessibilityService.class.getCanonicalName();

        try {

            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);

        } catch (Settings.SettingNotFoundException e) {

            Log.d("Accessibility", "Error finding setting: " + e.getMessage());
        }

        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == Constants.ACCESSIBILITY_ENABLED) {

            String settingValue = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

            if (settingValue != null) {

                splitter.setString(settingValue);

                while (splitter.hasNext()) {

                    String accessibilityService = splitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {

                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void launchAccessibility(Context context) {

        Log.d("Accessibility", "Launched");

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(context.getResources().getString(R.string.alert_header))
                .setMessage(context.getResources().getString(R.string.alert_message))
                .setPositiveButton("Open Settings", (dialogInterface, i) -> {

                    dialogInterface.cancel();
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                })
                .setNegativeButton("Maybe Later", (dialogInterface, i) -> {

                    dialogInterface.dismiss();
//                    ((AppCompatActivity) context).finishAffinity();
                });

        dialog.create().show();
    }

    public static Transaction getTransaction(String body) {

        Transaction transaction = new Transaction();
        Pattern pattern = Pattern.compile("\\b([0-9]*[,][0-9]*[.])[0-9]+\\b|\\b([0-9]*[.])[0-9]+\\b");
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {

            transaction.setTrxAmount(matcher.group().trim().replace(",", ""));
            matcher = Pattern.compile("\\b[0-9]{11}\\b|\\b[0-9]{12}\\b").matcher(body);

            if (matcher.find()) {

                transaction.setPhoneNumber(matcher.group().trim());
                matcher = Pattern.compile("\\b([A-Za-z]{4}[0-9]{3}|[0-9]{14}|[0-9]{12}|[0-9]{13}|[A-Za-z]{4}\\s[0-9]{3})\\b").matcher(body);

                if (matcher.find()) {

                    transaction.setPlayerId(matcher.group().trim());
                    matcher = Pattern.compile("\\b[A-Z0-9]{10}\\b|\\b[A-Z0-9]{8}\\b").matcher(body);

                    if (matcher.find()) {

                        transaction.setTrxId(matcher.group().trim());

                    } else {

                        return null;
                    }

                } else {

                    transaction.setPlayerId("");
                    matcher = Pattern.compile("\\b[A-Z0-9]{10}\\b|\\b[A-Z0-9]{8}\\b").matcher(body);

                    if (matcher.find()) {

                        transaction.setTrxId(matcher.group().trim());

                    } else {

                        return null;
                    }
                }

            } else {

                return null;
            }
        }

        return transaction;
    }
}