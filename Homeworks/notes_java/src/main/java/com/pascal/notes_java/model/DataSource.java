package com.pascal.notes_java.model;

import java.util.ArrayList;
import java.util.Date;

public class DataSource {
    public static ArrayList<NoteModel> getNotesList() {
        ArrayList<NoteModel> notesList = new ArrayList<NoteModel>();
        notesList.add(new NoteModel("1", "a", new Date()));
        notesList.add(new NoteModel("2", "b", new Date()));
        notesList.add(new NoteModel("3", "c", new Date()));
        return notesList;
    }
}
