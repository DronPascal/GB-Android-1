package com.pascal.notes_java.list;

import android.view.View;

import com.pascal.notes_java.model.CardData;

public interface AdapterCallback {
    public void openNote(CardData cardData);

    public void registerForContextMenu(View view);
}
