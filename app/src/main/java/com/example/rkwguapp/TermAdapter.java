package com.example.rkwguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends ListAdapter<TermEntity, TermAdapter.TermHolder> {

    private OnItemClickListener listener;

    public TermAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<TermEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TermEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            return oldItem.getTermTitle() == newItem.getTermTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            return oldItem.getTermTitle().equals(newItem.getTermTitle()) &&
                    oldItem.getTermStartDate().equals(newItem.getTermStartDate()) &&
                    oldItem.getTermEndDate().equals(newItem.getTermEndDate());
        }
    };

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_individual, parent,false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        TermEntity currentTermEntity = getItem(position);
        holder.textViewTermTitle.setText(currentTermEntity.getTermTitle());

       // String formatter
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(currentTermEntity.getTermStartDate());
        holder.textViewTermStart.setText(date);
        String date2 = sdf.format(currentTermEntity.getTermEndDate());
        holder.textViewTermEnd.setText(date2);
    }
    /*
    public void setCourses(List<TermEntity> coursesInTerm) {
        mTerms = coursesInTerm;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    } */

    public TermEntity getTermAt(int position) {
        return getItem(position);
    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTermTitle;
        private TextView textViewTermStart;
        private TextView textViewTermEnd;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            textViewTermTitle = itemView.findViewById(R.id.text_view_term_title);
            textViewTermStart = itemView.findViewById(R.id.text_view_term_start);
            textViewTermEnd = itemView.findViewById(R.id.text_view_term_end);

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
        void onItemClick(TermEntity termEntity);
    }

/*
    private List<TermEntity> courseList;

    public void setCourses(List<TermEntity> courses) {
        courseList = courses;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (courseList != null)
            return courseList.size();
        else return 0;
    } */


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
