package com.pascal.notes_java.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pascal.notes_java.R;


public class NoteFragment extends Fragment {
    private static final String ARGUMENT_TITLE = "title";
    private static final String ARGUMENT_DESCRIPTION = "description";

    private String mTitle;
    private String mDescription;

    private TextView mTextTitle;
    private TextView mTextDescription;

    public NoteFragment() {
    }


    public static NoteFragment newInstance(String title, String description) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_TITLE, title);
        args.putString(ARGUMENT_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARGUMENT_TITLE);
            mDescription = getArguments().getString(ARGUMENT_DESCRIPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        mTextTitle = view.findViewById(R.id.text_note_title);
        mTextDescription = view.findViewById(R.id.text_note_description);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextTitle.setText(mTitle);
        mTextDescription.setText(mDescription);
    }
}