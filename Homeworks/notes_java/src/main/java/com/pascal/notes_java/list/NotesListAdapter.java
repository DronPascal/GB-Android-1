package com.pascal.notes_java.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.NoteModel;
import com.pascal.notes_java.model.CardsSource;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    CardsSource dataSource;
    private final AdapterCallback mCallback;

    public NotesListAdapter(AdapterCallback callback, CardsSource data) {
        mCallback = callback;
        dataSource = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String title = dataSource.getCardData(position).getTitle();
        final String description = dataSource.getCardData(position).getDescription();
        final String date = dataSource.getCardData(position).getDate();
        final String id = dataSource.getCardData(position).getId();
        holder.bind(title, description, date, id);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
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

        public void bind(String title, String description, String date, String id) {
            textTitle.setText(title);
            textDescription.setText(description);
            textDate.setText(date);
            itemView.setOnClickListener(view ->
                    mCallback.openNote(title, description, id));
        }
    }
}