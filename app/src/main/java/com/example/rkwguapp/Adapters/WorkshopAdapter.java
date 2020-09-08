package com.example.rkwguapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WorkshopAdapter extends RecyclerView.Adapter<WorkshopAdapter.WorkshopHolder> {
    private List<WorkshopEntity> WorkshopEntityList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public WorkshopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshop_individual, parent, false);
        return new WorkshopHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopHolder holder, int position) {
        WorkshopEntity currentWorkshopEntity = WorkshopEntityList.get(position);
        holder.textViewTitle.setText(currentWorkshopEntity.getWorkshopTitle());
        String formatter = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        String date = sdf.format(currentWorkshopEntity.getWorkshopDate());
        holder.textViewDate.setText(date);

        String date1 = sdf.format(currentWorkshopEntity.getWorkshopCompleteDate());
        holder.textViewCompleteDate.setText(date1);
    }

    @Override
    public int getItemCount() {
        return WorkshopEntityList.size();
    }

    public void setWorkshopEntityList(List<WorkshopEntity> WorkshopEntityList) {
        this.WorkshopEntityList = WorkshopEntityList;
        notifyDataSetChanged();
    }

    public WorkshopEntity getWorkshopAt(int positon) {
        return WorkshopEntityList.get(positon);
    }

    class WorkshopHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDate;
        private TextView textViewCompleteDate;

        public WorkshopHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_workshop);
            textViewDate = itemView.findViewById(R.id.text_view_workshop_date);
            textViewCompleteDate = itemView.findViewById(R.id.text_view_end_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(WorkshopEntityList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(WorkshopEntity WorkshopEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
