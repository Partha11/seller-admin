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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.supernova.selleradmin.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomProgress extends DialogFragment {

    @BindView(R.id.dialog_title)
    AppCompatTextView dialogTitleText;
    @BindView(R.id.progress_circle)
    SpinKitView progressCircle;
    @BindView(R.id.dialog_message)
    AppCompatTextView dialogMessageText;

    private String dialogTitle;
    private String dialogMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_progress, container, false);
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

        progressCircle.setIndeterminateDrawable(new DoubleBounce());

        if (dialogTitle != null && !TextUtils.isEmpty(dialogTitle)) {

            dialogTitleText.setText(dialogTitle);

        } else {

            dialogTitleText.setText(getResources().getString(R.string.please_wait));
        }

        if (dialogMessage != null && !TextUtils.isEmpty(dialogMessage)) {

            dialogMessageText.setText(dialogMessage);

        } else {

            dialogMessageText.setText(getResources().getString(R.string.please_wait));
        }
    }

    public void setDialogTitle(String dialogTitle) {

        this.dialogTitle = dialogTitle;
    }

    public void setDialogMessage(String dialogMessage) {

        this.dialogMessage = dialogMessage;
    }
}
