package com.supernova.selleradmin.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.adapter.SettingsAdapter;
import com.supernova.selleradmin.dialog.CustomDialog;
import com.supernova.selleradmin.listener.OnItemClick;
import com.supernova.selleradmin.model.SettingsModel;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements OnItemClick {

    @BindView(R.id.settings_recycler)
    RecyclerView recyclerView;

    private Context context;
    private SharedPrefs prefs;
    private DashboardViewModel viewModel;

    private List<SettingsModel> settings;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    private void initialize() {

        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(DashboardViewModel.class);
        settings = new ArrayList<>();
        prefs = new SharedPrefs(context);

        settings.add(new SettingsModel("Security", true));
        settings.add(new SettingsModel("Change Password", false));
        settings.add(new SettingsModel("Numbers", true));
        settings.add(new SettingsModel("Bkash Agent", false));
        settings.add(new SettingsModel("Bkash Payment", false));
        settings.add(new SettingsModel("Nogod Agent", false));
        settings.add(new SettingsModel("Nogod Payment", false));
        settings.add(new SettingsModel("Rocket", false));
        settings.add(new SettingsModel("Contact Number", false));
        settings.add(new SettingsModel("Extra", true));
        settings.add(new SettingsModel("Chips Price", false));

        SettingsAdapter adapter = new SettingsAdapter(context, settings);
        adapter.setListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;

        Objects.requireNonNull(getActivity()).setTitle("Settings");
    }

    @Override
    public void itemClicked(int position) {

        String title = "Update " + settings.get(position).getTitle();

        if (title.contains("Bkash") || title.contains("Nogod") || title.contains("Rocket")) {

            title += " Number";
        }

        AppCompatEditText editText = new AppCompatEditText(context);

        editText.setHint(title.replace("Update", "").trim());

        if (position == 1) {

            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editText.setText("");
            editText.setHint("New Password");
            editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Constants.PASSWORD_LENGTH)});

        } else if (position >= 3 && position <= 8) {

            editText.setText(prefs.getNumber(position - 3));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Constants.PHONE_NUMBER_LENGTH)});

        } else {

            editText.setText(prefs.getChipsPrice());
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setHint("Price Per Cr");
            editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Constants.AMOUNT_LENGTH)});
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(editText)
                .setPositiveButton("Update", (dialogInterface, i) -> {

                    if (editText.getText() == null || TextUtils.isEmpty(editText.getText().toString())) {

                        editText.setError("Required");
                        return;
                    }

                    if (position == 1) {

                        updatePassword(editText.getText().toString());

                    } else if (position >= 3 && position <= 8) {

                        if (editText.length() != 11) {

                            editText.setError("Malformed Number");

                        } else {

                            updateNumber(position - 3, editText.getText().toString());
                        }

                    } else {

                        updatePrice(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create().show();
        builder.setCancelable(false);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins((int) getResources().getDimension(R.dimen._15sdp),
                (int) getResources().getDimension(R.dimen._15sdp),
                (int) getResources().getDimension(R.dimen._15sdp),
                (int) getResources().getDimension(R.dimen._10sdp));

        editText.setLayoutParams(params);
    }

    private void updatePrice(String price) {

        viewModel.updatePrice(prefs.getUserEmail(), prefs.getUserToken(),
                price).observe(Objects.requireNonNull(getActivity()), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        Toast.makeText(context, "Price updated", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, response.getFailReason(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePassword(String password) {

        viewModel.updatePassword(prefs.getUserEmail(), prefs.getUserToken(),
                password).observe(Objects.requireNonNull(getActivity()), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        Toast.makeText(context, "Password updated", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, response.getFailReason(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateNumber(int position, String text) {

        viewModel.updateNumber(prefs.getUserEmail(), prefs.getUserToken(),
                position, text).observe(Objects.requireNonNull(getActivity()), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        prefs.setNumber(position, text);
                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();

                    } else {

                        CustomDialog dialog = new CustomDialog();

                        dialog.setTitle("Error");
                        dialog.setMessage(response.getFailReason());
                        dialog.setCancelable(false);
                        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                    }
                }
            }
        });
    }
}
