package com.pascal.notes_java.model;

import android.content.res.Resources;

import com.pascal.notes_java.R;

import java.util.ArrayList;

public class CardsSourceImpl implements CardsSource {
    private final Resources resources;
    private ArrayList<CardData> dataSource;

    public CardsSourceImpl(Resources resources) {
        this.resources = resources;
        dataSource = new ArrayList(3);
    }

    public CardsSourceImpl init() {
        final String[] titles = resources.getStringArray(R.array.title);
        final String[] descriptions = resources.getStringArray(R.array.description);
        final String[] dates = resources.getStringArray(R.array.date);
        final String[] ids = resources.getStringArray(R.array.id);

        for (int i = 0; i < titles.length; i++) {
            dataSource.add(new CardData(
                            titles[i],
                            descriptions[i],
                            dates[i],
                            ids[i]
                    )
            );
        }
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}