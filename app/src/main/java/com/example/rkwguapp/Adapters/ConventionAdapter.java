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
import com.example.rkwguapp.Entities.ConventionEntity;
import com.example.rkwguapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ConventionAdapter extends ListAdapter<ConventionEntity, ConventionAdapter.ConventionHolder> {

    private OnItemClickListener listener;

    public ConventionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ConventionEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<ConventionEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull ConventionEntity oldItem, @NonNull ConventionEntity newItem) {
            return oldItem.getConventionTitle() == newItem.getConventionTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ConventionEntity oldItem, @NonNull ConventionEntity newItem) {
            return oldItem.getConventionTitle().equals(newItem.getConventionTitle()) &&
                    oldItem.getConventionStartDate().equals(newItem.getConventionStartDate()) &&
                    oldItem.getConventionEndDate().equals(newItem.getConventionEndDate());
        }
    };

    @NonNull
    @Override
    public ConventionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.convention_individual, parent,false);
        return new ConventionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConventionHolder holder, int position) {
        ConventionEntity currentConventionEntity = getItem(position);
        holder.textViewConventionTitle.setText(currentConventionEntity.getConventionTitle());

       // String formatter
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String date = sdf.format(currentConventionEntity.getConventionStartDate());
        holder.textViewConventionStart.setText(date);
        String date2 = sdf.format(currentConventionEntity.getConventionEndDate());
        holder.textViewConventionEnd.setText(date2);
    }

    public ConventionEntity getTermAt(int position) {
        return getItem(position);
    }

    class ConventionHolder extends RecyclerView.ViewHolder {
        private TextView textViewConventionTitle;
        private TextView textViewConventionStart;
        private TextView textViewConventionEnd;
        private TextView textViewSubjectList;

        public ConventionHolder(@NonNull View itemView) {
            super(itemView);
            textViewConventionTitle = itemView.findViewById(R.id.text_view_convention_title);
            textViewConventionStart = itemView.findViewById(R.id.text_view_start);
            textViewConventionEnd = itemView.findViewById(R.id.text_view_end);
            textViewSubjectList = itemView.findViewById(R.id.text_view_subjects);

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
        void onItemClick(ConventionEntity ConventionEntity);
    }

    private List<SubjectEntity> workshopList;

    public void setCourses(List<SubjectEntity> courses) {
        workshopList = courses;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
