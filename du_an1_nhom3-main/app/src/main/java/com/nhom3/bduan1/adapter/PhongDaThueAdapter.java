package com.nhom3.bduan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom3.bduan1.models.QuanLiHopDongModels;
import com.nhom3.bduan1.R;
import com.nhom3.bduan1.models.QuanLyPhongTroModels;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class PhongDaThueAdapter extends RecyclerView.Adapter<PhongDaThueAdapter.phongdathueHolder> {
    private Context context;
    private List<QuanLyPhongTroModels> list;
    private FirebaseFirestore db;

    public PhongDaThueAdapter(Context context, List<QuanLyPhongTroModels> list) {
        this.context = context;
        this.list = list;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public phongdathueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danh_sach_phong_da_thue, parent, false);
        return new phongdathueHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull phongdathueHolder holder, int position) {
        QuanLyPhongTroModels model = list.get(position);
        holder.tvTenPhong.setText(model.getTenPhong());
        getNgay(model.getId(),holder);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class phongdathueHolder extends RecyclerView.ViewHolder {
        TextView tvTenPhong, tvNgayBatDau, tvNgayKetThuc;

        public phongdathueHolder(@NonNull View itemView) {
            super(itemView);
            tvTenPhong = itemView.findViewById(R.id.tvtenphong_dspdt);
            tvNgayBatDau = itemView.findViewById(R.id.tvngaybatdau_dspdt);
            tvNgayKetThuc = itemView.findViewById(R.id.tvngayketthuc_dspdt);

        }
    }private void getNgay(String idPhong, PhongDaThueAdapter.phongdathueHolder holder){
        db.collection("HopDong")
                .whereEqualTo("idPhong",idPhong)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            QuanLiHopDongModels model = document.toObject(QuanLiHopDongModels.class);
                            holder.tvNgayBatDau.setText(model.getNgayBatDau());
                            holder.tvNgayKetThuc.setText(model.getNgayKetThuc());

                        }
                    }
                });
    }
}
