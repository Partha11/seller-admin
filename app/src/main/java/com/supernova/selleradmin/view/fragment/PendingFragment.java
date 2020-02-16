package com.supernova.selleradmin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.adapter.PendingAdapter;
import com.supernova.selleradmin.listener.PendingActionListener;
import com.supernova.selleradmin.model.Pending;
import com.supernova.selleradmin.viewmodel.DashboardViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingFragment extends Fragment implements PendingActionListener {

    @BindView(R.id.pending_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.not_found_image)
    AppCompatImageView notFoundImage;

    private Context context;
    private DashboardViewModel viewModel;
    private PendingAdapter adapter;

    public PendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;

        Objects.requireNonNull(getActivity()).setTitle("Pending");
    }

    private void initialize() {

        viewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(DashboardViewModel.class);
        adapter = new PendingAdapter();

        adapter.setListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        viewModel.getPendingList().observe(getActivity(), p -> {

            if (p != null) {

                if (p.size() > 0) {

                    notFoundImage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    adapter.setTransactions(p);
                    adapter.notifyDataSetChanged();

                } else {

                    notFoundImage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void delete(Pending pending) {

        viewModel.deletePending(pending).observe(Objects.requireNonNull(getActivity()), value -> {

            if (value) {

                adapter.notifyDataSetChanged();

            } else {

                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void resend(Pending pending) {

    }
}
