package com.pascal.notes_java.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.NoteModel;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private final List<NoteModel> notesList;
    private final AdapterCallback mCallback;

    public NotesListAdapter(AdapterCallback callback, List<NoteModel> notes) {
        mCallback = callback;
        notesList = notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String title = notesList.get(position).getTitle();
        final String description = notesList.get(position).getDescription();
        final String creationDay = notesList.get(position).getCreationDate().toString();
        holder.bind(title, description, creationDay);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textDescription;
        private final TextView textDate;
        private NoteModel noteItem;

        public ViewHolder(View view) {
            super(view);

            textTitle = view.findViewById(R.id.text_note_title);
            textDescription = view.findViewById(R.id.text_note_description);
            textDate = view.findViewById(R.id.text_note_date);
        }

        public void bind(String title, String description, String creationDay) {
            textTitle.setText(title);
            textDescription.setText(description);
            textDate.setText(creationDay);
            itemView.setOnClickListener(view ->
                    mCallback.openNote(title, description));
        }
    }
}