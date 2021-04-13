package com.pascal.notes_java.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pascal.notes_java.R;
import com.pascal.notes_java.note.NoteFragment;
import com.pascal.notes_java.model.CardsSource;
import com.pascal.notes_java.model.CardsSourceImpl;

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
        CardsSource data = new CardsSourceImpl(getResources()).init();
        RecyclerView recyclerView = view.findViewById(R.id.notes_list);
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, CardsSource data) {
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
        }
        recyclerView.setAdapter(new NotesListAdapter(this, data));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        DividerItemDecoration itemDecoration2 = new DividerItemDecoration(getContext(), StaggeredGridLayoutManager.HORIZONTAL);
        itemDecoration2.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration2);
    }

    @Override
    public void openNote(String title, String description, String id) {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.container_main, NoteFragment.newInstance(title, description, id))
                .addToBackStack(null)
                .commit();
    }
}