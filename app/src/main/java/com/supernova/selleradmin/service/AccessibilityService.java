package com.supernova.selleradmin.service;

import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.supernova.selleradmin.util.Utility;


public class AccessibilityService extends android.accessibilityservice.AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {

            if (!Utility.isServiceRunning(getApplicationContext())) {

                startService(new Intent(getApplicationContext(), SmsService.class));
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}