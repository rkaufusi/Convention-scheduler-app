package com.example.rkwguapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.ConventionDetail;
import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.R;

import java.util.List;

public class ConventionDetailAdapter extends RecyclerView.Adapter<ConventionDetailAdapter.PartViewHolder> {

    class PartViewHolder extends RecyclerView.ViewHolder {
        private final TextView partItemView;

        private PartViewHolder(View itemView) {
            super(itemView);
            partItemView = itemView.findViewById(R.id.text_view_subjects);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final SubjectEntity current = workshopList.get(position);
                    Intent intent = new Intent(context, ConventionDetail.class);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<SubjectEntity> workshopList;

    public ConventionDetailAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.subject_list, parent, false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PartViewHolder holder, int position) {
        if (workshopList != null) {
            SubjectEntity current = workshopList.get(position);
            holder.partItemView.setText(current.getSubjectTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.partItemView.setText("No Word");
        }
    }

    public void setCourses(List<SubjectEntity> courses) {
        workshopList = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (workshopList != null)
            return workshopList.size();
        else return 0;
    }
}
