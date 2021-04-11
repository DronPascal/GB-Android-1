package com.pascal.notes_java.list;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pascal.notes_java.R;
import com.pascal.notes_java.model.DataSource;
import com.pascal.notes_java.note.NoteFragment;

import java.util.Objects;

public class NotesListFragment extends Fragment implements AdapterCallback {
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
        boolean isHorizontal = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        if (isHorizontal) {
            getActivity().findViewById(R.id.container_notes_list).setVisibility(View.GONE);
        } else {
            getActivity().findViewById(R.id.container_notes_list).setVisibility(View.VISIBLE);
        }
        int container = isHorizontal ? R.id.container_note : R.id.container_notes_list;
        fragmentManager
                .popBackStack();
        fragmentManager
                .beginTransaction()
                .replace(container, NoteFragment.newInstance(title, description))
                .addToBackStack(null)
                .commit();
    }
}