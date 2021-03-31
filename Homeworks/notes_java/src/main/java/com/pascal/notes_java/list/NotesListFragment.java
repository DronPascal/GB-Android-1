package com.pascal.notes_java.list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.DataSource;


public class NotesListFragment extends Fragment implements AdapterCallback{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance(int columnCount) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new NotesListAdapter(this, DataSource.getNotesList()));
        }
        return view;
    }

    @Override
    public void openNote(String title, String description) {
        if (getActivity().getSupportFragmentManager().findFragmentById(R.id.container_notes_list) == null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_notes_list, NotesListFragment.newInstance(1))
                    .commit();
        }
    }
}