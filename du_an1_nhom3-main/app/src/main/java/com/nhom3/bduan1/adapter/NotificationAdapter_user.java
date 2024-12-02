package com.example.bduan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bduan1.R;
import com.example.bduan1.models.ThongBaoModels;

import java.util.List;

public class NotificationAdapter_user extends RecyclerView.Adapter<NotificationAdapter_user.NotificationViewHolder> {
    private List<ThongBaoModels> listNotifi;
    private Context context;

    public NotificationAdapter_user(List<ThongBaoModels> listNotifi, Context context) {
        this.listNotifi = listNotifi;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item_notification_user, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        ThongBaoModels notifi = listNotifi.get(position);
        holder.tvTitleNotification_user.setText(notifi.getTieuDe());
        holder.tvMessageNotification_user.setText(notifi.getChiTiet());
        holder.tvDateNotification_user.setText(notifi.getNgay());

    }

    @Override
    public int getItemCount() {
        return listNotifi!=null?listNotifi.size():0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleNotification_user, tvMessageNotification_user, tvDateNotification_user;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleNotification_user = itemView.findViewById(R.id.tvTitleNotification_user);
            tvMessageNotification_user = itemView.findViewById(R.id.tvMessageNotification_user);
            tvDateNotification_user=itemView.findViewById(R.id.tvDateNotification_user);
        }
    }
}