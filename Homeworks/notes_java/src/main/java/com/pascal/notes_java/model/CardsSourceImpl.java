package com.pascal.notes_java.model;

import android.content.res.Resources;

import com.pascal.notes_java.R;

import java.util.ArrayList;
import java.util.List;

public class CardsSourceImpl implements CardsSource {
    private final Resources resources;
    private List<CardData> dataSource;

    public CardsSourceImpl(Resources resources) {
        this.resources = resources;
        dataSource = new ArrayList(3);
    }

    @Override
    public CardsSource init(CardsSourceResponse cardsSourceResponse) {
        final String[] titles = resources.getStringArray(R.array.title);
        final String[] descriptions = resources.getStringArray(R.array.description);
        final String[] dates = resources.getStringArray(R.array.date);
        final String[] ids = resources.getStringArray(R.array.id);

        for (int i = 0; i < titles.length; i++) {
            CardData cardData = new CardData(
                    titles[i],
                    descriptions[i],
                    dates[i]);
            cardData.setId(ids[i]);
            dataSource.add(cardData);
        }

//        if (cardsSourceResponse != null)
//            cardsSourceResponse.initialized(this);
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

    @Override
    public void deleteCardData(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        dataSource.set(position, cardData);
    }

    @Override
    public void addCardData(CardData cardData) {
        dataSource.add(0, cardData);
    }

    @Override
    public void clearCardData() {
        dataSource.clear();
    }

    @Override
    public String getNewCardId() {
        return String.valueOf(size());
    }
}