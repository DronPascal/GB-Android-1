package com.pascal.notes_java.model;

import java.util.HashMap;
import java.util.Map;

public class CardDataMapping {
    public static class Fields {
        public final static String TITLE = "title";
        public final static String DESCRIPTION = "description";
        public final static String DATE = "date";
        public final static String ID = "id";
    }

    public static CardData toCarData(String id, Map<String, Object> doc) {
        CardData answer = new CardData(
                (String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DESCRIPTION),
                (String) doc.get(Fields.DATE));
        answer.setId(id);
        return answer;
    }

    public static Object toDocument(CardData cardData) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, cardData.getTitle());
        answer.put(Fields.DESCRIPTION, cardData.getDescription());
        answer.put(Fields.DATE, cardData.getDate());
        return answer;
    }
}
