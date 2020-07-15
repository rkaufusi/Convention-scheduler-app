package com.example.rkwguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {
    private List<AssessmentEntity> assessmentEntityList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_individual, parent, false);
        return new AssessmentHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        AssessmentEntity currentAssessmentEntity = assessmentEntityList.get(position);
        holder.textViewTitle.setText(currentAssessmentEntity.getAssessmentsTitle());
        String formatter = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        String date = sdf.format(currentAssessmentEntity.getAssessmentDueDate());
        holder.textViewAssessDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return assessmentEntityList.size();
    }

    public void setAssessmentEntityList(List<AssessmentEntity> assessmentEntityList) {
        this.assessmentEntityList = assessmentEntityList;
        notifyDataSetChanged();
    }

    public AssessmentEntity getAssessmentAt(int positon) {
        return assessmentEntityList.get(positon);
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewAssessDate;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_assessment);
            textViewAssessDate = itemView.findViewById(R.id.text_view_assessment_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assessmentEntityList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity assessmentEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
