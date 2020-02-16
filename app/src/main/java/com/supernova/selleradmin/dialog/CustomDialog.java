package com.supernova.selleradmin.dialog;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.supernova.selleradmin.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialog extends DialogFragment {

    @BindView(R.id.dialog_custom_title)
    AppCompatTextView dialogCustomTitle;
    @BindView(R.id.dialog_custom_message)
    AppCompatTextView dialogCustomMessage;

    private String title;
    private String message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_custom, container, false);
        ButterKnife.bind(this, view);

        initialize();

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

    private void initialize() {

        if (title != null && !TextUtils.isEmpty(title)) {

            dialogCustomTitle.setText(title);
        }

        if (message != null && !TextUtils.isEmpty(message)) {

            dialogCustomMessage.setText(Html.fromHtml(message));
        }
    }

    @OnClick(R.id.cancel_button)
    public void onViewClicked() {

        dismiss();
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setMessage(String message) {

        this.message = message;
    }
}
