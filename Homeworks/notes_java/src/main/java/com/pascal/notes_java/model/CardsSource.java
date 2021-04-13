package com.pascal.notes_java.model;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}