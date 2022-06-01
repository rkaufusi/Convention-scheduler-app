package com.example.rkwguapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Entities.SubjectEntity;
import com.example.rkwguapp.R;

import java.text.SimpleDateFormat;

public class SubjectAdapter extends ListAdapter<SubjectEntity, SubjectAdapter.SubjectHolder> {
    private OnItemClickListener listener;

    public SubjectAdapter() {
        super(DIFF_CALLBACK_COURSE);
    }

    private static final DiffUtil.ItemCallback<SubjectEntity> DIFF_CALLBACK_COURSE = new DiffUtil.ItemCallback<SubjectEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull SubjectEntity oldItem, @NonNull SubjectEntity newItem) {
            return oldItem.getSubjectTitle() == newItem.getSubjectTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull SubjectEntity oldItem, @NonNull SubjectEntity newItem) {
            return oldItem.getSubjectTitle().equals(newItem.getSubjectTitle()) &&
                    oldItem.getSubjectStartDate().equals(newItem.getSubjectStartDate()) &&
                    oldItem.getSubjectEndDate().equals(newItem.getSubjectEndDate()) &&
                    oldItem.getAssociatedConvention().equals(newItem.getAssociatedConvention());
        }
    };

    @NonNull
    @Override
    public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_individual, parent,false);
        return new SubjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
        SubjectEntity currentSubjectEntity = getItem(position);
        holder.textViewsubjectTitle.setText(currentSubjectEntity.getSubjectTitle());

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(currentSubjectEntity.getSubjectStartDate());
        holder.textViewCourseStart.setText(date);
        String date2 = sdf.format(currentSubjectEntity.getSubjectEndDate());
        holder.textViewCourseEnd.setText(date2);

    }

    public SubjectEntity getCourseAt(int position) {
        return getItem(position);
    }

    class SubjectHolder extends RecyclerView.ViewHolder {
        private TextView textViewsubjectTitle;
        private TextView textViewCourseStart;
        private TextView textViewCourseEnd;

        public SubjectHolder(@NonNull View itemView) {
            super(itemView);
            textViewsubjectTitle = itemView.findViewById(R.id.text_view_subject_title);
            textViewCourseStart = itemView.findViewById(R.id.text_view_start);
            textViewCourseEnd = itemView.findViewById(R.id.text_view_end);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SubjectEntity SubjectEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
