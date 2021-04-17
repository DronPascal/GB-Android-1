package com.pascal.notes_java.model;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements CardsSource {
    public static final String CARD_COLLECTION = "card";
    public static final String TAG = "CardsSourceFirebaseImpl";
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARD_COLLECTION);
    private List<CardData> cardsData = new ArrayList<>();

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        collection
                //.orderBy(CardDataMapping.Fields.DATE, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cardsData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + "==>" + document.getData());
                            Map<String, Object> doc = document.getData();

                            CardData cardData = CardDataMapping.toCarData(document.getId(), doc);
                            cardsData.add(cardData);
                        }
                        Log.d(TAG, "success " + cardsData.size() + " qnt");
                        cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                });
        return this;
    }

    @Override
    public CardData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void deleteCardData(int position) {
        collection.document(cardsData.get(position).getId())
                .delete();
        cardsData.remove(position);
    }

    @Override
    public void updateCardData(int position, CardData cardData) {
        String id = cardsData.get(position).getId();
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addCardData(CardData cardData) {
        collection
                .add(CardDataMapping.toDocument(cardData))
                .addOnSuccessListener(documentReference ->
                        cardData.setId(documentReference.getId()));
        cardsData.add(0, cardData);
    }

    @Override
    public void clearCardData() {
        for (CardData cardData : cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData.clear();
    }

    @Override
    public String getNewCardId() {
        return null;
    }
}
