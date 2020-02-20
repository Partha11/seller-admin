package com.supernova.selleradmin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.Gson;
import com.supernova.selleradmin.R;
import com.supernova.selleradmin.adapter.NotificationAdapter;
import com.supernova.selleradmin.dialog.CustomDialog;
import com.supernova.selleradmin.dialog.CustomProgress;
import com.supernova.selleradmin.dialog.NotificationDialog;
import com.supernova.selleradmin.listener.NotificationActionListener;
import com.supernova.selleradmin.model.NotificationItem;
import com.supernova.selleradmin.util.Constants;
import com.supernova.selleradmin.util.SharedPrefs;
import com.supernova.selleradmin.util.Utility;
import com.supernova.selleradmin.viewmodel.DashboardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment implements NotificationActionListener {

    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.home_toolbar)
    Toolbar homeToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.weekly_total_text)
    AppCompatTextView weeklyHighestText;
    @BindView(R.id.daily_total_text)
    AppCompatTextView dailyTotalText;
    @BindView(R.id.notification_recycler)
    RecyclerView recyclerView;

    private DashboardViewModel viewModel;
    private SharedPrefs prefs;
    private NotificationAdapter adapter;

    private Context context;
    private CustomProgress progress;
    private CustomDialog customDialog;

    private List<NotificationItem> notifications;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        collapsingToolbar.setContentScrimColor(context.getResources().getColor(R.color.colorPrimary));
        collapsingToolbar.setCollapsedTitleTextColor(context.getResources().getColor(android.R.color.white));

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {

                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (Math.abs(scrollRange + verticalOffset) < 10) {

                    collapsingToolbar.setTitle("Home");
                    isShow = true;

                } else if (isShow) {

                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });

        initialize();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {

        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {

        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }

    private void initialize() {

        customDialog = new CustomDialog();

        prefs = new SharedPrefs(context);
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(context, notifications);
        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(DashboardViewModel.class);
        progress = new CustomProgress();

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        progress.setDialogTitle("Please Wait");
        progress.setDialogMessage("Retrieving data from server...");
        progress.setCancelable(false);
        progress.show(getActivity().getSupportFragmentManager(), "progress");

        viewModel.getGraphData(prefs.getUserEmail(),
                prefs.getUserToken()).observe(getActivity(), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS) && response.getData() != null) {

                        progress.dismiss();

                        setGraph(response.getData());
                        setPieChart(response.getPieData());

                        int total = 0;

                        for (String s : response.getData()) {

                            total += Integer.parseInt(s);
                        }

                        String weekly = "Weekly Total: " + total;
                        String daily = "Daily Total: " + response.getData().get(response.getData().size() - 1);

                        weeklyHighestText.setText(weekly);
                        dailyTotalText.setText(daily);

                        notifications.addAll(response.getNotifications());
                        adapter.notifyDataSetChanged();

                    } else {

                        progress.dismiss();
                        showDialog("Warning", response.getFailReason());
                    }

                } else {

                    progress.dismiss();
                    showDialog("Error", "Please check your internet connection");
                }
            }
        });
    }

    private void setGraph(List<String> totals) {

        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < totals.size(); i++) {

            if (totals.get(i) == null) {

                totals.set(i, "0");
            }

            entries.add(new BarEntry(i + 1, Integer.parseInt(totals.get(i))));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Daily Sales");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData barData = new BarData(dataSet);

        barChart.setTouchEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setData(barData);
        barChart.invalidate();

        barChart.animateXY(6000, 8000, Easing.EaseInOutSine);
    }

    private void setPieChart(List<String> totals) {

        List<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < totals.size(); i++) {

            entries.add(new PieEntry(i + 1, Utility.getPieLabel(i + 1), Integer.parseInt(totals.get(i))));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(false);

        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.setTouchEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.animateXY(6000, 8000, Easing.EaseInOutSine);
    }

    @OnClick(R.id.notification_fab)
    public void onViewClicked() {

        NotificationDialog dialog = new NotificationDialog();

        dialog.setListener(this);
        dialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "notification");
    }

    @Override
    public void sendNotification(String title, String content) {

        CustomProgress progress = new CustomProgress();

        progress.setDialogMessage("Authenticating");
        progress.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "progress");
        progress.setCancelable(false);

        viewModel.sendNotification(prefs.getUserEmail(),
                prefs.getUserToken(), title, content).observe(getActivity(), response -> {

            if (response != null) {

                if (response.getStatus() != null) {

                    progress.dismiss();

                    if (response.getStatus().equals(Constants.STATUS_SUCCESS)) {

                        Log.d("Data", new Gson().toJson(response.getNotifications()));
                        Log.d("Data", "" + notifications.size());

                        notifications.addAll(response.getNotifications());
                        adapter.notifyDataSetChanged();

                        Log.d("Data", "" + notifications.size());

                        Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    } else {

                        showDialog("Warning", response.getFailReason());
                    }

                } else {

                    showDialog("Warning", "Something went wrong!");
                }

            } else {

                progress.dismiss();
                showDialog("Error", "Couldn't connect to server!");
            }
        });
    }

    private void showDialog(String title, String message) {

        customDialog.setTitle(title);
        customDialog.setMessage(message);
        customDialog.setCancelable(false);

        customDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "dialog");
    }
}
