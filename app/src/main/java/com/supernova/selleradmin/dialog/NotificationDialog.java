package com.supernova.selleradmin.dialog;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.listener.NotificationActionListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationDialog extends DialogFragment {

    @BindView(R.id.notification_title_text)
    AppCompatEditText notificationTitleText;
    @BindView(R.id.notification_content_text)
    AppCompatEditText notificationContentText;

    private NotificationActionListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_notification, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();

        Window window = Objects.requireNonNull(getDialog()).getWindow();
        Point size = new Point();

        Display display = Objects.requireNonNull(window).getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER_HORIZONTAL);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @OnClick({R.id.cancel_button, R.id.confirm_button})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.cancel_button:
                dismiss();
                break;

            case R.id.confirm_button:
                sendNotification();
                break;
        }
    }

    public void setListener(NotificationActionListener listener) {

        this.listener = listener;
    }

    private void sendNotification() {

        if (notificationTitleText.getText() == null || TextUtils.isEmpty(notificationTitleText.getText().toString())) {

            notificationTitleText.setError("Required");

        } else if (notificationContentText.getText() == null || TextUtils.isEmpty(notificationContentText.getText().toString())) {

            notificationContentText.setError("Required");

        } else {

            dismiss();
            listener.sendNotification(notificationTitleText.getText().toString(),
                    notificationContentText.getText().toString());
        }
    }
}
