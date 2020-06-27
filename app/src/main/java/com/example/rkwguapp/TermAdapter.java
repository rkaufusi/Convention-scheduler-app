package com.example.rkwguapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {
    private List<TermEntity> termEntities = new ArrayList<>();

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_individual, parent,false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        TermEntity currentTermEntity = termEntities.get(position);
        holder.textViewTermTitle.setText(currentTermEntity.getTermTitle());
        holder.textViewTermStart.setText(String.valueOf(currentTermEntity.getTermStartDate()));
        holder.textViewTermEnd.setText(String.valueOf(currentTermEntity.getTermEndDate()));


    }

    @Override
    public int getItemCount() {
        return termEntities.size();
    }

    public void setTermEntities(List<TermEntity> termEntities) {
        this.termEntities = termEntities;
        notifyDataSetChanged();
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
        }
    }
}
