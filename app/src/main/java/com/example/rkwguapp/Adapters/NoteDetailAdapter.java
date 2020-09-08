package com.example.rkwguapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.ConventionDetail;
import com.example.rkwguapp.Entities.NotesEntity;
import com.example.rkwguapp.Entities.WorkshopEntity;
import com.example.rkwguapp.R;

import java.util.List;

public class NoteDetailAdapter extends RecyclerView.Adapter<NoteDetailAdapter.PartViewHolder> {

    class PartViewHolder extends RecyclerView.ViewHolder {
        private final TextView partItemView;


        private PartViewHolder(View itemView) {
            super(itemView);
            partItemView = itemView.findViewById(R.id.text_view_subjects);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final NotesEntity current = notesList.get(position);
                    Intent intent = new Intent(context, ConventionDetail.class);
                    //intent.putExtra("EXTRA_TITLE_SUBJECT", current.getSubjectTitle());
                    // context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<NotesEntity> notesList;

    public NoteDetailAdapter(Context context) {
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
        if (notesList != null) {
            NotesEntity current = notesList.get(position);
            holder.partItemView.setText(current.getNoteTitle());
        } else {
            holder.partItemView.setText("No Word");
        }
    }

    public void setNotes(List<NotesEntity> notes) {
        notesList = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (notesList != null)
            return notesList.size();
        else return 0;
    }
}

