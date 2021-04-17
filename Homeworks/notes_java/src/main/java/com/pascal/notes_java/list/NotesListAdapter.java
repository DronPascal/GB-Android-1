package com.pascal.notes_java.list;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.CardData;
import com.pascal.notes_java.model.CardsSource;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private CardsSource dataSource;
    private final AdapterCallback mCallback;
    private int menuPosition;

    public NotesListAdapter(AdapterCallback callback) {
        mCallback = callback;
    }

    public void setDataSource(CardsSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(dataSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textDescription;
        private final TextView textDate;

        public ViewHolder(View view) {
            super(view);

            textTitle = view.findViewById(R.id.text_note_title);
            textDescription = view.findViewById(R.id.text_note_description);
            textDate = view.findViewById(R.id.text_note_date);

            itemView.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    itemView.showContextMenu(10, 10);
                }
                return false;
            });

            if (mCallback != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                mCallback.registerForContextMenu(view);
            }
        }

        public void bind(CardData cardData) {
            textTitle.setText(cardData.getTitle());
            textDescription.setText(cardData.getDescription());
            textDate.setText(cardData.getDate());
            itemView.setOnClickListener(v ->
                    mCallback.openNote(cardData));

        }
    }
}