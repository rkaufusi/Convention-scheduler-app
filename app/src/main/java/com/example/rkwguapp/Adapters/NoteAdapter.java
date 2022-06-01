package com.example.rkwguapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rkwguapp.Entities.NotesEntity;
import com.example.rkwguapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends ListAdapter<NotesEntity, NoteAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK_NOTE);
    }

    private static final DiffUtil.ItemCallback<NotesEntity> DIFF_CALLBACK_NOTE = new DiffUtil.ItemCallback<NotesEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotesEntity oldItem, @NonNull NotesEntity newItem) {
            return oldItem.getNoteTitle() == newItem.getNoteTitle();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotesEntity oldItem, @NonNull NotesEntity newItem) {
            return oldItem.getNoteTitle().equals(newItem.getNoteTitle()) &&
                    oldItem.getNoteBody().equals(newItem.getNoteBody()) ;
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_individual, parent,false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        NotesEntity currentNoteEntity = getItem(position);
        holder.textViewsubjectTitle.setText(currentNoteEntity.getNoteTitle());
    }

    public NotesEntity getCourseAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewsubjectTitle;
        private TextView textViewCourseStart;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewsubjectTitle = itemView.findViewById(R.id.text_view_note_title);
            textViewCourseStart = itemView.findViewById(R.id.text_view_start);

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

    private List<NotesEntity> noteList;

    public interface OnItemClickListener {
        void onItemClick(NotesEntity NoteEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


