package com.pascal.notes_java.list;

import android.view.View;

import com.pascal.notes_java.model.CardData;

public interface noteOpenerCallback {
    void registerForContextMenu(View view);

    void openNote(CardData cardData);
}
