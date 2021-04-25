package com.pascal.notes_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pascal.notes_java.list.NotesListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.container_notes_list) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_notes_list, NotesListFragment.newInstance(1))
                    .commit();
        }
    }
}