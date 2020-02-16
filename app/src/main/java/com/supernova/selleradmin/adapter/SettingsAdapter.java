package com.supernova.selleradmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.listener.OnItemClick;
import com.supernova.selleradmin.model.SettingsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private List<SettingsModel> settings;
    private Context context;
    private OnItemClick listener;

    public SettingsAdapter(Context context, List<SettingsModel> settings) {

        this.settings = settings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_settings, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (settings.get(position).isTitle()) {

            holder.settingsBodyLayout.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
            holder.settingsTitle.setText(settings.get(position).getTitle());

        } else {

            holder.settingsTitleLayout.setVisibility(View.GONE);
            holder.divider.setVisibility(View.VISIBLE);
            holder.settingsBody.setText(settings.get(position).getTitle());
        }

        if ((position != settings.size() - 1 && settings.get(position + 1).isTitle()) ||
                position == settings.size() - 1) {

            holder.divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        return settings == null ? 0 : settings.size();
    }

    public void setListener(OnItemClick listener) {

        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.settings_title)
        AppCompatTextView settingsTitle;
        @BindView(R.id.settings_title_layout)
        LinearLayout settingsTitleLayout;
        @BindView(R.id.settings_body)
        AppCompatTextView settingsBody;
        @BindView(R.id.settings_body_layout)
        LinearLayout settingsBodyLayout;
        @BindView(R.id.divider)
        View divider;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.settings_body_layout)
        public void onViewClicked() {

            listener.itemClicked(getAdapterPosition());
        }
    }
}
