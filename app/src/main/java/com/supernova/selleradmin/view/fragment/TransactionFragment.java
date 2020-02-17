package com.supernova.selleradmin.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.supernova.selleradmin.R;
import com.supernova.selleradmin.adapter.TransactionAdapter;
import com.supernova.selleradmin.dialog.CustomDialog;
import com.supernova.selleradmin.dialog.CustomProgress;
import com.supernova.selleradmin.model.Transaction;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.viewmodel.DashboardViewModel;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionFragment extends Fragment implements TextView.OnEditorActionListener, View.OnTouchListener {

    @BindView(R.id.transaction_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.not_found_image)
    AppCompatImageView notFoundImage;
    @BindView(R.id.search_view)
    AppCompatEditText searchView;

    private DashboardViewModel viewModel;
    private SharedPrefs prefs;
    private CustomProgress progress;
    private CustomDialog dialog;

    private TransactionAdapter adapter;

    private List<Transaction> backupTransactions;

    private static boolean syncing = false;

    private Context context;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initialize() {

        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(DashboardViewModel.class);
        prefs = new SharedPrefs(context);
        progress = new CustomProgress();
        dialog = new CustomDialog();

        searchView.setOnEditorActionListener(this);
        searchView.setOnTouchListener(this);

        progress.setDialogTitle(context.getString(R.string.please_wait));
        progress.setDialogMessage("Getting transactions");
        progress.show(getActivity().getSupportFragmentManager(), "progress");
        progress.setCancelable(false);

        retrieveTransactions();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;

        Objects.requireNonNull(getActivity()).setTitle("Transactions");
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {

            if (viewModel != null) {

                if (searchView.getText() != null && !TextUtils.isEmpty(searchView.getText().toString())) {

                    viewModel.searchTransaction(prefs.getUserEmail(), prefs.getUserToken(),
                            searchView.getText().toString().toUpperCase()).observe(Objects.requireNonNull(getActivity()), response -> {

                        if (response != null) {

                            if (response.getStatus() != null) {

                                Log.d("Response", new Gson().toJson(response));

                                if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                                    if (response.getTransactions().size() == 0) {

                                        notFoundImage.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);

                                    } else {

                                        notFoundImage.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);

                                        if (adapter == null) {

                                            adapter = new TransactionAdapter(context, response.getTransactions());

                                        } else {

                                            backupTransactions = adapter.getTransactions();
                                            adapter.setTransactions(response.getTransactions());
                                        }

                                        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                                        recyclerView.setAdapter(adapter);
                                    }

                                    progress.dismiss();

                                } else {

                                    progress.dismiss();
                                    dialog.setTitle("Failed");
                                    dialog.setMessage(response.getFailReason());
                                    dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                                }

                            } else {

                                progress.dismiss();
                                dialog.setTitle("Failed");
                                dialog.setMessage("Couldn't connect to server");
                                dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                            }
                        }
                    });
                }
            }
        }

        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

            int leftEdge = searchView.getRight() - searchView.getCompoundDrawables()[Constants.DRAWABLE_RIGHT].getBounds().width();
            leftEdge -= getResources().getDimension(R.dimen._5sdp);

            if (motionEvent.getRawX() >= leftEdge) {

                searchView.setText("");

                if (!syncing) {

                    syncing = true;
                    resetList();
                }

                return true;
            }
        }

        return false;
    }

    private void retrieveTransactions() {

        viewModel.getTransactions(prefs.getUserEmail(),
                prefs.getUserToken()).observe(Objects.requireNonNull(getActivity()), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        if (response.getTransactions().size() == 0) {

                            notFoundImage.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);

                        } else {

                            notFoundImage.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            if (adapter == null) {

                                adapter = new TransactionAdapter(context, response.getTransactions());

                            } else {

                                adapter.setTransactions(response.getTransactions());
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter);
                        }

                        progress.dismiss();

                    } else {

                        progress.dismiss();
                        dialog.setTitle("Failed");
                        dialog.setMessage(response.getFailReason());
                        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                    }

                    syncing = false;

                } else {

                    progress.dismiss();
                    dialog.setTitle("Failed");
                    dialog.setMessage("Couldn't connect to server");
                    dialog.show(getActivity().getSupportFragmentManager(), "dialog");
                }
            }
        });
    }

    private void resetList() {

        if (backupTransactions != null) {

            notFoundImage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter.setTransactions(backupTransactions);
            adapter.notifyDataSetChanged();

            syncing = false;

        } else {

            retrieveTransactions();
        }
    }
}
