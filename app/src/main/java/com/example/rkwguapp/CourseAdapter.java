package com.example.rkwguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ListAdapter<CourseEntity, CourseAdapter.CourseHolder> {
    private OnItemClickListener listener;

    public CourseAdapter() {
        super(DIFF_CALLBACK_COURSE);
    }

    private static final DiffUtil.ItemCallback<CourseEntity> DIFF_CALLBACK_COURSE = new DiffUtil.ItemCallback<CourseEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            return oldItem.getCourseTitle() == newItem.getCourseTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            return oldItem.getCourseTitle().equals(newItem.getCourseTitle()) &&
                    oldItem.getCourseStartDate().equals(newItem.getCourseStartDate()) &&
                    oldItem.getCourseEndDate().equals(newItem.getCourseEndDate()) &&
                    oldItem.getAssociatedTerm().equals(newItem.getAssociatedTerm())&&
                    oldItem.getCourseStatus().equals(newItem.getCourseStatus())&&
                    oldItem.getCourseMentor().equals(newItem.getCourseMentor())&&
                    oldItem.getCourseMentorPhone().equals(newItem.getCourseMentorPhone())&&
                    oldItem.getCourseMentorEmail().equals(newItem.getCourseMentorEmail())&&
                    oldItem.getCourseNote().equals(newItem.getCourseNote());
        }
    };

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_individual, parent,false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        CourseEntity currentCourseEntity = getItem(position);
        holder.textViewCourseTitle.setText(currentCourseEntity.getCourseTitle());

        String pattern = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(currentCourseEntity.getCourseStartDate());
        holder.textViewCourseStart.setText(date);
        String date2 = sdf.format(currentCourseEntity.getCourseEndDate());
        holder.textViewCourseEnd.setText(date2);

    }

    public CourseEntity getCourseAt(int position) {
        return getItem(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewCourseTitle;
        private TextView textViewCourseStart;
        private TextView textViewCourseEnd;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewCourseStart = itemView.findViewById(R.id.text_view_course_start);
            textViewCourseEnd = itemView.findViewById(R.id.text_view_course_end);

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
        void onItemClick(CourseEntity courseEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
