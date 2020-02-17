package com.supernova.selleradmin.dialog;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.listener.PendingActionListener;
import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.util.Constants;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResendDialog extends DialogFragment {

    @BindView(R.id.resend_phone_number)
    AppCompatEditText resendPhoneNumber;
    @BindView(R.id.resend_trx_id)
    AppCompatEditText resendTrxId;
    @BindView(R.id.resend_amount)
    AppCompatEditText resendAmount;
    @BindView(R.id.resend_confirm)
    AppCompatButton resendConfirm;

    private Pending pending;
    private PendingActionListener listener;

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //Empty
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (charSequence.length() == 0) {

                resendConfirm.setEnabled(false);

            } else {

                resendConfirm.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //Empty
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_resend, container, false);
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

    @OnClick({R.id.resend_confirm, R.id.resend_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.resend_confirm:
                validate();
                break;
            case R.id.resend_cancel:
                dismiss();
                break;
        }
    }

    private void validate() {

        if (resendPhoneNumber.getText() != null && !TextUtils.isEmpty(resendPhoneNumber.getText().toString())) {

            pending.setPhoneNumber(resendPhoneNumber.getText().toString().toUpperCase());
            listener.resend(pending, Constants.FROM_DIALOG);
        }
    }

    private void initialize() {

        if (pending != null) {

            resendPhoneNumber.setText(pending.getPhoneNumber());
            resendTrxId.setText(pending.getTrxId());
            resendAmount.setText(pending.getAmount());
        }

        resendPhoneNumber.addTextChangedListener(watcher);
    }

    public void setPending(Pending pending) {

        this.pending = pending;
    }

    public void setListener(PendingActionListener listener) {

        this.listener = listener;
    }
}
