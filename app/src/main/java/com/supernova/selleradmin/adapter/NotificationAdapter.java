package com.supernova.selleradmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.supernova.selleradmin.R;
import com.supernova.selleradmin.model.NotificationItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    private List<NotificationItem> notificationItems;

    public NotificationAdapter(Context context, List<NotificationItem> notificationItems) {

        this.context = context;
        this.notificationItems = notificationItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String id = "Notification #" + notificationItems.get(position).getId();
        String created = notificationItems.get(position).getTime();
        String title = "Title: " + notificationItems.get(position).getNotificationTitle();
        String content = "Content: " + notificationItems.get(position).getNotificationContent();

        holder.notificationId.setText(id);
        holder.notificationCreated.setText(created);
        holder.notificationTitle.setText(title);
        holder.notificationContent.setText(content);
    }

    @Override
    public int getItemCount() {

        return notificationItems == null ? 0 : notificationItems.size();
    }

    public void setNotificationItems(List<NotificationItem> notificationItems) {

        this.notificationItems = notificationItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_id_text)
        AppCompatTextView notificationId;
        @BindView(R.id.notification_created_text)
        AppCompatTextView notificationCreated;
        @BindView(R.id.notification_title)
        AppCompatTextView notificationTitle;
        @BindView(R.id.notification_content)
        AppCompatTextView notificationContent;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
